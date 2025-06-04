package com.danzucker.echojournal.echos.presentation.echos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danzucker.echojournal.R
import com.danzucker.echojournal.core.presentation.designsystem.dropdown.Selectable
import com.danzucker.echojournal.core.presentation.util.UiText
import com.danzucker.echojournal.echos.presentation.echos.models.EchoFilterChip
import com.danzucker.echojournal.echos.presentation.echos.models.MoodChipContent
import com.danzucker.echojournal.echos.presentation.models.MoodUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class EchosViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(EchosState())

    private val selectedMoodFilters = MutableStateFlow<List<MoodUi>>(emptyList())
    private val selectedTopicFilters = MutableStateFlow<List<String>>(emptyList())

    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                observeFilters()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = EchosState()
        )

    fun onAction(action: EchosAction) {
        when (action) {
            EchosAction.OnAudioPermissionGranted -> {}
            EchosAction.OnCancelRecording -> {}
            EchosAction.OnCompleteRecording -> {}
            EchosAction.OnDismissMoodDropDown,
            EchosAction.OnDismissTopicDropDown -> {
                _state.update {
                    it.copy(
                        selectedEchoFilterChip = null
                    )
                }
            }
            is EchosAction.OnFilterByMoodClick -> {
                toggleMoodFilter(action.moodUi)
            }
            is EchosAction.OnFilterByTopicClick -> {
                toggleTopicFilter(action.topic)
            }

            EchosAction.OnPauseAudioClick -> {}
            EchosAction.OnPauseRecordingClick -> {}
            is EchosAction.OnPlayEchoClick -> {}
            EchosAction.OnRecordButtonLongClick -> {}
            EchosAction.OnRecordFabClick -> {}
            is EchosAction.OnRemoveFilters -> {
                when (action.filterType) {
                    EchoFilterChip.MOODS -> selectedMoodFilters.update { emptyList() }
                    EchoFilterChip.TOPICS -> selectedTopicFilters.update { emptyList() }
                }
            }
            EchosAction.OnRequestPermissionQuickRecording -> {}
            EchosAction.OnResumeRecordingClick -> {}
            EchosAction.OnSettingsClick -> {}
            EchosAction.OnMoodChipClick -> {
                _state.update {
                    it.copy(
                        selectedEchoFilterChip = EchoFilterChip.MOODS
                    )
                }
            }
            EchosAction.OnTopicChipClick -> {
                _state.update {
                    it.copy(
                        selectedEchoFilterChip = EchoFilterChip.TOPICS
                    )
                }
            }

            is EchosAction.OnTrackSizeAvailable -> {}
        }
    }

    private fun observeFilters() {
        combine(
            selectedMoodFilters,
            selectedTopicFilters
        ) { selectedMoods, selectedTopics ->

            _state.update {
                it.copy(
                    topics = it.topics.map { selectableTopic ->
                        Selectable(
                            item = selectableTopic.item,
                            selected = selectedTopics.contains(selectableTopic.item)
                        )
                    },
                    moods = MoodUi.entries.map { mood ->
                        Selectable(
                            item = mood,
                            selected = selectedMoods.contains(mood)
                        )
                    },
                    hasActiveMoodFilters = selectedMoods.isNotEmpty(),
                    hasActiveTopicFilters = selectedTopics.isNotEmpty(),
                    topicChipTitle = selectedTopics.deriveTopicsToText(),
                    moodChipContent = selectedMoods.asMoodChipContent(),
                )
            }

        }.launchIn(viewModelScope)
    }

    private fun toggleMoodFilter(moodUi: MoodUi) {
        selectedMoodFilters.update { selectedMoods ->
            if (moodUi in selectedMoods) {
                selectedMoods - moodUi
            } else {
                selectedMoods + moodUi
            }
        }
    }

    private fun toggleTopicFilter(topic: String) {
        selectedTopicFilters.update { selectedTopics ->
            if (topic in selectedTopics) {
                selectedTopics - topic
            } else {
                selectedTopics + topic
            }
        }
    }

    private fun List<String>.deriveTopicsToText(): UiText {
        return when (size) {
            0 -> UiText.StringResourceWithArgs(R.string.all_topics)
            1 -> UiText.DynamicString(this.first())
            2 -> UiText.DynamicString("${this.first()}, ${this.last()}")
            else -> {
                val extraElementCount = size - 2
                UiText.DynamicString("${this.first()}, ${this[1]} +$extraElementCount")
            }
        }
    }

    private fun List<MoodUi>.asMoodChipContent(): MoodChipContent {
        if (this.isEmpty()) {
            return MoodChipContent()
        }

        val icons = this.map { it.iconSet.fill }
        val moodNames = this.map { it.title }

        return when (size) {
            1 -> MoodChipContent(
                iconsRes = icons,
                title = moodNames.first()
            )

            2 -> MoodChipContent(
                iconsRes = icons,
                title = UiText.Combined(
                    format = "%s, %s",
                    uiTexts = moodNames.toTypedArray()
                )
            )

            else -> {
                val extraElementCount = size - 2
                MoodChipContent(
                    iconsRes = icons,
                    title = UiText.Combined(
                        format = "%s, %s +$extraElementCount",
                        uiTexts = moodNames.take(2).toTypedArray()
                    )
                )
            }
        }
    }


}