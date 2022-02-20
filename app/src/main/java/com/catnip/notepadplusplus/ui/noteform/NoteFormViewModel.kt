package com.catnip.notepadplusplus.ui.noteform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.catnip.notepadplusplus.base.model.Resource
import com.catnip.notepadplusplus.base.arch.BaseViewModelImpl
import com.catnip.notepadplusplus.data.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class NoteFormViewModel(
    private val repository: NoteFormContract.Repository
) : NoteFormContract.ViewModel, BaseViewModelImpl() {

    companion object {
        const val INSERT_OPERATION = 0
        const val EDIT_OPERATION = 1
        const val DELETE_OPERATION = 2
    }

    private val isPasswordAvailable = MutableLiveData<Boolean>()
    private val resultNoteLiveData = MutableLiveData<Pair<Int, Resource<Long>>>()

    override fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val affectedRow = repository.insertNote(note)
                viewModelScope.launch(Dispatchers.Main) {
                    if (affectedRow > 0) {
                        resultNoteLiveData.value =
                            Pair(INSERT_OPERATION, Resource.Success(affectedRow))
                    } else {
                        resultNoteLiveData.value =
                            Pair(INSERT_OPERATION, Resource.Error("", affectedRow))
                    }
                }
            } catch (ex: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    resultNoteLiveData.value =
                        Pair(INSERT_OPERATION, Resource.Error(ex.message.orEmpty()))
                }
            }
        }
    }

    override fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val affectedRow = repository.deleteNote(note)
                viewModelScope.launch(Dispatchers.Main) {
                    if (affectedRow > 0) {
                        resultNoteLiveData.value =
                            Pair(DELETE_OPERATION, Resource.Success(affectedRow.toLong()))
                    } else {
                        resultNoteLiveData.value =
                            Pair(DELETE_OPERATION, Resource.Error("", affectedRow.toLong()))
                    }
                }
            } catch (ex: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    resultNoteLiveData.value =
                        Pair(DELETE_OPERATION, Resource.Error(ex.message.orEmpty()))
                }
            }
        }
    }

    override fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val affectedRow = repository.updateNote(note)
                viewModelScope.launch(Dispatchers.Main) {
                    if (affectedRow > 0) {
                        resultNoteLiveData.value =
                            Pair(EDIT_OPERATION, Resource.Success(affectedRow.toLong()))
                    } else {
                        resultNoteLiveData.value =
                            Pair(EDIT_OPERATION, Resource.Error("", affectedRow.toLong()))
                    }
                }
            } catch (ex: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    resultNoteLiveData.value =
                        Pair(EDIT_OPERATION, Resource.Error(ex.message.orEmpty()))
                }
            }
        }
    }

    override fun checkPasswordAvailability() {
        isPasswordAvailable.value = repository.isPasswordExist()
    }

    override fun getResultNoteLiveData(): MutableLiveData<Pair<Int, Resource<Long>>> =
        resultNoteLiveData

    override fun isPasswordAvailableLiveData(): MutableLiveData<Boolean> = isPasswordAvailable


}