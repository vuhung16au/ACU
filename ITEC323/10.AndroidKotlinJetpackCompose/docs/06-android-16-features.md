# Android 16 Baklava Features

## Overview

**Android 16 (Baklava)** - Released in 2025, API Level 36

Android 16 continues Google's focus on performance, privacy, and developer productivity. It builds upon the Material You design system and enhances support for foldable devices and large screens.

---

## Key Platform Features

### 1. Material Design 3 (Material You)

**Dynamic Color Theming**

Android 16 expands Material You with more customization options:

```kotlin
@Composable
fun App() {
    MaterialTheme(
        colorScheme = dynamicColorScheme(LocalContext.current)
    ) {
        // App content automatically uses system colors
    }
}
```

**Benefits**:
- Colors adapt to user's wallpaper
- Consistent across all apps
- Light/dark mode support built-in
- Improved accessibility

**Components**:
- `Card`, `TopAppBar`, `BottomAppBar`
- `Button`, `OutlinedButton`, `TextButton`
- `FloatingActionButton`
- `NavigationBar`, `NavigationRail`
- `Dialog`, `AlertDialog`

### 2. Performance Improvements

**Memory Efficiency**:
- 15% reduction in system memory usage
- Faster app startup times
- Improved garbage collection

**Graphics**:
- Hardware-accelerated rendering optimizations
- Better support for high refresh rate displays (up to 144Hz)
- Reduced jank in animations

**Battery**:
- More aggressive background app management
- Improved Doze mode
- Better detection of battery-draining apps

### 3. Privacy Enhancements

**Intent Redirection Protection**:
- Prevents malicious apps from hijacking intents
- Users get clear warning when app redirects to another app

**Improved Permission System**:
- Granular media permissions (photos vs videos)
- Temporary permission grants
- Better permission audit logs

**Privacy Dashboard**:
- See which apps accessed what permissions
- Timeline of permission usage
- Quick revoke access

---

## Developer Features

### 1. Jetpack Compose Improvements

**Performance**:
- 20% faster recomposition
- Better handling of large lists
- Improved animation performance

**New APIs**:
```kotlin
// Predictive back gesture
BackHandler(enabled = true) {
    // Handle back with animation preview
}

// Shared element transitions
SharedTransitionLayout {
    AnimatedContent(targetState = screen) { targetScreen ->
        when (targetScreen) {
            Screen.List -> ListScreen()
            Screen.Detail -> DetailScreen()
        }
    }
}
```

### 2. Room Database Enhancements

**Better Type Safety**:
```kotlin
@Database(version = 2)
abstract class AppDatabase : RoomDatabase() {
    // Automatic migration detection
    abstract fun studentDao(): StudentDao
}
```

**Performance**:
- Faster query execution
- Better indexing suggestions
- Improved full-text search

### 3. DataStore (Replaces SharedPreferences)

**Type-safe data storage**:

```kotlin
// Define schema
data class UserPreferences(
    val theme: Theme = Theme.SYSTEM,
    val notifications: Boolean = true
)

// Save data
suspend fun saveTheme(theme: Theme) {
    dataStore.updateData { preferences ->
        preferences.copy(theme = theme)
    }
}

// Read data
val themeFlow: Flow<Theme> = dataStore.data
    .map { it.theme }
```

**Benefits**:
- Coroutine-based (async by default)
- Type safety
- Better error handling
- Migration from SharedPreferences built-in

### 4. WorkManager Improvements

**Battery-efficient background tasks**:

```kotlin
val uploadWork = OneTimeWorkRequestBuilder<UploadWorker>()
    .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
    .setConstraints(
        Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    )
    .build()

WorkManager.getInstance(context).enqueue(uploadWork)
```

**Android 16 Enhancements**:
- Better scheduling algorithms
- Improved battery awareness
- More predictable execution

---

## Connectivity Features

### 1. 5G & Wi-Fi 7 Support

**Wi-Fi 7 (802.11be)**:
- Up to 46 Gbps theoretical speed
- Lower latency (< 10ms)
- Better performance in crowded areas

**5G Enhancements**:
- Network slicing support
- Better 5G standalone mode
- Improved handoff between 5G and Wi-Fi

**Usage in Apps**:
```kotlin
val connectivityManager = getSystemService(ConnectivityManager::class.java)
val network = connectivityManager.activeNetwork
val capabilities = connectivityManager.getNetworkCapabilities(network)

if (capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true) {
    // High-speed connection, load HD content
}
```

### 2. Improved WebView

**Chrome Engine Updates**:
- Latest HTML5/CSS3 support
- Better JavaScript performance
- Improved security sandbox

**Dark Mode Support**:
```kotlin
WebView(
    state = webViewState,
    modifier = Modifier.fillMaxSize(),
    onCreated = { webView ->
        // Force dark mode
        WebSettingsCompat.setForceDark(
            webView.settings,
            WebSettingsCompat.FORCE_DARK_ON
        )
    }
)
```

---

## Large Screen & Foldable Support

### Window Size Classes

```kotlin
@Composable
fun AdaptiveLayout() {
    val windowSizeClass = calculateWindowSizeClass(this)
    
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            // Phone in portrait
            SinglePaneLayout()
        }
        WindowWidthSizeClass.Medium -> {
            // Phone in landscape or small tablet
            TwoPaneLayout()
        }
        WindowWidthSizeClass.Expanded -> {
            // Large tablet or foldable unfolded
            ThreePaneLayout()
        }
    }
}
```

### Foldable Awareness

**Detect fold**:
```kotlin
val displayFeatures = calculateDisplayFeatures(activity)

displayFeatures.forEach { feature ->
    if (feature is FoldingFeature) {
        when (feature.state) {
            FoldingFeature.State.FLAT -> {
                // Device fully opened
            }
            FoldingFeature.State.HALF_OPENED -> {
                // Device at angle (tabletop mode)
            }
        }
    }
}
```

---

## Machine Learning Features

### On-Device ML

**ML Kit Integration**:
- Text recognition (OCR)
- Face detection
- Barcode scanning
- Smart reply suggestions

**Example**:
```kotlin
// Text recognition
val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

recognizer.process(inputImage)
    .addOnSuccessListener { visionText ->
        val detectedText = visionText.text
        println(detectedText)
    }
```

**TensorFlow Lite**:
- Improved performance
- Smaller model sizes
- GPU acceleration

---

## Notification Improvements

### Richer Notifications

```kotlin
val notification = NotificationCompat.Builder(context, CHANNEL_ID)
    .setSmallIcon(R.drawable.ic_notification)
    .setContentTitle("New Message")
    .setContentText("Hello from Android 16!")
    .setPriority(NotificationCompat.PRIORITY_HIGH)
    .addAction(
        R.drawable.ic_reply,
        "Reply",
        replyPendingIntent
    )
    .setStyle(
        NotificationCompat.MessagingStyle(user)
            .addMessage("Hello!", timestamp, sender)
    )
    .build()
```

**New Features**:
- Better conversation grouping
- Improved actions (reply inline)
- Media controls enhancements
- Custom notification layouts with Compose

---

## Development Tools

### Android Studio Updates

**Gemini AI Assistant**:
- Code generation from natural language
- Bug detection and fixes
- Code explanations
- Refactoring suggestions

**Live Edit**:
- See Compose changes instantly
- No need to rebuild app
- Works with running app on device/emulator

**Device Manager**:
- Faster emulator startup
- Better performance on Apple Silicon Macs
- Snapshot support for quick resets

**App Quality Insights**:
- Integrated Firebase Crashlytics
- ANR (App Not Responding) detection
- Performance monitoring

---

## Migration Considerations

### From Android 15 to Android 16

**Minimal Breaking Changes**:
- Most apps work without modification
- Target SDK update recommended: `targetSdk = 36`

**Gradle Configuration**:
```kotlin
android {
    compileSdk = 36
    
    defaultConfig {
        minSdk = 26  // Android 8.0
        targetSdk = 36  // Android 16
    }
}
```

### Deprecated Features

**SharedPreferences** → **DataStore**:
- Migration helper available
- Better performance
- Type safety

**LiveData** → **StateFlow**:
- Better Compose integration
- More Kotlin-idiomatic
- Improved coroutines support

---

## Week 10 Relevance

### What You'll Use

✅ **Material Design 3**: All projects use Material You components  
✅ **Jetpack Compose**: Declarative UI for all UIs  
✅ **ViewModel + StateFlow**: State management pattern  
✅ **Navigation Compose**: Multi-screen apps  
✅ **Room Database**: (Week 11) Local data persistence  

### What's Beyond Week 10

⏭️ **WorkManager**: Background tasks (Week 11)  
⏭️ **Retrofit**: Network API calls (Week 11)  
⏭️ **ML Kit**: Machine learning features (Advanced)  
⏭️ **Foldable Support**: Adaptive layouts (Advanced)  

---

## Key Takeaways

1. **Material Design 3**: Now standard for all Android apps
2. **Performance**: Faster, more efficient than ever
3. **Privacy**: User data protection is priority
4. **Compose**: Recommended UI toolkit, fully mature
5. **Large Screens**: First-class support for tablets/foldables
6. **On-Device ML**: Powerful AI features without cloud
7. **Modern APIs**: DataStore, StateFlow, Coroutines

---

## Resources

- [Android 16 Features](https://developer.android.com/about/versions/16)
- [Material Design 3](https://m3.material.io/)
- [Jetpack Compose Roadmap](https://developer.android.com/jetpack/androidx/compose-roadmap)
- [Android Developers Blog](https://android-developers.googleblog.com/)

---

**Next Steps**: Start with [01.HelloWorldKotlin](../01.HelloWorldKotlin/) project to build your first Android 16 app!

---

*Android 16 provides the best platform yet for building modern mobile apps!*
