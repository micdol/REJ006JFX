package com.dolinskm.rej006.models.wrappers;

import com.dolinskm.rej006.models.AppSettings;
import javafx.beans.Observable;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

@Component
public class UserPrefsAppSettings extends AppSettings implements IAppSettingsWrapper {

    private final Preferences preferences;

    @SneakyThrows
    public UserPrefsAppSettings() {
        super();

        final File appRootDir = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());

        preferences = Preferences.userRoot();
        final String registrationDirectoryPath = preferences.get("registrations-directory", appRootDir.getCanonicalPath());
        final String settingsDirectoryPath = preferences.get("settings-directory", appRootDir.getCanonicalPath());
        final String lastUsedPortName = preferences.get("last-used-port-name", "COM1");

        setRegistrationsDirectory(new File(registrationDirectoryPath));
        setSettingsDirectory(new File(settingsDirectoryPath));
        setLastUsedPortName(lastUsedPortName);

        lastUsedPortNameProperty().addListener(this::onLastUsedPortNameChanged);
        registrationsDirectoryProperty().addListener(this::onRegistrationDirectoryChanged);
        settingsDirectoryProperty().addListener(this::onSettingsDirectoryChanged);
    }

    private void onSettingsDirectoryChanged(Observable o, File ov, File nv) {
        if (!ov.equals(nv)) {
            try {
                preferences.put("registrations-directory", nv.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onRegistrationDirectoryChanged(Observable o, File ov, File nv) {
        if (!ov.equals(nv)) {
            try {
                preferences.put("settings-directory", nv.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onLastUsedPortNameChanged(Observable o, String ov, String nv) {
        if (!ov.equals(nv)) {
            preferences.put("last-used-port-name", nv);
        }
    }


    @Override
    public AppSettings getAppSettings() {
        return this;
    }
}
