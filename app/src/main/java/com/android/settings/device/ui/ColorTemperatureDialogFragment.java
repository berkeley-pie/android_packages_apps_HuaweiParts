package com.android.settings.device.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.PreferenceDialogFragmentCompat;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.settings.device.DeviceSettings;
import com.android.settings.device.R;

public class ColorTemperatureDialogFragment extends PreferenceDialogFragmentCompat implements SeekBar.OnSeekBarChangeListener {

    private static String STATE_VALUE = "value";

    private ColorTemperaturePreference seekBarPreference;
    private int value;

    private SeekBar mSeekBar;
    private TextView mValueLabel;

    public static ColorTemperatureDialogFragment newInstance(String key) {
        ColorTemperatureDialogFragment fragment = new ColorTemperatureDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_KEY, key);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seekBarPreference = (ColorTemperaturePreference) getPreference();
        value = savedInstanceState != null ? savedInstanceState.getInt(STATE_VALUE) : seekBarPreference.getValue();
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        mSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
        mValueLabel = (TextView) view.findViewById(R.id.value);

        mSeekBar.setMax(255);
        mSeekBar.setProgress(value);
        mSeekBar.setOnSeekBarChangeListener(this);

        mValueLabel.setText(String.valueOf(value));
    }

    @Override
    public void onDialogClosed(boolean result) {
        if (result) {
            seekBarPreference.setValue(mSeekBar.getProgress());
        }
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);

        builder.setNeutralButton(R.string.set_default, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                seekBarPreference.setValue(DeviceSettings.COLOR_TEMPERATURE_DEFAULT);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_VALUE, mSeekBar.getProgress());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mValueLabel.setText(String.valueOf(mSeekBar.getProgress()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // LOL WHO CARES
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // LOL WHO CARES
    }
}
