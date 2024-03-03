package com.example.jetpackdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackdemo.ui.theme.JetPackDemoTheme
import com.example.jetpackdemo.view_models.ProgressViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackDemoTheme {
                MyApp()
            }
        }
    }

    @Composable
    fun MyApp(
        viewModel: ProgressViewModel = viewModel()
    ) {
        Column {
            HeaderSection(modifier = Modifier.weight(0.1f))
            BodySection(modifier = Modifier.weight(0.8f), viewModel.progress)
            FooterSection(modifier = Modifier.weight(0.1f)) { viewModel.increase()}
        }
    }


    @Composable
    fun HeaderSection(
        modifier: Modifier,
    ) {
        Surface(
            modifier = modifier.fillMaxWidth(1f),
            color = MaterialTheme.colorScheme.primary
        ) {
            Text(
                text = "ANIMATE SAMPLE",
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.wrapContentHeight(Alignment.CenterVertically, true)
            )
        }
    }


    @Composable
    fun BodySection(
        modifier: Modifier,
        progress: Float
    ) {
        Box(
            modifier = modifier.fillMaxSize(1f),
            contentAlignment = Alignment.Center
        ) {
            val progressState by animateFloatAsState(
                targetValue = progress, label=""
            )
            CircularProgressIndicator(progress = progressState)
        }
    }


    @Composable
    fun FooterSection(
        modifier: Modifier,
        onClick: () -> Unit
    ) {
        Box(
            modifier = modifier
                .fillMaxSize(1f),
            contentAlignment = Alignment.Center
        ) {
            OutlinedButton(
                onClick = {
                    onClick()
                }
            ) {
                Text(text = "INCREASE")
            }
        }
    }
}