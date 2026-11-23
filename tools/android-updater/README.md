# CrASHInk Updater for Android

Use a local Wi-Fi connection to install signed firmware on the Xteink X4 Pro. The reader only listens while **Receive updates** is open.

## First-time setup

1. Install the regular X4 Pro firmware `.bin` using **SD Card Firmware Update**. This installs the receiver and the public key it uses to verify future updates.
2. Install the CrASHInk Updater APK. Tap **Allow Downloads access** once so the app can read firmware downloaded by the browser.
3. Save a signed `crASHInk-*-wifi-x4pro.bin` in Android Downloads.

## Update

1. On the reader, open **Settings → System → Receive updates**. The reader connects to saved Wi-Fi and displays its IP. With no saved network, select Wi-Fi the first time.
2. On Android, enter the displayed IP and tap **Update**. The app remembers successful addresses and selects the most recently downloaded signed Wi-Fi build. Android shows transfer progress and an estimate.
3. Once the reader verifies the signature, digest and firmware image, it turns Wi-Fi off and installs. The reader shows flash progress and restarts on success. The phone's receipt acknowledges transfer and validation.

The receiving session expires after five minutes. The raw SD image and signed Wi-Fi image are two versions of the same firmware. Use the raw image for SD installation and the signed image for Android.

## Build the APK and sign firmware

With Android SDK Platform 35, JDK 17 and Gradle 8.11.1, run `gradle :app:assembleDebug :app:lintDebug`. The APK is in `app/build/outputs/apk/debug/`.

Create a signed Wi-Fi image using the private key matching the receiver's public key.

```sh
python3 sign-firmware.py firmware.bin crASHInk-example-wifi-x4pro.bin --key /path/to/firmware.pem
```

Keep the private key outside version control. It is not used on the phone or reader.

## GitHub Actions

Run **Build crASHink X4 Pro** on `main` to get both a regular SD `.bin` and a signed Wi-Fi `.bin`. Signing runs in a separate job and uses the repository Actions secret `CRASHINK_WIFI_SIGNING_KEY`. Its public key must match the one in the firmware patch. Builds dispatched from other branches produce the regular firmware only.
