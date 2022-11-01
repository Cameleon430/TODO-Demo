package com.example.todo_demo.presenter.home

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.example.todo_demo.databinding.FragmentCreateGroupDialogBinding

class CreateGroupDialogFragment: DialogFragment() {

    //region Properties

    private lateinit var binding: FragmentCreateGroupDialogBinding

    //endregion

    //region Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateGroupDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding){
            dialogEditText.doOnTextChanged { text, _, _, _ ->
                dialogButton.isEnabled = text.toString().isNotBlank()
            }

            dialogButton.setOnClickListener{
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        resizeDialog()
    }

    override fun onDismiss(dialog: DialogInterface) {
        val requestKey = takeRequestKey()
        val groupName = takeGroupName()
        setFragmentResult(requestKey, bundleOf(requestKey to groupName))
        super.onDismiss(dialog)
    }

    //endregion

    //region Actions

    private fun resizeDialog() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
    }

    private fun takeGroupName(): String {
        return binding.dialogEditText.text.toString()
    }

    private fun takeRequestKey(): String {
        return arguments?.getString(REQUEST_KEY) ?: ""
    }

    //endregion

    //region Nested

    companion object{

        private const val REQUEST_KEY = "create.group.dialog.fragment.request.key"
        private const val TAG = "CreateGroupDialog"

        fun show(fragmentManager: FragmentManager, requestKey: String): CreateGroupDialogFragment{

            val dialog = CreateGroupDialogFragment().apply {
                arguments = bundleOf(
                    REQUEST_KEY to requestKey
                )
            }

            dialog.show(fragmentManager, TAG)
            return dialog
        }
    }

    //endregion
}