package com.catnip.notepadplusplus.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Parcelize
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "body")
    var body: String?,
    @ColumnInfo(name = "is_archived")
    var isArchived: Boolean?,
    @ColumnInfo(name = "is_protected")
    var isProtected: Boolean?,
    @ColumnInfo(name = "hex_card_color")
    var hexCardColor: String?,
    @ColumnInfo(name = "created_at")
    var createdAt: Long = 0,
    @ColumnInfo(name = "modified_at")
    var modifiedAt: Long = 0
) : Parcelable
