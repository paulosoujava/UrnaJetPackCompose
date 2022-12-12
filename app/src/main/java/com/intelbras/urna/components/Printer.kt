package com.intelbras.urna.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intelbras.urna.R
import com.intelbras.urna.UrnaViewModel
import com.intelbras.urna.model.Vote
import com.intelbras.urna.model.list
import com.intelbras.urna.presentation.UiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Printer(viewModel: UrnaViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Text(
                text = "Voltar",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier
                    .clickable { viewModel.redirection(UiState.InititalState) }
                    .padding(20.dp)
            )
        }

    ) {
        val state = viewModel.state.collectAsState()
        val list = state.value.vote as List<Vote>

        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Start
        ) {
            items(list.sortedBy { it.qtdVote }.reversed()) { item ->
                Card(
                    modifier = Modifier.padding(4.dp),
                    backgroundColor = Color.LightGray
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = item.photo),
                            contentDescription = null,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Column {
                            Text(text = "Nome: ${item.name}")
                            Text(text = "Partido: ${item.number1} ${item.number2}")
                            Text(
                                text = "Votos : ${item.qtdVote}",
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }
                }

            }
        }
    }
}