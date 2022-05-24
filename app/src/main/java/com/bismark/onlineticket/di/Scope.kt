package com.bismark.onlineticket.di

import java.lang.annotation.RetentionPolicy
import javax.inject.Scope as InjectScope

@InjectScope
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ApplicationContext()
