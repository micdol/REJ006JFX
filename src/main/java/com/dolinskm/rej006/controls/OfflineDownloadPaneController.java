package com.dolinskm.rej006.controls;

import com.dolinskm.rej006.managers.ViewManager;
import com.dolinskm.rej006.models.Connection;
import com.dolinskm.rej006.models.device.Device;
import com.dolinskm.rej006.models.device.Registration;
import com.dolinskm.rej006.models.wrappers.IAppSettingsWrapper;
import com.dolinskm.rej006.models.wrappers.IConnectionWrapper;
import com.dolinskm.rej006.services.DeviceService;
import com.dolinskm.rej006.services.tasks.RegistrationDownloadTask;
import com.dolinskm.rej006.utils.RegistrationUtils;
import com.dolinskm.rej006.views.View;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@FxmlView("offline-download-pane.fxml")
public class OfflineDownloadPaneController {

    @Autowired
    IAppSettingsWrapper appSettingsWrapper;

    @Autowired
    private ViewManager viewManager;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private IConnectionWrapper connectionWrapper;

    private final ObjectProperty<Registration> registration = new SimpleObjectProperty<>();

    // region FXML Controls

    @FXML
    private Spinner<Integer> spnRegistrationNumber;

    @FXML
    private Button btnDownload;

    @FXML
    private ProgressBar prgDownloadProgress;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnPlot;

    @FXML
    private BackPaneController backPaneController;

    // endregion

    // region FXML Action Handlers

    @FXML
    void onDownloadClicked(ActionEvent event) {
        final int n = spnRegistrationNumber.getValue();
        final RegistrationDownloadTask downloadTask = new RegistrationDownloadTask(n);
        registration.bind(downloadTask.registrationProperty());
        deviceService.enqueue(downloadTask);
        deviceService.restart();
    }

    @FXML
    void onPlotClicked(ActionEvent event) {
        viewManager.show(View.Plot);
    }

    @FXML
    void onSaveClicked(ActionEvent event) {
        final TextInputDialog dialog = new TextInputDialog(registration.getName());
        dialog.setTitle("Nazwa pliku rejestracji");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        Platform.runLater(dialog.getEditor()::requestFocus);
        dialog.showAndWait().ifPresent(this::onSaveDialog);
    }

    // endregion

    private void onSaveDialog(String name) {
        final Registration registration = getRegistration();
        registration.setName(name);

        final File registrationsDirectory = appSettingsWrapper.getAppSettings().getRegistrationsDirectory();
        final String fileName = registration.getMode().toString() + "_" + registration.getName() + RegistrationUtils.FILE_EXTENSION;
        final File registrationFile = new File(registrationsDirectory, fileName);

        // apparently cannot modify simple boolean within lambda
        AtomicBoolean proceed = new AtomicBoolean(true);
        if (registrationFile.exists()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Plik istnieje - nadpisać?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait().ifPresent(buttonType -> proceed.set(buttonType == ButtonType.YES));
        }
        if (!proceed.get()) {
            return;
        }

        try {
            RegistrationUtils.save(registration, registrationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        final Connection connection = connectionWrapper.getConnection();
        final Device device = connection.getDevice();

        IntegerSpinnerValueFactory valueFactory = new IntegerSpinnerValueFactory(1, device.getRegistrationCount(), 1);
        spnRegistrationNumber.setValueFactory(valueFactory);
        spnRegistrationNumber.disableProperty().bind(connection.busyProperty()
                .or(deviceService.runningProperty()));

        btnDownload.disableProperty().bind(connection.busyProperty()
                .or(deviceService.runningProperty()));
        btnPlot.disableProperty().bind(connection.busyProperty()
                .or(deviceService.runningProperty())
                .or(registration.isNull()));
        btnSave.disableProperty().bind(connection.busyProperty()
                .or(deviceService.runningProperty())
                .or(registration.isNull()));

        prgDownloadProgress.progressProperty().bind(Bindings
                .when(deviceService.runningProperty())
                .then(deviceService.progressProperty())
                .otherwise(0));
    }

    // region Properties Getters/Setters

    public Registration getRegistration() {
        return registration.get();
    }

    public ReadOnlyObjectProperty<Registration> registrationProperty() {
        return registration;
    }

    protected void setRegistration(Registration registration) {
        this.registration.set(registration);
    }

    // endregion
}
