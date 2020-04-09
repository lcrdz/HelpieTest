package com.lcardoso.helpietest.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lcardoso.helpietest.R
import com.lcardoso.helpietest.data.model.UserResponse
import kotlinx.android.synthetic.main.user_item.view.*

class UsersAdapter(
    private val users: List<UserResponse>,
    private val onClickListener: (user: UserResponse) -> Unit
) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view, onClickListener)
    }

    override fun getItemCount() = users.count()

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindView(users[position])
    }

    class UserViewHolder(
        view: View,
        private val onClickListener: (user: UserResponse) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val id = view.tvId
        private val name = view.tvName
        private val email = view.tvEmail
        private val company = view.tvCompany
        private val city = view.tvCity

        fun bindView(user: UserResponse) {
            id.text = user.id.toString()
            name.text = user.name
            email.text = user.email
            company.text = user.company.name
            city.text = user.address.city

            itemView.setOnClickListener {
                onClickListener.invoke(user)
            }
        }
    }
}