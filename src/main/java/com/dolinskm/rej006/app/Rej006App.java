package com.dolinskm.rej006.app;

import com.dolinskm.rej006.SpringBootApp;
import com.dolinskm.rej006.managers.ViewManager;
import com.dolinskm.rej006.views.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class Rej006App extends Application {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        logger.info("init");

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

        final ViewManager viewManager = context.getBean(ViewManager.class);
        viewManager.show(View.Main);
    }
}
