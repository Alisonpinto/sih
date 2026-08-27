package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.example.ui.navigation.VoxShieldNavGraph
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.VoxShieldTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      VoxShieldTheme {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(CyberBlack)
            .safeDrawingPadding()
        ) {
          VoxShieldNavGraph()
        }
      }
    }
  }
}

