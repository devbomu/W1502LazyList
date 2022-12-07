package kr.ac.kumoh.s20180100.w1502lazylist

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class SongViewModel(application: Application) : AndroidViewModel(application) {
    data class Song (var id: Int, var title: String, var type: String, var image: String)

    companion object {
        const val QUEUE_TAG = "SongVolleyRequest"

        // NOTE: 서버 주소는 본인의 서버 IP 사용할 것
        const val SERVER_URL = "https://isd-ycvnu.run.goorm.io"
    }

    val songs = mutableStateListOf<Song>()

    private var queue: RequestQueue = Volley.newRequestQueue(getApplication())

    fun requestSong() {
        val request = JsonArrayRequest(
            Request.Method.GET,
            "$SERVER_URL/song",
            null,
            {
                //Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
                songs.clear()
                parseJson(it)
                //_list.value = songs
            },
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
            }
        )

        request.tag = QUEUE_TAG
        queue.add(request)
    }

    private fun parseJson(items: JSONArray) {
        for (i in 0 until items.length()) {
            val item: JSONObject = items[i] as JSONObject
            val id = item.getInt("s_id")
            val title = item.getString("s_title")
            val type = item.getString("s_type")
            val image = item.getString("s_image")

            songs.add(Song(id, title, type, image))
        }
    }

    override fun onCleared() {
        super.onCleared()
        queue.cancelAll(QUEUE_TAG)
    }
}