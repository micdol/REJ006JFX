package com.dolinskm.rej006.views;

import com.dolinskm.rej006.controls.BackPaneController;
import com.dolinskm.rej006.models.Connection;
import com.dolinskm.rej006.models.device.Device;
import com.dolinskm.rej006.models.device.Status;
import com.dolinskm.rej006.models.wrappers.IConnectionWrapper;
import com.dolinskm.rej006.services.DeviceService;
import com.dolinskm.rej006.services.tasks.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("device-info-view.fxml")
public class DeviceInfoViewController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IConnectionWrapper connectionController;

    @Autowired
    private DeviceService deviceService;

    // region FXML Controls

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

    // endregion

    // region FXML Action Handlers

    @FXML
    void onChangeIDClicked(ActionEvent event) {
        final Device device = connectionController.getConnection().getDevice();
        final int currentId = device.getId();

        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("ID");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Spinner<Integer> spnId = new Spinner<>();
        final IntegerSpinnerValueFactory valueFactory = new IntegerSpinnerValueFactory(0, 255, currentId);
        valueFactory.setWrapAround(true);
        spnId.setValueFactory(valueFactory);
        Platform.runLater(spnId::requestFocus);

        grid.add(new Label("ID:"), 0, 0);
        grid.add(spnId, 1, 0);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(btn -> btn == ButtonType.APPLY ? spnId.getValue() : null);

        dialog.showAndWait().ifPresent(newId -> {
            logger.info("new id: {}", newId);
            deviceService.enqueue(new ChangeIDTask(newId));
            deviceService.enqueue(new NameIDTask());
            deviceService.restart();
        });
    }

    @FXML
    void onChangeStatusSyncCode(ActionEvent event) {
        final Device device = connectionController.getConnection().getDevice();
        final Status currentStatus = device.getStatus();
        final int currentSyncCode = device.getSyncCode();

        Dialog<Pair<Status, Integer>> dialog = new Dialog<>();
        dialog.setTitle("Status i kod sync");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        final ComboBox<Status> cbxStatus = new ComboBox<>();
        cbxStatus.setMaxWidth(Double.MAX_VALUE);
        cbxStatus.getItems().addAll(Status.Master, Status.Neutral, Status.Slave);
        cbxStatus.getSelectionModel().select(currentStatus);
        Platform.runLater(cbxStatus::requestFocus);

        Spinner<Integer> spnSync = new Spinner<>();
        final IntegerSpinnerValueFactory valueFactory = new IntegerSpinnerValueFactory(0, 255, currentSyncCode);
        valueFactory.setWrapAround(true);
        spnSync.setValueFactory(valueFactory);
        spnSync.prefWidthProperty().bind(cbxStatus.widthProperty());

        grid.add(new Label("Status:"), 0, 0);
        grid.add(cbxStatus, 1, 0);
        grid.add(new Label("Sync:"), 0, 1);
        grid.add(spnSync, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(btn -> btn == ButtonType.APPLY ? new Pair<>(cbxStatus.getValue(), spnSync.getValue()) : null);

        dialog.showAndWait().ifPresent(result -> {
            final Status newStatus = result.getKey();
            final Integer newSyncCode = result.getValue();
            logger.info("new status: {}, sync: {}", newStatus, newSyncCode);
            deviceService.enqueue(new ChangeStatusSyncCodeTask(newStatus, newSyncCode));
            deviceService.restart();
        });

    }

    @FXML
    void onClearFlashClicked(ActionEvent event) {
        deviceService.enqueue(new ClearFlashTask());
        deviceService.restart();
    }

    @FXML
    void onRefreshClicked(ActionEvent event) {
        refresh();
    }

    @FXML
    void onResetClicked(ActionEvent event) {
        deviceService.enqueue(new ResetTask());
        deviceService.restart();
    }

    // endregion

    @FXML
    void initialize() {
        final Connection connection = connectionController.getConnection();
        final Device device = connection.getDevice();

        txtName.textProperty().bind(device.nameProperty());
        txtID.textProperty().bind(Bindings
                .when(device.idProperty().greaterThan(-1).and(device.idProperty().lessThan(256)))
                .then(device.idProperty().asString())
                .otherwise(""));
        txtFirmware.textProperty().bind(device.firmwareProperty());
        txtFlashCapacity.textProperty().bind(Bindings
                .when(device.capacityProperty().greaterThanOrEqualTo(0))
                .then(device.capacityProperty().asString())
                .otherwise(""));
        txtRegistrationCount.textProperty().bind(Bindings
                .when(device.registrationCountProperty().greaterThanOrEqualTo(0))
                .then(device.registrationCountProperty().asString())
                .otherwise(""));
        txtBattery.textProperty().bind(Bindings
                .when(device.batteryProperty().greaterThanOrEqualTo(0))
                .then(device.batteryProperty().asString("%.2f %%"))
                .otherwise(""));
        txtStatus.textProperty().bind(Bindings
                .when(device.statusProperty().isNotNull())
                .then(device.statusProperty().asString())
                .otherwise(""));
        txtSyncCode.textProperty().bind(Bindings
                .when(device.syncCodeProperty().greaterThan(-1).and(device.syncCodeProperty().lessThan(256)))
                .then(device.syncCodeProperty().asString())
                .otherwise(""));

        btnChangeID.disableProperty().bind(connection.busyProperty());
        btnChangeStatusSyncCode.disableProperty().bind(connection.busyProperty());
        btnClearFlash.disableProperty().bind(connection.busyProperty());
        btnRefresh.disableProperty().bind(connection.busyProperty());
        btnReset.disableProperty().bind(connection.busyProperty());

        refresh();
    }

    void refresh() {
        final Device device = connectionController.getConnection().getDevice();
        deviceService.enqueue(new NameIDTask());
        if (device.getFirmware() == null || device.getFirmware().equals("")) {
            deviceService.enqueue(new FirmwareTask());
        }
        if (device.getCapacity() <= 0) {
            deviceService.enqueue(new CapacityTask());
        }
        deviceService.enqueue(new RegistrationCountTask());
        deviceService.enqueue(new BatteryTask());
        deviceService.enqueue(new StatusSyncCodeTask());
        deviceService.restart();
    }
}
