@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.danzucker.echojournal.echos.presentation.createecho.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.danzucker.echojournal.R
import com.danzucker.echojournal.core.presentation.designsystem.buttons.PrimaryButton
import com.danzucker.echojournal.core.presentation.designsystem.buttons.SecondaryButton
import com.danzucker.echojournal.echos.presentation.components.MoodSelectorRow
import com.danzucker.echojournal.echos.presentation.models.MoodUi

@Composable
fun SelectMoodSheet(
    selectedMood: MoodUi,
    onMoodClick: (MoodUi) -> Unit,
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val allMoods = MoodUi.entries.toList()
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.how_are_you_doing),
                style = MaterialTheme.typography.titleMedium
            )

            MoodSelectorRow(
                selectedMood = selectedMood,
                onMoodClick = onMoodClick,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SecondaryButton(
                    text = stringResource(R.string.cancel),
                    onClick = onDismiss
                )
                PrimaryButton(
                    text = stringResource(R.string.confirm),
                    onClick = onConfirmClick,
                    modifier = Modifier.weight(1f),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(R.string.confirm),
                        )
                    }
                )
            }
        }
    }
}