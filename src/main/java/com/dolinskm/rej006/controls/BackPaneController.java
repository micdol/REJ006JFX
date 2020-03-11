package com.dolinskm.rej006.controls;

import com.dolinskm.rej006.managers.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BackPaneController {

    @Autowired
    private ViewManager viewManager;

    // region FXML Controls

    @FXML
    private Button btnBack;

    // endregion

    // region FXML Action Handlers

    @FXML
    void onBackClicked(ActionEvent event) {
        viewManager.back();
    }

    // endregion
}
