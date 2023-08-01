package com.example.room_mvvm.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.room_mvvm.Modelo.Model.Task
import com.example.room_mvvm.Modelo.Model.TaskDao
import com.example.room_mvvm.Modelo.Model.TaskDataBase
import com.example.room_mvvm.Modelo.TaskRepository
import kotlinx.coroutines.launch


//clase del viewmodel, generalmente va dentro de su propio package
//para atras se comunica con repositorio y para adelante con la vista
//aca en el viewmodel se deben matar las corrutinas
//en su contructor debe ir applicantion y la clase debe heredar de androdviewmodel
//viewmodel esta diseñado para ser utilizado con livedata, por lo tanto en el viewmodel
//debe incorporarse livedata
class TaskViewModel (application: Application): AndroidViewModel(application) {

    //para conectar con el repositorio hacemos una variable que sea igual al repositorio
    //al hacer esta variable dará error hasta que se inicialice
    private val repository: TaskRepository

    //livedata expone la informaciona del modelo, para eso tambien hacemos su variable
    //recibe Task, hay que importar su clase, al crearla dara error hasta que se inicialice
    val allTask: LiveData<List<Task>>

    //inicializamos las variables, obteniendo las instancias de las capas anteriores

    init {

        //instancia del dao=basededato.seobtiene(se lepasa la aplicaciom).se obtiene el dao
        //desde la instancia en la base de datos(funcion abstracta)
        val TaskDao= TaskDataBase.getDatabase(application).getTaskDao()

        //se instancia el repositorio y se le pasa el dao
        repository= TaskRepository(TaskDao)

        //finalmente inicializamos allTask
        allTask=repository.listAllTask
    }

    //ahora llamamos las funciones del repositorio
    //aca se lanza la corrutina con viewmodelscope.launch
    //se ejecutan todas las corrutinas en un hilo secundario

    fun insertTask(task: Task)= viewModelScope.launch {
        //aca le paso la funcion del repositorio
        repository.insertTask(task)

    }

    fun updateTask(task: Task)= viewModelScope.launch {
        repository.updateTask(task)
    }

    fun deleteAllTask()=viewModelScope.launch {
        repository.deleteAll()
    }

    fun deleteOneTask(task: Task?)=viewModelScope.launch {
       repository.deleteOneTask(task)
    }

    //para seleccionar algun elemento
    //hacer una variable que represente la accion de seleccionar que hereda de la lista anterior
    // por eso es MutableLiveData, que recibe una Task que puede ser nula (Task?)
    //y eso se iguala a MutableLiveData. En resumen, recibimos una Task y la envolvemos en livedata
    //para poder notificar los cambios

    private val selectedTask: MutableLiveData<Task?> = MutableLiveData()


    //la funcion recibe un Task envuelto en livedata puede ser nula en caso que no se seleccione
    //nada, por eso TASK?, esta funcion es para seleccionar desde la vista

    fun selectedItem(): LiveData<Task?> =selectedTask

    //funcion para recibir el objeto, en este caso un task


    //a esta funcion le damos Task? para que pueda ser nula en caso de que no se haga ninguna
    //seleccion
    fun selected(task: Task?){
        //la tarea seleccionada en la funcion anterior se guarda, guardamos lo seleccionado
        selectedTask.value=task
    }

    //ahora el viewmodel se va a comunicar con la vista(activitys y fragments)
    //activitys y fragments van en el paquete view

}