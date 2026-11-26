package com.medicinemaster.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.medicinemaster.app.navigation.MedicineMasterApp
import com.medicinemaster.app.ui.theme.MedicineMasterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicineMasterTheme {
                MedicineMasterApp()
            }
        }
    }
}
