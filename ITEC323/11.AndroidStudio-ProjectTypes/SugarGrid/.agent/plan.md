# Project Plan

SugarGrid: A Dessert Recipes application using Jetpack Compose. 
Key features:
- Large-screen friendly, utilizing a List-Detail flow suitable for tablets and phones.
- Home screen: A grid of recipe cards, showing the recipe image and title.
- Detail screen: Tapping a recipe opens a detail view with a large "hero" image at the top, ingredients and cooking instructions below it.
- Phase 1 focus: Only implement the home screen. Leave other features and screens blank.
- UI: Material Design 3, vibrant and energetic color scheme, adaptive app icon, full edge-to-edge display.

## Project Brief

# SugarGrid Project Brief

SugarGrid is a modern, vibrant dessert recipe application designed to provide a seamless browsing experience across all Android form factors. The app emphasizes visual appeal and ease of use, utilizing a responsive grid layout to showcase delicious treats.

## Features

- **Dynamic Recipe Grid**: A visually engaging home screen featuring a responsive grid of dessert recipe cards with high-quality images and titles.
- **Adaptive List-Detail Flow**: A robust architecture optimized for both phone and tablet displays, ensuring a professional look on any screen size.
- **Rich Recipe Details**: Comprehensive recipe views that include a prominent "hero" image, organized ingredient lists, and clear cooking instructions.
- **Modern Material 3 Design**: A vibrant and energetic UI implementation featuring full edge-to-edge display support and an adaptive app icon.

## High-Level Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material Design 3)
- **Concurrency**: Kotlin Coroutines
- **Image Loading**: Coil
- **Annotation Processing**: KSP (Kotlin Symbol Processing)
- **Architecture**: MVVM (Model-View-ViewModel) with Jetpack Lifecycle & Navigation Compose

## Implementation Steps
**Total Duration:** 4m 16s

### Task_1_SetupThemeAndModel: Configure Material 3 theme with a vibrant energetic color scheme (warm/orange tones), enable edge-to-edge display in MainActivity, and define the Recipe data model.
- **Status:** COMPLETED
- **Updates:** Configured Material 3 theme with vibrant orange/warm tones in Color.kt and Theme.kt. Enabled edge-to-edge display in MainActivity.kt and defined the Recipe data model (id, title, imageUrl) in a new model package. Created an adaptive app icon matching the SugarGrid brand identity.
- **Acceptance Criteria:**
  - Material 3 theme is configured with vibrant colors
  - Edge-to-edge display is enabled
  - Recipe data model (id, title, imageUrl) is created
- **Duration:** 1m 54s

### Task_2_DataAndViewModel: Implement the data layer with mock dessert recipes and a ViewModel to provide the state for the home screen.
- **Status:** COMPLETED
- **Updates:** Implemented MockRecipeDataSource in com.acu.sugargrid.data and RecipeViewModel in com.acu.sugargrid.ui. The RecipeViewModel uses a StateFlow to expose a RecipeUiState, which can be observed by the UI to display loading, success, or error states. Populated the mock data with 8 dessert recipes.
- **Acceptance Criteria:**
  - Mock recipe data is available
  - RecipeViewModel exposes a list of recipes
- **Duration:** 40s

### Task_3_HomeScreenUI: Build the Home Screen featuring a responsive grid of recipe cards using LazyVerticalGrid and Coil for image loading.
- **Status:** IN_PROGRESS
- **Updates:** Reopening Task 3 to address UI discrepancies identified during final verification. Issues to fix: 1. Change the color scheme from orange to blue/navy to match image_3.png. 2. Fix missing images (gray placeholders) by ensuring valid URLs or providing fallbacks. 3. Update 'All' card color and layout to match the reference image. 4. Ensure true edge-to-edge display by setting transparent backgrounds for system bars.
- **Acceptance Criteria:**
  - Responsive grid layout is implemented
  - Recipe cards show image and title
  - The implemented UI must match the design provided in /Users/vuhung/00.Work/02.ACU/github/ITEC323/11.AndroidStudio-ProjectTypes/SugarGrid/input_images/image_3.png
- **Duration:** 1m 42s

### Task_4_FinalizeAndVerify: Create an adaptive app icon matching the dessert theme and perform a final verification of the application.
- **Status:** IN_PROGRESS
- **Updates:** Performed final verification of the application. The application is stable and the core features (Home Screen with Recipe Grid) are implemented and functional. However, there are critical UI discrepancies regarding the color scheme and missing images. The application uses a vibrant orange color scheme, but the reference image (image_3.png) displays a blue/navy theme for the selected chips, the 'All' card, and top bar elements. Some recipe cards are displaying gray placeholders instead of recipe images. The 'All' card color is orange, while the reference is blue. The system bars appear with an opaque white background. I will trigger a refinement loop to address these issues.
- **Acceptance Criteria:**
  - Adaptive app icon is created
  - Project builds successfully
  - App does not crash
  - Existing tests pass
  - Application stability and UI alignment verified
- **StartTime:** 2026-03-09 17:58:58 AEDT

