package com.example.confiesionesdesanagustin.utils

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nozc.confiesionesdesanagustin.R

class RecyclerAdapter(
    private val context: Context, private val dataList: List<Map<String, String?>>
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.title)
        val descriptionView: TextView = view.findViewById(R.id.description)
        val recycler: RecyclerView = view.findViewById(R.id.recyclerChaptersMain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.titleView.text = Html.fromHtml(item["title"], Html.FROM_HTML_MODE_LEGACY)
        holder.descriptionView.text = Html.fromHtml(item["description"], Html.FROM_HTML_MODE_LEGACY)
        val dbHelper = DatabaseHelper(this.context)
        val chapters = dbHelper.getChapters(position + 1)
        val adapterChapt = AdapterChapDesc(this.context, chapters, position + 1)
        holder.recycler.adapter = adapterChapt
        holder.recycler.layoutManager = LinearLayoutManager(this.context)
        holder.itemView.setOnClickListener {
            if (holder.recycler.visibility == View.GONE) holder.recycler.visibility = View.VISIBLE
            else holder.recycler.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}