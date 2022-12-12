package com.intelbras.urna


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intelbras.urna.model.Vote
import com.intelbras.urna.model.Zone
import com.intelbras.urna.model.list
import com.intelbras.urna.model.listFail
import com.intelbras.urna.presentation.StateVote
import com.intelbras.urna.presentation.UiState
import com.intelbras.urna.presentation.UrnaState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random


class UrnaViewModel : ViewModel() {

    private val _state = MutableStateFlow(UrnaState())
    val state: StateFlow<UrnaState> = _state


    private val _numberZone = MutableStateFlow<Triple<String, String, String>?>(null)
    val numberZone: StateFlow<Triple<String, String, String>?> = _numberZone

    private val _numberVote = MutableStateFlow<Pair<String, String>?>(null)
    val numberVote: StateFlow<Pair<String, String>?> = _numberVote

    var vote: Vote? = null

    init {
        viewModelScope.launch {
            _numberVote.collect {
                val (n1, n2) = it ?: Pair("", "")
                if (n1.isNotEmpty() && n2.isNotEmpty()) {
                    getPresident(n1, n2)
                }
            }
        }
    }

    fun redirection(uiState: UiState) {
        _state.update {
            it.copy(state = uiState)
        }
    }

    fun clear(isSetUp: Boolean) {
        if (isSetUp) {
            _numberZone.value = null
        } else {
            _numberVote.value = Pair("", "")
            _state.update { it.copy(currentVote = null) }


        }
    }

    fun switch(num: Int, isSetUp: Boolean) {
        if (isSetUp) {
            setUpZone(num.toString())
        } else {
            vote(num.toString())
        }

    }

    private fun vote(number: String) {
        val (n1, n2) = _numberVote.value ?: Pair("", "")
        if (n1.isEmpty()) {
            _numberVote.value = Pair(number, n2)
        }
        if (n2.isEmpty()) {
            _numberVote.value = Pair(n1, number)

        }

    }

    private fun getPresident(n1: String, n2: String) {
        vote = list.find { it.number1 == n2 && it.number2 == n1 }
        if (vote != null)
            _state.update { it.copy(currentVote = vote) }

    }


    private fun setUpZone(number: String) {
        val (n1, n2, n3) = _numberZone.value ?: Triple("", "", "")

        if (n1.isEmpty()) {
            _numberZone.value = Triple(number, n2, n3)
        }
        if (n2.isEmpty()) {
            _numberZone.value = Triple(n1, number, n3)
        }
        if (n3.isEmpty()) {
            _numberZone.value = Triple(n1, n2, number)
        }
    }


    fun action(state: StateVote, isSetUp: Boolean) {
        when (state) {
            StateVote.WHITE -> {}
            StateVote.CONFIRM -> {
                if (isSetUp) {
                    val (n1, n2, n3) = _numberZone.value ?: Triple("", "", "")
                    if (n1.isNotEmpty() && n2.isNotEmpty() && n3.isNotEmpty())
                        _state.update {
                            it.copy(
                                zone = numberZone.value?.let { list -> Zone("$list") },
                                state = UiState.VoteState
                            )
                        }
                } else {
                    _state.update {
                        it.copy(state = UiState.END)
                    }


                    val codeFail = Zone(number = Triple("4", "2", "1").toString())
                    computeVote(_state.value.zone == codeFail)
                }
            }

        }
    }

    private fun computeVote(isFail: Boolean) {
        var newList = _state.value.vote
        if(isFail){
            newList = listFail
        }else{

            val vote = _state.value.currentVote
            if (newList == null || newList.isEmpty() && vote != null) {
                vote!!.qtdVote += 1
                val list = mutableListOf(vote!!)
                newList = list
            } else {
                val hasPerson =
                    newList.find { it.number1 == vote?.number1 && it.number2 == vote.number2 }
                if (hasPerson != null) {
                    vote!!.qtdVote = hasPerson.qtdVote + 1
                    newList.remove(hasPerson)
                    newList.add(vote)
                } else {
                    vote!!.qtdVote += 1
                    newList.add(vote!!)
                }
            }
        }





        viewModelScope.launch {

            delay(1000)

            _state.update {
                it.copy(
                    vote = newList,
                    state = UiState.VoteState,
                    currentVote = null
                )
            }

            clear(false)
        }
    }


}