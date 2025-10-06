package com.acu.games.slotmachine.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.acu.games.slotmachine.SlotMachineApp;
import javafx.scene.input.MouseEvent;

public class LeverComponent extends Component {

    private ViewComponent view;
    private String currentTextureName = "lever0.png";

    @Override
    public void onAdded() {
        view.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> trigger());
    }

    public void trigger() {
        if (FXGL.<SlotMachineApp>getAppCast().isMachineSpinning())
            return;

        currentTextureName = currentTextureName.equals("lever0.png") ? "lever1.png" : "lever0.png";

        view.clearChildren();
        view.addChild(FXGL.texture(currentTextureName));

        FXGL.<SlotMachineApp>getAppCast().spin();
    }
}
