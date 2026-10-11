# PocketPay Demo (Android 9+)

A small Google Pay-inspired **practice-only** Android app. It has a demo wallet balance, quick actions, and local sample activity.

## Safety and scope
- This is not Google Pay, PhonePe, or a real payment app.
- It does not connect to a bank, UPI, camera, network, or payment gateway.
- No Android permissions are requested.
- All money amounts are simulated. Never enter bank credentials, UPI PINs, or card details.
- The project uses Android `minSdk 28` (Android 9).

## Build with GitHub Actions (no Android Studio required on your PC)
1. Create a new **public or private** GitHub repository.
2. Upload the contents of this folder to the repository root (the `app` and `.github` folders should be at the root).
3. Commit/push to `main` or `master`.
4. Open the repository's **Actions** tab and open **Build PocketPay Demo APK**.
5. Wait for the workflow to finish successfully.
6. In the workflow run's **Artifacts** section, download `PocketPayDemo-debug-apk`.
7. Extract the downloaded ZIP; it contains `app-debug.apk`.

## Install on your Android 9 phone
1. Transfer `app-debug.apk` to the phone using a USB cable or your own trusted file-transfer method.
2. Tap the APK in your phone's Files app.
3. If Android asks, allow **Install unknown apps** for that specific file app only (Settings > Apps & notifications > Special app access > Install unknown apps; wording differs by phone).
4. Return to the installer and tap Install.
5. After installation, turn off that file app's **Allow from this source** permission again if you enabled it.

## Build locally
Install JDK 17, Android Studio/Android SDK, and Gradle 8.7. From this folder, run:

```bash
gradle assembleDebug
```

The APK will be at `app/build/outputs/apk/debug/app-debug.apk`.

## Learning exercises
- Change the title and colors in `MainActivity.java`.
- Add a new sample transaction.
- Add a confirmation dialog before simulating a payment.
- Learn Android app state and persistence before adding a local database.
