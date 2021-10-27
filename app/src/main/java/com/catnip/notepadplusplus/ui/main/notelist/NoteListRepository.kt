package com.catnip.notepadplusplus.ui.main.notelist

import com.catnip.notepadplusplus.data.local.room.datasource.NotesDataSource
import com.catnip.notepadplusplus.data.model.Note

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class NoteListRepository(private val dataSource: NotesDataSource) : NoteListContract.Repository {
    override suspend fun getAllNotes(): List<Note> {
        return dataSource.getAllNotes()
    }

    override suspend fun getArchivedNotes(): List<Note> {
        return dataSource.getArchivedNotes()
    }
}