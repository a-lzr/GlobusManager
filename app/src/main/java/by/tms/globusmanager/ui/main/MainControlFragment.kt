package by.tms.globusmanager.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.tms.globusmanager.R

class MainControlFragment : Fragment() {

    private lateinit var viewModel: MainControlViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);
        viewModel = ViewModelProvider(this).get(MainControlViewModel::class.java)

        return inflater.inflate(R.layout.fragment_main_control, container, false)

/*
        <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/nav_menu_view"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:background="?android:attr/windowBackground"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:menu="@menu/activity_main_bottom_menu" />
*/
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu);
    }
}
