package com.news.cba.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.news.cba.data.Articles
import com.news.cba.databinding.FragmentFirstBinding
import com.news.cba.databinding.FragmentFirstBinding.inflate
import com.news.cba.presentation.view.adapter.NewsListAdapter
import com.news.cba.presentation.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NewsListFragment : Fragment(), NewsListAdapter.OnDisplayItemClickListener {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by sharedViewModel<NewsViewModel>()
    private lateinit var newsAdapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        viewModel.getAllNews()
        newsAdapter = NewsListAdapter(this.requireContext(), this)
        newsAdapter.also { _binding?.newsList?.adapter = it }
        binding.newsList.layoutManager = LinearLayoutManager(this.context)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.newsList.observe(this.viewLifecycleOwner) {
            newsAdapter?.let { adapter ->
                it?.let { it1 -> adapter.allLaunches = it1 }
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDisplayItemClicked(displayItem: Articles) {
        viewModel.selectedArticle(displayItem, this)
    }
}