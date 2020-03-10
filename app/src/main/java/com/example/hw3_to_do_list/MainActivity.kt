package com.example.hw3_to_do_list

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivityOscar"
    private val TAG_2= "SavedStuff"

    private val FILE_NAME = "MyList"
     var myList = arrayListOf<String>()

    // REQUEST_CODE can be any value you like, used for identifier
    private val REQUEST_CODE = 123 // MUST BE 0 - 65535

    lateinit var myAdapter : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }




    fun openSecondActivity(view: View){
        //Create an Intent object with two parameters: 1) context, 2) class of the activity to launch
        val myIntent = Intent(this, SecondActivity::class.java)
        startActivityForResult(myIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            // come back from the second activity
            Log.d(TAG, "in the onActivityResult...")

            val dataComingFromSecondActivity = data?.getStringArrayListExtra("mydata")
            myList = data?.getStringArrayListExtra("mydata") as ArrayList<String>
            Log.d(TAG, "in the onActivityResult... $myList" )

            Log.d(TAG, "data: $dataComingFromSecondActivity")

            if (dataComingFromSecondActivity != null) {


                Log.d(TAG, "data: $dataComingFromSecondActivity")
                myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList
                )
                saveIT()
                list_view_adding.adapter = myAdapter

            }
            list_view_adding.setOnItemLongClickListener { parent, view, position, id ->

                val selectedItem = parent.getItemAtPosition(position).toString()
                Toast.makeText(this, "This is a long press, Deleting $selectedItem", Toast.LENGTH_SHORT).show()

                myList.removeAt(position)
                if (position == 0){
                    Toast.makeText(this, "You finished All of the task.!!!  You last was:$selectedItem", Toast.LENGTH_SHORT).show()

                }

                myAdapter.notifyDataSetChanged()

                return@setOnItemLongClickListener true

            }

        }
    }
    fun saveIT(){
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Create an instance of Gson (make sure to include its dependency first to be able use gson)
        val gson = Gson()

        // toJson() method serializes the specified object into its equivalent Json representation.
        val myAdapterListJson = gson.toJson(myList)
        // Put the  Json representation, which is a string, into sharedPreferences
        editor.putString("vehicles", myAdapterListJson)
        // Apply the changes
        editor.apply()

        val toast = Toast.makeText(this, "Saved", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP, 0, 150)
        toast.show()
       // loadIT()
    }
    override fun onStart() {
        super.onStart()
        // The activity is about to become visible.
      //  loadIT()
        Log.d(TAG, "OnStart was called")
    }

    fun loadIT(view: View) {

        // Create an instance of getSharedPreferences for retrieve the data
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        // Retrieve data using the key, default value is empty string in case no saved data in there
        val tasks = sharedPreferences.getString("vehicles", "") ?: ""

        if (tasks.isNotEmpty()){

            // Create an instance of Gson
            val gson = Gson()
            // create an object expression that descends from TypeToken
            // and then get the Java Type from that
            val sType = object : TypeToken<List<String>>() { }.type
            // provide the type specified above to fromJson() method
            // this will deserialize the previously saved Json into an object of the specified type (e.g., list)
            val savedAdapterListJson = gson.fromJson<List<String>>(tasks, sType)
            for (savedOne in savedAdapterListJson) {
                myList.add(savedOne)
                myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList
                )
                list_view_adding.adapter = myAdapter

                Log.d(TAG_2, savedOne)
            }


            }
        }
    }


