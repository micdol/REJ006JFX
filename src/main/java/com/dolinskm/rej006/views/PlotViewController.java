package com.dolinskm.rej006.views;

import com.dolinskm.rej006.controls.BackPaneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("plot-view.fxml")
public class PlotViewController {

    @FXML
    private ComboBox<?> cbxPlotMode;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnStop;

    @FXML
    private ToggleButton tglCursorsX;

    @FXML
    private ToggleButton tglCursorsY;

    @FXML
    private BackPaneController backPaneController;

    @FXML
    void onCursorsXToggled(ActionEvent event) {

    }

    @FXML
    void onCursorsYToggled(ActionEvent event) {

    }

    @FXML
    void onStartClicked(ActionEvent event) {

    }

    @FXML
    void onStopClicked(ActionEvent event) {

    }

}
