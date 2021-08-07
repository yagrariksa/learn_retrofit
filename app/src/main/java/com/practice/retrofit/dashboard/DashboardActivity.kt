package com.practice.retrofit.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.retrofit.adapter.StoreAdapter
import com.practice.retrofit.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var adapter: StoreAdapter
    private lateinit var viewModel: StoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = StoreAdapter(this)

        binding.rvStore.layoutManager = LinearLayoutManager(this)
        binding.rvStore.adapter = adapter

        viewModel = ViewModelProvider(this).get(StoreViewModel::class.java)

        viewModel.state.observe({ lifecycle }, { state ->
            Log.d("STATE", state.toString())
        })

        viewModel.response.observe({ lifecycle }, { response ->
            if (response.body != null) {
                adapter.populate(response.body)
            }
        })

        viewModel.error.observe({ lifecycle }, { error ->
            if (error.isNotEmpty())
                Log.e("ERRORViewModel", error)
        })

        viewModel.getData()

        binding.btnShowModal.setOnClickListener {
            binding.modal.visibility = View.VISIBLE
            binding.btnShowModal.visibility = View.INVISIBLE
        }

        binding.btnCari.setOnClickListener {
            if (validate(binding)) {
                binding.modal.visibility = View.GONE
                binding.btnShowModal.visibility = View.VISIBLE
                viewModel.findStore(
                    startlat = binding.edtLatStart.text.toString(),
                    endlat = "333" + binding.edtLatStart.text.toString(),
                    startlong = binding.edtLongStart.text.toString(),
                    endlong = "999" + binding.edtLongStart.text.toString()
                )
            }
        }

        binding.btnModalCancel.setOnClickListener {
            binding.modal.visibility = View.GONE
            binding.btnShowModal.visibility = View.VISIBLE
        }
    }

    private fun validate(binding: ActivityDashboardBinding): Boolean {
        var returnal = true
        if (binding.edtLatStart.text.toString().isEmpty()) {
            binding.inputLayoutStartlat.helperText = "wajib diisi"
            binding.inputLayoutStartlat.error = "wajib diisi"
            returnal = false
        } else {
            binding.inputLayoutStartlat.helperText = null
            binding.inputLayoutStartlat.error = null
        }

        if (binding.edtLongStart.text.toString().isEmpty()) {
            binding.inputLayoutStartlong.helperText = "wajib diisi"
            binding.inputLayoutStartlong.error = "wajib diisi"
            returnal = false
        } else {
            binding.inputLayoutStartlong.helperText = null
            binding.inputLayoutStartlong.error = null
        }

        return returnal
    }
}