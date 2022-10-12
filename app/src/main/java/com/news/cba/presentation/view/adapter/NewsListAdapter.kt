package com.news.cba.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.news.cba.R
import com.news.cba.data.Articles
import com.squareup.picasso.Picasso

class NewsListAdapter(
    context: Context,
    private val onDisplayItemClickListener: OnDisplayItemClickListener
) : RecyclerView.Adapter<NewsListAdapter.LaunchViewHolder>() {

    var allLaunches: List<Articles> = emptyList()

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        bindPayeeRow(holder, allLaunches.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        return LaunchViewHolder(layoutInflater.inflate(R.layout.news_list, parent, false))
    }

    override fun getItemCount(): Int {
        return allLaunches.size
    }

    private fun bindPayeeRow(holder: LaunchViewHolder, display: Articles) {
        holder.itemView.run {
            holder.name.text = display.title
            holder.date.text = display.publishedAt
            Picasso.get().load(display.urlToImage).into(holder.image)
            setOnClickListener {
                onDisplayItemClickListener.onDisplayItemClicked(display)
            }
        }
    }

    //region View Holder
    class LaunchViewHolder(newsView: View) : RecyclerView.ViewHolder(newsView) {
        val name = newsView.findViewById(R.id.item_title) as TextView
        val date = newsView.findViewById(R.id.item_date) as TextView
        val image = newsView.findViewById(R.id.item_image) as ImageView
    }
    //endregion

    interface OnDisplayItemClickListener {
        fun onDisplayItemClicked(displayItem: Articles)
    }
}