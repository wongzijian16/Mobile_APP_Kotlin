package com.example.naturemagnet.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.naturemagnet.R
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.MyPostCardBinding
import com.example.naturemagnet.entity.Post
import com.example.naturemagnet.viewmodel.PostDetailsViewModel


class MyPostsAdapter(private val data: MutableList<Post>,val application : Application,
                     val db : NatureMagnetDB,val cusID :Long, val sharedViewModel: PostDetailsViewModel)  :
    RecyclerView.Adapter<MyPostsAdapter.MyPostsAdapterViewHolder>() {


    inner class MyPostsAdapterViewHolder(val binding: MyPostCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post, position: Int) {
            binding.post = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostsAdapter.MyPostsAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val postListBinding = MyPostCardBinding.inflate(inflater,parent,false)

        return MyPostsAdapterViewHolder(postListBinding)

    }

    override fun onBindViewHolder(holder: MyPostsAdapter.MyPostsAdapterViewHolder, position: Int) {
        holder.bind(data[position],position)

        var title = data[position].title
        val date = data[position].postDate
        val bitmap = data[position].imgPost
        val cus = db.customerDao().getCust(data[position].custID)
        val postID = data[position].postID

        val loginedCus: Long = cusID

        holder.binding.deleteBtn.setOnClickListener{
            db.postDao().deleteCusPost(postID)
            data.remove(data[position])
            Toast.makeText(application, "Post has been deleted", Toast.LENGTH_SHORT)
                .show()
            this.notifyDataSetChanged()
        }
        var passPost : MutableLiveData<Post>
        holder.binding.updateBtn.setOnClickListener {
            passPost = MutableLiveData<Post>(data[position])
            sharedViewModel.setPost(passPost)
            it.findNavController().navigate(R.id.action_my_posts_fragment_to_edit_post)
        }

        holder.binding.root.setOnClickListener{
            passPost = MutableLiveData<Post>(data[position])
            sharedViewModel.setPost(passPost)
            it.findNavController().navigate(R.id.action_my_posts_fragment_to_product_details)
        }

        if (title!!.length > 20) {
            title = title.substring(0, 20) + "...."
        }


        holder.binding.postTitle.text = title
        holder.binding.imageView.setImageBitmap(bitmap)
        holder.binding.postAuthor.text = "By ${cus.custName}"
        holder.binding.date.text = date

    }

    override fun getItemCount(): Int {
        return data.size
    }
}