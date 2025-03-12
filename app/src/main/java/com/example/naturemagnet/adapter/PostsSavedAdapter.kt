package com.example.naturemagnet.adapter

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.naturemagnet.R
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentSavedPostsAndNewsBinding
import com.example.naturemagnet.databinding.PostsVerticalItemBinding
import com.example.naturemagnet.entity.Post
import com.example.naturemagnet.entity.PostLiked
import com.example.naturemagnet.entity.PostSaved
import com.example.naturemagnet.viewmodel.PostDetailsViewModel
import java.text.SimpleDateFormat
import java.util.*

class PostsSavedAdapter(
    private val data: MutableList<Post>, val application: Application, val db: NatureMagnetDB,
    val cusID: Long, val sharedViewModel: PostDetailsViewModel, private val sBinding: FragmentSavedPostsAndNewsBinding
) :
    RecyclerView.Adapter<PostsSavedAdapter.PostsSavedAdapterViewHolder>() {

    inner class PostsSavedAdapterViewHolder(val binding: PostsVerticalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post, position: Int) {
            binding.post = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostsSavedAdapter.PostsSavedAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val postListBinding = PostsVerticalItemBinding.inflate(inflater, parent, false)

        return PostsSavedAdapterViewHolder(postListBinding)

    }

    override fun onBindViewHolder(
        holder: PostsSavedAdapter.PostsSavedAdapterViewHolder,
        position: Int
    ) {
        holder.bind(data[position], position)
        var title = data[position].title
        var content = data[position].content
        val date = data[position].postDate
        val bitmap = data[position].imgPost
        val cus = db.customerDao().getCust(data[position].custID)
        val postID = data[position].postID
        var passPost: MutableLiveData<Post>
        val loginedCus: Long = cusID

        val cusPostLiked = db.postDao().getCustomerWithPostLiked(loginedCus)


        var likedPostForthisCus: MutableList<Long> = mutableListOf()

        cusPostLiked[0].post.forEachIndexed { _, element ->
            likedPostForthisCus.add(element.postID)
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        lateinit var currentDate: String

        holder.binding.saveBtn.setOnClickListener {

            Log.e("ClickSaveBtn", "unsaved")
            db.postDao().deletePostSaved(PostSaved(loginedCus, data[position].postID))
            data.remove(data[position])
            sBinding.viewAllPostsLink.text = "Save ($itemCount)"
            this.notifyDataSetChanged()
        }

        holder.binding.loveBtn.setOnClickListener {
            Log.e("ClickLikeBtn", "clicked like btn")

            if (!likedPostForthisCus.contains(postID)) {
                Log.e("ClickLikeBtn", "liked")
                currentDate = sdf.format(Date())
                db.postDao()
                    .insertPostLiked(PostLiked(loginedCus, data[position].postID, currentDate))
            } else {
                Log.e("ClickLikeBtn", "unlike")
                db.postDao().deletePostLiked(PostLiked(loginedCus, data[position].postID))
            }
            this.notifyDataSetChanged()
        }

        holder.binding.root.setOnClickListener {
            passPost = MutableLiveData<Post>(data[position])
            sharedViewModel.setPost(passPost)
            it.findNavController().navigate(R.id.action_saved_posts_and_news_to_post_details)
        }

        if (title!!.length > 20) {
            title = title.substring(0, 20) + "...."
        }

        Glide.with(application).load(R.drawable.ic_baseline_star_24)
            .into(holder.binding.saveBtn)

        //check customer liked post
        if (likedPostForthisCus.contains(postID)) {
            Glide.with(application).load(R.drawable.ic_love2).into(holder.binding.loveBtn)
        } else {
            holder.binding.loveBtn.setImageResource(R.drawable.ic_love__2_)
        }

        sBinding.viewAllPostsLink.text = "Save ($itemCount)"
        holder.binding.titlePost.text = title
        holder.binding.date.text = date
        holder.binding.authorName.text = "By " + cus.custName
        holder.binding.imageView.setImageBitmap(bitmap)

    }

    override fun getItemCount(): Int {
        return data.size
    }
}