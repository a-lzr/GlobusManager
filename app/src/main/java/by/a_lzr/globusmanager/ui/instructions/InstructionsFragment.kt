package by.a_lzr.globusmanager.ui.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.a_lzr.globusmanager.R

class InstructionsFragment : Fragment() {

    private lateinit var viewModel: InstructionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(InstructionsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_instructions, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        viewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
