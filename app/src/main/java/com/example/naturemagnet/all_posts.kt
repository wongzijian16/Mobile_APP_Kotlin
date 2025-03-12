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
import com.example.naturemagnet.adapter.PostDetailsAdapter
import com.example.naturemagnet.entity.PrefManager
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentAllPostsBinding
import com.example.naturemagnet.viewmodel.PostDetailsViewModel

class all_posts : Fragment() {

    lateinit var binding : FragmentAllPostsBinding
    lateinit var manager: RecyclerView.LayoutManager
    private lateinit var prefManager: PrefManager
    private val sharedViewModel: PostDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_all_posts, container, false
        )
        val application = requireNotNull(this.activity).application
        prefManager = PrefManager(requireActivity())
        val loginedCus: Long = prefManager.getId()!!

        val db = NatureMagnetDB.getInstance(application)!!
        val postList = db.postDao().getAllPost().asReversed()

        manager = LinearLayoutManager(application, LinearLayoutManager.VERTICAL ,false)
        binding.postsAllRecyclerView.apply {
            adapter = PostDetailsAdapter(postList,application,db,loginedCus,sharedViewModel)
            layoutManager = manager
        }

        binding.addPostBtn.setOnClickListener{
            findNavController().navigate(R.id.action_fragment_all_post_to_createPostFragment)
        }

        return binding.root
    }

}