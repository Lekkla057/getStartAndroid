package com.example.papernote.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.papernote.R
import com.example.papernote.database.Record
import com.example.papernote.database.RecordDatabase
import com.example.papernote.databinding.FragmentMainBinding
import com.example.papernote.hideKeyboard
import com.example.papernote.swipecontrollerdemo

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
    var mainViewModel: MainViewModel? = null
    lateinit var binding: FragmentMainBinding
    lateinit var adapter: MainAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main, container, false
        )

        adapter = MainAdapter({ item -> doClick(item) }, { i, title -> editTitle(i!!, title!!) })
        binding.showList.adapter = adapter
        val application = requireNotNull(this.activity).application
        val dataSource = RecordDatabase.getInstance(application).recordDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource, application)
        mainViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(MainViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.mainFragmentModel = mainViewModel

        mainViewModel!!.showSnackBarEvent.observe(this, Observer {
            if (it == true) {
                dialogAdd()
            }

        })
        mainViewModel!!.allRecord.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        ///Change Name title bar
        (activity as AppCompatActivity).supportActionBar?.title = "Paper Note"

        mainViewModel!!.swipe.observe(this, Observer {

      actionSwipe()



        })

        return binding.root
    }

    fun editTitle(i: Record, title: Any) {
        mainViewModel?.editTitle(i as Record, title as String)
        adapter.notifyDataSetChanged()
//        adapter.status = adapter.status != true
         //adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.edit, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item!!.itemId

        if (id == R.id.action_sort) {
            when (adapter.status) {
                false -> item.setTitle("DONE");
                else -> item.setTitle("EDIT");
            }
            when (adapter.status) {
                false -> mainViewModel?.doneSwipe();
                else -> mainViewModel?.startSwipe();
            }

            adapter.status = adapter.status != true
            adapter.notifyDataSetChanged()
            Toast.makeText(activity, adapter.status.toString(), Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    fun doClick(item: Record) {
        System.out.println(item)
        mainViewModel?.delete(item.noteId)
        Toast.makeText(
            activity,
            "Delete " + item.title.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

    fun dialogAdd() {
        val builder = AlertDialog.Builder(activity)
        val inflater = layoutInflater
        builder.setTitle("Create Title Name")
        val dialogLayout = inflater.inflate(R.layout.fragment_popup, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.editText2)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i ->
//                navController.navigate(
//                    LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            mainViewModel!!.Insert(editText.text.toString())
            this.findNavController()
                .navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(-1))
            dialogInterface.dismiss();
            Toast.makeText(
                activity,
                "Create " + editText.text.toString(),
                Toast.LENGTH_SHORT
            ).show()

        }

        builder.setNegativeButton("CANCEL") { dialogInterface, i ->
            dialogInterface.dismiss();
        }
        val activity = this.activity
        if (activity is AppCompatActivity) {

            activity.hideKeyboard()
        }
        mainViewModel!!.doneShowingSnackbar()
        builder.show()
    }
    fun actionSwipe(){
        val swipeHandler = object : swipecontrollerdemo(context, mainViewModel?.swipe) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // val adapter = recyclerView.adapter as SimpleAdapter
                System.out.println(viewHolder)
                adapter.removeAt(viewHolder.adapterPosition)


            }

        }



        val itemTouchHelper = ItemTouchHelper(swipeHandler)


        itemTouchHelper.attachToRecyclerView(binding.showList)



    }

}
