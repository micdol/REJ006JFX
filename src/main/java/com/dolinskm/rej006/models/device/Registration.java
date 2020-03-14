package com.dolinskm.rej006.models.device;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;

public class Registration {
    private final ObjectProperty<Settings> settings = new SimpleObjectProperty<>();
    private final ObjectProperty<Mode> mode = new SimpleObjectProperty<>();
    private final ObservableList<Byte> data = FXCollections.observableArrayList();
    private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty<LocalTime> date = new SimpleObjectProperty<>();
    private final StringProperty notes = new SimpleStringProperty();

    // region Properties Getters/Setters

    public Settings getSettings() {
        return settings.get();
    }

    public ObjectProperty<Settings> settingsProperty() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings.set(settings);
    }

    public Mode getMode() {
        return mode.get();
    }

    public ObjectProperty<Mode> modeProperty() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode.set(mode);
    }

    public ObservableList<Byte> getData() {
        return data;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public LocalTime getDate() {
        return date.get();
    }

    public ObjectProperty<LocalTime> dateProperty() {
        return date;
    }

    public void setDate(LocalTime date) {
        this.date.set(date);
    }

    public String getNotes() {
        return notes.get();
    }

    public StringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    // endregion
}
