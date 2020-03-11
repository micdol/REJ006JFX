package com.dolinskm.rej006.models;

import javafx.beans.property.*;

public class Settings {
    private final IntegerProperty delay = new SimpleIntegerProperty();
    private final IntegerProperty length = new SimpleIntegerProperty();
    private final IntegerProperty channelCount = new SimpleIntegerProperty();
    private final ObjectProperty<Frequency> frequency = new SimpleObjectProperty<>();
    private final ObjectProperty<Accelerometer> accelerometer = new SimpleObjectProperty<>();
    private final ObjectProperty<Gyroscope> gyroscope = new SimpleObjectProperty<>();
    private final ObjectProperty<Mode> mode = new SimpleObjectProperty<>(Mode.Offline);

    private final StringProperty name = new SimpleStringProperty();

    // region Properties Getters/Setters

    public int getDelay() {
        return delay.get();
    }

    public IntegerProperty delayProperty() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay.set(delay);
    }

    public int getLength() {
        return length.get();
    }

    public IntegerProperty lengthProperty() {
        return length;
    }

    public void setLength(int length) {
        this.length.set(length);
    }

    public int getChannelCount() {
        return channelCount.get();
    }

    public IntegerProperty channelCountProperty() {
        return channelCount;
    }

    public void setChannelCount(int channelCount) {
        this.channelCount.set(channelCount);
    }

    public Frequency getFrequency() {
        return frequency.get();
    }

    public ObjectProperty<Frequency> frequencyProperty() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency.set(frequency);
    }

    public Accelerometer getAccelerometer() {
        return accelerometer.get();
    }

    public ObjectProperty<Accelerometer> accelerometerProperty() {
        return accelerometer;
    }

    public void setAccelerometer(Accelerometer accelerometer) {
        this.accelerometer.set(accelerometer);
    }

    public Gyroscope getGyroscope() {
        return gyroscope.get();
    }

    public ObjectProperty<Gyroscope> gyroscopeProperty() {
        return gyroscope;
    }

    public void setGyroscope(Gyroscope gyroscope) {
        this.gyroscope.set(gyroscope);
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    // endregion

}

