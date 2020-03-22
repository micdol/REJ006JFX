package com.dolinskm.rej006.controls;

import com.dolinskm.rej006.models.device.*;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class RegistrationsTableView extends TableView<Registration> {

    // region FXML Controls

    @FXML
    public TableColumn<Registration, String> colName;

    @FXML
    public TableColumn<Registration, String> colDate;

    @FXML
    public TableColumn<Registration, Frequency> colFrequency;

    @FXML
    public TableColumn<Registration, Accelerometer> colAccelerometer;

    @FXML
    public TableColumn<Registration, Gyroscope> colGyroscope;

    @FXML
    public TableColumn<Registration, String> colChannels;

    // endregion

    public RegistrationsTableView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registrations-tableview.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(param ->
                Bindings.createStringBinding(
                        () -> DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(param.getValue().getDate()),
                        param.getValue().dateProperty()));
        colAccelerometer.setCellValueFactory(param -> param.getValue().getSettings().accelerometerProperty());
        colFrequency.setCellValueFactory(param -> param.getValue().getSettings().frequencyProperty());
        colGyroscope.setCellValueFactory(param -> param.getValue().getSettings().gyroscopeProperty());
        colChannels.setCellValueFactory(param ->
        {
            final Registration value = param.getValue();
            final Settings settings = value.getSettings();
            return Bindings.createObjectBinding(() -> {
                        StringBuilder sb = new StringBuilder("[");
                        if (settings.isAx()) sb.append("AX,");
                        if (settings.isAx()) sb.append("AY,");
                        if (settings.isAx()) sb.append("AZ,");
                        if (settings.isAx()) sb.append("GR,");
                        if (settings.isAx()) sb.append("GP,");
                        if (settings.isAx()) sb.append("GY,");
                        // Replace last comma with closing bracket
                        sb.replace(sb.length() - 1, sb.length(), "]");
                        return sb.toString();
                    },
                    settings.axProperty(), settings.ayProperty(), settings.azProperty(), settings.rollProperty(), settings.pitchProperty(), settings.yawProperty());
        });
    }
}
