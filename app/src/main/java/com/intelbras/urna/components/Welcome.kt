package com.intelbras.urna.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Welcome(
    count: () -> Unit,
    conf: () -> Unit,
    list: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "CONFIGURAR URNA",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier
                .clickable { conf() }
                .padding(20.dp)
        )
        Text(
            text = "LISTA DE CANDIDATOS",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier
                .clickable { list() }
                .padding(20.dp)
        )
        Text(
            text = "CONTAGEM",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier
                .clickable { count() }
                .padding(20.dp)
        )
    }
}