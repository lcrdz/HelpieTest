package com.lcardoso.helpietest.ui.posts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lcardoso.helpietest.R
import com.lcardoso.helpietest.data.model.PostResponse
import com.lcardoso.helpietest.data.model.StateResponse
import com.lcardoso.helpietest.util.nonNullObserve
import kotlinx.android.synthetic.main.activity_posts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostsActivity : AppCompatActivity() {

    private val viewModel: PostsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        val userId: Int = intent.getIntExtra(USER_ID, 0)

        setupObservables()
        userId.let {
            viewModel.getPosts(it)
        }
    }

    private fun setupObservables() {
        viewModel.postResponse.nonNullObserve(this) { state ->
            processRequest(state)
        }
    }

    private fun processRequest(state: StateResponse<List<PostResponse>>) = when (state) {
        is StateResponse.StateSuccess -> setupAdapter(state.data)
        is StateResponse.StateError -> Toast.makeText(this, "Erro ao carregar, tente novamente...", Toast.LENGTH_SHORT).show()
        is StateResponse.StateLoading -> isLoading(true)
    }

    private fun setupAdapter(data: List<PostResponse>) {
        isLoading(false)
        with(recyclerPosts) {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = PostsAdapter(data)
        }
    }

    private fun isLoading(visible: Boolean): Unit =
        if (visible) pbLoadingPosts.visibility = View.VISIBLE else pbLoadingPosts.visibility = View.GONE

    companion object {
        const val USER_ID: String = "USER_ID"

        fun getStartIntent(context: Context, userId: Int): Intent {
            return Intent(context, PostsActivity::class.java).apply {
                putExtra(USER_ID, userId)
            }
        }
    }
}
