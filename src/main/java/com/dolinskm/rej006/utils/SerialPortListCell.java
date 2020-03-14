package com.dolinskm.rej006.utils;

import com.fazecast.jSerialComm.SerialPort;
import javafx.scene.control.ListCell;

public class SerialPortListCell extends ListCell<SerialPort> {
    @Override
    protected void updateItem(SerialPort item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.getSystemPortName());
        }
    }
}
