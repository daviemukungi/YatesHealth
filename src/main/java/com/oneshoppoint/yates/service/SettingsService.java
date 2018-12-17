package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.wrapper.Settings;


/**
 * Created by robinson on 5/20/16.
 */
public interface SettingsService {
    void save(Settings settings);
    Settings get();
}
