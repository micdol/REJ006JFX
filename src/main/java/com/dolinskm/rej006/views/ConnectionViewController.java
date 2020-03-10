package com.dolinskm.rej006.views;

import com.dolinskm.rej006.controls.BackPaneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class ConnectionViewController {

    @FXML
    private ComboBox<?> cbxPorts;

    @FXML
    private Button btnRefreshPorts;

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnDisconnect;

    @FXML
    private BackPaneController backPaneController;

    @FXML
    void onConnectClicked(ActionEvent event) {

    }

    @FXML
    void onDisconnectClicked(ActionEvent event) {

    }

    @FXML
    void onRefreshPortsClicked(ActionEvent event) {

    }

}
