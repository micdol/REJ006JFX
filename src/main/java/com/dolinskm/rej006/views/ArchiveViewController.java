package com.dolinskm.rej006.views;

import com.dolinskm.rej006.controls.BackPaneController;
import com.dolinskm.rej006.managers.ViewManager;
import com.dolinskm.rej006.models.AppSettings;
import com.dolinskm.rej006.models.device.Registration;
import com.dolinskm.rej006.models.wrappers.IAppSettingsWrapper;
import com.dolinskm.rej006.utils.RegistrationUtils;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;

@Controller
@FxmlView("archive-view.fxml")
public class ArchiveViewController {

    @Autowired
    private ViewManager viewManager;

    @Autowired
    private IAppSettingsWrapper appSettingsWrapper;

    // region FXML Controls

    @FXML
    private ComboBox<File> cbxDirectory;

    @FXML
    private Button btnBrowseDirectory;

    @FXML
    private TableView<Registration> tblRegistrations;

    @FXML
    private TextArea txtNotes;

    @FXML
    private Button btnSaveNotes;

    @FXML
    private Button btnShowPlot;

    @FXML
    private BackPaneController backPaneController;

    // endregion


    // region FXML Action Handlers

    @FXML
    void onBrowseDirectoryClicked(ActionEvent event) {
        // Apparently cannot use getValue()/getSelectedItem() since when the combo is editable it returns String (sic!)
        final File currentDirectory = getSelectedDirectory();
        final Window window = btnBrowseDirectory.getScene().getWindow();

        final DirectoryChooser dialog = new DirectoryChooser();
        dialog.setInitialDirectory(currentDirectory);
        dialog.setTitle("Wybierz folder");

        final File newDirectory = dialog.showDialog(window);
        if (newDirectory != null && newDirectory.isDirectory()) {
            cbxDirectory.getItems().add(newDirectory);
            cbxDirectory.getSelectionModel().select(newDirectory);
        }
    }

    @FXML
    void onSaveNotesClicked(ActionEvent event) {
        final String newNotes = txtNotes.getText();
        final Registration registration = tblRegistrations.getSelectionModel().getSelectedItem();
        registration.setNotes(newNotes);

        final File selectedDirectory = getSelectedDirectory();
        final String fileName = registration.getMode().toString() + "_" + registration.getName() + RegistrationUtils.FILE_EXTENSION;
        final File registrationFile = new File(selectedDirectory, fileName);

        try {
            RegistrationUtils.save(registration, registrationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onShowPlotClicked(ActionEvent event) {
        viewManager.show(View.Plot);
    }

    // endregion

    @FXML
    void initialize() {
        final AppSettings appSettings = appSettingsWrapper.getAppSettings();
        final File registrationsDirectory = appSettings.getRegistrationsDirectory();
        final ObservableList<File> directories = cbxDirectory.getItems();
        directories.add(registrationsDirectory);

        cbxDirectory.getSelectionModel().select(0);
        cbxDirectory.getEditor().setOnAction(e -> System.out.println(cbxDirectory.getEditor().getText()));
        cbxDirectory.getEditor().focusedProperty().addListener(this::onValidatePathText);

        tblRegistrations.getSelectionModel().selectedItemProperty().addListener(this::onSelectedRegistrationChanged);

        // This will be enabled on registration selection & notes change
        btnSaveNotes.setDisable(true);

        loadRegistrations();
    }

    private String previousPathText;

    private void onValidatePathText(ObservableValue<? extends Boolean> o, Boolean hadFocus, Boolean hasFocus) {
        final TextField editor = cbxDirectory.getEditor();
        final String text = editor.getText();
        if (!hasFocus && hadFocus && !text.equals(previousPathText)) {
            final File tmpFile = new File(text);
            if (!tmpFile.exists()) {
                editor.setText(previousPathText);
                loadRegistrations();
            }
        } else if (!hadFocus && hasFocus) {
            previousPathText = text;
        }
    }

    private void onSelectedRegistrationChanged(ObservableValue<? extends Registration> o, Registration ov, Registration nv) {
        if (nv != null) {
            txtNotes.setText(nv.getNotes());
            btnSaveNotes.disableProperty().bind(nv.notesProperty().isEqualTo(txtNotes.textProperty()));
        }
    }

    private File getSelectedDirectory() {
        final String pathText = cbxDirectory.getEditor().getText();
        return new File(pathText);
    }

    private void loadRegistrations() {
        final File directory = getSelectedDirectory();
        final File[] registrationFiles = directory.listFiles((dir, name) -> name.endsWith(RegistrationUtils.FILE_EXTENSION));
        if (registrationFiles == null || registrationFiles.length == 0) {
            return;
        }
        final ObservableList<Registration> registrations = tblRegistrations.getItems();
        registrations.clear();
        for (File registrationFile : registrationFiles) {
            try {
                final Registration registration = RegistrationUtils.load(registrationFile);
                registrations.add(registration);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
