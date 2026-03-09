# Project Plan

Create a "Pomodoro"-style Focus Timer application called "CreateWithAI-Pomodoro" using Jetpack Compose. The UI should be modern, using Material 3 guidelines. Use a light-red ("tomato") color motif. Allow the user to toggle between "Focus" (25 min), "Short Break" (5 min), and "Long Break" (15 min) using a Segmented Button row. Display a large countdown timer (MM:SS) in the center of the screen, surrounded by a circular progress bar. Provide a large "Play/Pause" button and a smaller "Reset" button. When the timer hits 00:00, show a simple snackbar and play a default system notification sound.

## Project Brief

# Project Brief: CreateWithAI-Pomodoro

## Features
- **Session Switching**: A Segmented Button row to quickly toggle between "Focus" (25 min), "Short Break" (5 min), and "Long Break" (15 min) modes.
- **Visual Countdown**: A high-visibility digital timer (MM:SS) centered within a circular progress bar that provides a real-time visual representation of elapsed time.
- **Intuitive Controls**: Accessible playback controls including a large "Play/Pause" toggle and a "Reset" button for session management.
- **Smart Completion Alerts**: Immediate feedback via a Material 3 Snackbar and a system notification sound once the countdown reaches 00:00.

## High-Level Technical Stack
- **Kotlin**: The primary language for modern, concise, and safe Android development.
- **Jetpack Compose**: A modern declarative UI toolkit used to build the responsive Material 3 interface.
- **Material 3 (M3)**: Implementation of the latest Material Design guidelines with a custom "tomato" red color scheme and full edge-to-edge support.
- **Kotlin Coroutines**: Essential for managing the countdown timer logic and UI state updates efficiently in the background.
- **KSP (Kotlin Symbol Processing)**: Optimized code generation for architectural components and dependency management.

## UI Design Image
![UI Design](file:///Users/vuhung/00.Work/02.ACU/github/ITEC323/11.AndroidStudio-ProjectTypes/CreateWithAIPomodoro/input_images/image_0.png)
Image path = file:///Users/vuhung/00.Work/02.ACU/github/ITEC323/11.AndroidStudio-ProjectTypes/CreateWithAIPomodoro/input_images/image_0.png

## Implementation Steps
**Total Duration:** 9m 14s

### Task_1_Setup_Theme_And_Icons: Implement the Material 3 'tomato' red theme, enable edge-to-edge support, and create an adaptive app icon matching the focus timer theme.
- **Status:** COMPLETED
- **Updates:** Successfully implemented the Material 3 'tomato' red theme, enabled edge-to-edge support, and created a custom adaptive app icon for the CreateWithAI-Pomodoro application.
- **Acceptance Criteria:**
  - Material 3 theme with tomato red color scheme implemented
  - Edge-to-edge display enabled
  - Adaptive app icon created and applied
- **Duration:** 7m 47s

### Task_2_Timer_Core_Logic: Implement the TimerViewModel using Kotlin Coroutines to manage the countdown logic, providing states for 'Focus' (25m), 'Short Break' (5m), and 'Long Break' (15m) modes, and handling play/pause/reset actions.
- **Status:** COMPLETED
- **Updates:** Successfully implemented the TimerViewModel for the CreateWithAI-Pomodoro application.
- **Acceptance Criteria:**
  - Timer logic correctly handles countdown in all three modes
  - Play, Pause, and Reset functionality works in the ViewModel
  - State is preserved appropriately
- **Duration:** 35s

### Task_3_Main_UI_Implementation: Build the main UI using Jetpack Compose, featuring the Segmented Button row for mode switching, a central circular progress bar with MM:SS text, and control buttons.
- **Status:** COMPLETED
- **Updates:** Successfully implemented the main UI for the CreateWithAI-Pomodoro application, fulfilling all the requirements and matching the design specifications.
Key Implementation Details:
1. Main UI Structure (PomodoroScreen.kt):
   - Segmented Button Row: Implemented using Material 3 SingleChoiceSegmentedButtonRow and SegmentedButton for seamless switching between Focus, Short Break, and Long Break modes.
   - Circular Progress Timer: Created a custom timer display using a Box containing a CircularProgressIndicator and a centered Text component. The progress is smoothly animated using animateFloatAsState.
   - Intuitive Controls: A Large Floating Action Button (FAB) acts as the primary Play/Pause toggle. A secondary IconButton with a refresh icon handles the timer reset functionality.
2. Integration with ViewModel: The UI is fully reactive, observing states from TimerViewModel using collectAsState. Time formatting is handled via the formatTime helper method, ensuring a clean MM:SS display.
3. Completion Feedback: Implemented a LaunchedEffect that listens to onTimerFinished events. When the timer ends: A System Notification Sound is played using RingtoneManager. A Material 3 Snackbar is displayed to inform the user that the session is complete.
4. Theming and Edge-to-Edge: The UI strictly follows the custom "tomato" red theme (Material 3). MainActivity.kt uses enableEdgeToEdge(), and the PomodoroScreen uses Scaffold with innerPadding to ensure correct layout on all devices.
5. Verification: The code compiles perfectly as confirmed by a successful Gradle build (:app:assembleDebug). A @Preview function is included for quick visual iteration.
Task_3_Main_UI_Implementation is now complete, and with it, the entire project "CreateWithAI-Pomodoro" is ready.
- **Acceptance Criteria:**
  - UI matches the design in file:///Users/vuhung/00.Work/02.ACU/github/ITEC323/11.AndroidStudio-ProjectTypes/CreateWithAIPomodoro/input_images/image_0.png
  - Segmented buttons correctly switch timer modes
  - Circular progress bar updates in sync with the countdown
- **Duration:** 52s

### Task_4_Feedback_And_Verification: Integrate completion alerts (Snackbar and system notification sound) when the timer reaches 00:00. Perform a final run and verify the application stability and requirements.
- **Status:** IN_PROGRESS
- **Acceptance Criteria:**
  - Material 3 Snackbar appears when timer hits zero
  - Default system notification sound plays on completion
  - App builds and runs successfully without crashes
  - All existing tests pass
  - Critical UI alignment with user requirements confirmed by critic_agent
- **StartTime:** 2026-03-09 17:36:55 AEDT

