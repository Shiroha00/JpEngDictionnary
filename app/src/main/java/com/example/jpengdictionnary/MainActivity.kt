package com.example.jpengdictionnary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jpengdictionnary.ui.theme.JpEngDictionnaryTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryAppTheme {
                Surface(
                    color =MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var keyword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center

    ) {
        OutlinedTextField(
            value = keyword,
            onValueChange = {
                keyword = it
            },
            label = { Text("Enter a word") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    viewModel.searchWords(keyword)
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        val isLoading by viewModel.isLoading.observeAsState(initial = false)
        val wordData by viewModel.wordData.observeAsState(initial = emptyList())

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            WordList(wordData = wordData)
        }
    }
}

@Composable
fun WordList(wordData: List<WordData>) {
    LazyColumn {
        items(wordData) { word ->
            WordItem(word = word)
        }
    }
}

@Composable
fun WordItem(word: WordData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row {
            Text(
                text = word.japanese.firstOrNull()?.word.orEmpty(),
                style = MaterialTheme.typography.headlineLarge,
                color = (Color.Red)

            )
        }
        Row {
            Text(
                text = "◆Readings:",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = word.japanese.firstOrNull()?.reading.orEmpty(),
                style = MaterialTheme.typography.headlineMedium,
            )
        }
        Row {
            Text(
                text = "◆Meanings:",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = word.senses.firstOrNull()?.english_definitions?.joinToString(", ").orEmpty(),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}
@Composable
fun DictionaryAppTheme(content: @Composable () -> Unit) {

    val customTypography = Typography(
        displayLarge = TextStyle(fontSize = 24.sp),
        displayMedium = TextStyle(fontSize = 18.sp),
    )
    MaterialTheme(
        typography = customTypography,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewGreeting() {
    DictionaryAppTheme {
        MainScreen()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainApplication() {
    DictionaryAppTheme {
        MainScreen()
    }
}