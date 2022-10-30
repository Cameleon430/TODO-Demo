package com.example.todo_demo.presenter.base

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    override fun onStart() {
        super.onStart()
        initializeBackNavigation()
    }

    private fun initializeBackNavigation() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        })
    }

    protected open fun onBackPressed(){

    }
}