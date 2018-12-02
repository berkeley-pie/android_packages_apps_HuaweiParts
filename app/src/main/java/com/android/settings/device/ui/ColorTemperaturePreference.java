package com.android.settings.device.ui;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;

import com.android.settings.device.DeviceSettings;
import com.android.settings.device.R;

public class ColorTemperaturePreference extends DialogPreference {

    private Context mContext;

    public ColorTemperaturePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public int getValue() {
        try {
            return Settings.Secure.getInt(mContext.getContentResolver(), DeviceSettings.COLOR_TEMPERATURE_KEY);
        } catch (Settings.SettingNotFoundException ex) {
            return DeviceSettings.COLOR_TEMPERATURE_DEFAULT;
        }
    }

    public void setValue(int value) {
        Settings.Secure.putInt(mContext.getContentResolver(), DeviceSettings.COLOR_TEMPERATURE_KEY, value);
    }

    @Override
    public int getDialogLayoutResource() {
        return R.layout.preference_seekbar;
    }
}
