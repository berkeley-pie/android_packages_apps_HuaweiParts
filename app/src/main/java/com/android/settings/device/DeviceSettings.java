package com.android.settings.device;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.android.settings.device.ui.ColorTemperatureDialogFragment;
import com.android.settings.device.ui.ColorTemperaturePreference;

public class DeviceSettings extends PreferenceFragmentCompat {

    // Color mode
    public static final String COLOR_MODE_KEY = "color_mode";
    public static final int COLOR_MODE_DEFAULT = 1;

    // Color temperature
    public static final String COLOR_TEMPERATURE_KEY = "color_temperature";
    public static final int COLOR_TEMPERATURE_DEFAULT = 128;

    // Touchscreen glove mode
    public static final String HIGH_TOUCH_SENSITIVITY_KEY = "high_sensitivity_touch";
    public static final int HIGH_TOUCH_SENSITIVITY_DEFAULT = 0;

    @Override
    public void onCreatePreferences(Bundle bundle, String root) {
        setPreferencesFromResource(R.xml.device_settings, root);
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        if (preference instanceof ColorTemperaturePreference) {
            ColorTemperatureDialogFragment dialog = ColorTemperatureDialogFragment.newInstance(preference.getKey());
            FragmentManager fragmentManager = getFragmentManager();
            dialog.setTargetFragment(this, 0);
            dialog.show(fragmentManager, "android.support.v7.preference.PreferenceFragment.DIALOG");

        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }
}
