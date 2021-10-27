package com.catnip.notepadplusplus.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.catnip.notepadplusplus.R
import com.catnip.notepadplusplus.base.BaseActivity
import com.catnip.notepadplusplus.data.local.preference.UserPreference
import com.catnip.notepadplusplus.databinding.ActivityMainBinding
import com.catnip.notepadplusplus.ui.changepassword.ChangePasswordBottomSheet
import com.catnip.notepadplusplus.ui.enterpassword.EnterPasswordBottomSheet
import com.catnip.notepadplusplus.ui.main.notelist.NoteListFragment
import com.catnip.notepadplusplus.ui.noteform.NoteFormActivity

class MainActivity :
    BaseActivity<ActivityMainBinding, MainActivityContract.Presenter>(ActivityMainBinding::inflate),
    MainActivityContract.View {
    private var allNotesFragment = NoteListFragment()
    private var archivedNotesFragment = NoteListFragment(true)
    private var activeFragment: Fragment = allNotesFragment

    companion object {
        private const val TAG_FRAGMENT_ALL_NOTES = "TAG_FRAGMENT_ALL_NOTES"
        private const val TAG_FRAGMENT_ARCHIVED_NOTES = "TAG_FRAGMENT_ARCHIVED_NOTES"
    }


    override fun initView() {
        setupFragment()
        getViewBinding().fab.setOnClickListener {
            NoteFormActivity.startActivity(this,NoteFormActivity.FORM_MODE_INSERT)
        }
    }

    override fun setupFragment() {
        getViewBinding().bottomNavigationView.background = null
        // delete all fragment in fragment manager first
        for (fragment in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        // add fragment to fragment manager
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fl_container, allNotesFragment, TAG_FRAGMENT_ALL_NOTES)
            add(R.id.fl_container, archivedNotesFragment, TAG_FRAGMENT_ARCHIVED_NOTES)
            hide(archivedNotesFragment)
        }.commit()
        // set title for first fragment
        supportActionBar?.title = getString(R.string.title_toolbar_menu_all_notes)
        // set click menu for changing fragment
        getViewBinding().bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_all_notes -> {
                    supportActionBar?.title = getString(R.string.title_toolbar_menu_all_notes)
                    showFragment(allNotesFragment)
                    true
                }
                else -> {
                    supportActionBar?.title = getString(R.string.title_toolbar_menu_archived_notes)
                    showFragment(archivedNotesFragment)
                    true
                }
            }
        }
    }

    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .hide(activeFragment)
            .show(fragment)
            .commit()
        activeFragment = fragment
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_change_password -> {
                changePassword()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun changePassword(){
        getPresenter().checkPasswordAvailability()
    }

    override fun initPresenter() {
        val repository = MainActivityRepository(UserPreference(this))
        setPresenter(MainActivityPresenter(this, repository))
    }

    override fun showEnterPasswordDialog() {
        EnterPasswordBottomSheet { isPasswordConfirmed ->
            if (isPasswordConfirmed) {
                showDialogChangePassword()
            } else {
                Toast.makeText(this, R.string.text_wrong_password, Toast.LENGTH_SHORT).show()
            }
        }.show(supportFragmentManager, null)
    }

    override fun showDialogChangePassword() {
        ChangePasswordBottomSheet {
            Toast.makeText(this, getString(R.string.text_success_set_password), Toast.LENGTH_SHORT)
                .show()

        }.show(supportFragmentManager, null)
    }
}
