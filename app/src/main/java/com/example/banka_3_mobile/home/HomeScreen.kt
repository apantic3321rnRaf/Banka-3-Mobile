package com.example.banka_3_mobile.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.homePage(
    route: String,
    navController: NavController,
) = composable(
    route = route,
) {navBackStackEntry ->

    val homeViewModel = hiltViewModel<HomeViewModel>(navBackStackEntry)
    val state by homeViewModel.state.collectAsState()
    HomeScreen(
        state = state,
        eventPublisher = {
            homeViewModel.setEvent(it)
        }
    )
}

@Composable
fun HomeScreen(
    state: HomeContract.HomeUiState,
    eventPublisher: (uiEvent: HomeContract.HomeUIEvent) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        content = {
            /*Text("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
            Text("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
            Text("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
            Text("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
            Text("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
            Text("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
            Text("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")*/

            when {
                /*state.error != null ->
                    ErrorScreen(message = state.error.message!!)*/

                    state.fetching ->
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Text(modifier = Modifier.align(Alignment.Center), text = "Loading account info...")
                        }
                    state.error != null ->
                        Text(color = Color.Red, text = "Hello, ${state.client?.lastName}")
                    else -> {
                        HomeColumn(state = state, eventPublisher = eventPublisher)
                    }


            }
        })
}

@Composable
fun HomeColumn (
    state: HomeContract.HomeUiState,
    eventPublisher: (uiEvent: HomeContract.HomeUIEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text("Hello, ${state.client?.email}")
        Spacer(modifier = Modifier.height(60.dp))
        Text("Hello, ${state.client?.firstName}")
        Spacer(modifier = Modifier.height(60.dp))
        Text("Hello, ${state.client?.lastName}")
        Spacer(modifier = Modifier.height(60.dp))
        Text("Hello, ${state.client?.jmbg}")
        Spacer(modifier = Modifier.height(60.dp))
        Text("Hello, ${state.client?.birthDate}")
        Spacer(modifier = Modifier.height(60.dp))
        Text("Hello, ${state.client?.id}")
    }


}