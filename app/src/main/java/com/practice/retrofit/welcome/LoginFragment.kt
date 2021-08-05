package com.practice.retrofit.welcome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.practice.retrofit.R
import com.practice.retrofit.dashboard.DashboardActivity
import com.practice.retrofit.databinding.FragmentLoginBinding
import com.practice.retrofit.network.RequestState

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.tvGotoRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)

        viewModel.state.observe({ lifecycle }, { state ->
            Log.d("STATE", "state $state")
            when (state) {
                RequestState.REQUEST_START -> {
                    binding.btnLogin.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                }
                RequestState.REQUEST_END -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.visibility = View.VISIBLE
                    binding.tvMainError.visibility = View.GONE
                }
                RequestState.REQUEST_ERROR -> {
                    binding.btnLogin.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
        viewModel.response.observe({ lifecycle }, { response ->
            Log.d("RESPONSE", response.toString())
            if (response != null) {
                if (response.body != null) {
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardActivity)
                    activity?.finish()
                } else {
                    binding.tvMainError.visibility = View.VISIBLE
                    binding.tvMainError.text = response.message
                }
            }
        })
        viewModel.error.observe({ lifecycle }, { error ->
            if (error.isNotEmpty()) {
                binding.tvMainError.visibility = View.VISIBLE
                binding.tvMainError.text = error
            }
        })

        view.findViewById<MaterialButton>(R.id.btn_login).setOnClickListener {
            if (verified(binding)) {

                viewModel.doLogin(
                    email = binding.edtEmail.text.toString(),
                    password = binding.edtPassword.text.toString()
                )
            }

        }


    }

    private fun verified(binding: FragmentLoginBinding): Boolean {
        var returnal = true
        if (binding.edtEmail.text.toString().isEmpty()) {
            binding.inputLayoutEmail.helperText = "Wajib diisi"
            binding.inputLayoutEmail.error = "Wajib diisi"
            returnal = false
        } else if (!binding.edtEmail.text.toString().contains('@')) {
            binding.inputLayoutEmail.helperText = "Wajib Email"
            binding.inputLayoutEmail.error = "Wajib Email"
            returnal = false
        } else {
            binding.inputLayoutEmail.helperText = null
            binding.inputLayoutEmail.error = null
        }

        if (binding.edtPassword.text.toString().isEmpty()) {
            binding.inputLayoutPassword.helperText = "Wajib diisi"
            binding.inputLayoutPassword.error = "Wajib diisi"
            returnal = false
        } else {
            binding.inputLayoutPassword.helperText = null
            binding.inputLayoutPassword.error = null
        }
        return returnal
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}