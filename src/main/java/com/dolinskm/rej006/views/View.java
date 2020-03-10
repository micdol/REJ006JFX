package com.dolinskm.rej006.views;

public enum View {
    AppSettings(AppSettingsViewController.class),
    Archive(ArchiveViewController.class),
    Connection(ConnectionViewController.class),
    DeviceInfo(DeviceInfoViewController.class),
    Main(MainViewController.class),
    Offline(OfflineViewController.class),
    Online(OnlineViewController.class),
    Plot(PlotViewController.class);

    private final Class controllerClass;

    View(Class controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Class getControllerClass() {
        return controllerClass;
    }
}
