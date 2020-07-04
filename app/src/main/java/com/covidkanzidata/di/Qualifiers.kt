package com.covidkanzidata.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class StantonAPI

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScopeIO

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PrintInt

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PrintString
