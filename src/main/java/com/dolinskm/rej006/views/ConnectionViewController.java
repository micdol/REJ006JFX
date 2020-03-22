package com.dolinskm.rej006.views;

import com.dolinskm.rej006.controls.BackPaneController;
import com.dolinskm.rej006.models.Connection;
import com.dolinskm.rej006.models.wrappers.IAppSettingsWrapper;
import com.dolinskm.rej006.models.wrappers.IConnectionWrapper;
import com.dolinskm.rej006.services.DeviceService;
import com.dolinskm.rej006.services.tasks.ConnectTask;
import com.dolinskm.rej006.services.tasks.DisconnectTask;
import com.dolinskm.rej006.services.tasks.RegistrationCountTask;
import com.dolinskm.rej006.services.tasks.base.DeviceTaskBase;
import com.dolinskm.rej006.utils.SerialPortListCell;
import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("connection-view.fxml")
public class ConnectionViewController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAppSettingsWrapper appSettingsWrapper;

    @Autowired
    private IConnectionWrapper connectionController;

    @Autowired
    private DeviceService deviceService;

    // region FXML Controls

    @FXML
    private ComboBox<SerialPort> cbxPorts;

    @FXML
    private Button btnRefreshPorts;

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnDisconnect;

    @FXML
    private BackPaneController backPaneController;

    // endregion

    // region FXML Action Handlers

    @FXML
    void onConnectClicked(ActionEvent event) {
        logger.info("onConnectClicked");

        deviceService.enqueue(new ConnectTask());
        // Required for offline registration screen
        final RegistrationCountTask task = new RegistrationCountTask();
        deviceService.enqueue(task);
        deviceService.restart();

        task.setOnSucceeded(e -> appSettingsWrapper
                .getAppSettings()
                .setLastUsedPortName(cbxPorts.getValue().getSystemPortName()));
    }

    @FXML
    void onDisconnectClicked(ActionEvent event) {
        logger.info("onDisconnectClicked");

        final DeviceTaskBase task = new DisconnectTask();
        deviceService.enqueue(task);
        deviceService.restart();
    }

    @FXML
    void onRefreshPortsClicked(ActionEvent event) {
        refreshPorts();
    }

    // endregion

    @FXML
    void initialize() {
        cbxPorts.setCellFactory(unused -> new SerialPortListCell());
        cbxPorts.setButtonCell(new SerialPortListCell());

        final Connection connection = connectionController.getConnection();
        connection.portProperty().bind(cbxPorts.valueProperty());

        cbxPorts.disableProperty().bind(connection.activeProperty().or(connection.busyProperty()));
        btnRefreshPorts.disableProperty().bind(connection.activeProperty().or(connection.busyProperty()));
        btnConnect.disableProperty().bind(connection.activeProperty().or(connection.busyProperty()));
        btnDisconnect.disableProperty().bind(connection.activeProperty().not().or(connection.busyProperty()));

        refreshPorts();

        // Select default port if available
        final ObservableList<SerialPort> items = cbxPorts.getItems();
        final String lastUsedPortName = appSettingsWrapper.getAppSettings().getLastUsedPortName();
        for (int i = 0; i < items.size(); i++) {
            final SerialPort item = items.get(i);
            if (item.getSystemPortName().equals(lastUsedPortName)) {
                cbxPorts.getSelectionModel().select(i);
                break;
            }
        }

        cbxPorts.requestFocus();
    }

    public void refreshPorts() {
        final SingleSelectionModel<SerialPort> selectionModel = cbxPorts.getSelectionModel();
        final SerialPort selectedItem = selectionModel.getSelectedItem();
        final ObservableList<SerialPort> items = cbxPorts.getItems();

        items.clear();
        items.addAll(SerialPort.getCommPorts());

        selectionModel.select(0);
        for (int i = 0; i < items.size() && selectedItem != null; i++) {
            final SerialPort item = items.get(i);
            if (item.getSystemPortName().equals(selectedItem.getSystemPortName())) {
                selectionModel.select(i);
                break;
            }
        }
    }
}
