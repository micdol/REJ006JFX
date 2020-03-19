package com.dolinskm.rej006.controls;

import com.dolinskm.rej006.models.device.Settings;
import com.dolinskm.rej006.models.wrappers.IConnectionWrapper;
import com.dolinskm.rej006.services.DeviceService;
import com.dolinskm.rej006.services.tasks.ChangeSettingsTask;
import com.dolinskm.rej006.services.tasks.SettingsTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("offline-settings-pane.fxml")
public class OfflineSettingsPaneController {

    @Autowired
    IConnectionWrapper connectionWrapper;

    @Autowired
    DeviceService deviceService;

    // region FXML Controls

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

    // endregion

    // region FXML Action Handlers

    @FXML
    void onGetSettingsClicked(ActionEvent event) {
        deviceService.enqueue(new SettingsTask());
        deviceService.restart();
    }

    @FXML
    void onSetSettingsClicked(ActionEvent event) {
        final Settings settings = settingsGridController.getSettings();
        final ChangeSettingsTask changeSettingsTask = new ChangeSettingsTask(settings);
        deviceService.enqueue(changeSettingsTask);
        deviceService.restart();
    }

    @FXML
    void onStartRegistrationClicked(ActionEvent event) {

    }

    // endregion

    @FXML
    void initialize() {
        btnStartRegistration.disableProperty().bind(settingsGridController.validProperty().not());
        btnSetSettings.disableProperty().bind(settingsGridController.validProperty().not());
    }
}
