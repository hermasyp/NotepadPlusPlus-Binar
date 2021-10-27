package com.catnip.notepadplusplus.ui.main.notelist

import com.catnip.notepadplusplus.base.BasePresenterImpl
import com.catnip.notepadplusplus.base.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class NoteListPresenter(
    private val view: NoteListContract.View,
    private val repository: NoteListContract.Repository
) : NoteListContract.Presenter, BasePresenterImpl() {
    override fun getAllNotes() {
        view.onDataCallback(Resource.Loading())
        scope.launch {
            try {
                delay(2000)
                val notes = repository.getAllNotes()
                scope.launch(Dispatchers.Main) {
                    //check if data is empty
                    view.onDataCallback(Resource.Success(notes))
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }

    override fun getArchivedNotes() {
        view.onDataCallback(Resource.Loading())
        scope.launch {
            try {
                delay(2000)
                val notes = repository.getArchivedNotes()
                scope.launch(Dispatchers.Main) {
                    //check if data is empty
                    view.onDataCallback(Resource.Success(notes))
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }

}