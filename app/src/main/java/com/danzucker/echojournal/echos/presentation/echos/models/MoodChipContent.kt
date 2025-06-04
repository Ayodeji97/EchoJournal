package com.danzucker.echojournal.echos.presentation.echos.models

import com.danzucker.echojournal.R
import com.danzucker.echojournal.core.presentation.util.UiText

data class MoodChipContent(
    val iconsRes: List<Int> = emptyList(),
    val title: UiText = UiText.StringResourceWithArgs(R.string.all_moods)
)