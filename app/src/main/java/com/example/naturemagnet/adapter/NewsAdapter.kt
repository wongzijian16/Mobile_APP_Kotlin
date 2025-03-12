package com.example.naturemagnet.adapter

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.naturemagnet.R
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.NewsHorizontalItemBinding
import com.example.naturemagnet.entity.*
import java.text.SimpleDateFormat
import java.util.*


class NewsAdapter(private val data: List<News>, val application : Application,val db: NatureMagnetDB,
                  val cusID:Long) :
    RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>() {

    inner class NewsAdapterViewHolder(val binding: NewsHorizontalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: News,position: Int) {
            binding.news = item
            binding.indexNumNews.text = (position+1).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val newsListBinding = NewsHorizontalItemBinding.inflate(inflater,parent,false)

        return NewsAdapterViewHolder(newsListBinding)

    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsAdapterViewHolder, position: Int) {
        holder.bind(data[position],position)
        val newsID = data[position].newsID

        val loginedCus: Long = cusID

        val cusNewsSaved  = db.postDao().getCustomerWithNewsSaved(loginedCus)

        var savedNewsForthisCus: MutableList<Long> = mutableListOf()


        cusNewsSaved[0].newsSaved.forEachIndexed { _, element ->
            savedNewsForthisCus.add(element.newsID)
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        lateinit var currentDate : String

        holder.binding.imageView4.setOnClickListener {
            Log.e("ClickSaveBtn","clicked Save btn")

            if(!savedNewsForthisCus.contains(newsID)){
                Log.e("ClickSaveBtn","saved")
                currentDate = sdf.format(Date())
                db.newsDao().insertCusNewsSaved(NewsSaved(loginedCus,data[position].newsID,currentDate))
            }else{
                Log.e("ClickSaveBtn","unsaved")
                db.newsDao().deleteNewsSaved(NewsSaved(loginedCus,newsID))
            }
            this.notifyDataSetChanged()
        }

        holder.binding.root.setOnClickListener{

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data[position].newsLink))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(intent)
        }

        if(savedNewsForthisCus.contains(newsID)){
            holder.binding.imageView4.setImageResource(R.drawable.ic_baseline_star_24)
        }else{
            holder.binding.imageView4.setImageResource(R.drawable.ic_baseline_star_border_24)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}