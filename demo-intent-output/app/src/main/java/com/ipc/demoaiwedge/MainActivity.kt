package com.ipc.demoaiwedge

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ipc.demoaiwedge.ui.theme.DemoAIWTheme

class MainActivity : ComponentActivity() {

    var barcode by mutableStateOf("")

    private var broadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            if (intent?.action == "com.ipc.aiwedge.intent.ACTION") {
                val extras = intent!!.extras
                if (extras == null) {
                    return
                }

                if (extras.containsKey("com.ipc.aiwedge.intent.barcodeData")) {
                    val barcodeData = extras.getString("com.ipc.aiwedge.intent.barcodeData")
                    if (barcodeData != null) {
                        if (extras.containsKey("com.ipc.aiwedge.intent.barcodeType")) {
                            val barcodeType = extras.getString("com.ipc.aiwedge.intent.barcodeType")
                            if (barcodeType != null) {
                                // Do something ...
                                barcode = "${barcodeData} - ${barcodeType}"
                            }
                        }
                    }
                }

                if (extras.containsKey("com.ipc.aiwedge.intent.buttonDidRelease")) {
                    val buttonid = extras.getInt("com.ipc.aiwedge.intent.buttonDidRelease")
                    println("Button released: ${buttonid}")
                }

                if (extras.containsKey("com.ipc.aiwedge.intent.buttonDidPress")) {
                    val buttonid = extras.getInt("com.ipc.aiwedge.intent.buttonDidPress")
                    println("Button pressed: ${buttonid}")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentFilter = IntentFilter()
        intentFilter.addAction("com.ipc.aiwedge.intent.ACTION")
        registerReceiver(broadcastReceiver, intentFilter, RECEIVER_EXPORTED)

        setContent {
            DemoAIWTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Demo AIWedge Intent Output")
                        Spacer(modifier = Modifier.height(50.dp))
                        Text(text = "Barcode: $barcode")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoAIWTheme {
        Text(text = "Demo AIWedge Intent Output")
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Barcode: 1234567890")
    }
}