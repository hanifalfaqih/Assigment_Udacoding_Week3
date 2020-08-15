package com.allana.assigmentudacodingweek3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allana.assigmentudacodingweek3.R
import com.allana.assigmentudacodingweek3.model.ArticlesNews
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_news.view.*
import kotlinx.android.synthetic.main.list_item_news.view.*
import kotlinx.android.synthetic.main.list_item_news.view.img_news
import kotlinx.android.synthetic.main.list_item_news.view.tv_news_title

class NewsAdapter(var listNews: ArrayList<ArticlesNews>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNewsSource: TextView? = itemView.tv_news_source
        val tvNewsPublished: TextView? = itemView.tv_news_date_published
        val imgNews: ImageView? = itemView.img_news
        val tvNewsTitle: TextView? = itemView.tv_news_title
        val tvNewsDescription: TextView? = itemView.tv_news_description
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = listNews[position]

        holder.tvNewsSource?.text = news.source?.name

        holder.tvNewsPublished?.text = news.publishedAt

        holder.tvNewsTitle?.text = news.title
        holder.tvNewsDescription?.text = news.description

        holder.imgNews?.let {
            Glide.with(holder.itemView.context)
                .load(news.urlToImage)
                .into(it)
        }

        holder.itemView.setOnClickListener { (onItemClickCallback.onItemClicked(listNews[holder.adapterPosition])) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(news: ArticlesNews)
    }

}