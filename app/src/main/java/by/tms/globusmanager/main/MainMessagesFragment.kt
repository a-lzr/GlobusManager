package by.tms.globusmanager.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import by.tms.globusmanager.R

class MainMessagesFragment : Fragment() {

    private lateinit var viewModel: MainMessagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MainMessagesViewModel::class.java)

        return inflater.inflate(R.layout.fragment_main_messages, container, false)
    }
}
