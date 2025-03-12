package com.example.naturemagnet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.naturemagnet.adapter.NewsAdapter
import com.example.naturemagnet.adapter.PostsAdapter
import com.example.naturemagnet.entity.PrefManager
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentAwarenessMainBinding
import com.example.naturemagnet.viewmodel.PostDetailsViewModel

class awareness_main : Fragment() {
    lateinit var binding: FragmentAwarenessMainBinding
    lateinit var manager: RecyclerView.LayoutManager
    private lateinit var prefManager: PrefManager
    private val sharedViewModel: PostDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_awareness_main, container, false
        )

        val application = requireNotNull(this.activity).application
        val db = NatureMagnetDB.getInstance(application)!!
        val newsList = db.newsDao().getAllNews()
        val postList = db.postDao().getRandomPost().shuffled()
        prefManager = PrefManager(requireActivity())
        val loginedCus: Long = prefManager.getId()!!

        manager = LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL ,false)
        binding.newsRecyclerView.apply {
            adapter = NewsAdapter(newsList,application,db,loginedCus)
            layoutManager = manager
        }

        manager = LinearLayoutManager(application, LinearLayoutManager.VERTICAL ,false)
        binding.postsRecyclerView.apply {
            adapter = PostsAdapter(postList,application,db,loginedCus,sharedViewModel)
            layoutManager = manager
        }

        binding.viewAllPostsLink.setOnClickListener{
            findNavController().navigate(R.id.action_awarenessMainFragment_to_fragment_all_post)
        }



        return binding.root
    }



}