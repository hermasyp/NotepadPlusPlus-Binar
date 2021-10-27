package com.catnip.notepadplusplus.ui.noteform

import com.catnip.notepadplusplus.data.local.preference.UserPreference
import com.catnip.notepadplusplus.data.local.room.datasource.NotesDataSource
import com.catnip.notepadplusplus.data.model.Note

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class NoteFormRepository(
    private val localDataSource: NotesDataSource,
    private val preference: UserPreference
) :
    NoteFormContract.Repository {
    override suspend fun insertNote(note: Note): Long {
        return localDataSource.insertNote(note)
    }

    override suspend fun deleteNote(note: Note): Int {
        return localDataSource.deleteNote(note)
    }

    override suspend fun updateNote(note: Note): Int {
        return localDataSource.updateNote(note)
    }

    override fun isPasswordExist(): Boolean {
        return !preference.password.isNullOrEmpty()
    }

}