package com.example.newyorkschools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newyorkschools.ui.theme.NewYorkSchoolsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<NewYorkSchoolViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewYorkSchoolsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SchoolsList(

                        modifier = Modifier.padding(innerPadding),
                        viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun SchoolsList( modifier: Modifier = Modifier, viewModel: NewYorkSchoolViewModel) {
    val schools = viewModel.schoolsFlow.collectAsStateWithLifecycle().value
    Surface(modifier= modifier.fillMaxSize(),color = Color.White) {

    }
    LazyColumn(modifier = modifier.fillMaxSize()) {
        itemsIndexed(schools.toList()){ index,item ->
            Button(onClick = { viewModel.onClick() }) {


                item.school_name?.let {
                    Text(text = it, color = Color.Red)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewYorkSchoolsTheme {

    }
}