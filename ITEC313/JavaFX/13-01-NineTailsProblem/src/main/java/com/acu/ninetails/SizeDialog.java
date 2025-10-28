package com.acu.ninetails;

import javafx.scene.control.ChoiceDialog;

import java.util.Arrays;

/** Utility dialog to choose a new board size. */
final class SizeDialog {
    private SizeDialog() {}

    static int ask(int current) {
        Integer[] options = {2, 3, 4, 5};
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(current, Arrays.asList(options));
        dialog.setTitle("Choose Board Size");
        dialog.setHeaderText(null);
        dialog.setContentText("Size (N x N):");
        return dialog.showAndWait().orElse(current);
    }
}


