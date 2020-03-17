package com.dolinskm.rej006.utils;

import com.dolinskm.rej006.models.device.Settings;
import javafx.scene.control.ListCell;

public class SettingsListCell extends ListCell<Settings> {
    @Override
    protected void updateItem(Settings item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.getName());
        }
    }
}
