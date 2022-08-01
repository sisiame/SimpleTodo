package com.twoccs.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private var listOfTasks = mutableListOf<String>()
    private lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {

            override fun onItemLongClicked(position: Int) {
                // the code to remove an item from the list would go here
            }

        }

        // Look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.tasksList)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerView to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the button and input field, so that the user can enter a
        // task and add it to the list

        val addTaskField = findViewById<EditText>(R.id.addTaskField)

        // Get a reference to the button
        // and then set an onClickListener
        addTaskButton.setOnClickListener {
            // Grab the text the user has inputted into @id/addTaskField
            val userInputtedTask = addTaskField.text.toString()

            // Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            // Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // Reset text field
            addTaskField.setText("")
        }

    }

}