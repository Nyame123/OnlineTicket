package com.bismark.onlineticket;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelFactories implements ViewModelProvider.Factory {

    @Inject
    Map<Class<? extends ViewModel>, Provider<? extends ViewModel>> providerMap;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) providerMap.get(modelClass);
    }
}
