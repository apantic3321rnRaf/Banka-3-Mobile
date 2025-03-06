package com.example.banka_3_mobile.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
): ViewModel() {
    private val _state = MutableStateFlow(LoginContract.LoginUiState())
    val state: StateFlow<LoginContract.LoginUiState> = _state.asStateFlow()

    private fun setState(reducer: LoginContract.LoginUiState.() -> LoginContract.LoginUiState) =
        _state.update(reducer)

    private  val events = MutableSharedFlow<LoginContract.LoginUIEvent>()
    fun setEvent(event: LoginContract.LoginUIEvent) = viewModelScope.launch {
        events.emit(event)
    }

    init {
        observeEvents()
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    LoginContract.LoginUIEvent.Login -> validate()
                }
            }
        }
    }

    private fun validate() {
        setState { copy (incorrectEmailFormat = true) }
    }
}