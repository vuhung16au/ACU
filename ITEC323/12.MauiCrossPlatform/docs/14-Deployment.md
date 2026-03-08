# Deploying .NET MAUI Applications

## Deployment Overview

Publish your MAUI app to:
- **Google Play Store** (Android)
- **Apple App Store** (iOS)
- **Microsoft Store** (Windows)
- **Direct distribution** (sideloading)

## Build Configurations

### Debug vs Release

| Configuration | Purpose | Optimizations | Debugging |
|--------------|---------|---------------|-----------|
| **Debug** | Development | Disabled | Full symbols |
| **Release** | Production | Enabled | Minimal |

**Always deploy Release builds to stores!**

## Android Deployment

### 1. Generate Signing Key

Create a keystore for signing your APK/AAB:

```bash
keytool -genkey -v -keystore my-release-key.keystore \
  -alias my-key-alias \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000
```

**Save this file and password securely!** You'll need it for all updates.

### 2. Configure Signing

Edit `Platforms/Android/AndroidManifest.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android" 
          package="com.yourcompany.yourapp" 
          android:versionCode="1" 
          android:versionName="1.0.0">
    
    <uses-sdk android:minSdkVersion="24" android:targetSdkVersion="34" />
    
    <application 
        android:label="Your App"
        android:icon="@mipmap/appicon">
    </application>
</manifest>
```

### 3. Build Release AAB

**AAB (Android App Bundle)** is required for Play Store:

```bash
dotnet publish -f net8.0-android -c Release \
  -p:AndroidPackageFormat=aab \
  -p:AndroidKeyStore=true \
  -p:AndroidSigningKeyStore=my-release-key.keystore \
  -p:AndroidSigningKeyAlias=my-key-alias \
  -p:AndroidSigningKeyPass=YOUR_KEY_PASSWORD \
  -p:AndroidSigningStorePass=YOUR_STORE_PASSWORD
```

**Output**: `bin/Release/net8.0-android/publish/com.yourcompany.yourapp-Signed.aab`

### 4. Build APK (Sideloading)

For direct distribution:

```bash
dotnet publish -f net8.0-android -c Release
```

**Output**: `bin/Release/net8.0-android/publish/com.yourcompany.yourapp-Signed.apk`

### 5. Google Play Store Submission

1. **Create Google Play Console account**: https://play.google.com/console
2. **Create new app**: Select language, name, category
3. **Upload AAB**: Production → Create new release → Upload
4. **Fill store listing**: Description, screenshots, icon
5. **Set pricing**: Free or Paid
6. **Submit for review**: Takes 1-7 days

**Requirements**:
- Privacy policy URL
- Screenshots (phone, tablet, optional)
- Feature graphic (1024x500)
- App icon (512x512)

## iOS Deployment

### 1. Apple Developer Account

**Required**: Apple Developer Program ($99/year)
- Sign up: https://developer.apple.com

### 2. Certificates and Provisioning (Visual Studio Mac)

Visual Studio handles this automatically:

1. **Preferences → Apple Developer Accounts**
2. Add your Apple ID
3. Click **View Details** → Download profiles

Or manually:
- Create App ID in Apple Developer portal
- Create Distribution Certificate
- Create Provisioning Profile

### 3. Configure App Info

Edit `Platforms/iOS/Info.plist`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<plist version="1.0">
<dict>
    <key>CFBundleDisplayName</key>
    <string>Your App</string>
    
    <key>CFBundleIdentifier</key>
    <string>com.yourcompany.yourapp</string>
    
    <key>CFBundleVersion</key>
    <string>1</string>
    
    <key>CFBundleShortVersionString</key>
    <string>1.0.0</string>
    
    <!-- Required permissions -->
    <key>NSCameraUsageDescription</key>
    <string>This app needs camera access</string>
</dict>
</plist>
```

### 4. Build Release IPA

**Command Line**:
```bash
dotnet publish -f net8.0-ios -c Release
```

**Visual Studio**: 
1. Set configuration to **Release**
2. Select target: **Generic Device**
3. Build → Archive for Publishing
4. Sign and Distribute

**Output**: `.ipa` file for App Store submission

### 5. App Store Connect Submission

1. **App Store Connect**: https://appstoreconnect.apple.com
2. **Create new app**: Select bundle ID, name
3. **Upload build**: Use Transporter app or Xcode
4. **Fill app information**: Description, screenshots, keywords
5. **Select build**: Choose uploaded build
6. **Submit for review**: Takes 1-3 days

**Requirements**:
- App icon (1024x1024, no alpha)
- Screenshots (all required device sizes)
- Privacy policy URL
- App demo video (optional)
- Test account credentials (if login required)

## Windows Deployment

### 1. Configure App

Edit `Platforms/Windows/Package.appxmanifest`:

```xml
<Identity Name="YourCompany.YourApp" 
          Publisher="CN=YourCompany" 
          Version="1.0.0.0" />

<Properties>
    <DisplayName>Your App</DisplayName>
    <PublisherDisplayName>Your Company</PublisherDisplayName>
</Properties>
```

### 2. Build Release Package

```bash
dotnet publish -f net8.0-windows10.0.19041.0 -c Release \
  -p:GenerateAppxPackageOnBuild=true
```

**Output**: `bin/Release/net8.0-windows10.0.19041.0/publish/`

### 3. Microsoft Store Submission

1. **Partner Center**: https://partner.microsoft.com
2. **Create app**: Reserve name
3. **Upload package**: Upload `.msix` or `.appx`
4. **Fill store listing**: Description, screenshots
5. **Submit**: Review takes 1-3 days

### 4. Sideloading (Direct Install)

For enterprise/internal distribution:

1. Build package
2. Right-click `.msix` → Install
3. Or use PowerShell: `Add-AppxPackage -Path "app.msix"`

## Version Management

### Update Version Number

**Android** (`AndroidManifest.xml`):
```xml
<manifest android:versionCode="2" android:versionName="1.1.0">
```
- `versionCode`: Integer, must increase (1, 2, 3...)
- `versionName`: Display version (1.0.0, 1.1.0, 2.0.0)

**iOS** (`Info.plist`):
```xml
<key>CFBundleVersion</key>
<string>2</string>

<key>CFBundleShortVersionString</key>
<string>1.1.0</string>
```

**Windows** (`Package.appxmanifest`):
```xml
<Identity Version="1.1.0.0" />
```

### Semantic Versioning

Follow **MAJOR.MINOR.PATCH**:
- `1.0.0` → Initial release
- `1.0.1` → Bug fix (PATCH)
- `1.1.0` → New feature (MINOR)
- `2.0.0` → Breaking change (MAJOR)

## App Icons

### Generate Icons

Use **app icon generator** or create manually:

**Android**: `Resources/AppIcon/appicon.svg`
**iOS**: `Resources/AppIcon/appicon.svg`
**Windows**: `Resources/AppIcon/appicon.svg`

MAUI auto-generates all sizes from SVG!

### Manual Icon Sizes

**Android** (`Resources/mipmap-*/ic_launcher.png`):
- `mipmap-mdpi`: 48x48
- `mipmap-hdpi`: 72x72
- `mipmap-xhdpi`: 96x96
- `mipmap-xxhdpi`: 144x144
- `mipmap-xxxhdpi`: 192x192

**iOS** (`Assets.xcassets/AppIcon.appiconset`):
- Multiple sizes from 20x20 to 1024x1024

## Splash Screen

**MAUI Splash Screen** (`Resources/Splash/splash.svg`):

```xml
<MauiSplashScreen Include="Resources\Splash\splash.svg" 
                  Color="#512BD4" 
                  BaseSize="128,128" />
```

Auto-generates for all platforms!

## Performance Optimization

### Enable AOT (Ahead-of-Time Compilation)

**Android** (`.csproj`):
```xml
<PropertyGroup Condition="'$(Configuration)' == 'Release'">
    <AndroidEnableProfiledAot>true</AndroidEnableProfiledAot>
</PropertyGroup>
```

**iOS** (always AOT by default)

### Enable Trimming

Reduce app size by removing unused code:

```xml
<PropertyGroup>
    <PublishTrimmed>true</PublishTrimmed>
</PropertyGroup>
```

⚠️ **Test thoroughly!** Trimming can break reflection-based code.

## Testing Before Release

### Checklist

- [ ] Test on multiple devices (phone, tablet)
- [ ] Test on different OS versions
- [ ] Test offline functionality
- [ ] Test all permissions
- [ ] Test in-app purchases (if applicable)
- [ ] Check memory usage (no leaks)
- [ ] Verify all screens/features work
- [ ] Check app icon and splash screen
- [ ] Test deep linking/notifications
- [ ] Review store listing (description, screenshots)

### Beta Testing

**Android**: Google Play Console → Testing → Internal/Closed testing
**iOS**: TestFlight (bundled with App Store Connect)
**Windows**: Microsoft Store → Private audience

## Store Optimization (ASO)

### App Title
- Keep it short (25-30 characters)
- Include main keyword
- Make it memorable

### Description
- First 3 lines are critical (visible without expanding)
- List key features with bullet points
- Include relevant keywords naturally
- Add social proof (users, ratings)

### Screenshots
- Show key features (5-8 screenshots)
- Use captions to explain features
- Show the app in action
- Use device frames for polish

### Keywords (iOS)
- 100 characters max
- Comma-separated
- Research popular keywords
- Don't repeat words

## Common Deployment Issues

| Issue | Solution |
|-------|----------|
| **Signing error (Android)** | Check keystore path and passwords |
| **Provisioning error (iOS)** | Refresh profiles in Xcode/VS |
| **App crashes on launch** | Check for missing permissions in manifest |
| **Large app size** | Enable trimming and AOT |
| **Store rejection** | Read review feedback, fix issues |
| **Version conflict** | Increment version code/number |

## Post-Release

### Monitor Crashes
- **Android**: Google Play Console → Quality
- **iOS**: App Store Connect → Analytics → Crashes
- **Windows**: Partner Center → Analytics

### Update Strategy
- **Bug fixes**: Release ASAP
- **Features**: Monthly or quarterly
- **Major updates**: Plan marketing

### Analytics
Consider adding analytics:
- App Center
- Firebase Analytics
- Google Analytics for Firebase

## Deployment Scripts

### Android Release Script
```bash
#!/bin/bash
# build-android-release.sh

VERSION_CODE=1
VERSION_NAME="1.0.0"

dotnet publish -f net8.0-android -c Release \
  -p:AndroidPackageFormat=aab \
  -p:ApplicationVersion=$VERSION_CODE \
  -p:ApplicationDisplayVersion=$VERSION_NAME \
  -p:AndroidKeyStore=true \
  -p:AndroidSigningKeyStore=my-release-key.keystore \
  -p:AndroidSigningKeyAlias=my-key-alias \
  -p:AndroidSigningKeyPass=$ANDROID_KEY_PASSWORD \
  -p:AndroidSigningStorePass=$ANDROID_STORE_PASSWORD

echo "Build complete: bin/Release/net8.0-android/publish/"
```

### iOS Release Script
```bash
#!/bin/bash
# build-ios-release.sh

VERSION="1.0.0"
BUILD_NUMBER=1

dotnet publish -f net8.0-ios -c Release \
  -p:ApplicationVersion=$BUILD_NUMBER \
  -p:ApplicationDisplayVersion=$VERSION

echo "Build complete. Archive in Visual Studio for publishing."
```

## Key Takeaways

- **Android**: Generate keystore, build AAB for Play Store
- **iOS**: Need Apple Developer account ($99/year)
- **Windows**: Build MSIX for Microsoft Store
- Always test Release builds before submission
- Increment version code/number for updates
- Optimize with AOT and trimming
- Use semantic versioning (MAJOR.MINOR.PATCH)
- Beta test before public release
- Monitor crashes and reviews post-launch
- Keep signing keys secure (backup!)

## Next Steps

- Build your [06.ComprehensiveTaskListApp](../06.ComprehensiveTaskListApp/)
- Prepare store listings (description, screenshots)
- Set up beta testing with real users
- Learn about app monetization (if needed)
- Explore CI/CD for automated deployments
