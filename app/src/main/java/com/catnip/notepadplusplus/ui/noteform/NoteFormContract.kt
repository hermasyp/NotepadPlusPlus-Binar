package com.catnip.notepadplusplus.ui.noteform

import com.catnip.notepadplusplus.base.BaseContract
import com.catnip.notepadplusplus.data.model.Note

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface NoteFormContract {
    interface View : BaseContract.BaseView{
        fun onInsertSuccess(rowsAffected : Number)
        fun onDeleteSuccess(rowsAffected : Number)
        fun onUpdateSuccess(rowsAffected : Number)
        fun onDataFailed(msg : String?)
        fun setPasswordToggleEnabled(isEnabled: Boolean)
    }

    interface Presenter : BaseContract.BasePresenter {
        fun insertNote(note: Note)
        fun deleteNote(note: Note)
        fun updateNote(note: Note)
        fun checkPasswordAvailability()
    }

    interface Repository {
        suspend fun insertNote(note: Note): Long
        suspend fun deleteNote(note: Note): Int
        suspend fun updateNote(note: Note): Int
        fun isPasswordExist() : Boolean
    }
}