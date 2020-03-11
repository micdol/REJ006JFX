package com.dolinskm.rej006.models;

import javafx.beans.property.*;

public class Device {
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty id = new SimpleIntegerProperty(-1);
    private final StringProperty firmware = new SimpleStringProperty();
    private final IntegerProperty registrationCount = new SimpleIntegerProperty(-1);
    private final IntegerProperty capacity = new SimpleIntegerProperty(-1);
    private final DoubleProperty battery = new SimpleDoubleProperty(-1.0);
    private final ObjectProperty<Status> status = new SimpleObjectProperty<>();
    private final IntegerProperty syncCode = new SimpleIntegerProperty(-1);
    private final ObjectProperty<Settings> offlineSettings = new SimpleObjectProperty<>();

    // region Properties Getters/Setters

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirmware() {
        return firmware.get();
    }

    public StringProperty firmwareProperty() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware.set(firmware);
    }

    public int getRegistrationCount() {
        return registrationCount.get();
    }

    public IntegerProperty registrationCountProperty() {
        return registrationCount;
    }

    public void setRegistrationCount(int registrationCount) {
        this.registrationCount.set(registrationCount);
    }

    public int getCapacity() {
        return capacity.get();
    }

    public IntegerProperty capacityProperty() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    public double getBattery() {
        return battery.get();
    }

    public DoubleProperty batteryProperty() {
        return battery;
    }

    public void setBattery(double battery) {
        this.battery.set(battery);
    }

    public Status getStatus() {
        return status.get();
    }

    public ObjectProperty<Status> statusProperty() {
        return status;
    }

    public void setStatus(Status status) {
        this.status.set(status);
    }

    public int getSyncCode() {
        return syncCode.get();
    }

    public IntegerProperty syncCodeProperty() {
        return syncCode;
    }

    public void setSyncCode(int syncCode) {
        this.syncCode.set(syncCode);
    }

    public Settings getOfflineSettings() {
        return offlineSettings.get();
    }

    public ObjectProperty<Settings> offlineSettingsProperty() {
        return offlineSettings;
    }

    public void setOfflineSettings(Settings offlineSettings) {
        this.offlineSettings.set(offlineSettings);
    }

    // endregion
}
