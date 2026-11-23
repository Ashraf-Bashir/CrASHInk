package com.crashink.updater;

import android.os.SystemClock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Locale;


final class FirmwareClient {
    interface Progress { void update(String label, long done, long total, long started); }

    static String hex(byte[] bytes) {
        char[] output = new char[bytes.length * 2];
        String digits = "0123456789abcdef";
        for (int i = 0; i < bytes.length; ++i) {
            output[i * 2] = digits.charAt((bytes[i] & 255) >> 4);
            output[i * 2 + 1] = digits.charAt(bytes[i] & 15);
        }
        return new String(output);
    }

    static void install(URL base, File file, Progress progress) throws Exception {
        final byte[] marker = "CRASHOTA1".getBytes(StandardCharsets.US_ASCII);
        byte[] signature = new byte[256];
        byte[] suffix = new byte[marker.length];
        long total;
        try (RandomAccessFile input = new RandomAccessFile(file, "r")) {
            total = input.length() - signature.length - suffix.length;
            if (total < 24 || total > 6553600 || input.read() != 0xe9)
                throw new IOException("This file is not a signed X4 Pro Wi-Fi firmware.");
            input.seek(total);
            input.readFully(signature);
            input.readFully(suffix);
        }
        if (!java.util.Arrays.equals(marker, suffix))
            throw new IOException("This file has no CrASHInk Wi-Fi signature.");

        byte[] buffer = new byte[16384];
        MessageDigest hasher = MessageDigest.getInstance("SHA-256");
        progress.update("Checking firmware file", 0, 0, 0);
        try (InputStream input = new FileInputStream(file)) {
            long remaining = total;
            while (remaining > 0) {
                int count = input.read(buffer, 0, (int) Math.min(remaining, buffer.length));
                if (count < 0) throw new IOException("Firmware file changed while reading.");
                hasher.update(buffer, 0, count);
                remaining -= count;
            }
        }
        String digest = hex(hasher.digest());

        HttpURLConnection probe = connect(new URL(base, "/ota/status"));
        try (BufferedReader reader = reader(probe)) {
            if (!"crashink-receive-v3".equals(reader.readLine()))
                throw new IOException("Open Receive updates on the reader and install the compatible reader firmware.");
        } finally {
            probe.disconnect();
        }

        HttpURLConnection update = connect(new URL(base, "/ota/install"));
        update.setRequestMethod("POST");
        update.setRequestProperty("Content-Type", "application/octet-stream");
        update.setRequestProperty("X-Crashink-SHA256", digest);
        update.setRequestProperty("X-Crashink-Signature", hex(signature));
        update.setDoOutput(true);
        update.setFixedLengthStreamingMode(total);
        try {
            long start = SystemClock.elapsedRealtime();
            try (InputStream input = new FileInputStream(file); OutputStream out = update.getOutputStream()) {
                long sent = 0;
                long last = 0;
                while (sent < total) {
                    int count = input.read(buffer, 0, (int) Math.min(total - sent, buffer.length));
                    if (count < 0) throw new IOException("Firmware file changed during transfer.");
                    out.write(buffer, 0, count);
                    sent += count;
                    long now = SystemClock.elapsedRealtime();
                    if (now - last >= 250 || sent == total) {
                        progress.update("Sending firmware", sent, total, start);
                        last = now;
                    }
                }
            }
            progress.update("Reader is validating firmware", 0, 0, 0);
            int status = update.getResponseCode();
            if (status != 200) {
                String detail = "";
                InputStream error = update.getErrorStream();
                if (error != null) {
                    try (BufferedReader response = new BufferedReader(
                            new InputStreamReader(error, StandardCharsets.US_ASCII))) {
                        String line = response.readLine();
                        if (line != null && line.length() <= 80) detail = " " + line;
                    }
                }
                throw new IOException(String.format(Locale.ROOT,
                        "Reader rejected the firmware (HTTP %d).%s", status, detail));
            }
            try (BufferedReader response = reader(update)) {
                if (!"received".equals(response.readLine()))
                    throw new IOException("Connection ended before the reader confirmed receipt.");
            }
            progress.update("Firmware received. The reader installs offline. Watch its screen.", 1, 1, 0);
        } finally {
            update.disconnect();
        }
    }

    private static HttpURLConnection connect(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(java.net.Proxy.NO_PROXY);
        connection.setInstanceFollowRedirects(false);
        connection.setConnectTimeout(8000);
        connection.setReadTimeout(120000);
        return connection;
    }

    private static BufferedReader reader(HttpURLConnection connection) throws IOException {
        return new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.US_ASCII));
    }
}
