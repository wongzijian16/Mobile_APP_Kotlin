package com.example.naturemagnet

import android.R.attr.path
import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.ColumnInfo
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentCreatePostBinding
import com.example.naturemagnet.entity.Post
import com.example.naturemagnet.entity.PrefManager
import java.text.SimpleDateFormat
import java.util.*


class create_post : Fragment() {
    lateinit var binding: FragmentCreatePostBinding
    lateinit var bitmap : Bitmap
    private lateinit var prefManager: PrefManager

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_post, container, false
        )
        val application = requireNotNull(this.activity).application

        binding.imageButton.setOnClickListener {
            pickImageGallery()
        }

        binding.postBtn.setOnClickListener{
            checkValidPost(application)
        }

        return binding.root
    }

    fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type ="image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    fun checkValidPost(application : Application){
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        sdf.timeZone = TimeZone.getTimeZone("Asia/Kuala_Lumpur")
        val currentDate = sdf.format(Date())

        try {
            val title = binding.textinputTtl.text.toString().trim()
            val content = binding.textinputTxt.text.toString().trim()
            var imgPost = bitmap
            var eventLink = null
            val shareCount = 0
            val postDate = currentDate

            val db = NatureMagnetDB.getInstance(application)!!
            prefManager = PrefManager(requireActivity())
            val loginedCus: Long = prefManager.getId()!!


            if (title.isNotEmpty() && content.isNotEmpty() && imgPost != null) {
                Toast.makeText(application, "The post has been published", Toast.LENGTH_SHORT)
                    .show();
                db.postDao()
                    .insertPost(Post(title, content, imgPost, eventLink, shareCount, postDate, loginedCus))
                //navigate to post page
                findNavController().navigate(R.id.action_createPostFragment_to_fragment_all_post)
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

        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
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