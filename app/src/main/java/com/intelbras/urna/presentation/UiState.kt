package com.intelbras.urna.presentation

interface UiState {
    object InititalState : UiState
    object ConfigState : UiState
    object VoteState : UiState
    object PrintState : UiState
    object END : UiState
    object LIST : UiState
}