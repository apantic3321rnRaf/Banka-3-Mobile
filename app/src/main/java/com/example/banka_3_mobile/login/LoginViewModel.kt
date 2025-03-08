package com.example.banka_3_mobile.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.banka_3_mobile.user.account.datastore.AccountData
import com.example.banka_3_mobile.user.account.datastore.AccountDataStore
import com.example.banka_3_mobile.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountDataStore: AccountDataStore,
    private val userRepository: UserRepository
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
        if (accountDataStore.data.value.email == ""
            //|| accountDataStore.data.value.fullName == ""
        )
            observeEvents()
        else setState { copy(
           // fullName = accountDataStore.data.value.fullName,
            email = accountDataStore.data.value.email,
            loggedIn = true) }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    LoginContract.LoginUIEvent.Login -> validate()
                    is LoginContract.LoginUIEvent.TypingEmail -> {
                        setState { copy(email = it.email) }
                        if(!checkEmailValidity())
                            setState { copy(incorrectEmailFormat = true) }
                        else setState { copy(incorrectEmailFormat = false) }
                    }
                    is LoginContract.LoginUIEvent.TypingPassword -> {
                        setState { copy(password = it.password) }

                    }
                }
            }
        }
    }

    private fun validate() {
        if (!checkEmailValidity())
            setState { copy(incorrectEmailFormat = true) }
        else {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    withContext(Dispatchers.IO) {
                        /*accountDataStore.updateProfileData(
                            AccountData(wh
                                fullName = state.value.fullName,
                                email = state.value.email,
                            )
                        )*/
                        val response =
                            userRepository.login(email = state.value.email,
                                password = state.value.password)
                        setState { copy(response = response.token) }

                    }
               //     Log.d("raf", "u datastore je zapisano ${profileDataStore.data.value}")
                //    setState { copy(loggedIn = true) }
                } catch (e: Exception) {
                    setState { copy(error = e) }
                    Log.e("raf", e.message!!)
                }
            }
        }
    }

    private fun checkEmailValidity(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()
    }

}