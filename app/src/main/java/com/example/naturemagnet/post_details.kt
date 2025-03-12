package com.example.naturemagnet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentPostDetailsBinding
import com.example.naturemagnet.entity.PostLiked
import com.example.naturemagnet.entity.PostSaved
import com.example.naturemagnet.entity.PrefManager
import com.example.naturemagnet.viewmodel.PostDetailsViewModel
import java.text.SimpleDateFormat
import java.util.*


class post_details : Fragment() {

    lateinit var binding: FragmentPostDetailsBinding
    lateinit var manager: RecyclerView.LayoutManager
    private lateinit var prefManager: PrefManager
    private val sharedViewModel: PostDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_post_details, container, false
        )
        val application = requireNotNull(this.activity).application
        prefManager = PrefManager(requireActivity())
        var loginedCus: Long = prefManager.getId()!!

        var passPost = sharedViewModel.post.value!!

        var postID = passPost.postID

        val db = NatureMagnetDB.getInstance(application)!!

        var cusPostLiked = db.postDao().getCusPostLiked(postID,loginedCus)
        var cusPostSaved = db.postDao().getCusPostSaved(postID,loginedCus)
        var cus = db.customerDao().getCust(passPost.custID)

        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        lateinit var currentDate : String
        binding.loveButton.setOnClickListener {
            try{
                currentDate = sdf.format(Date())
                db.postDao().insertPostLiked(PostLiked(loginedCus,postID,currentDate))
                Glide.with(application).load(R.drawable.ic_love2).into(binding.loveButton)
            }catch (ex:Exception){
                db.postDao().deletePostLiked(PostLiked(loginedCus,postID))
                binding.loveButton.setImageResource(R.drawable.ic_love__2_)
            }
        }
        binding.saveButton.setOnClickListener{
            try{
                currentDate = sdf.format(Date())
                db.postDao().insertPostSaved(PostSaved(loginedCus,postID,currentDate))
                binding.saveButton.setImageResource(R.drawable.ic_baseline_star_24)
            }catch(ex:Exception){
                db.postDao().deletePostSaved(PostSaved(loginedCus,postID))
                binding.saveButton.setImageResource(R.drawable.ic_baseline_star_border_24)
            }
        }

        if(cusPostSaved != null){
            Glide.with(application).load(R.drawable.ic_baseline_star_24).into(binding.saveButton)
        }else{
            binding.saveButton.setImageResource(R.drawable.ic_baseline_star_border_24)
        }

        if(cusPostLiked != null){
            Glide.with(application).load(R.drawable.ic_love2).into(binding.loveButton)
        }else{
            binding.loveButton.setImageResource(R.drawable.ic_love__2_)
        }

        binding.name.text = cus.custName
        binding.postDate.text = passPost.postDate
        binding.mediaImage.setImageBitmap(passPost.imgPost)
        binding.postTitle.text = passPost.title
        binding.postContent.text = passPost.content

        return binding.root
    }


}