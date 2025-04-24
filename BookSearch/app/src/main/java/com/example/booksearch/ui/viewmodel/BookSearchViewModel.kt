package com.example.booksearch.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksearch.data.model.Book
import com.example.booksearch.data.model.SearchResponse
import com.example.booksearch.data.repository.BookSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// viewModel 그 자체로는 생서 시 초기값을 받을 수 없으므로 factory 필요
class BookSearchViewModel (
    private val bookSearchRepository: BookSearchRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    // Api
    private val _searchResult = MutableLiveData<SearchResponse>()
    val searchResult: LiveData<SearchResponse> get() = _searchResult

    fun searchBooks(query: String) = viewModelScope.launch(Dispatchers.IO) {

        val response = bookSearchRepository.searchBooks(query, "accuracy", 1, 15)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _searchResult.postValue(body)
            }
        }

    }

    // Room
    fun saveBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.insertBooks(book)
    }

    fun deleteBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.deleteBooks(book)
    }

    // Flow
//    val favoriteBooks: Flow<List<Book>> = bookSearchRepository.getFavoriteBooks()

    // favoriteBooks를 stateFlow로 변환해서 fragment의 lifeCycle과 동기화
    // 5000은 백그라운드로 전환되어 flow 수집을 멈추는 경우와, 단순 화면 가로 전환 등 액티비티 변경이어서 flow 수집이 이어지는 경우를 구분하기 위함
    val favoriteBooks: StateFlow<List<Book>> = bookSearchRepository.getFavoriteBooks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    // Saved State
    // 앱이 종료되어도 데이터 유지
    var query = String()
        set(value) {
            field = value
            savedStateHandle[SAVE_STATE_KEY] = value
        }

    // viewModel 초기화 시 savedStateHandle에서 KEY로 값을 가져옴
    // 없으면 empty string
    init {
        query = savedStateHandle.get<String>(SAVE_STATE_KEY) ?: ""
    }

    companion object {
        private const val SAVE_STATE_KEY = "query"
    }

}