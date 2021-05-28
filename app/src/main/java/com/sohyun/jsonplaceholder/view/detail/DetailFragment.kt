package com.sohyun.jsonplaceholder.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
            }
        }

        return binding.root
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