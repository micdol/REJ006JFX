package com.dolinskm.rej006.models.device;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;

public class Settings {

    private final IntegerProperty delay = new SimpleIntegerProperty(0);
    private final IntegerProperty length = new SimpleIntegerProperty(30);
    private final IntegerProperty channelCount = new SimpleIntegerProperty(6);
    private final ObjectProperty<Frequency> frequency = new SimpleObjectProperty<>(Frequency.F100);
    private final ObjectProperty<Accelerometer> accelerometer = new SimpleObjectProperty<>(Accelerometer.A6);
    private final ObjectProperty<Gyroscope> gyroscope = new SimpleObjectProperty<>(Gyroscope.G250);
    private final ObjectProperty<Mode> mode = new SimpleObjectProperty<>(Mode.Offline);
    private final BooleanProperty ax = new SimpleBooleanProperty(true);
    private final BooleanProperty ay = new SimpleBooleanProperty(true);
    private final BooleanProperty az = new SimpleBooleanProperty(true);
    private final BooleanProperty roll = new SimpleBooleanProperty(true);
    private final BooleanProperty pitch = new SimpleBooleanProperty(true);
    private final BooleanProperty yaw = new SimpleBooleanProperty(true);
    private final StringProperty name = new SimpleStringProperty("new settings");

    {
        channelCount.bind(
                Bindings.when(ax).then(1).otherwise(0)
                        .add(Bindings.when(ay).then(1).otherwise(0))
                        .add(Bindings.when(az).then(1).otherwise(0))
                        .add(Bindings.when(roll).then(1).otherwise(0))
                        .add(Bindings.when(pitch).then(1).otherwise(0))
                        .add(Bindings.when(yaw).then(1).otherwise(0)));
    }

    // TODO maybe better to override equals?
    public boolean isSame(Settings other) {
        return other != null
                && other.getMode() == getMode()
                && other.getDelay() == getDelay()
                && other.getLength() == getLength()
                && other.getFrequency() == getFrequency()
                && other.getAccelerometer() == getAccelerometer()
                && other.getGyroscope() == getGyroscope()
                && other.isAx() == isAx()
                && other.isAy() == isAy()
                && other.isAz() == isAz()
                && other.isRoll() == isRoll()
                && other.isPitch() == isPitch()
                && other.isYaw() == isYaw()
                && other.getName().equals(getName());
    }

    @Override
    public String toString() {
        return "Settings{" +
                "\ndelay=" + delay +
                "\n, length=" + length +
                "\n, channelCount=" + channelCount +
                "\n, frequency=" + frequency +
                "\n, accelerometer=" + accelerometer +
                "\n, gyroscope=" + gyroscope +
                "\n, mode=" + mode +
                "\n, ax=" + ax +
                "\n, ay=" + ay +
                "\n, az=" + az +
                "\n, roll=" + roll +
                "\n, pitch=" + pitch +
                "\n, yaw=" + yaw +
                "\n, name=" + name +
                '}';
    }

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

    public ReadOnlyIntegerProperty channelCountProperty() {
        return channelCount;
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

    public boolean isAx() {
        return ax.get();
    }

    public BooleanProperty axProperty() {
        return ax;
    }

    public void setAx(boolean ax) {
        this.ax.set(ax);
    }

    public boolean isAy() {
        return ay.get();
    }

    public BooleanProperty ayProperty() {
        return ay;
    }

    public void setAy(boolean ay) {
        this.ay.set(ay);
    }

    public boolean isAz() {
        return az.get();
    }

    public BooleanProperty azProperty() {
        return az;
    }

    public void setAz(boolean az) {
        this.az.set(az);
    }

    public boolean isRoll() {
        return roll.get();
    }

    public BooleanProperty rollProperty() {
        return roll;
    }

    public void setRoll(boolean roll) {
        this.roll.set(roll);
    }

    public boolean isPitch() {
        return pitch.get();
    }

    public BooleanProperty pitchProperty() {
        return pitch;
    }

    public void setPitch(boolean pitch) {
        this.pitch.set(pitch);
    }

    public boolean isYaw() {
        return yaw.get();
    }

    public BooleanProperty yawProperty() {
        return yaw;
    }

    public void setYaw(boolean yaw) {
        this.yaw.set(yaw);
    }

    // endregion

}

