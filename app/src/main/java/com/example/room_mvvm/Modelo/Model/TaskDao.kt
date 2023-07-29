package com.example.room_mvvm.Modelo.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.room_mvvm.Modelo.Model.Task


@Dao
interface TaskDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)


    //para varias tareas se inserta una lista

    @Insert
    suspend fun insertMultipleTask(List:List<Task>)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteOneTask(task: Task)

    //para eliminar todas las tareas
    //no recibe nada

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    //para llamar todas las tareas, usamos livedata
    //livedata nos permite ver los cambios en cualquier parte de la app
    //por lo tanto livedata va a recibir la lista de tareas

    @Query("SELECT * FROM task_table")
     fun getAllTask1(): LiveData<List<Task>>

     //TODO ¿cuando una funcion es suspended o no en un dao?

     @Query("SELECT * FROM task_table ORDER BY id ASC")
     fun getAlltask(): LiveData<List<Task>>

     //traer por titulo, con :title llamamos a val title de la clase task
     @Query("SELECT * FROM task_table WHERE title=:title LIMIT 1")
     //la funcion recibe un title que es un string en la base de datos
     //se subscribe a Livedata que recibe una tarea(Task)
     fun getTaskByTitle(title: String): LiveData<Task>

     //traer por id
     @Query("SELECT * FROM task_table WHERE id=:id LIMIT 1")
     fun getTaskById(id: Int): LiveData<Task>

}