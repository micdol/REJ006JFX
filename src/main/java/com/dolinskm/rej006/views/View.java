package com.dolinskm.rej006.views;

public enum View {
    Archive(ArchiveViewController.class),
    Connection(ConnectionViewController.class),
    DeviceInfo(DeviceInfoViewController.class),
    Main(MainViewController.class),
    // TODO offline views: settings and download - should be under in common view (TabPane?)
    Offline(OfflineRegistrationDownloadViewController.class),
    Online(OnlineRegistrationViewController.class),
    Plot(PlotViewController.class);

    private final Class controllerClass;

    View(Class controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Class getControllerClass() {
        return controllerClass;
    }
}
