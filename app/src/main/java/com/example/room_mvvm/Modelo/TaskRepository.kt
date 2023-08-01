package com.example.room_mvvm.Modelo

import androidx.lifecycle.LiveData
import com.example.room_mvvm.Modelo.Model.Task
import com.example.room_mvvm.Modelo.Model.TaskDao


//El repositorio es parte del modelo, incluso puede tener su propio package dentro del modelo
//esta clase se comunica con viewmodel, su funcion es exponer las funciones del dao al viewmodel
//para eso instanciamos el dao en el constructor de la clase

class TaskRepository (private val taskDao: TaskDao) {

    //hacemos una variable envuelta en livedata segun el livedata del dao
    //importamos la clase Task e igualamos a la funcion de todas las tareas del dao
    //entonces esta variable al final trae todos los datos de la base de datos
    //osea, segun yo es ordenar en capas para lograr distintos accesos

    val listAllTask: LiveData<List<Task>> = taskDao.getAllTask1()

    //llamamos a las funciones que necesitamos, pueden ser todas o no

    suspend fun insertTask(task: Task){
        //le paso las funcion del dao
        taskDao.insertTask(task)

    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)

    }

    suspend fun deleteAll(){
        taskDao.deleteAll()

    }

    suspend fun deleteOneTask(task: Task?){
        taskDao.deleteOneTask(task)

    }






}