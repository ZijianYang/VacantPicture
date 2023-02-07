package com.yzj.vacantpicture.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(var Name: String,var Password: String)
{
    @PrimaryKey(autoGenerate = true)
    var id: Long=0
}