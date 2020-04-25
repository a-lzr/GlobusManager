package by.a_lzr.globusmanager.ui.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import by.a_lzr.globusmanager.R

class InfoFragment : Fragment() {

    private lateinit var viewModel: InfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_info, container, false)

        viewModel = ViewModelProvider(this).get(InfoViewModel::class.java)

        return root
    }
}
