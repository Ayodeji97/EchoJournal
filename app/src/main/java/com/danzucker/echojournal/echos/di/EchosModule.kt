package com.danzucker.echojournal.echos.di

import com.danzucker.echojournal.echos.data.audio.AndroidAudioPlayer
import com.danzucker.echojournal.echos.data.echo.RoomEchoDataSource
import com.danzucker.echojournal.echos.data.recording.AndroidVoiceRecorder
import com.danzucker.echojournal.echos.data.recording.InternalRecordingStorage
import com.danzucker.echojournal.echos.domain.audio.AudioPlayer
import com.danzucker.echojournal.echos.domain.echo.EchoDataSource
import com.danzucker.echojournal.echos.domain.recording.RecordingStorage
import com.danzucker.echojournal.echos.domain.recording.VoiceRecorder
import com.danzucker.echojournal.echos.presentation.createecho.CreateEchoViewModel
import com.danzucker.echojournal.echos.presentation.echos.EchosViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val echoModule = module {
    singleOf(::AndroidVoiceRecorder) bind VoiceRecorder::class
    singleOf(::InternalRecordingStorage) bind RecordingStorage::class
    singleOf(::AndroidAudioPlayer) bind AudioPlayer::class
    singleOf(::RoomEchoDataSource) bind EchoDataSource::class
//    singleOf(::DataStoreSettings) bind SettingsPreferences::class
//
    viewModelOf(::EchosViewModel)
    viewModelOf(::CreateEchoViewModel)
//    viewModelOf(::SettingsViewModel)
}