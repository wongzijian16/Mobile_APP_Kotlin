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
import com.example.naturemagnet.entity.PrefManager
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentMyPostsBinding
import com.example.naturemagnet.entity.Post
import com.example.naturemagnet.viewmodel.PostDetailsViewModel

class my_posts : Fragment() {

    lateinit var binding: FragmentMyPostsBinding
    lateinit var manager: RecyclerView.LayoutManager
    private lateinit var prefManager: PrefManager
    private val sharedViewModel: PostDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_my_posts, container, false
        )
        val application = requireNotNull(this.activity).application
        prefManager = PrefManager(requireActivity())
        val loginedCus: Long = prefManager.getId()!!

        val db = NatureMagnetDB.getInstance(application)!!
        val cusPostList : MutableList<Post> = db.postDao().getCusPost(loginedCus).asReversed().toMutableList()

        manager = LinearLayoutManager(application, LinearLayoutManager.VERTICAL ,false)
        binding.myPostRecyclerView.apply {
            adapter = MyPostsAdapter(cusPostList,application,db,loginedCus,sharedViewModel)
            layoutManager = manager

        }

        return binding.root
    }


}