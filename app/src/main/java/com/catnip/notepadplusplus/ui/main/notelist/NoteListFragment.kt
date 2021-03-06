package com.catnip.notepadplusplus.ui.main.notelist

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.catnip.notepadplusplus.R
import com.catnip.notepadplusplus.base.BaseFragment
import com.catnip.notepadplusplus.base.model.Resource
import com.catnip.notepadplusplus.data.local.room.NotesDatabase
import com.catnip.notepadplusplus.data.local.room.datasource.NotesDataSourceImpl
import com.catnip.notepadplusplus.data.model.Note
import com.catnip.notepadplusplus.databinding.FragmentNoteListBinding
import com.catnip.notepadplusplus.ui.enterpassword.EnterPasswordBottomSheet
import com.catnip.notepadplusplus.ui.main.notelist.adapter.NoteListAdapter
import com.catnip.notepadplusplus.ui.noteform.NoteFormActivity
import com.catnip.notepadplusplus.utils.CommonFunction
import com.catnip.notepadplusplus.utils.SpacesItemDecoration

class NoteListFragment(private val isArchiveOnly: Boolean = false) :
    BaseFragment<FragmentNoteListBinding, NoteListContract.Presenter>(FragmentNoteListBinding::inflate),
    NoteListContract.View {

    private lateinit var adapter: NoteListAdapter

    override fun initView() {
        initList()
        initSwipeRefresh()
        getData()
    }

    override fun initPresenter() {
        context?.let {
            val dataSource = NotesDataSourceImpl(NotesDatabase.getInstance(it).noteDao())
            val repository = NoteListRepository(dataSource)
            setPresenter(NoteListPresenter(this@NoteListFragment, repository))
        }
    }

    override fun getData() {
        if (isArchiveOnly) getPresenter().getArchivedNotes() else getPresenter().getAllNotes()
    }

    override fun showLoading(isLoading: Boolean) {
        super.showLoading(isLoading)
        getViewBinding().layoutScenario.progressBar.isVisible = isLoading
    }

    override fun showContent(isContentVisible: Boolean) {
        super.showContent(isContentVisible)
        getViewBinding().rvNotes.isVisible = isContentVisible
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        super.showError(isErrorEnabled, msg)
        getViewBinding().layoutScenario.tvMessage.isVisible = isErrorEnabled
        getViewBinding().layoutScenario.tvMessage.text = msg
    }

    override fun onDataCallback(response: Resource<List<Note>>) {
        when (response) {
            is Resource.Loading -> {
                showLoading(true)
                showError(false, null)
                showContent(false)
            }
            is Resource.Success -> {
                showLoading(false)
                response.data?.let {
                    if (it.isEmpty()) {
                        showError(true, getString(R.string.text_empty_notes))
                        showContent(false)
                    } else {
                        showError(false, null)
                        showContent(true)
                        setListData(it)
                    }
                }
            }
            is Resource.Error -> {
                showLoading(false)
                showError(true, response.message)
                showContent(false)
            }
        }
    }

    override fun initList() {
        adapter = NoteListAdapter { note ->
            if (note.isProtected == true) {
                showDialogPassword(note)
            } else {
                NoteFormActivity.startActivity(context, NoteFormActivity.FORM_MODE_EDIT, note)
            }
        }
        getViewBinding().rvNotes.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            addItemDecoration(SpacesItemDecoration(CommonFunction.dpToPixels(context, 8)))
            adapter = this@NoteListFragment.adapter
        }
    }

    override fun initSwipeRefresh() {
        getViewBinding().srlNotes.setOnRefreshListener {
            getData()
            getViewBinding().srlNotes.isRefreshing = false
        }
    }

    override fun setListData(data: List<Note>) {
        adapter.setItems(data)
    }

    override fun showDialogPassword(note: Note) {
        EnterPasswordBottomSheet { isPasswordConfirmed ->
            if (isPasswordConfirmed) {
                NoteFormActivity.startActivity(context, NoteFormActivity.FORM_MODE_EDIT, note)

            } else {
                Toast.makeText(context, getString(R.string.text_wrong_password), Toast.LENGTH_SHORT)
                    .show()
            }
        }.show(childFragmentManager, null)
    }
}
