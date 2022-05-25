package com.bismark.onlineticket.di;

import androidx.lifecycle.ViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

@Target({
        ElementType.METHOD,
        ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface ViewModelKeys  {
    Class<? extends ViewModel> value();
}
