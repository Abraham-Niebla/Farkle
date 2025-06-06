package uabc.farkle.presentation

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import uabc.farkle.utils.*
import uabc.farkle.R
import uabc.farkle.dialogs.NoInternetDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RulesView(modifier: Modifier) {
    val context = LocalContext.current
    var isConnected by remember { mutableStateOf(true) }

    // Checar al abrir
    LaunchedEffect(Unit) {
        isConnected = networkAvailable(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                title = {
                    Text(
                        text = stringResource(R.string.rules),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val activity = context as? Activity
                        activity?.finish()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.page_return)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isConnected){
            WebViewWithLoadingIndicator(
                url = RULES_URL,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
        else{
            NoInternetDialog {
                val activity = context as? Activity
                activity?.finish()
            }
        }
    }
}