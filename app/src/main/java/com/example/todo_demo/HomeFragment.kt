package com.example.todo_demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class HomeFragment : Fragment() {

    //region Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val createGroupButton: View = view.findViewById(R.id.createGroupButton)
        val groupDetailViewButton : View = view.findViewById(R.id.groupDetailViewButton)

        groupDetailViewButton.setOnClickListener {
            onNavigateGroupDetailFragment()
        }

        createGroupButton.setOnClickListener{
            onCreateGroup()
        }
    }

    //endregion

    //region Actions

    private fun onNavigateGroupDetailFragment(){
        val fragment = GroupDetailFragment.newInstance(1)
        parentFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fragment_container_view, fragment)
        }
    }

    private fun onCreateGroup(){
        Toast.makeText(context, getString(R.string.button_toast_template), Toast.LENGTH_SHORT).show()
    }

    //endregion

}