package com.dolinskm.rej006.app;

import com.dolinskm.rej006.SpringBootApp;
import com.dolinskm.rej006.views.MainViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

public class Rej006App extends Application {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        logger.info("init");

        super.init();

        String[] args = getParameters()
                .getRaw()
                .toArray(new String[0]);

        context = new SpringApplicationBuilder()
                .sources(SpringBootApp.class)
                .run(args);
    }

    @Override
    public void stop() {
        logger.info("stop");
        context.stop();
        Platform.exit();
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("start");

        final FxWeaver fxWeaver = context.getBean(FxWeaver.class);
        final Pane root = fxWeaver.loadView(MainViewController.class);
        root.setPrefSize(800, 600);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
