package io.atomic.sdk.screens.multicards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.io.atomic.jetpackcomposesdk.sdk.ComposableStreamContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiCardsRoute(navController: NavHostController) {
    val viewModel = hiltViewModel<MultiCardsViewModel>()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dummy Screen") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier
            .padding(it)
            .verticalScroll(rememberScrollState())) {
            Row{
                viewModel.streamContainerBottom?.let { streamContainer ->
                    Row {
                        Text(text = "ContainerId :")
                        Text(text = streamContainer.containerId)
                    }
                    ComposableStreamContainer(streamContainer = streamContainer)
                }
            }
            Row {
                viewModel.streamContainerTop?.let { streamContainer ->
                    Row {
                        Text(text = "ContainerId :")
                        Text(text = streamContainer.containerId)
                    }
                    ComposableStreamContainer(streamContainer = streamContainer)
                }
            }
        }
    }
}
