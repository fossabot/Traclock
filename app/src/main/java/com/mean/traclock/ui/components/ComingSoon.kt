package com.mean.traclock.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun ComingSoon() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
    ) {
        LottieWorkingLoadingView()
        Text(
            text = "Coming Soon",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LottieWorkingLoadingView() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("working.json"))
    LottieAnimation(
        composition,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .defaultMinSize(300.dp),
        iterations = 10
    )
}
