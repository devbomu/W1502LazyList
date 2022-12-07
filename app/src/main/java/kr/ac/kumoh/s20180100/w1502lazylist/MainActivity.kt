package kr.ac.kumoh.s20180100.w1502lazylist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import kr.ac.kumoh.s20180100.w1502lazylist.ui.theme.W1502LazyListTheme
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
//    data class Song(var title: String, var type: String)
//    private val songs = mutableStateListOf<Song>()
    private lateinit var  model: SongViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this)[SongViewModel::class.java]

        setContent {
            MyApp()
        }
    }

    @Composable
    fun MyApp() {
        W1502LazyListTheme {
            Column {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = {
                        // songs.add(Song("My WAKTAVERSE", "개사 곡"))
                        model.requestSong()
                    }
                ) {
                    Text("로드")
                }
                SongList()
            }
        }
    }

    @Composable
    fun SongItem(index: Int, song: SongViewModel.Song) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(255, 210, 210))
                .padding(8.dp)
                .clickable {
                    Toast
                        .makeText(application, song.title, Toast.LENGTH_LONG)
                        .show()
                }
        ) {
            AsyncImage(
                model = "${SongViewModel.SERVER_URL}/image/${song.image}",
                contentDescription = "노래 앨범 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(percent = 10))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = song.title, fontSize = 25.sp)
                Text(text = song.type, fontSize = 20.sp)
            }
        }
    }

    @Composable
    fun SongList() {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(4.dp)
        ) {
            itemsIndexed(model.songs) { index, song ->
                SongItem(index, song)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    W1502LazyListTheme {
//        MyApp()
//    }
}