package com.dolinskm.rej006.views;

import com.dolinskm.rej006.controls.BackPaneController;
import com.dolinskm.rej006.controls.SettingsGridController;
import com.dolinskm.rej006.managers.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("online-view.fxml")
public class OnlineViewController {

    @Autowired
    private ViewManager viewManager;

    // region FXML Controls

    @FXML
    private Button btnZeroing;

    @FXML
    private Button btnStartRegistration;

    @FXML
    private SettingsGridController settingsGridController;

    @FXML
    private BackPaneController backPaneController;

    // endregion

    // region FXML Action Handlers

    @FXML
    void onStartRegistrationClicked(ActionEvent event) {
        viewManager.show(View.Plot);
    }

    @FXML
    void onZeroingClicked(ActionEvent event) {

    }

    // endregion
}
