package by.a_lzr.globusmanager.ui.tools

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import by.a_lzr.globusmanager.R

class ToolsFragment : Fragment() {

    private lateinit var viewModel: ToolsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tools, container, false)

        viewModel = ViewModelProvider(this).get(ToolsViewModel::class.java)

        return root
    }
}
