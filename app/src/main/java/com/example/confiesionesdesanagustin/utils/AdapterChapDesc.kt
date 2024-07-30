package com.example.confiesionesdesanagustin.utils

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.confiesionesdesanagustin.activities.ReaderActivity
import com.nozc.confiesionesdesanagustin.R

class AdapterChapDesc(
    private val context: Context,
    private val itemList: List<Map<String, String?>>,
    private val book: Number
) : RecyclerView.Adapter<AdapterChapDesc.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.txtTitleChapMain)
        val desc: TextView = view.findViewById(R.id.txtDescriptionChapMain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_chapter_desc, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.title.text = Html.fromHtml(item["title"], Html.FROM_HTML_MODE_LEGACY)
        holder.desc.text = Html.fromHtml(item["description"], Html.FROM_HTML_MODE_LEGACY)
        holder.itemView.setOnClickListener {
            val intent = Intent(this.context, ReaderActivity::class.java)
            intent.putExtra("book", book)
            intent.putExtra("chapter", position)
            this.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return this.itemList.size
    }
}