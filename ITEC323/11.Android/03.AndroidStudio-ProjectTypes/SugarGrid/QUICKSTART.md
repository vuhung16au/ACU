# Quick Start Guide

Follow these steps to get **SugarGrid** up and running on your local machine.

## 🚀 Building the Project

### Using Android Studio (Recommended)
1. Open Android Studio Ladybug (or newer).
2. Select **File > Open** and navigate to the `SugarGrid` project folder.
3. Wait for the Gradle sync to complete successfully.
4. Click the **Run** button (green play icon) in the toolbar.

### Using Command Line (Gradle)
If you prefer the terminal, you can build the project using the Gradle wrapper:

```bash
# On macOS/Linux
./gradlew assembleDebug

# On Windows
gradlew.bat assembleDebug
```

## 📱 Running the App

### On an Emulator
1. Open the **Device Manager** in Android Studio.
2. Create and start an Android Virtual Device (AVD) running **API Level 34 or higher**.
3. Select the emulator from the run configuration dropdown and click **Run**.

### On a Physical Device
1. Enable **Developer Options** and **USB Debugging** on your Android device.
2. Connect your device via USB or Wi-Fi.
3. Select your device in Android Studio and click **Run**.

## ✨ Expected Output

Once the app starts, you should see:
- A vibrant **Navy Blue** header with "Recipe Keeper".
- A responsive grid of **11 dessert cards** with high-quality images.
- An orange/navy themed footer on each card showing the recipe name.
- A functional bottom navigation bar.
- A seamless **Edge-to-Edge** display where the content flows behind the system bars.

---
Happy Baking! 🍰
