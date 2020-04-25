package by.a_lzr.globusmanager.deprecated.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.a_lzr.globusmanager.R
import kotlinx.android.synthetic.main.activity_view_stream_item.*

class ViewStreamItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_stream_item)

        view_stream_item_uri.text = intent.dataString;
    }
}
