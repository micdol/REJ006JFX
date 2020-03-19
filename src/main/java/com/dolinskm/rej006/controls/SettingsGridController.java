package com.dolinskm.rej006.controls;

import com.dolinskm.rej006.models.AppSettings;
import com.dolinskm.rej006.models.Connection;
import com.dolinskm.rej006.models.device.*;
import com.dolinskm.rej006.models.wrappers.IAppSettingsWrapper;
import com.dolinskm.rej006.models.wrappers.IConnectionWrapper;
import com.dolinskm.rej006.utils.SettingsListCell;
import com.dolinskm.rej006.utils.SettingsUtils;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
public class SettingsGridController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectProperty<Settings> settings = new SimpleObjectProperty<>(new Settings());
    private final BooleanProperty valid = new SimpleBooleanProperty(true);

    @Autowired
    IConnectionWrapper connectionWrapper;

    @Autowired
    IAppSettingsWrapper appSettingsWrapper;

    // region FXML Controls

    @FXML
    private GridPane grdSettings;

    @FXML
    private RowConstraints rowDelay;

    @FXML
    private RowConstraints rowLength;

    @FXML
    private Label lblDelay;

    @FXML
    private Label lblLength;

    @FXML
    private ComboBox<Frequency> cbxFrequency;

    @FXML
    private ComboBox<Accelerometer> cbxAccelerometer;

    @FXML
    private ComboBox<Gyroscope> cbxGyroscope;

    @FXML
    private Spinner<Integer> spnLength;

    @FXML
    private Spinner<Integer> spnDelay;

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
    private ComboBox<Settings> cbxSettings;

    // endregion

    // region FXML Action Handlers

    @FXML
    void onLoadClicked(ActionEvent event) {
        final Settings newSettings = cbxSettings.getValue();
        setSettings(newSettings);
    }

    @FXML
    void onSaveClicked(ActionEvent event) {
        final Dialog<String> dialog = saveDialog();
        dialog.showAndWait().ifPresent(this::onSaveDialog);
    }

    // endregion

    private Dialog<String> saveDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Nazwa");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

        HBox hbox = new HBox();
        hbox.setSpacing(10);

        TextField txtName = new TextField();
        txtName.setEditable(true);

        final ObservableList<Node> children = hbox.getChildren();
        children.add(new Label("Przyjazna nazwa ustawień: "));
        children.add(txtName);

        Platform.runLater(txtName::requestFocus);

        dialog.getDialogPane().setContent(hbox);
        dialog.setResultConverter(btn -> btn == ButtonType.APPLY ? txtName.getText() : null);

        return dialog;
    }

    private void onSaveDialog(String name) {
        final Settings settings = getSettings();
        final File settingsDirectory = appSettingsWrapper.getAppSettings().getSettingsDirectory();
        final String prefix = settings.getMode().toString().toLowerCase();
        final File settingsFile = new File(settingsDirectory, prefix + "_" + name + ".r6s");

        // apparently cannot modify simple boolean within lambda later on
        AtomicBoolean proceed = new AtomicBoolean(true);
        if (settingsFile.exists()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Plik istnieje - nadpisać?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait().ifPresent(overwrite -> proceed.set(overwrite == ButtonType.YES));
        }

        if (!proceed.get()) {
            return;
        }

        settings.setName(name);
        try {
            SettingsUtils.save(settings, settingsFile);
            // TODO I think if equals is implemented on Settings this can be just items.contains
            final ObservableList<Settings> items = cbxSettings.getItems();
            boolean found = false;
            for (Settings item : items) {
                if (item.isSame(settings)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                items.add(settings);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SettingsGridController() {
        settings.addListener(this::onSettingsChanged);
    }

    @FXML
    void initialize() {
        final Connection connection = connectionWrapper.getConnection();
        final Device device = connection.getDevice();

        device.offlineSettingsProperty().addListener(this::onDeviceSettingsChanged);
        spnDelay.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));
        spnLength.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 65535, 1));
        cbxFrequency.getItems().addAll(Frequency.values());
        cbxAccelerometer.getItems().addAll(Accelerometer.values());
        cbxGyroscope.getItems().addAll(Gyroscope.values());
        btnSave.disableProperty().bind(valid.not());
        btnLoad.disableProperty().bind(cbxSettings.valueProperty().isNull());

        cbxSettings.setButtonCell(new SettingsListCell());
        cbxSettings.setCellFactory(unused -> new SettingsListCell());

        setSettings(new Settings());
    }

    private void onDeviceSettingsChanged(ObservableValue<? extends Settings> o, Settings oldSettings, Settings newSettings) {
        final Settings settings = this.settings.get();
        settings.setDelay(newSettings.getDelay());
        settings.setLength(newSettings.getLength());
        settings.setAccelerometer(newSettings.getAccelerometer());
        settings.setFrequency(newSettings.getFrequency());
        settings.setGyroscope(newSettings.getGyroscope());
        settings.setAx(newSettings.isAx());
        settings.setAy(newSettings.isAy());
        settings.setAz(newSettings.isAz());
        settings.setRoll(newSettings.isRoll());
        settings.setPitch(newSettings.isPitch());
        settings.setYaw(newSettings.isYaw());
        settings.setMode(newSettings.getMode());
    }

    private void onSettingsChanged(ObservableValue<? extends Settings> o, Settings oldSettings, Settings newSettings) {
        if (oldSettings != null) {
            oldSettings.modeProperty().removeListener(this::onSettingsModeChanged);
        }
        if (newSettings != null) {
            newSettings.modeProperty().addListener(this::onSettingsModeChanged);

            /*
            Delay and Length might be "invalid" - its due to the fact that they are bound
            to spinner values, these might change making bound value invalid
            */
            spnDelay.getValueFactory().valueProperty().bindBidirectional(newSettings.delayProperty().asObject());
            spnLength.getValueFactory().valueProperty().bindBidirectional(newSettings.lengthProperty().asObject());
            cbxFrequency.valueProperty().bindBidirectional(newSettings.frequencyProperty());
            cbxAccelerometer.valueProperty().bindBidirectional(newSettings.accelerometerProperty());
            cbxGyroscope.valueProperty().bindBidirectional(newSettings.gyroscopeProperty());
            chkX.selectedProperty().bindBidirectional(newSettings.axProperty());
            chkY.selectedProperty().bindBidirectional(newSettings.ayProperty());
            chkZ.selectedProperty().bindBidirectional(newSettings.azProperty());
            chkRoll.selectedProperty().bindBidirectional(newSettings.rollProperty());
            chkPitch.selectedProperty().bindBidirectional(newSettings.pitchProperty());
            chkYaw.selectedProperty().bindBidirectional(newSettings.yawProperty());

            final BooleanBinding delayValid = newSettings.delayProperty().greaterThan(-1).and(newSettings.delayProperty().lessThan(256));
            final BooleanBinding lengthValid = newSettings.lengthProperty().greaterThan(0).and(newSettings.lengthProperty().lessThan(65536));
            final BooleanBinding channelCountValid =
                    Bindings.when(newSettings.frequencyProperty().isEqualTo(Frequency.F1000))
                            .then(newSettings.channelCountProperty().lessThan(3))
                            .otherwise(
                                    Bindings.when(newSettings.frequencyProperty().isEqualTo(Frequency.F700))
                                            .then(newSettings.channelCountProperty().lessThan(5))
                                            .otherwise(true)
                            );
            valid.bind(delayValid.and(lengthValid).and(channelCountValid));

            reloadAvailableSettings();
        }
    }

    private void onSettingsModeChanged(ObservableValue<? extends Mode> o, Mode oldMode, Mode newMode) {
        final ObservableList<Node> children = grdSettings.getChildren();
        final ObservableList<RowConstraints> rowConstraints = grdSettings.getRowConstraints();

        /*
        By default mode is set to Offline. When changed to Online top two rows need to be removed.
        To have layout updated all other nodes in grid must be moved 2 rows upwards.
        ASSUMPTION:
        Only changes from offline to online are possible. No way to set mode online and then back again offline
        (so no implementation for such case - adding rows, moving nodes etc)
         */
        if (newMode == Mode.Online && rowConstraints.contains(rowDelay)) {
            System.out.println("online");
            rowConstraints.removeAll(rowDelay, rowLength);
            children.removeAll(lblDelay, spnDelay, lblLength, spnLength);
            children.forEach(c -> GridPane.setRowIndex(c, GridPane.getRowIndex(c) - 2));
        }

        reloadAvailableSettings();
    }

    private void reloadAvailableSettings() {
        final Mode mode = getSettings().getMode();
        final ObservableList<Settings> items = cbxSettings.getItems();
        final AppSettings appSettings = appSettingsWrapper.getAppSettings();
        final File settingsDirectory = appSettings.getSettingsDirectory();
        final File[] settingsFiles = settingsDirectory.listFiles((dir, name) -> name.endsWith(".r6s"));
        items.clear();

        for (File settingsFile : settingsFiles) {
            try {
                final Settings settings = SettingsUtils.load(settingsFile);
                if (settings.getMode() == mode) {
                    items.add(settings);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // region Properties Getters/Setters

    public Settings getSettings() {
        return settings.get();
    }

    public ReadOnlyObjectProperty<Settings> settingsProperty() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings.set(settings);
    }

    public boolean isValid() {
        return valid.get();
    }

    public ReadOnlyBooleanProperty validProperty() {
        return valid;
    }

    // endregion

}
