package com.intelbras.urna.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intelbras.urna.UrnaViewModel

@Composable
fun SetupZone(
    viewModel: UrnaViewModel,
    isEnd: Boolean = false
) {
    Row(
        modifier = Modifier.padding(10.dp)
    ) {

        Visor(
            title = "Cadastre a ZONA",
            isZone = true,
            viewModel = viewModel
        )
        Keyboard(true, viewModel)
    }
}