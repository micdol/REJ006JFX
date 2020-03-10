package com.dolinskm.rej006.controls;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.RowConstraints;
import org.springframework.stereotype.Controller;

@Controller
public class SettingsGridController {

    // region FXML Controls

    @FXML
    private RowConstraints rowDelay;

    @FXML
    private RowConstraints rowLength;

    @FXML
    private Label lblDelay;

    @FXML
    private Label lblLength;

    @FXML
    private ComboBox<?> cbxFrequency;

    @FXML
    private ComboBox<?> cbxAccelerometer;

    @FXML
    private ComboBox<?> cbxGyroscope;

    @FXML
    private Spinner<?> spnLength;

    @FXML
    private Spinner<?> spnDelay;

    @FXML
    private CheckBox chkX;

    @FXML
    private CheckBox chkRoll;

    @FXML
    private CheckBox chkY;

    @FXML
    private CheckBox chkZ;

    @FXML
    private CheckBox chkYaw;

    @FXML
    private CheckBox chkPitch;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnLoad;

    @FXML
    private ComboBox<?> cbxSettings;

    // endregion

    // region FXML Action Handlers

    @FXML
    void onLoadClicked(ActionEvent event) {

    }

    @FXML
    void onSaveClicked(ActionEvent event) {

    }

    // endregion
}
