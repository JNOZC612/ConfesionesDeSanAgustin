package com.example.confiesionesdesanagustin.utils

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.confiesionesdesanagustin.objects.Chapter
import com.nozc.confiesionesdesanagustin.R

class AdapterReader(val itemList: List<Chapter>) :
    RecyclerView.Adapter<AdapterReader.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.txtPageTitle)
        val desc: TextView = view.findViewById(R.id.txtPageDesc)
        val cont: TextView = view.findViewById(R.id.txtPageContent)
    }

    override fun getItemCount(): Int {
        return this.itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reader_page, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chapter = itemList[position]
        holder.title.text = Html.fromHtml(chapter.title, Html.FROM_HTML_MODE_LEGACY)
        holder.desc.text = Html.fromHtml(chapter.desc, Html.FROM_HTML_MODE_LEGACY)
        holder.cont.text = Html.fromHtml(chapter.cont, Html.FROM_HTML_MODE_LEGACY)
    }
}