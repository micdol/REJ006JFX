package com.dolinskm.rej006.models.wrappers;

import com.dolinskm.rej006.models.Connection;
import org.springframework.stereotype.Component;

@Component
public class CurrentConnection implements IConnectionWrapper {

    private Connection connection;

    @Override
    public Connection getConnection() {
        if (connection == null) {
            connection = new Connection();
        }
        return connection;
    }
}
