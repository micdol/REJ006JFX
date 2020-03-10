package com.dolinskm.rej006.views;

import com.dolinskm.rej006.controls.BackPaneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("device-info-view.fxml")
public class DeviceInfoViewController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtFirmware;

    @FXML
    private TextField txtFlashCapacity;

    @FXML
    private TextField txtRegistrationCount;

    @FXML
    private TextField txtBattery;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtSyncCode;

    @FXML
    private Button btnChangeID;

    @FXML
    private Button btnClearFlash;

    @FXML
    private Button btnChangeStatusSyncCode;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnReset;

    @FXML
    private BackPaneController backPaneController;

    @FXML
    void onChangeIDClicked(ActionEvent event) {

    }

    @FXML
    void onChangeStatusSyncCode(ActionEvent event) {

    }

    @FXML
    void onClearFlashClicked(ActionEvent event) {

    }

    @FXML
    void onRefreshClicked(ActionEvent event) {

    }

    @FXML
    void onResetClicked(ActionEvent event) {

    }

}
