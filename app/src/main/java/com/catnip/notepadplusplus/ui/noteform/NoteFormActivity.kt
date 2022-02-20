package com.catnip.notepadplusplus.ui.noteform

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.catnip.notepadplusplus.R
import com.catnip.notepadplusplus.base.model.Resource
import com.catnip.notepadplusplus.base.arch.BaseActivity
import com.catnip.notepadplusplus.base.arch.GenericViewModelFactory
import com.catnip.notepadplusplus.data.local.preference.UserPreference
import com.catnip.notepadplusplus.data.local.room.NotesDatabase
import com.catnip.notepadplusplus.data.local.room.datasource.NotesDataSourceImpl
import com.catnip.notepadplusplus.data.model.Note
import com.catnip.notepadplusplus.databinding.ActivityNoteFormBinding
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch

class NoteFormActivity :
    BaseActivity<ActivityNoteFormBinding, NoteFormViewModel>(ActivityNoteFormBinding::inflate),
    NoteFormContract.View {

    private var formMode = FORM_MODE_INSERT
    private var note: Note? = null
    private var pickedCardColor = DEFAULT_CARD_COLOR


    override fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getIntentData()
        initializeForm()
        setClickListeners()
    }


    private fun getIntentData() {
        formMode = intent.getIntExtra(INTENT_FORM_MODE, FORM_MODE_INSERT)
        note = intent.getParcelableExtra(INTENT_NOTE_DATA)
    }

    override fun onInsertSuccess(rowsAffected: Number) {
        Toast.makeText(this, "Insert Data Success", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onDeleteSuccess(rowsAffected: Number) {
        Toast.makeText(this, "Delete Data Success", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onUpdateSuccess(rowsAffected: Number) {
        Toast.makeText(this, "Update Data Success", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onDataFailed(msg: String?) {
        Toast.makeText(this, msg ?: "Something Error when Execute Query", Toast.LENGTH_SHORT).show()
    }

    override fun setPasswordToggleEnabled(isEnabled: Boolean) {
        getViewBinding().swProtectNote.isEnabled = isEnabled
    }

    override fun observeData() {
        getViewModel().getResultNoteLiveData().observe(this) {
            when (it.first) {
                NoteFormViewModel.INSERT_OPERATION -> {
                    if (it.second is Resource.Success) {
                        onInsertSuccess(it.second.data ?: 0)
                    }
                    else{
                        onDataFailed(it.second.message)
                    }
                }
                NoteFormViewModel.EDIT_OPERATION -> {
                    if (it.second is Resource.Success) {
                        onUpdateSuccess(it.second.data ?: 0)
                    }
                    else{
                        onDataFailed(it.second.message)
                    }
                }
                NoteFormViewModel.DELETE_OPERATION -> {
                    if (it.second is Resource.Success) {
                        onDeleteSuccess(it.second.data ?: 0)
                    }
                    else{
                        onDataFailed(it.second.message)
                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form_note, menu)
        val menuDelete = menu?.findItem(R.id.menu_delete)
        menuDelete?.isVisible = formMode == FORM_MODE_EDIT
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_save -> {
                saveNote()
                true
            }
            R.id.menu_delete -> {
                deleteNote()
                true
            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                true
            }
        }
    }

    private fun deleteNote() {
        if (formMode == FORM_MODE_EDIT) {
            note?.let {
                getViewModel().deleteNote(it)
            }
        }
    }

    private fun saveNote() {
        if (validateForm()) {
            if (formMode == FORM_MODE_EDIT) {
                // do edit
                note = note?.copy()?.apply {
                    title = getViewBinding().etNoteTitle.text.toString()
                    body = getViewBinding().etNoteBody.text.toString()
                    isArchived = getViewBinding().swArchiveNote.isChecked
                    isProtected = getViewBinding().swProtectNote.isChecked
                    hexCardColor = pickedCardColor
                }
                note?.let { getViewModel().updateNote(it) }
            } else {
                //do insert
                note = Note(
                    title = getViewBinding().etNoteTitle.text.toString(),
                    body = getViewBinding().etNoteBody.text.toString(),
                    isArchived = getViewBinding().swArchiveNote.isChecked,
                    isProtected = getViewBinding().swProtectNote.isChecked,
                    hexCardColor = pickedCardColor
                )
                note?.let { getViewModel().insertNote(it) }
            }
        }
    }

    private fun validateForm(): Boolean {
        val title = getViewBinding().etNoteTitle.text.toString()
        var isFormValid = true
        //for checking is title empty
        if (title.isEmpty()) {
            isFormValid = false
            getViewBinding().tilNoteTitle.isErrorEnabled = true
            getViewBinding().tilNoteTitle.error = getString(R.string.error_form_note_title_empty)
        } else {
            getViewBinding().tilNoteTitle.isErrorEnabled = false
        }
        return isFormValid
    }

    private fun initializeForm() {
        getViewModel().checkPasswordAvailability()
        if (formMode == FORM_MODE_EDIT) {
            note?.let {
                getViewBinding().etNoteTitle.setText(it.title)
                getViewBinding().etNoteBody.setText(it.body)
                getViewBinding().swArchiveNote.isChecked = it.isArchived ?: false
                getViewBinding().swProtectNote.isChecked = it.isProtected ?: false
                pickedCardColor = it.hexCardColor ?: DEFAULT_CARD_COLOR
            }
            //"Edit Data"
            supportActionBar?.title = getString(R.string.text_title_edit_note_form_activity)
        } else {
            supportActionBar?.title = getString(R.string.text_title_insert_note_form_activity)
        }
        getViewBinding().ivColorPreview.setBackgroundColor(Color.parseColor(pickedCardColor))
    }

    private fun showColorPickerDialog() {
        MaterialColorPickerDialog
            .Builder(this)                            // Pass Activity Instance
            .setTitle(getString(R.string.text_color_picker_title))                // Default "Choose Color"
            .setColorShape(ColorShape.SQAURE)    // Default ColorShape.CIRCLE
            .setColorSwatch(ColorSwatch._300)    // Default ColorSwatch._500
            .setDefaultColor(pickedCardColor)        // Pass Default Color
            .setColorListener { _, colorHex ->
                // Handle Color Selection
                pickedCardColor = colorHex
                getViewBinding().ivColorPreview.setBackgroundColor(Color.parseColor(pickedCardColor))
            }
            .show()
    }

    private fun setClickListeners() {
        getViewBinding().llCardColor.setOnClickListener {
            showColorPickerDialog()
        }
    }

    companion object {
        const val FORM_MODE_INSERT = 0
        const val FORM_MODE_EDIT = 1
        const val INTENT_FORM_MODE = "INTENT_FORM_MODE"
        const val INTENT_NOTE_DATA = "INTENT_NOTE_DATA"
        private const val DEFAULT_CARD_COLOR = "#d3bdff"

        @JvmStatic
        fun startActivity(context: Context?, formMode: Int, note: Note? = null) {
            val intent = Intent(context, NoteFormActivity::class.java)
            intent.putExtra(INTENT_FORM_MODE, formMode)
            note?.let {
                intent.putExtra(INTENT_NOTE_DATA, note)
            }
            context?.startActivity(intent)
        }
    }

    override fun initViewModel(): NoteFormViewModel {
        val dataSource = NotesDataSourceImpl(NotesDatabase.getInstance(this).noteDao())
        val repository = NoteFormRepository(dataSource, UserPreference(this))
        return GenericViewModelFactory(NoteFormViewModel(repository)).create(
            NoteFormViewModel::class.java
        )
    }
}