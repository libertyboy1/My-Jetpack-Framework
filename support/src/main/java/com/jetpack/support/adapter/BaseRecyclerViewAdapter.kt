package com.jetpack.support.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<B:ViewDataBinding,D>(val mData: MutableLiveData<ArrayList<D>>,private val lifecycle: LifecycleOwner): RecyclerView.Adapter<BaseRecyclerViewAdapter<B,D>.BaseRecyclerViewViewHolder>(){

    abstract fun getLayoutResId():Int

    override fun getItemCount(): Int = mData.value?.size?:0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewViewHolder {
        return BaseRecyclerViewViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),getLayoutResId(),parent,false))
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewViewHolder, position: Int) {
        holder.bind(mData.value?.get(position))
    }

     open inner class BaseRecyclerViewViewHolder(private val binding: B):RecyclerView.ViewHolder(binding.root){
        fun bind(itemData:D?){
            bind(binding,itemData)
        }
     }

    abstract fun bind(binding: B,itemDate:D?)

    init {
        mData.observe(lifecycle){
            notifyItemRangeChanged(0,itemCount)
        }
    }

}