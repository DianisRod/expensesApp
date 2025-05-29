package com.dr.expensesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dr.expensesapp.ui.tabs.HistoryTab
import com.dr.expensesapp.ui.tabs.StatisticsTab
import com.dr.expensesapp.ui.tabs.LogTab
import com.dr.expensesapp.ui.theme.ExpensesAppTheme
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIconType
import com.guru.fontawesomecomposelib.FaIcons

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpensesAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
                    TabLayout(modifier = Modifier.padding(contentPadding))
                }
            }
        }
    }
}

@Composable
fun TabLayout(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            TabRow(selectedTabIndex = selectedTabIndex) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Log expense") },
                    icon = { FaIcon(faIcon = FaIconType.SolidIcon(0xf53b)) }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("History") },
                    icon = { FaIcon(faIcon = FaIconType.SolidIcon(0xf1da)) }
                )
                Tab(
                    selected = selectedTabIndex == 2,
                    onClick = { selectedTabIndex = 2 },
                    text = { Text("Statistics") },
                    icon = { FaIcon(faIcon = FaIcons.ChartLine) }
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            when (selectedTabIndex) {
                0 -> LogTab()
                1 -> HistoryTab()
                2 -> StatisticsTab()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TabLayoutPreview() {
    ExpensesAppTheme {
        TabLayout()
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ExpensesAppTheme {
//        Greeting("Android")
//    }
//}