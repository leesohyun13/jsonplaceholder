package com.sohyun.jsonplaceholder.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sohyun.jsonplaceholder.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        const val ARG_ID = "id"
        const val ARG_USER_ID = "userId"
        const val ARG_TITLE = "title"
        const val ARG_BODY = "bobdy"

        @JvmStatic
        fun newInstance(id: Int, userId: Int, title: String, body: String) = DetailFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_ID, id)
                putInt(ARG_USER_ID, userId)
                putString(ARG_TITLE, title)
                putString(ARG_BODY, body)
            }
        }

    }
}