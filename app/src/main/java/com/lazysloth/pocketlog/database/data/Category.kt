package com.lazysloth.pocketlog.database.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

enum class Category(val string : String) {
    GROCERY("grocery"), ENTERTAINMENT("entertainment"), VEGETABLE("vegetable"), HEALTHCARE("healthcare"), TRAVEL("travel")
}
@Entity(
    tableName = "category",
//    foreignKeys = [ForeignKey(
//        entity = User::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("userId"),
//        onUpdate = ForeignKey.CASCADE,
//        onDelete = ForeignKey.CASCADE
//    )],
//    indices = [Index("userId")]
)
data class Category1(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
//    val userId: Int = 0,
    val name: String = "",
    val type: CategoryType
)
enum class CategoryType{
    INCOME,EXPENSE
}