package com.example.todolist

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_view.view.*


class CardViewAdapter(val context: Context, val db: SQLiteDatabase): RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val cardView_note = layoutInflater.inflate(R.layout.card_view, p0, false)
        return MyViewHolder(cardView_note)
    }

    override fun getItemCount(): Int {

        val cursor = db.query(TableInfo.TABLE_NAME, null, null, null,
            null, null, null)

        val liczbaWierszy = cursor.count

        cursor.close()
        return liczbaWierszy

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cardView_note = holder.view.note_cardView
        val title = holder.view.title_cardView
        val message = holder.view.message_cardView
        val context: Context = holder.view.context

        val cursor = db.query(TableInfo.TABLE_NAME, null, BaseColumns._ID + "=?", arrayOf(holder.adapterPosition.plus(1).toString()),
        null, null, null)

        if(cursor.moveToFirst()){
            if(!(cursor.getString(1).isNullOrEmpty()&&
                        cursor.getString(2).isNullOrEmpty())){
                title.setText(cursor.getString(1))
                message.setText(cursor.getString(2))
            }
        }


        cardView_note.setOnClickListener{
            val intent_edit = Intent(context, DetailsActivity::class.java)
            val title_edit = title.text.toString()
            val message_edit = message.text.toString()
            val id_edit = holder.adapterPosition.plus(1).toString()
            intent_edit.putExtra("title", title_edit)
            intent_edit.putExtra("message", message_edit)
            intent_edit.putExtra("ID", id_edit)

            context.startActivity(intent_edit)

        }


    }

}

class MyViewHolder(val view: View): RecyclerView.ViewHolder(view)

