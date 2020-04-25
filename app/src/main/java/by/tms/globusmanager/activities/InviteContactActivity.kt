package by.tms.globusmanager.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.tms.globusmanager.R
import kotlinx.android.synthetic.main.activity_invite_contact.*

class InviteContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_contact)

        invite_contact_uri.text = intent.dataString;
    }
}
