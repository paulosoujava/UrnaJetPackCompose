package com.intelbras.urna.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intelbras.urna.UrnaViewModel
import com.intelbras.urna.model.Vote


@Composable
fun Start(
    viewModel: UrnaViewModel,
    isZone: Boolean,
    isEnd: Boolean = false,
    finish: () -> Unit
) {
    Column {
        TextButton(onClick = finish) {
            Text(text = "Encerrar")
        }
        Row(
            modifier = Modifier.padding(10.dp)
        ) {

            if(!isEnd){
                Visor(viewModel=viewModel)
            }

            if(isEnd){
                End()
            }
            Keyboard(isZone, viewModel)
        }
    }

}