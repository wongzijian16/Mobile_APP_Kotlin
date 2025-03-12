package com.example.naturemagnet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.naturemagnet.adapter.MyPostsAdapter
import com.example.naturemagnet.adapter.NewsSavedAdapter
import com.example.naturemagnet.adapter.PostsSavedAdapter
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentPostDetailsBinding
import com.example.naturemagnet.databinding.FragmentSavedPostsAndNewsBinding
import com.example.naturemagnet.entity.News
import com.example.naturemagnet.entity.Post
import com.example.naturemagnet.entity.PrefManager
import com.example.naturemagnet.entity.entityRelationship.CustomerWithNewsSaved
import com.example.naturemagnet.entity.entityRelationship.CustomerWithPostSaved
import com.example.naturemagnet.viewmodel.PostDetailsViewModel

class saved_posts_and_news : Fragment() {
    lateinit var binding: FragmentSavedPostsAndNewsBinding
    lateinit var manager: RecyclerView.LayoutManager
    private lateinit var prefManager: PrefManager
    private val sharedViewModel: PostDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_saved_posts_and_news, container, false
        )
        val application = requireNotNull(this.activity).application
        prefManager = PrefManager(requireActivity())
        val loginedCus: Long = prefManager.getId()!!
        val db = NatureMagnetDB.getInstance(application)!!
        val cusPostSavedList : MutableList<CustomerWithPostSaved> = db.postDao().getCustomerWithPostSaved(loginedCus)
            .asReversed().toMutableList()
        val cusNewsSavedList : MutableList<CustomerWithNewsSaved> = db.postDao().getCustomerWithNewsSaved(loginedCus)
            .asReversed().toMutableList()

        var savedPostForthisCus: MutableList<Post> = mutableListOf()
        var savedNewsForthisCus: MutableList<News> = mutableListOf()


        cusPostSavedList[0].post.forEachIndexed { _, element ->
            savedPostForthisCus.add(element)
        }
        cusNewsSavedList[0].newsSaved.forEachIndexed { _, element ->
            val news = db.newsDao().getNews(element.newsID)
            savedNewsForthisCus.add(news)
        }


        manager = LinearLayoutManager(application, LinearLayoutManager.VERTICAL ,false)
        binding.postsRecyclerView.apply {
            adapter = PostsSavedAdapter(savedPostForthisCus,application,db,loginedCus,sharedViewModel,binding)
            layoutManager = manager

        }

        manager = LinearLayoutManager(application, LinearLayoutManager.VERTICAL ,false)
        binding.newsRecyclerView.apply {
            adapter = NewsSavedAdapter(savedNewsForthisCus,application,db,loginedCus,binding)
            layoutManager = manager

        }
        return binding.root
    }


}