package com.dolinskm.rej006.views;

import com.dolinskm.rej006.controls.BackPaneController;
import com.dolinskm.rej006.controls.SettingsGridController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("online-registration-view.fxml")
public class OnlineRegistrationViewController {

    @FXML
    private Button btnZeroing;

    @FXML
    private Button btnStartRegistration;

    @FXML
    private SettingsGridController settingsGridController;

    @FXML
    private BackPaneController backPaneController;

    @FXML
    void onStartRegistrationClicked(ActionEvent event) {

    }

    @FXML
    void onZeroingClicked(ActionEvent event) {

    }

}
