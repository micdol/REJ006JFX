package com.dolinskm.rej006.models.device;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Registration {
    private final ObjectProperty<Settings> settings = new SimpleObjectProperty<>(new Settings());
    private final ObservableList<Byte> data = FXCollections.observableArrayList();
    private final StringProperty name = new SimpleStringProperty("new registration");
    private final ObjectProperty<LocalDateTime> date = new SimpleObjectProperty<>(LocalDateTime.now());
    private final StringProperty notes = new SimpleStringProperty("");

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
        return getSettings().getMode();
    }

    public ReadOnlyObjectProperty<Mode> modeProperty() {
        return getSettings().modeProperty();
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

    public LocalDateTime getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDateTime> dateProperty() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
