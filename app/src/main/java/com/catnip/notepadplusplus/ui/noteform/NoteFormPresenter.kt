package com.catnip.notepadplusplus.ui.noteform

import com.catnip.notepadplusplus.base.BasePresenterImpl
import com.catnip.notepadplusplus.data.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class NoteFormPresenter(
    private val view: NoteFormContract.View,
    private val repository: NoteFormContract.Repository
) : NoteFormContract.Presenter, BasePresenterImpl() {
    override fun insertNote(note: Note) {
        scope.launch {
            try {
                val affectedRow = repository.insertNote(note)
                scope.launch(Dispatchers.Main) {
                    if (affectedRow > 0) {
                        view.onInsertSuccess(affectedRow)
                    } else {
                        view.onDataFailed(null)
                    }
                }
            } catch (ex: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataFailed(ex.message.orEmpty())
                }
            }
        }
    }

    override fun deleteNote(note: Note) {
        scope.launch {
            try {
                val affectedRow = repository.deleteNote(note)
                scope.launch(Dispatchers.Main) {
                    if (affectedRow > 0) {
                        view.onDeleteSuccess(affectedRow)
                    } else {
                        view.onDataFailed(null)
                    }
                }
            } catch (ex: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataFailed(ex.message.orEmpty())
                }
            }
        }
    }

    override fun updateNote(note: Note) {
        scope.launch {
            try {
                val affectedRow = repository.updateNote(note)
                scope.launch(Dispatchers.Main) {
                    if (affectedRow > 0) {
                        view.onUpdateSuccess(affectedRow)
                    } else {
                        view.onDataFailed(null)
                    }
                }
            } catch (ex: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataFailed(ex.message.orEmpty())
                }
            }
        }
    }

    override fun checkPasswordAvailability() {
        view.setPasswordToggleEnabled(repository.isPasswordExist())
    }


}