package com.example.todo_demo.presenter.group

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_demo.databinding.ItemTaskBinding
import com.example.todo_demo.presenter.base.TaskViewState

class TaskViewItemAdapter(
    private val listener: OnItemSelectListener? = null
) : RecyclerView.Adapter<TaskViewItemAdapter.ViewHolder>() {

    //region Properties

    private var items: List<TaskViewState> = emptyList()

    //endregion

    //region Actions

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: TaskViewState = items[position]
        holder.onBind(item, listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItemsView(task: List<TaskViewState>){
        items = task
        notifyDataSetChanged()
    }

    //endregion

    //region Nested

    class ViewHolder(
        private val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: TaskViewState, listener: OnItemSelectListener?) {
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
        fun onClick(task: TaskViewState)
    }

    //endregion

}