package com.danzucker.echojournal.echos.presentation.echos.models

enum class PlaybackState {
    PLAYING,
    PAUSED,
    STOPPED;


    companion object {
        fun fromString(value: String): PlaybackState {
            return when (value.lowercase()) {
                "playing" -> PLAYING
                "paused" -> PAUSED
                "stopped" -> STOPPED
                else -> STOPPED // Default state if the value is unknown
            }
        }
    }
}