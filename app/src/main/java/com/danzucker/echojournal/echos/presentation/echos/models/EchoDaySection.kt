package com.danzucker.echojournal.echos.presentation.echos.models

import com.danzucker.echojournal.core.presentation.util.UiText
import com.danzucker.echojournal.echos.presentation.models.EchoUi

data class EchoDaySection(
    val dateHeader: UiText,
    val echos: List<EchoUi>
)
