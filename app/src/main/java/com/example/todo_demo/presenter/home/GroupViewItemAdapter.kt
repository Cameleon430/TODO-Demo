package com.example.todo_demo.presenter.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_demo.databinding.ItemGroupBinding
import com.example.todo_demo.presenter.base.GroupViewState

class GroupViewItemAdapter(
    private val listener: OnItemSelectListener? = null
) : RecyclerView.Adapter<GroupViewItemAdapter.ViewHolder>() {

    //region Properties

    private var items: List<GroupViewState> = emptyList()

    //endregion

    //region Actions

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGroupBinding.inflate(inflater, parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: GroupViewState = items[position]
        holder.onBind(item, listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItemsView(group: List<GroupViewState>){
        items = group
        notifyDataSetChanged()
    }

    //endregion

    //region Nested

    class ViewHolder(
        private val binding: ItemGroupBinding
        ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: GroupViewState, listener: OnItemSelectListener?) {
            with(binding) {

                root.setOnClickListener {
                    listener?.onClick(item)
                }
                titleTextView.text = item.name
                iconTextView.text = item.name[0].toString()
            }

        }
    }

    interface OnItemSelectListener{
        fun onClick(group: GroupViewState)
    }

    //endregion

}