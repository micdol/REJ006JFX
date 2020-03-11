package com.dolinskm.rej006.managers;

import com.dolinskm.rej006.views.View;
import com.sun.istack.internal.NotNull;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public class ViewManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FxWeaver fxWeaver;

    private Stage currentStage;
    private View currentView;
    private final Stack<View> viewStack = new Stack<>();

    public void show(@NotNull View view) {
        logger.info("show - new: " + view + ", old: " + currentView);

        if (currentStage == null) {
            logger.info("show - no stage, creating new");
            currentStage = new Stage();
            currentStage.setTitle("REJ006");
        }

        viewStack.push(currentView);
        display(view);
    }

    public void back() {
        if (viewStack.empty()) {
            throw new IllegalStateException("View stack empty?!");
        }
        if (currentStage == null) {
            throw new IllegalStateException("No current stage?!");
        }

        final View view = viewStack.pop();
        logger.info("back - old: " + currentView + ", new: " + view);
        display(view);
    }

    private void display(@NotNull View view) {
        final Pane root = (Pane) fxWeaver.loadView(view.getControllerClass());
        root.setPrefSize(800, 600);
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        currentStage.show();
        currentView = view;
    }

}
