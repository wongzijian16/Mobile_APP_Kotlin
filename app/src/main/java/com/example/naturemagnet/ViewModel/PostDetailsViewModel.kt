package com.example.naturemagnet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.naturemagnet.entity.Activity
import com.example.naturemagnet.entity.Post

class PostDetailsViewModel : ViewModel(){
    private var _post = MutableLiveData<Post>()
    private var _parent: LiveData<String>? = MutableLiveData<String>()

    val post: LiveData<Post>
        get() = _post
    val parent: LiveData<String>?
        get() = _parent

    init {
        _post = MutableLiveData<Post>()
        _parent = null
    }

    fun setPost(post: MutableLiveData<Post>) {
        _post = post
    }
    fun setParent(parent: MutableLiveData<String>){
        _parent = parent
    }
}