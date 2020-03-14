package com.dolinskm.rej006.views;

import com.dolinskm.rej006.managers.ViewManager;
import com.dolinskm.rej006.models.Connection;
import com.dolinskm.rej006.models.wrappers.IConnectionWrapper;
import javafx.beans.binding.Bindings;
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

    @Autowired
    private ViewManager viewManager;

    @Autowired
    private IConnectionWrapper connectionWrapper;

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

    // region FXML Action Handlers

    @FXML
    void onAppSettingsClicked(ActionEvent event) {
        viewManager.show(View.AppSettings);
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

    // endregion

    @FXML
    void initialize() {
        final Connection connection = connectionWrapper.getConnection();

        btnDeviceInfo.disableProperty().bind(connection.activeProperty().not());
        btnOnlineRegistrations.disableProperty().bind(connection.activeProperty().not());
        btnOfflineRegistrations.disableProperty().bind(connection.activeProperty().not());

        lblPort.textProperty().bind(Bindings.createStringBinding(() -> {
            if (connection.isActive()) {
                return connection.getPort().getSystemPortName();
            }
            return "?";
        }, connection.activeProperty(), connection.portProperty()));
        chkConnection.selectedProperty().bind(connection.activeProperty());
    }
}
