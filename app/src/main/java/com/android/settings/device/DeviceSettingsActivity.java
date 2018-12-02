package com.android.settings.device;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class DeviceSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.device_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (fragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, new DeviceSettings())
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
