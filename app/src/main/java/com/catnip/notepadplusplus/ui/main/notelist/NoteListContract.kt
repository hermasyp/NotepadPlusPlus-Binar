package com.catnip.notepadplusplus.ui.main.notelist

import androidx.lifecycle.MutableLiveData
import com.catnip.notepadplusplus.base.model.Resource
import com.catnip.notepadplusplus.base.arch.BaseContract
import com.catnip.notepadplusplus.data.model.Note

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface NoteListContract {
    interface View : BaseContract.BaseView {
        fun getData()
        fun initList()
        fun initSwipeRefresh()
        fun setListData(data: List<Note>)
        fun showDialogPassword(note: Note)
    }

    interface ViewModel : BaseContract.BaseViewModel {
        fun getAllNotes()
        fun getArchivedNotes()
        fun getNotesLiveData() : MutableLiveData<Resource<List<Note>>>
    }

    interface Repository {
        suspend fun getAllNotes(): List<Note>
        suspend fun getArchivedNotes(): List<Note>
    }
}