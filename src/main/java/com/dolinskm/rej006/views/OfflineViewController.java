package com.dolinskm.rej006.views;

import com.dolinskm.rej006.controls.OfflineDownloadPaneController;
import com.dolinskm.rej006.controls.OfflineSettingsPaneController;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("offline-view.fxml")
public class OfflineViewController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // region FXML Controls

    @FXML
    private Tab tabSettings;

    @FXML
    private Tab tabDownload;

    @FXML
    private OfflineSettingsPaneController settingsController;

    @FXML
    private OfflineDownloadPaneController downloadController;

    // endregion

    @FXML
    void initialize() {

    }
}
