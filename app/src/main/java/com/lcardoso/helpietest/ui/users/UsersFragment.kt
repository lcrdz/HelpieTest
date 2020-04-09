package com.lcardoso.helpietest.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lcardoso.helpietest.R
import com.lcardoso.helpietest.data.HelpieRepository
import com.lcardoso.helpietest.data.model.StateResponse
import com.lcardoso.helpietest.data.model.UserResponse
import com.lcardoso.helpietest.ui.posts.PostsActivity
import com.lcardoso.helpietest.util.nonNullObserve
import kotlinx.android.synthetic.main.users_fragment.*

class UsersFragment : Fragment() {

    companion object {
        fun newInstance() = UsersFragment()
    }

    private lateinit var viewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.users_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this, UsersViewModel.UsersViewModelFactory(HelpieRepository())
        ).get(UsersViewModel::class.java)

        setupObservables()
        viewModel.getUsers()
    }

    private fun setupObservables() {
        viewModel.userResponse.nonNullObserve(viewLifecycleOwner) { state ->
            processRequest(state)
        }
    }

    private fun processRequest(state: StateResponse<List<UserResponse>>) = when (state) {
        is StateResponse.StateSuccess -> setupAdapter(state.data)
        is StateResponse.StateError -> Toast.makeText(context, "Erro ao carregar, tente novamente...", Toast.LENGTH_SHORT).show()
        is StateResponse.StateLoading -> isLoading(true)
    }

    private fun setupAdapter(data: List<UserResponse>) {
        isLoading(false)
        with(recyclerUsers) {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = UsersAdapter(data) { user -> redirectToPostsScreen(user.id) }
        }
    }

    private fun redirectToPostsScreen(userId: Int) {
        val intent = PostsActivity.getStartIntent(
            this.context!!.applicationContext,
            userId
        )
        this.startActivity(intent)
    }

    private fun isLoading(visible: Boolean): Unit =
        if (visible) pbLoading.visibility = View.VISIBLE else pbLoading.visibility = View.GONE
}
