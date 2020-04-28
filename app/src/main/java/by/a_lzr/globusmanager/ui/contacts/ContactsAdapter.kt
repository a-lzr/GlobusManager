package by.a_lzr.globusmanager.ui.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.toast.ToastHelper
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactsAdapter :
    RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return ContactsCollection.instance.contacts.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val itemView = holder.itemView

        itemView.setOnClickListener { v ->
            ToastHelper.showToast(v.context, "test")
        }

        with(ContactsCollection.instance.contacts[position]) {
            itemView.nameTitle.text = name
        }
    }
}