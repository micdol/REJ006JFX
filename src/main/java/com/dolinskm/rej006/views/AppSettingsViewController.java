package com.dolinskm.rej006.views;


import com.dolinskm.rej006.controls.BackPaneController;
import com.dolinskm.rej006.models.AppSettings;
import com.dolinskm.rej006.models.wrappers.IAppSettingsWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("app-settings-view.fxml")
public class AppSettingsViewController {

    @Autowired
    IAppSettingsWrapper appSettingsWrapper;

    // region FXML Controls

    @FXML
    private TextField txtArchiveDirectory;

    @FXML
    private Button btnBrowseArchive;

    @FXML
    private TextField txtSettingsDirectory;

    @FXML
    private Button btnBrowseSettings;

    @FXML
    private TextField txtPort;

    @FXML
    private BackPaneController backPaneController;

    // endregion

    // region FXML Action Handlers

    @FXML
    private void onBrowseArchive(ActionEvent actionEvent) {

    }

    @FXML
    private void onBrowseSettings(ActionEvent actionEvent) {
    }

    // endregion

    @FXML
    void initialize() {
        final AppSettings appSettings = appSettingsWrapper.getAppSettings();

        txtArchiveDirectory.textProperty().bind(appSettings.registrationsDirectoryProperty().asString());
        txtSettingsDirectory.textProperty().bind(appSettings.settingsDirectoryProperty().asString());
        txtPort.textProperty().bind(appSettings.lastUsedPortNameProperty());
    }
}
