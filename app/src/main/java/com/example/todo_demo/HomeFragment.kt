package com.example.todo_demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.todo_demo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    //region Properties
    private lateinit var binding: FragmentHomeBinding
    //endregion

    //region Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initializeListeners()

    }

    //endregion

    //region Actions

    private fun initializeListeners(){
        with(binding){
            groupDetailViewButton.setOnClickListener {
                onNavigateGroupDetailFragment()
            }

            createGroupButton.setOnClickListener{
                onCreateGroup()
            }
        }
    }

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