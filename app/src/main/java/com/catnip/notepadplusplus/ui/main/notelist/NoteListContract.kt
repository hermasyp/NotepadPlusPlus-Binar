package com.catnip.notepadplusplus.ui.main.notelist

import com.catnip.notepadplusplus.base.BaseContract
import com.catnip.notepadplusplus.base.model.Resource
import com.catnip.notepadplusplus.data.model.Note

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface NoteListContract {
    interface View : BaseContract.BaseView{
        fun getData()
        fun onDataCallback(response: Resource<List<Note>>)
        fun initList()
        fun initSwipeRefresh()
        fun setListData(data: List<Note>)
        fun showDialogPassword(note: Note)

    }

    interface Presenter : BaseContract.BasePresenter {
        fun getAllNotes()
        fun getArchivedNotes()
    }

    interface Repository {
        suspend fun getAllNotes(): List<Note>
        suspend fun getArchivedNotes(): List<Note>
    }
}