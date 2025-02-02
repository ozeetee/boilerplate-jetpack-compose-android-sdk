package io.atomic.sdk.screens.inbox

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.atomic.actioncards.feed.data.model.AACCardInstance
import com.io.atomic.jetpackcomposesdk.sdk.ComposableStreamContainer
import io.atomic.sdk.R
import io.atomic.sdk.components.CardDetails
import io.atomic.sdk.components.ComposableLifecycle
import java.text.DateFormat
import java.util.Calendar

@Composable
fun InboxRoute(navController: NavHostController) {
    val viewModel = hiltViewModel<InboxViewModel>()
    Surface(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        MaterialTheme {

            val toast = Toast.makeText(
                LocalContext.current,
                "You have just interacted with a composable",
                Toast.LENGTH_LONG
            )

            Column {
                Row {
                    CardDetails(
                        modifier = Modifier.padding(50.dp),
                        title = stringResource(R.string.title),
                        description = stringResource(R.string.description),
                        onClick = { navController.navigate("dummy") },
                        buttonLabel = stringResource(R.string.button_label)
                    )
                }
                Row {
                    // Pass the stream container into the ComposableStreamContainer
                    viewModel.streamContainer?.let {
                        ComposableStreamContainer(
                            modifier = Modifier.fillMaxSize(),
                            streamContainer = it
                        )
                    }
                }
            }
        }

        val supportFragmentManager =
            (LocalContext.current as AppCompatActivity).supportFragmentManager
        //Observe Lifecycle events
        ComposableLifecycle { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE,
                Lifecycle.Event.ON_RESUME ->
                    applyHandlers(viewModel = viewModel)

                Lifecycle.Event.ON_PAUSE ->
                    applyHandlers(viewModel = viewModel, shallReset = true)

                Lifecycle.Event.ON_DESTROY ->
                    viewModel.streamContainer?.destroy(supportFragmentManager)

                else ->
                    Log.d("TAG", "Out of life cycle")

            }
        }
    }

}


/** This is currently only setting runtime variables handler, but you could also setup
 * any handlers for link and submit buttons in here too */
private fun applyHandlers(
    viewModel: InboxViewModel,
    shallReset: Boolean = false
) {

    if (shallReset) {
        viewModel.streamContainer?.cardDidRequestRunTimeVariablesHandler = null
    }

    viewModel.streamContainer?.cardDidRequestRunTimeVariablesHandler = { cards, done ->
        cardDidRequestRunTimeVariablesHandler(cards, done)
    }
}

/** here is where we apply runtime variables to a card.
 * Action any you have in your cards here. */
private fun cardDidRequestRunTimeVariablesHandler(
    cards: List<AACCardInstance>,
    done: (cardsWithResolvedVariables: List<AACCardInstance>) -> Unit
) {

    for (card in cards) {

        val customerName = "Atomic guy!!!"

        val longDf: DateFormat = DateFormat.getDateInstance(DateFormat.SHORT)
        val shortDf: DateFormat = DateFormat.getDateInstance(DateFormat.LONG)
        val today = Calendar.getInstance().time
        val formattedLongDate = longDf.format(today)
        val formattedShortDate = shortDf.format(today)

        // Resolve the below runtime variables
        card.resolveVariableWithNameAndValue("dateShort", formattedShortDate)
        card.resolveVariableWithNameAndValue("dateLong", formattedLongDate)
        card.resolveVariableWithNameAndValue("customer_name", customerName)
    }

    done(cards)
}