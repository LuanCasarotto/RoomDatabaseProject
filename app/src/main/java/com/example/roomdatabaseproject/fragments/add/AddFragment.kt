package com.example.roomdatabaseproject.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomdatabaseproject.R
import com.example.roomdatabaseproject.model.User
import com.example.roomdatabaseproject.viewmodel.UserViewModel
import com.example.roomdatabaseproject.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnAdd.setOnClickListener {
            insertDataToDatabase()
        }

        return binding.root
    }

    private fun insertDataToDatabase() {
        binding.run {
            val firstName = etFirstNameAdd.text.toString()
            val lastName = etLastNameAdd.text.toString()
            val age = etAgeAdd.text

            if (inputCheck(firstName, lastName, age)) {
                // Create User Object
                val user = User(0, firstName, lastName, Integer.parseInt(age.toString()))
                // Add Data to Database
                mUserViewModel.addUser(user)
                Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
                // Navigate Back
                findNavController().navigate(R.id.action_addFragment_to_listFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }
}