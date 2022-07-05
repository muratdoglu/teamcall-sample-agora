package com.mrt.android.teamcall.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mrt.android.teamcall.R
import com.mrt.android.teamcall.data.model.User
import com.mrt.android.teamcall.databinding.ItemUserBinding
import com.mrt.android.teamcall.util.BaseRecyclerAdapter


class UserListAdapter(
    private val mainActivity: MainActivity,
    mDataSet: MutableList<User>,
    private val user: (User) -> Unit
) : BaseRecyclerAdapter<UserViewHolder, User>(mDataSet) {

    override fun createView(view: ViewGroup, viewType: Int): UserViewHolder {

        val layoutInflater =
            mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val binding = DataBindingUtil.inflate<ItemUserBinding>(
            layoutInflater,
            R.layout.item_user,
            view,
            false
        )
        return UserViewHolder(binding, user)
    }

    override fun bindView(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.setItem(user)
    }
}