package com.dolinskm.rej006.views;


import com.dolinskm.rej006.controls.BackPaneController;
import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("app-settings-view.fxml")
public class AppSettingsViewController {

    // region FXML Controls

    @FXML
    private BackPaneController backPaneController;

    // endregion
}
