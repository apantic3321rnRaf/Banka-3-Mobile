package com.example.banka_3_mobile.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.loginPage(
    route: String,
    navController: NavController,
) = composable(
    route = route,
) {navBackStackEntry ->

    val loginViewModel = hiltViewModel<LoginViewModel>(navBackStackEntry)
    val state by loginViewModel.state.collectAsState()

    LoginScreen(
        state = state,
        eventPublisher = {
            loginViewModel.setEvent(it)
        }
    )
}

@Composable
fun LoginScreen(
    state: LoginContract.LoginUiState,
    eventPublisher: (uiEvent: LoginContract.LoginUIEvent) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        content = {
            when {
                /*state.error != null ->
                    ErrorScreen(message = state.error.message!!)*/
                else ->
                    LoginColumn(state = state, eventPublisher = eventPublisher)
            }
        })
}

@Composable
fun LoginColumn(
    state: LoginContract.LoginUiState,
    eventPublisher: (uiEvent: LoginContract.LoginUIEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
      //  horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(top = 30.dp),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            text = "Login"
        )
        Spacer(modifier = Modifier.height(60.dp))
        if (state.incorrectEmailFormat) {
            Text(
                text = "Hello Banka-3 :)",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
         //       modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(60.dp))
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { eventPublisher(LoginContract.LoginUIEvent.Login) },
         //       modifier = Modifier.align(Alignment.Center)
            ) {
                Text("Login")
            }
        }

    }
}