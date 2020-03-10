package com.dolinskm.rej006.views;

import com.dolinskm.rej006.managers.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("main-view.fxml")
public class MainViewController {

    // region FXML Controls

    @FXML
    private Button btnArchive;

    @FXML
    private Button btnAppSettings;

    @FXML
    private Button btnConnection;

    @FXML
    private Label lblPort;

    @FXML
    private CheckBox chkConnection;

    @FXML
    private Button btnDeviceInfo;

    @FXML
    private Button btnOnlineRegistrations;

    @FXML
    private Button btnOfflineRegistrations;

    // endregion

    @Autowired
    private ViewManager viewManager;

    @FXML
    void onAppSettingsClicked(ActionEvent event) {

    }

    @FXML
    void onArchiveClicked(ActionEvent event) {
        viewManager.show(View.Archive);
    }

    @FXML
    void onConnectionClicked(ActionEvent event) {
        viewManager.show(View.Connection);
    }

    @FXML
    void onDeviceInfoClicked(ActionEvent event) {
        viewManager.show(View.DeviceInfo);
    }

    @FXML
    void onOfflineRegistrationsClicked(ActionEvent event) {
        viewManager.show(View.Offline);
    }

    @FXML
    void onOnlineRegistrationsClicked(ActionEvent event) {
        viewManager.show(View.Online);
    }

}
