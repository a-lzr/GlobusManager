package by.a_lzr.globusmanager.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.storage.DatabaseHelper
import by.a_lzr.globusmanager.ui.main.control.MainControlFragment
import by.a_lzr.globusmanager.ui.main.messages.MainMessagesFragment
import by.a_lzr.globusmanager.ui.main.messages.MainMessagesCollection
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainFragment : Fragment(), MainFragmentListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var tabLayout: TabLayout

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

        tabLayout = view.findViewById(R.id.main_tab_layout)

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
        updateMessagesBadge()
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

    override fun updateMessagesBadge() {
        CoroutineScope(Dispatchers.IO).launch {
            MainMessagesCollection.instance.countNotRead =
                DatabaseHelper.db.personDao.getMessagesCountNotRead()
            withContext(Dispatchers.Main) {
                if (MainMessagesCollection.instance.countNotRead > 0) {
                    val badge: BadgeDrawable = tabLayout.getTabAt(1)!!.orCreateBadge
                    badge.isVisible = true
                    badge.number = MainMessagesCollection.instance.countNotRead
                }
                else {
                    tabLayout.getTabAt(1)!!.removeBadge();
                }
            }
        }
    }
}