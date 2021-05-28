package com.sohyun.jsonplaceholder.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sohyun.jsonplaceholder.R
import com.sohyun.jsonplaceholder.data.model.Post
import com.sohyun.jsonplaceholder.databinding.FragmentMainBinding
import com.sohyun.jsonplaceholder.showToastMessage
import com.sohyun.jsonplaceholder.view.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(), OnItemClickListener {
    private lateinit var binding: FragmentMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false).apply {
            viewmodel = mainViewModel
            lifecycleOwner = this@MainFragment
        }

        binding.postRecyclerview.run {
            postAdapter = PostAdapter(this@MainFragment)
            adapter = postAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            ))

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(-1)) {
                        // detect top
                    } else if (!recyclerView.canScrollVertically(1)) {
                        // detect bottom
                        if (mainViewModel.isLoading().value!!) return
                        mainViewModel.increasePage()
                    }
                }
            })
        }

        mainViewModel.getResponse().observe(viewLifecycleOwner, { response ->
            if (response.isNullOrEmpty()) return@observe
            showToastMessage(requireContext(), response)
        })

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun clickedPost(post: Post) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_framelayout, DetailFragment.newInstance(post))
            .commitNow()
    }

    override fun deletePost(postId: Int) {
        lifecycleScope.launch { mainViewModel.deletePost(postId) }
    }
}