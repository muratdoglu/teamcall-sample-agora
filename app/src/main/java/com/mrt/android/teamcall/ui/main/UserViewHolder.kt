package com.mrt.android.teamcall.ui.main

import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.mrt.android.teamcall.data.model.User
import com.mrt.android.teamcall.databinding.ItemUserBinding

class UserViewHolder(
    private val binding: ItemUserBinding,
    private val select: (User) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var user: User
    private var mainActivity: MainActivity


    init {
        binding.holder = this
        mainActivity = binding.root.context as MainActivity
    }

    fun setItem(userInfo: User) {
        this.user = userInfo
        binding.user=user
        binding.executePendingBindings()
    }

    fun select(view: View) {
        select(user)
    }
}