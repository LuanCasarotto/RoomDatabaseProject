package com.example.roomdatabaseproject.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomdatabaseproject.R
import com.example.roomdatabaseproject.databinding.FragmentListBinding
import com.example.roomdatabaseproject.databinding.FragmentUpdateBinding
import com.example.roomdatabaseproject.model.User
import com.example.roomdatabaseproject.viewmodel.UserViewModel

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.run {
            etFirstNameUpdate.setText(args.currentUser.firstName)
            etLastNameUpdate.setText(args.currentUser.lastName)
            etAgeUpdate.setText(args.currentUser.age.toString())

            btnUpdate.setOnClickListener {
                updateItem()
            }
        }

        // Add Menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun updateItem() {
        binding.run {
            val firstName = etFirstNameUpdate.text.toString()
            val lastName = etLastNameUpdate.text.toString()
            val age = Integer.parseInt(etAgeUpdate.text.toString())

            if (inputCheck(firstName, lastName, etAgeUpdate.text)) {
                // Create User Object
                val updatedUser = User(args.currentUser.id, firstName, lastName, age)
                // Update Current User
                mUserViewModel.updateUser(updatedUser)
                Toast.makeText(requireContext(), "Update Successfully!", Toast.LENGTH_SHORT).show()
                // Navigate Back
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentUser.firstName}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}")
        builder.create().show()
    }

}