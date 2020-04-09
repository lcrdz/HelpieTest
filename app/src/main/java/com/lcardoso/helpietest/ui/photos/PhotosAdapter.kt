package com.lcardoso.helpietest.ui.photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lcardoso.helpietest.R
import com.lcardoso.helpietest.data.model.PhotoResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photo_item.view.*

class PhotosAdapter(
    private val photos: List<PhotoResponse>,
    private val onClickListener: (photo: PhotoResponse) -> Unit
) : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return PhotosViewHolder(view, onClickListener)
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: PhotosAdapter.PhotosViewHolder, position: Int) {
        holder.bindView(photos[position])
    }

    class PhotosViewHolder(
        view: View,
        private val onClickListener: (photo: PhotoResponse) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val photoThumb = view.imagePhoto
        private val photoText = view.tvPhoto

        fun bindView(photo: PhotoResponse) {

            Picasso.get().load(photo.thumbnailUrl).placeholder(R.drawable.photo_loading).into(photoThumb)
            photoText.text = photo.title

            itemView.setOnClickListener {
                onClickListener.invoke(photo)
            }
        }

    }
}