package com.example.naturemagnet

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentEditPostBinding
import com.example.naturemagnet.entity.Post
import com.example.naturemagnet.entity.PrefManager
import com.example.naturemagnet.viewmodel.PostDetailsViewModel
import java.text.SimpleDateFormat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import java.util.*


class edit_post : Fragment() {

    lateinit var binding: FragmentEditPostBinding
    lateinit var bitmap : Bitmap
    lateinit var application: Application
    lateinit var db : NatureMagnetDB
    var postID: Long = 0
    private lateinit var prefManager: PrefManager
    private val sharedViewModel: PostDetailsViewModel by activityViewModels()

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_post, container, false
        )
        application = requireNotNull(this.activity).application

        init()

        binding.imageButton.setOnClickListener {
            pickImageGallery()
        }

        binding.updateBtn.setOnClickListener{
            checkValidPost(application)
        }


        return binding.root
    }

    fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type ="image/*"
        startActivityForResult(intent, create_post.IMAGE_REQUEST_CODE)
    }

    fun init(){
        db = NatureMagnetDB.getInstance(application)!!
        val passPost = sharedViewModel.post.value
        postID = passPost!!.postID
        val post = db.postDao().getPost(postID)

        binding.imageButton.setImageBitmap(post.imgPost)
        binding.textField.editText!!.setText(post.title)
        binding.filledTextField.editText!!.setText(post.content)
    }

    fun checkValidPost(application : Application){
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        sdf.timeZone = TimeZone.getTimeZone("Asia/Kuala_Lumpur")
        val currentDate = sdf.format(Date())

        try {
            val title = binding.textinputTtl.text.toString().trim()
            val content = binding.textinputTxt.text.toString().trim()
            var imgPost = bitmap

            prefManager = PrefManager(requireActivity())
            val loginedCus: Long = prefManager.getId()!!


            if (title.isNotEmpty() && content.isNotEmpty() && imgPost != null) {
                Toast.makeText(application, "The post has been published", Toast.LENGTH_SHORT)
                    .show();
                db.postDao()
                    .updatePost(postID,title, content, imgPost)
                //navigate to post page
                findNavController().navigate(R.id.action_edit_post_to_my_posts_fragment)
            } else {
                Toast.makeText(application, "title/text/image cannot empty", Toast.LENGTH_SHORT)
                    .show();
            }
        }catch (ex : Exception){
            Toast.makeText(application, "Please pick an image", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == create_post.IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Glide.with(this)
                .asBitmap()
                .load(data?.data)
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        @Nullable transition: Transition<in Bitmap?>?
                    ) {
                        binding.imageButton.setImageBitmap(resource)
                        bitmap = resource
                    }

                    override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                })

        }
    }


}