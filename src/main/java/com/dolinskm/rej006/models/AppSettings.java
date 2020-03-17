package com.dolinskm.rej006.models;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.File;

public class AppSettings {

    private final ObjectProperty<File> registrationsDirectory = new SimpleObjectProperty<>();
    private final ObjectProperty<File> settingsDirectory = new SimpleObjectProperty<>();
    private final ObjectProperty<String> lastUsedPortName = new SimpleObjectProperty<>();

    // region Properties Getters/Setters

    public File getRegistrationsDirectory() {
        return registrationsDirectory.get();
    }
    public ObjectProperty<File> registrationsDirectoryProperty() {
        return registrationsDirectory;
    }
    public void setRegistrationsDirectory(File registrationsDirectory) {
        this.registrationsDirectory.set(registrationsDirectory);
    }
    public File getSettingsDirectory() {
        return settingsDirectory.get();
    }
    public ObjectProperty<File> settingsDirectoryProperty() {
        return settingsDirectory;
    }
    public void setSettingsDirectory(File settingsDirectory) {
        this.settingsDirectory.set(settingsDirectory);
    }
    public String getLastUsedPortName() {
        return lastUsedPortName.get();
    }
    public ObjectProperty<String> lastUsedPortNameProperty() {
        return lastUsedPortName;
    }
    public void setLastUsedPortName(String lastUsedPortName) {
        this.lastUsedPortName.set(lastUsedPortName);
    }

    // endregion
}
