package com.example.todolist

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase
        val save_info_toast = Toast.makeText(applicationContext, "notatka została zapisana, mozez wyjsc.", Toast.LENGTH_SHORT)

        if(intent.hasExtra("title")) title_details.setText(intent.getStringExtra("title"))
        if(intent.hasExtra("message")) message_details.setText(intent.getStringExtra("message"))

        save_BT_details.setOnClickListener{


            val title = title_details.text.toString()
            val message = message_details.text.toString()

            val value = ContentValues()
            value.put(TableInfo.TABLE_COLUMN_TITLE, title)
            value.put(TableInfo.TABLE_COLUMN_MESSAGE, message)


            if(intent.hasExtra("ID")) {

                db.update(TableInfo.TABLE_NAME, value, BaseColumns._ID + "=?",
                arrayOf(intent.getStringExtra("ID")))

                Toast.makeText(applicationContext, "notatka edytowana.", Toast.LENGTH_SHORT).show()


            }
            else
            {


                if(!title.isNullOrEmpty() || !message.isNullOrEmpty()) {

                    db.insertOrThrow(TableInfo.TABLE_NAME, null, value)
                    save_info_toast.show()
                }
                else
                {
                    Toast.makeText(applicationContext, "oj byczku nic nie napisales", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }
}
