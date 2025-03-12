package com.example.naturemagnet.adapter

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.naturemagnet.R
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentSavedPostsAndNewsBinding
import com.example.naturemagnet.databinding.NewsHorizontalItemBinding
import com.example.naturemagnet.databinding.NewsVerticalItemBinding
import com.example.naturemagnet.entity.News
import com.example.naturemagnet.entity.NewsSaved
import java.text.SimpleDateFormat
import java.util.*

class NewsSavedAdapter(
    private val data: MutableList<News>, val application: Application, val db: NatureMagnetDB,
    val cusID: Long, private val sBinding: FragmentSavedPostsAndNewsBinding
) :
    RecyclerView.Adapter<NewsSavedAdapter.NewsSavedAdapterViewHolder>() {

    inner class NewsSavedAdapterViewHolder(val binding: NewsVerticalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: News, position: Int) {
            binding.news = item
            binding.indexNumNews.text = (position + 1).toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsSavedAdapter.NewsSavedAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val newsListBinding = NewsVerticalItemBinding.inflate(inflater, parent, false)

        return NewsSavedAdapterViewHolder(newsListBinding)

    }

    override fun onBindViewHolder(
        holder: NewsSavedAdapter.NewsSavedAdapterViewHolder,
        position: Int
    ) {
        holder.bind(data[position], position)
        val newsID = data[position].newsID

        val loginedCus: Long = cusID


        holder.binding.imageView7.setOnClickListener {
            Log.e("ClickSaveBtn", "clicked Save btn")

            Log.e("ClickSaveBtn", "unsaved")
            db.newsDao().deleteNewsSaved(NewsSaved(loginedCus, data[position].newsID))
            data.remove(data[position])
            sBinding.viewMoreNewsLink.text = "Save ($itemCount)"
            this.notifyDataSetChanged()
        }

        holder.binding.root.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data[position].newsLink))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(intent)
        }

        var title = data[position].title
        if (title!!.length > 25) {
            title = title.substring(0, 25) + "...."
        }

        sBinding.viewMoreNewsLink.text = "Save ($itemCount)"
        holder.binding.title.text = title
        holder.binding.subTitle.text = data[position].sourceName + "-" + data[position].publishedDate
        holder.binding.imageView7.setImageResource(R.drawable.ic_baseline_star_24)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}