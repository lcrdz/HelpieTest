package com.lcardoso.helpietest.ui.photos

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.lcardoso.helpietest.R
import com.lcardoso.helpietest.data.model.PhotoResponse
import com.lcardoso.helpietest.data.model.StateResponse
import com.lcardoso.helpietest.util.nonNullObserve
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photos_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotosFragment : Fragment() {

    companion object {
        const val ALBUM_ID: Int = 1
    }

    private val viewModel: PhotosViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.photos_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObservables()
        viewModel.getPhotos(ALBUM_ID)
    }

    private fun setupObservables() {
        viewModel.photosResponse.nonNullObserve(viewLifecycleOwner) { state ->
            processRequest(state)
        }
    }

    private fun processRequest(state: StateResponse<List<PhotoResponse>>) = when (state) {
        is StateResponse.StateSuccess -> setupAdapter(state.data)
        is StateResponse.StateError -> Toast.makeText(context, "Erro ao carregar, tente novamente...", Toast.LENGTH_SHORT).show()
        is StateResponse.StateLoading -> isLoading(true)
    }

    private fun setupAdapter(data: List<PhotoResponse>) {
        isLoading(false)
        with(recyclerPhotos) {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = PhotosAdapter(data) { photo ->
                showPhoto(photo.url)
            }
        }
    }

    private fun isLoading(visible: Boolean): Unit =
        if (visible) pbLoadingPhotos.visibility = View.VISIBLE else pbLoadingPhotos.visibility =
            View.GONE

    private fun showPhoto(url: String) {
        val builder = AlertDialog.Builder(this.context)
        val view = layoutInflater.inflate(R.layout.open_photo_item, null)
        val openPhoto = view.findViewById<ImageView>(R.id.image)
        val close = view.findViewById<ImageView>(R.id.btnClose)

        builder.setView(view)
        val alert = builder.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.setCancelable(false)
        alert.show()

        Picasso.get().load(url).placeholder(R.drawable.photo_loading).into(openPhoto)
        close.setOnClickListener {
            alert.cancel()
        }
    }
}