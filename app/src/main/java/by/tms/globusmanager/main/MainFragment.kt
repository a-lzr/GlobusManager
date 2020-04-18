package by.tms.globusmanager.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import by.tms.globusmanager.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_main, container, false)

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<TabLayout>(R.id.main_tab_layout)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
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
//                Toast.makeText(context, "onTabSelected", Toast.LENGTH_SHORT)
//                Log.i("TextStats","NEW TAB SELECTED: " + tab.position)
//                viewPagerReference.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
//                Toast.makeText(context, "onTabUnselected", Toast.LENGTH_SHORT)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
//                Toast.makeText(context, "onTabReselected", Toast.LENGTH_SHORT)
            }
        })
    }
}
