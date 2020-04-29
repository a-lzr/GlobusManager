package by.a_lzr.globusmanager.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_main.*
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.toast.ToastHelper
import by.a_lzr.globusmanager.ui.main.control.MainControlFragment
import by.a_lzr.globusmanager.ui.main.messages.MainMessagesFragment

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<TabLayout>(R.id.main_tab_layout)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                updateTabPosition(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
//                updateTabPosition(tab.position)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
//                childFragmentManager.findFragmentById().
//                updateTabPosition(tab.position)
//                ToastHelper.showToast(context, "onTabReselected")
            }
        })

        updateTabPosition(tabLayout.selectedTabPosition)
    }

    private fun updateTabPosition(position: Int) {
        when (position) {
            1 -> {
                childFragmentManager
                    .beginTransaction()
                    .replace(main_fragment_widget.id, MainMessagesFragment())
                    .commit()
            }
            else -> {
                childFragmentManager
                    .beginTransaction()
                    .replace(main_fragment_widget.id, MainControlFragment())
                    .commit()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu);
    }
}
