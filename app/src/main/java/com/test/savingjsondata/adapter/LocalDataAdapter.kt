package com.test.savingjsondata.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.savingjsondata.R
import com.test.savingjsondata.database.LocalEntity
import com.test.savingjsondata.databinding.CustomAdapterViewBinding

class LocalDataAdapter(
    private val context: Context,
    private val list: ArrayList<LocalEntity>
) : RecyclerView.Adapter<LocalDataAdapter.LocalDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalDataViewHolder {
        val binding = CustomAdapterViewBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.custom_adapter_view, parent, false)
        )

        return LocalDataViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: LocalDataViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    inner class LocalDataViewHolder(private val binding: CustomAdapterViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(localEntity: LocalEntity) {
            binding.apply {
                Picasso.get()
                    .load(localEntity.url)
                    .error(R.drawable.baseline_image_24)
                    .into(cavImageIv)

                cavImageTitleTv.text = localEntity.title
            }
        }
    }
}