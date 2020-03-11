package com.dolinskm.rej006.controls;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("offline-settings-pane.fxml")
public class OfflineSettingsPaneController {

    @FXML
    private Button btnSetSettings;

    @FXML
    private Button btnGetSettings;

    @FXML
    private Button btnStartRegistration;

    @FXML
    private SettingsGridController settingsGridController;

    @FXML
    private BackPaneController backPaneController;

    @FXML
    void onGetSettingsClicked(ActionEvent event) {

    }

    @FXML
    void onSetSettingsClicked(ActionEvent event) {

    }

    @FXML
    void onStartRegistrationClicked(ActionEvent event) {

    }

}
