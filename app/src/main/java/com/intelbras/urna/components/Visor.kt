package com.intelbras.urna.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intelbras.urna.UrnaViewModel
import com.intelbras.urna.model.Vote


@Composable
fun RowScope.Visor(
    title: String = "PRESIDENTE",
    isZone: Boolean = false,
    viewModel: UrnaViewModel
) {

    val state = viewModel.state.collectAsState()
    val numberVote = viewModel.numberVote.collectAsState()
    val numberZone = viewModel.numberZone.collectAsState()



    Column(
        modifier = Modifier.weight(4f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        val space = if(state.value.currentVote == null) 120.dp else 0.dp
        Spacer(modifier = Modifier.height(space))
        AnimatedVisibility(
            visible = !isZone && state.value.currentVote != null,
            enter = slideInHorizontally(
                initialOffsetX = { -300 }, // small slide 300px
                animationSpec = tween(
                    durationMillis = 200,
                    easing = LinearEasing // interpolator
                )
            ),
            exit = slideOutHorizontally(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = LinearEasing
                )
            )

        ) {
            state.value.currentVote?.let { ShowThePresident(it) }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .border(1.dp, color = Color.Black)
                    .size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isZone) numberZone.value?.third ?: "" else numberVote.value?.second
                        ?: "",
                    fontSize = 25.sp, fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Box(
                modifier = Modifier
                    .border(1.dp, color = Color.Black)
                    .size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isZone) numberZone.value?.second ?: "" else numberVote.value?.first
                        ?: "",
                    fontSize = 25.sp, fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(5.dp))

            ScreeSetup(isZone = isZone, number = viewModel.numberZone.value?.first ?: "")

        }
    }
}

@Composable
private fun ScreeSetup(isZone: Boolean, number: String) {
    AnimatedVisibility(visible = isZone) {

        Box(
            modifier = Modifier
                .border(1.dp, color = Color.Black)
                .size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                fontSize = 25.sp, fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ShowThePresident(vote: Vote) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 40.dp, top = 20.dp, bottom = 20.dp)
    ) {
        Image(
            painter = painterResource(id = vote.photo),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = vote.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "${vote.number1} ${vote.number2}",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}