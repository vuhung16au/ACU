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
**Total Duration:** 10m 22s

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
- **Status:** COMPLETED
- **Updates:** Final refinement loop for Task 3 COMPLETED. 1. Updated the primary color scheme to Navy Blue (#3F51B5) for the Top Bar title, action icons, selected filter chips, recipe card footers, and the 'All' card to match image_3.png. 2. Fixed all missing images in MockRecipeDataSource.kt with reliable Unsplash URLs. 3. Redesigned the 'All' card with 'All' at TopStart and the total count '11' at BottomEnd, with a solid Navy Blue background. 4. Ensured true edge-to-edge display with transparent system bars. 5. Refined UI components like Top Bar, Filter Section, and Bottom Navigation to use the Navy Blue theme. Project builds successfully and matches the design in image_3.png.
- **Acceptance Criteria:**
  - Responsive grid layout is implemented
  - Recipe cards show image and title
  - The implemented UI must match the design provided in /Users/vuhung/00.Work/02.ACU/github/ITEC323/11.AndroidStudio-ProjectTypes/SugarGrid/input_images/image_3.png
- **Duration:** 1m 42s

### Task_4_FinalizeAndVerify: Create an adaptive app icon matching the dessert theme and perform a final verification of the application.
- **Status:** COMPLETED
- **Updates:** Performed final verification of the application. The application is stable and the core features are functional. The following have been verified: 1. Color Scheme: Updated to Navy Blue (#3F51B5) in Color.kt and Theme.kt to match image_3.png. 2. Recipe Images: All recipe cards in MockRecipeDataSource.kt now have valid Unsplash URLs. 3. 'All' Card Layout: Redesigned with 'All' at TopStart and the total count '11' at BottomEnd with a solid Navy Blue background. 4. Edge-to-Edge Display: Confirmed enableEdgeToEdge() in MainActivity.kt and transparent system bars in Theme.kt. 5. Adaptive App Icon: Created and verified. The project is now complete and meets all requirements.
- **Acceptance Criteria:**
  - Adaptive app icon is created
  - Project builds successfully
  - App does not crash
  - Existing tests pass
  - Application stability and UI alignment verified
- **Duration:** 6m 6s

