package com.example.booksearch.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.navArgs
import com.example.booksearch.databinding.FragmentBookBinding
import com.example.booksearch.ui.viewmodel.BookSearchViewModel
import com.google.android.material.snackbar.Snackbar

class BookFragment : Fragment() {

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<BookFragmentArgs>()
    private lateinit var bookSearchViewModel: BookSearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        val book = args.book
        binding.wvBook.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(book.url)
        }

        binding.fabFavorite.setOnClickListener {
            bookSearchViewModel.saveBook(book)
            Snackbar.make(view, "Book has saved", Snackbar.LENGTH_SHORT).show()
        }

        // 뒤로가기 처리
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
                // 또는 API 33 이상: findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onPause() {
        binding.wvBook.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.wvBook.onResume()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}