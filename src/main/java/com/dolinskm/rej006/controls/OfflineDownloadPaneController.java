package com.dolinskm.rej006.controls;

import com.dolinskm.rej006.controls.BackPaneController;
import com.dolinskm.rej006.managers.ViewManager;
import com.dolinskm.rej006.views.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("offline-download-pane.fxml")
public class OfflineDownloadPaneController {

    @Autowired
    private ViewManager viewManager;

    // region FXML Controls

    @FXML
    private Spinner<?> spnRegistrationNumber;

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

    }

    @FXML
    void onPlotClicked(ActionEvent event) {
        viewManager.show(View.Plot);
    }

    @FXML
    void onSaveClicked(ActionEvent event) {

    }

    // endregion
}
