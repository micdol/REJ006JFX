package com.dolinskm.rej006.views;

import com.dolinskm.rej006.controls.BackPaneController;
import com.dolinskm.rej006.managers.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("archive-view.fxml")
public class ArchiveViewController {

    // region FXML Controls

    @FXML
    private ComboBox<?> cbxDirectory;

    @FXML
    private Button btnBrowseDirectory;

    @FXML
    private TableView<?> tblRegistrations;

    @FXML
    private TextArea txtNotes;

    @FXML
    private Button btnSaveNotes;

    @FXML
    private Button btnShowPlot;

    @FXML
    private BackPaneController backPaneController;

    // endregion

    @Autowired
    private ViewManager viewManager;

    @FXML
    void onBrowseDirectoryClicked(ActionEvent event) {

    }

    @FXML
    void onSaveNotesClicked(ActionEvent event) {

    }

    @FXML
    void onShowPlotClicked(ActionEvent event) {
        viewManager.show(View.Plot);
    }

}
