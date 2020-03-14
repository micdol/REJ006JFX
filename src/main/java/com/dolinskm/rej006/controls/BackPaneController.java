package com.dolinskm.rej006.controls;

import com.dolinskm.rej006.managers.ViewManager;
import com.dolinskm.rej006.models.Connection;
import com.dolinskm.rej006.models.wrappers.IConnectionWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BackPaneController {

    @Autowired
    private ViewManager viewManager;

    @Autowired
    private IConnectionWrapper connectionWrapper;

    // region FXML Controls

    @FXML
    private Button btnBack;

    // endregion

    // region FXML Action Handlers

    @FXML
    void onBackClicked(ActionEvent event) {
        viewManager.back();
    }

    // endregion

    @FXML
    void initialize() {
        final Connection connection = connectionWrapper.getConnection();
        btnBack.disableProperty().bind(connection.busyProperty());
    }
}
