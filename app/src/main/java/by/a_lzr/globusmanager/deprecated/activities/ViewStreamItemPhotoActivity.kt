package by.a_lzr.globusmanager.deprecated.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.a_lzr.globusmanager.R
import kotlinx.android.synthetic.main.activity_view_stream_item_photo.*

class ViewStreamItemPhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_stream_item_photo)

        view_stream_item_photo_uri.text = intent.dataString;
    }
}
