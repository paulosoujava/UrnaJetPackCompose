package com.intelbras.urna.presentation


import com.intelbras.urna.model.Vote
import com.intelbras.urna.model.Zone

data class UrnaState(
    val vote: MutableList<Vote>? = null,
    val currentVote: Vote? = null,
    val zone: Zone? = null,
    val state: UiState = UiState.InititalState
)