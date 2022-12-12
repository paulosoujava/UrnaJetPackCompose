package com.intelbras.urna.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.intelbras.urna.UrnaViewModel
import com.intelbras.urna.presentation.StateVote

@Composable
fun RowScope.Keyboard(isZone: Boolean = false, viewModel: UrnaViewModel) {


    Column(
        modifier = Modifier
            .weight(4f)
            .background(color = Color.Black.copy(alpha = .5f))
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NumbersVisor(viewModel, isZone)
        Footer(viewModel, isZone)

    }

}

@Composable
private fun Footer(viewModel: UrnaViewModel, isZone: Boolean) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {

            Button(
                onClick = {
                    viewModel.action(
                        StateVote.WHITE,
                        isZone
                    )
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(text = "BRANCO", color = Color.Black)
            }
            Spacer(modifier = Modifier.width(9.dp))
            Button(
                onClick = { viewModel.clear(isZone) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFCD6636))
            ) {
                Text(text = "CORRIGE", color = Color.Black)
            }
            Spacer(modifier = Modifier.width(9.dp))
            Button(
                modifier = Modifier.height(80.dp),
                onClick = {
                    viewModel.action(
                        StateVote.CONFIRM,
                        isZone
                    )
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF109747))
            ) {
                Text(text = "CONFIRMA", color = Color.Black)
            }

    }
}


@Composable
private fun NumbersVisor(viewModel: UrnaViewModel, isZone: Boolean) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UrnaBtn(1, onClick = { viewModel.switch(1, isZone) })
                Spacer(modifier = Modifier.width(5.dp))
                UrnaBtn(2, onClick = { viewModel.switch(2, isZone) })
                Spacer(modifier = Modifier.width(5.dp))
                UrnaBtn(3, onClick = { viewModel.switch(3, isZone) })
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UrnaBtn(4, onClick = { viewModel.switch(4, isZone) })
                Spacer(modifier = Modifier.width(5.dp))
                UrnaBtn(5, onClick = { viewModel.switch(5, isZone) })
                Spacer(modifier = Modifier.width(5.dp))
                UrnaBtn(6, onClick = { viewModel.switch(6, isZone) })
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UrnaBtn(7, onClick = { viewModel.switch(7, isZone) })
                Spacer(modifier = Modifier.width(5.dp))
                UrnaBtn(8, onClick = { viewModel.switch(8, isZone) })
                Spacer(modifier = Modifier.width(5.dp))
                UrnaBtn(9, onClick = { viewModel.switch(9, isZone) })
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.weight(1f))
                UrnaBtn(0, onClick = { viewModel.switch(0, isZone) })
                Spacer(modifier = Modifier.weight(1f))
            }
        }

}