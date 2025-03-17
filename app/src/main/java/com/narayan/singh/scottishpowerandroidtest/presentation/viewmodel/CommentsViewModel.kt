package com.narayan.singh.scottishpowerandroidtest.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.narayan.singh.scottishpowerandroidtest.common.arch.BaseViewModel
import com.narayan.singh.scottishpowerandroidtest.common.arch.ViewModelUIState
import com.narayan.singh.scottishpowerandroidtest.common.di.IoDispatcher
import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import com.narayan.singh.scottishpowerandroidtest.domain.usecase.GetCommentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val getCommentsUseCase: GetCommentsUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<CommentsUIState>(initialUIState = CommentsUIState()) {

    init {
        fetchComments()
    }

    private fun fetchComments() {
        viewModelScope.launch(ioDispatcher) {
            _uiState.update { currentState ->
                currentState.copy(isLoading = true)
            }
            val comments = getCommentsUseCase()
            _uiState.update { currentState ->
                currentState.copy(isLoading = false, comments = comments)
            }
        }
    }
}

data class CommentsUIState(
    val comments: List<Comment> = emptyList(),
    val isLoading: Boolean = false
) : ViewModelUIState