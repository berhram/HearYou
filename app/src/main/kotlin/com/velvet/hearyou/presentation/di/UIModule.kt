package com.velvet.hearyou.presentation.di

import com.velvet.hearyou.presentation.ManagePermission
import com.velvet.hearyou.presentation.SpeechRecognition
import com.velvet.hearyou.presentation.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        MainViewModel(get(), get())
    }

    single<SpeechRecognition> {
        SpeechRecognition.Base(androidContext())
    }

    single<ManagePermission> {
        ManagePermission.Base()
    }
}