package com.example.hw3_to_do_list

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    val myList = arrayListOf<String>()
    private val TAG = "SecondActivityOscar"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
    override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    // Save the state of program such as text in the editText when the states changes
    // such as due to flipping from portrait to landscape
    Log.d(TAG, "onSaveInstanceState was called")
    outState.putString("test", editText_input.text.toString())
}

override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    // Called when there is a saved instance that is previously
    // saved by using onSaveInstanceState()
    // Set the value back to editText, which could be done in onCreate as well
    Log.d(TAG, "onRestoreInstanceState was called")
    val prevText = savedInstanceState?.getString("test", "")
    editText_input.setText(prevText)
}


    fun returnDataToFirstActivity(view: View) {

    // Need to create an intent to go back
    val myIntent = Intent()
    // Store any extra data in the intent
    myList.add(editText_input.text.toString())

    Log.d(TAG, "data: $myList")

    myIntent.putStringArrayListExtra("mydata", myList)

           setResult(Activity.RESULT_OK, myIntent)
          editText_input.text.clear()
          finish() // Stops and closes the second activity

}
    fun returnDataToFirstActivity_unstoped(view: View) {

        // Need to create an intent to go back
        val myIntent = Intent()
        // Store any extra data in the intent
        myList.add(editText_input.text.toString())

        Log.d(TAG, "data: $myList")

        myIntent.putStringArrayListExtra("mydata", myList)


        // Set the activity's result to RESULT_OK
        setResult(Activity.RESULT_OK, myIntent)
        editText_input.text.clear()

    }
    fun returnDataToFirstActivity_nothing(view: View) {

        finish() // Stops and closes the second activity

    }

}

