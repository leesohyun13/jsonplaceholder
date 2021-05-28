package com.sohyun.jsonplaceholder.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sohyun.jsonplaceholder.R
import com.sohyun.jsonplaceholder.data.model.Post
import com.sohyun.jsonplaceholder.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var post: Post
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            post = it[ARG_POST] as Post
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false).apply {
            viewmodel = detailViewModel
            lifecycleOwner = this@DetailFragment
        }

        lifecycleScope.launch { detailViewModel.fetchComments(post.id) }

        with(binding) {
            title.text = post.title
            body.text = post.body
            commentRecyclerview.run {
                commentAdapter = CommentAdapter()
                adapter = commentAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(
                    DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL)
                )
            }
            update.setOnClickListener { selectDialog() }
        }

        return binding.root
    }

    private fun selectDialog() {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(getString(R.string.array_select_update_item_title))
                setItems(
                    R.array.update_list
                ) { dialog, which ->
                    when (which) {
                        0 -> {
                            editDialog(UpdateType.TITLE)
                        }
                        1 -> {
                            editDialog(UpdateType.BODY)
                        }
                    }
                }
            }
            builder.create()
        }
        alertDialog?.show()
    }

    private fun editDialog(type: UpdateType) {
        val content: String = when (type) {
            UpdateType.TITLE -> binding.title.text.toString()
            UpdateType.BODY -> binding.body.text.toString()
        }
        //
        val editText = EditText(context).apply {
            setText(content)
        }
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(getString(R.string.array_update_title))
                setView(editText)
                setPositiveButton(
                    R.string.update
                ) { dialog, id ->
                    val updateText = editText.text.toString()
                    update(type, updateText)
                    detailViewModel.update(
                        post.id, hashMapOf(
                            type.text to updateText
                        )
                    )
                }
                setNegativeButton(
                    R.string.no
                ) { dialog, id ->
                    // do nothing
                }
            }
            builder.create()
        }
        alertDialog?.show()
    }

    private fun update(type: UpdateType, string: String) {
        when (type) {
            UpdateType.TITLE -> binding.title.text = string
            UpdateType.BODY -> binding.body.text = string
        }
    }

    companion object {
        const val ARG_POST = "post"

        @JvmStatic
        fun newInstance(post: Post) = DetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_POST, post)
            }
        }

    }
}