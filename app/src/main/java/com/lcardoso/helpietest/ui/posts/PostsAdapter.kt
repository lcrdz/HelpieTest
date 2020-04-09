package com.lcardoso.helpietest.ui.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lcardoso.helpietest.R
import com.lcardoso.helpietest.data.model.PostResponse
import kotlinx.android.synthetic.main.post_item.view.*

class PostsAdapter(
    private val posts: List<PostResponse>
) : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostsViewHolder(view)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bindView(posts[position])
    }

    class PostsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val id = view.tvPostId
        private val title = view.tvTitle
        private val body = view.tvPost

        fun bindView(post: PostResponse) {
            id.text = post.id.toString()
            title.text = post.title
            body.text = post.body
        }
    }
}