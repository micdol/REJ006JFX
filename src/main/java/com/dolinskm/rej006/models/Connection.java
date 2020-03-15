package com.dolinskm.rej006.models;

import com.dolinskm.rej006.models.device.Device;
import com.fazecast.jSerialComm.SerialPort;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Connection {

    private final ObjectProperty<Device> device = new SimpleObjectProperty<>(new Device());
    private final ObjectProperty<SerialPort> port = new SimpleObjectProperty<>();
    private final BooleanProperty active = new SimpleBooleanProperty(false);
    private final BooleanProperty busy = new SimpleBooleanProperty(false);

    // region Properties Getters/Setters

    public Device getDevice() {
        return device.get();
    }

    public ObjectProperty<Device> deviceProperty() {
        return device;
    }

    public void setDevice(Device device) {
        this.device.set(device);
    }

    public SerialPort getPort() {
        return port.get();
    }

    public ObjectProperty<SerialPort> portProperty() {
        return port;
    }

    public void setPort(SerialPort port) {
        this.port.set(port);
    }

    public boolean isActive() {
        return active.get();
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    public boolean isBusy() {
        return busy.get();
    }

    public BooleanProperty busyProperty() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy.set(busy);
    }

    // endregion
}
