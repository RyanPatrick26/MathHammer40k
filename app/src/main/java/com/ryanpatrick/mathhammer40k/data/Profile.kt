package com.ryanpatrick.mathhammer40k.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class Profile(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo("profile_name") val profileName: String,
    @ColumnInfo("weapons") val weapons: List<Weapon>,
    @ColumnInfo("model_count") val modelCount: Int = 0,
    @ColumnInfo("toughness") val toughness: Int = 0,
    @ColumnInfo("wounds") val wounds: Int = 0,
    @ColumnInfo("save") val save: Int = 0,
    @ColumnInfo("invulnerable") val invulnerable: Int = 0,
    @ColumnInfo("feel_no_pain") val feelNoPain: Int = 0,
    @ColumnInfo("keywords") val keywords: List<Keywords>,
    @ColumnInfo("roles") val roles: List<Roles>
)
