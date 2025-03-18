package com.narayan.singh.scottishpowerandroidtest.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.narayan.singh.scottishpowerandroidtest.common.arch.BaseViewModel
import com.narayan.singh.scottishpowerandroidtest.common.arch.ViewModelUIState
import com.narayan.singh.scottishpowerandroidtest.common.di.IoDispatcher
import com.narayan.singh.scottishpowerandroidtest.domain.model.Comment
import com.narayan.singh.scottishpowerandroidtest.domain.usecase.GetCommentByIdUseCase
import com.narayan.singh.scottishpowerandroidtest.presentation.navigation.NavigationConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentDetailsViewModel @Inject constructor(
    private val getCommentByIdUseCase: GetCommentByIdUseCase,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<CommentDetailsUIState>(initialUIState = CommentDetailsUIState()) {

    init {
        val commentId: String? = savedStateHandle[NavigationConstants.COMMENT_ID_KEY]
        commentId?.let {
            try {
                fetchCommentById(it.toInt())
            } catch (ex: NumberFormatException) {
                _uiState.update { currentState ->
                    currentState.copy(comment = null)
                }
            }
        }
    }

    private fun fetchCommentById(commentId: Int) {
        viewModelScope.launch(ioDispatcher) {
            _uiState.update { currentState ->
                currentState.copy(isLoading = true)
            }
            val comment = getCommentByIdUseCase(commentId)
            _uiState.update { currentState ->
                currentState.copy(isLoading = false, comment = comment)
            }
        }
    }
}

data class CommentDetailsUIState(
    val isLoading: Boolean = false,
    val comment: Comment? = null
) : ViewModelUIState