/*
 * Copyright (C) 2018 The LineageOS Project
 * Copyright (C) 2018 David Sn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.device;

import android.os.SystemProperties;
import android.util.Log;

import com.android.server.display.DisplayEngineService;
import com.android.server.display.DisplayEngineService_V1_0;
import com.android.server.display.DisplayEngineService_V1_1;
import com.android.server.HwSmartDisplayService;
import com.android.server.power.HwPowerManagerService;

import vendor.huawei.hardware.tp.V1_0.ExtTouchScreen;

/*
 * Display Modes API
 *
 * A device may implement a list of preset display modes for different
 * viewing intents, such as movies, photos, or extra vibrance. These
 * modes may have multiple components such as gamma correction, white
 * point adjustment, etc, but are activated by a single control point.
 *
 * This API provides support for enumerating and selecting the
 * modes supported by the hardware.
 */

public class DisplayEngineController {

    private static final String DISPLAY_MODE_NORMAL = "Normal";
    private static final String DISPLAY_MODE_VIVID = "Vivid";

    private static final int[] AVAILABLE_MODES = {
            0, //DISPLAY_MODE_NORMAL
            1, //DISPLAY_MODE_VIVID
    };

    private static final String DISPLAY_ENGINE_V1_0_PROP = "init.svc.displayengine-hal-1-0";
    private static final String DISPLAY_ENGINE_V1_1_PROP = "init.svc.displayengine-hal-1-1";

    public static DisplayEngineService sDisplayEngineService;
    public static int sColorEnhancementCurrentMode;
    public static HwSmartDisplayService sHwSmartDisplayService;
    public static HwPowerManagerService sHwPowerManager;
    public static ExtTouchScreen sExtTouchScreen;

    static {
        try {
            if (SystemProperties.get(DISPLAY_ENGINE_V1_0_PROP, "") != "") {
                sDisplayEngineService = new DisplayEngineService_V1_0();
            } else if (SystemProperties.get(DISPLAY_ENGINE_V1_1_PROP, "") != "") {
                sDisplayEngineService = new DisplayEngineService_V1_1();
            }

            sHwSmartDisplayService = new HwSmartDisplayService();
            sHwSmartDisplayService.init_native();

            sHwPowerManager = new HwPowerManagerService();
            sExtTouchScreen = new ExtTouchScreen();

            sColorEnhancementCurrentMode = 0;

            Log.d("DisplayEngineController", "DisplayEngine initialized");
        } catch (Throwable t) {
            Log.d("DisplayEngineController", "DisplayEngineService is unavailable");
        }
    }

    public static boolean isAvailable() {
        return sDisplayEngineService != null && sDisplayEngineService.isColorModeSupported();
    }

    public static int[] getAvailableModes() {
        if (sDisplayEngineService == null) {
            return new int[0];
        }

        return AVAILABLE_MODES;
    }

    public static int getCurrentMode() {
        if (sDisplayEngineService == null) {
            return -1;
        }

        return sColorEnhancementCurrentMode;
    }

    public boolean setMode(int mode) {
        if (sDisplayEngineService == null) {
            return false;
        }

        sColorEnhancementCurrentMode = mode;
        if (sColorEnhancementCurrentMode == 0) {
            sDisplayEngineService.enableColorMode(false);
        } else if (sColorEnhancementCurrentMode == 1) {
            sDisplayEngineService.enableColorMode(true);
        }

        // 2 : COLOR_ENHANCEMENT (1 : Eye Comfort)
        sHwSmartDisplayService.nativeSetSmartDisplay(2, sColorEnhancementCurrentMode);
        return true;
    }

    public int getDefaultMode() {
        if (sDisplayEngineService == null) {
            return -1;
        }

        return 0;
    }

    public String getModeEntry(int mode) {
        String entry = null;
        if (mode == 0) {
            entry = DISPLAY_MODE_NORMAL;
        } else if (mode == 1) {
            entry = DISPLAY_MODE_VIVID;
        }

        return entry;
    }
}