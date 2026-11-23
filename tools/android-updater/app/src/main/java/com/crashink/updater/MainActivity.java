package com.crashink.updater;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public final class MainActivity extends Activity {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final java.util.ArrayList<String> recent = new java.util.ArrayList<>();
    private AutoCompleteTextView address;
    private ArrayAdapter<String> addresses;
    private boolean busy;
    private Button update;
    private Button history;
    private TextView status;
    private ProgressBar bar;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        String previous = getPreferences(MODE_PRIVATE).getString("recent", "");
        if (!previous.isEmpty()) recent.addAll(java.util.Arrays.asList(previous.split(",")));
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        int padding = Math.round(24 * getResources().getDisplayMetrics().density);
        layout.setPadding(padding, padding, padding, padding);
        TextView title = new TextView(this);
        title.setText("CrASHInk Updater");
        title.setTextSize(28);
        layout.addView(title);
        address = new AutoCompleteTextView(this);
        address.setHint("IP shown in Receive updates");
        address.setSingleLine(true);
        address.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
        addresses = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                new java.util.ArrayList<>(recent));
        address.setAdapter(addresses);
        if (!recent.isEmpty()) address.setText(recent.get(0), false);
        layout.addView(address);
        history = new Button(this);
        history.setText("Recent IP addresses");
        history.setEnabled(!recent.isEmpty());
        history.setOnClickListener(v -> new android.app.AlertDialog.Builder(this)
                .setTitle("Recent IP addresses")
                .setItems(recent.toArray(new String[0]), (dialog, index) -> address.setText(recent.get(index), false))
                .show());
        layout.addView(history);
        Button access = new Button(this);
        access.setText("Allow Downloads access");
        access.setOnClickListener(v -> requestDownloads());
        layout.addView(access);
        update = new Button(this);
        update.setText("Update");
        update.setOnClickListener(v -> startUpdate());
        layout.addView(update);
        bar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        bar.setMax(100);
        layout.addView(bar);
        status = new TextView(this);
        status.setAccessibilityLiveRegion(android.view.View.ACCESSIBILITY_LIVE_REGION_POLITE);
        layout.addView(status);
        setContentView(layout);

    }

    private void rememberAddress(String value) {
        recent.remove(value);
        recent.add(0, value);
        if (recent.size() > 8) recent.remove(recent.size() - 1);
        getPreferences(MODE_PRIVATE).edit().putString("recent", String.join(",", recent)).apply();
        addresses.clear();
        addresses.addAll(recent);
        history.setEnabled(true);
    }

    private boolean canReadDownloads() {
        if (Build.VERSION.SDK_INT >= 30) return Environment.isExternalStorageManager();
        return checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestDownloads() {
        if (canReadDownloads()) {
            status.setText("Downloads access is already enabled.");
        } else if (Build.VERSION.SDK_INT >= 30) {
            startActivity(new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    Uri.parse("package:" + getPackageName())));
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }
    }

    private void startUpdate() {
        if (busy) return;
        if (!canReadDownloads()) {
            requestDownloads();
            return;
        }
        String ip = address.getText().toString().trim();
        String[] octets = ip.split("\\.", -1);
        if (octets.length != 4 || !java.util.Arrays.stream(octets).allMatch(part ->
                part.matches("[0-9]{1,3}") && Integer.parseInt(part) <= 255)) {
            status.setText("Enter the IPv4 address shown on the X4 Pro.");
            return;
        }
        URL target;
        try {
            target = new URL("http", ip, 8081, "/");
        } catch (Exception invalid) {
            status.setText("Reader IP address is invalid.");
            return;
        }
        busy = true;
        update.setEnabled(false);
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        executor.execute(() -> {
            try {
                File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File[] files = folder.listFiles(file -> file.isFile() && file.getName().toLowerCase(Locale.ROOT)
                        .matches("crashink-.*-wifi-x4pro\\.bin"));
                File newest = null;
                if (files != null) {
                    for (File file : files) {
                        if (newest == null || file.lastModified() > newest.lastModified()
                                || (file.lastModified() == newest.lastModified()
                                    && file.getName().compareTo(newest.getName()) > 0)) newest = file;
                    }
                }
                if (newest == null) throw new IllegalStateException("No signed CrASHInk Wi-Fi firmware in Downloads.");
                File selected = newest;
                FirmwareClient.install(target, selected,
                        (stage, done, total, started) -> showProgress(selected.getName(), stage, done, total, started));
                runOnUiThread(() -> rememberAddress(ip));
            } catch (Exception error) {
                runOnUiThread(() -> {
                    bar.setIndeterminate(false);
                    status.setText(error.getMessage() == null ? "Update failed." : error.getMessage());
                });
            } finally {
                runOnUiThread(() -> {
                    busy = false;
                    update.setEnabled(true);
                    getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                });
            }
        });
    }

    private void showProgress(String file, String stage, long done, long total, long started) {
        long elapsed = started <= 0 ? 0 : SystemClock.elapsedRealtime() - started;
        int percent = total > 0 ? (int) Math.min(100, done * 100 / total) : 0;
        String estimate = "";
        if (elapsed >= 1000 && done > 0 && done < total) {
            long seconds = Math.max(1, Math.round((total - done) * (elapsed / 1000.0) / done));
            estimate = seconds >= 60
                    ? String.format(Locale.ROOT, " · about %d min %02d sec left", seconds / 60, seconds % 60)
                    : String.format(Locale.ROOT, " · about %d sec left", seconds);
        }
        String details = total > 0 && started > 0 ? " " + percent + "%" + estimate : "";
        runOnUiThread(() -> {
            bar.setIndeterminate(total == 0);
            if (total > 0) bar.setProgress(percent);
            status.setText(file + "\n" + stage + details);
        });
    }

    @Override protected void onDestroy() {
        executor.shutdown();
        super.onDestroy();
    }
}
