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

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {

            override fun onItemLongClicked(position: Int) {
                // Remove the item from the list
                listOfTasks.removeAt(position)
                // Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()
                // Save list of items
                saveItems()
            }

        }

        // Load list of items
        loadItems()

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

            // Save the items to the file
            saveItems()
        }

    }

    // Save the data that the user has inputted
    // Save data by writing and reading from a file

    /**
     * Get the file we need
     */
    private fun getDataFile() : File {
        // Every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    /**
     * Load the items by reading every line in the data file
     */
    private fun loadItems() {

        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch(ioException: IOException) {
            ioException.printStackTrace()
        }

    }

    /**
     * Save items by writing them into our data file
     */
    fun saveItems() {

        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch(ioException: IOException) {
            ioException.printStackTrace()
        }

    }

    /**
     * Launch edit text intent
     */
    fun editItem(pos: Int) {

        val editIntent = Intent(this@MainActivity, EditTextActivity::class.java)
        editIntent.putExtra("item", listOfTasks.get(pos))
        startActivity(editIntent)

    }

}