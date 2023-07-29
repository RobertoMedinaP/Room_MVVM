package com.example.room_mvvm.Modelo.Model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(tableName = "task_table")
data class Task (

        @PrimaryKey(autoGenerate = true)
        @NotNull
        val id: Int=0,
        val title: String,
        val descripcion: String,
        val date: String,
        val priority: Int,
        val state: Boolean




        )