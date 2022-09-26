package com.example.todo_demo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class HomeFragment : Fragment(R.layout.fragment_home) {

    //region Lifecycles

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val createGroupButton: View = view.findViewById(R.id.createGroupButton)
        val groupDetailViewButton : View = view.findViewById(R.id.groupDetailViewButton)

        groupDetailViewButton.setOnClickListener {
            startGroupDetailFragment()
        }

        createGroupButton.setOnClickListener{
            createGroup()
        }
    }

    //endregion

    //region Functions

    private fun startGroupDetailFragment(){
        val fragment = GroupDetailFragment.newInstance(1)
        parentFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fragment_container_view, fragment)
        }
    }

    private fun createGroup(){
        Toast.makeText(context, "Hey, you clicked it!", Toast.LENGTH_SHORT).show()
    }

    //endregion
}