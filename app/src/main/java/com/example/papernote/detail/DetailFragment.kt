package com.example.papernote.detail

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.papernote.R
import com.example.papernote.database.Record
import com.example.papernote.database.RecordDatabase
import com.example.papernote.database.RecordDatabase.Companion.getInstance
import com.example.papernote.databinding.FragmentDetailBinding



/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.title =""
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false)

        val application = requireNotNull(this.activity).application

        val arguments = DetailFragmentArgs.fromBundle(arguments!!)
        val dataSource = RecordDatabase.getInstance(application).recordDatabaseDao
        val viewModelFactory = DetailViewModelFactory(arguments.title, dataSource)
        val DetailViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(DetailViewModel::class.java)

        binding.detailViewModel = DetailViewModel
        DetailViewModel.data.observe(this, Observer {
            (activity as AppCompatActivity).supportActionBar?.title = DetailViewModel.data.value?.title.toString()
            binding.editText.setText(DetailViewModel.data.value?.detail.toString());
        })
        return binding.root
    }


}
