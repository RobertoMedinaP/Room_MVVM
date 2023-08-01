package com.example.room_mvvm.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.room_mvvm.Modelo.Model.Task
import com.example.room_mvvm.R
import com.example.room_mvvm.ViewModel.TaskViewModel
import com.example.room_mvvm.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //creamos referencia al viewmodel
    private val viewModel: TaskViewModel by activityViewModels()
   //variable para id inicializada en 0
    private var idTask: Int=0
    //variable para la tarea, inicializada en null porque puede no traer nada, trae una task, se
    //debe importar la clase
    private var taskSelected: Task?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/

        /**Recibiendo datos enviados desde el primer fragmento, volvemos a usar viewmodel y a
         * observe
         */

        viewModel.selectedItem().observe(viewLifecycleOwner,{
            it?.let {
                //creamos una variable y la asignamos(->) con binding a los distintos campos
                //es decir, iremos llenando con la tarea seleccionada los distintos campos
                selectedTask->
                binding.etTitle.setText(selectedTask.title)
                binding.etDate.setText(selectedTask.date)
                binding.etDescription.setText(selectedTask.descripcion)
                binding.etPriority.setText(selectedTask.priority.toString())
                //para pasar el estado
                binding.cbStatenew.isChecked=selectedTask.state
                //asignamos id al id de la tarea seleccionada
                idTask=selectedTask.id
                //igualamos taskSelected a selectedTask
                taskSelected=selectedTask




            }
        })

        //vamos a hacer que el boton guardar use la funcion para guardar
        binding.btnsave.setOnClickListener {
            saveData()
            //avisamos a viewmodel que no hemos seleccionado nada
            viewModel.selected(null)
            //ademas hacemos navegacion hacia el primer fragmento
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        /**Funciona
         */

        binding.button.setOnClickListener {
            viewModel.selectedItem().observe(viewLifecycleOwner,{
                it?.let {

                        selectedTask->
                    idTask=selectedTask.id
                    taskSelected=selectedTask
                }
            })

            viewModel.deleteOneTask(taskSelected)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Función para guardar datos:
    private fun saveData(){
        //asociamos todos los campos a una variable
        val title=binding.etTitle.text.toString()//esta variable el profe la hizo sin text
        val description=binding.etDescription.text.toString()
        val date=binding.etDate.text.toString()
        //priority es int
        val priority=binding.etPriority.text.toString().toInt()
        val state=binding.cbStatenew.isChecked

        //algunas validaciones
        if (title.isEmpty()||description.isEmpty()||date.isEmpty()){
            Toast.makeText(context,"Ingrese datos",Toast.LENGTH_LONG).show()
        }else{
            //si el id es el primero, es decir es una tarea nueva, insertamos una nueva tarea
            if (idTask==0){
                val newTask= Task(
                    title=title,
                    descripcion = description,
                    date= date,
                    priority = priority,
                    state=state
                )
                viewModel.insertTask(newTask)
                Toast.makeText(context,"Tarea guardada",Toast.LENGTH_LONG).show()
            }else{
                //si el id es distinto a cero, la tarea ya existe, entonces hay que actualizar
                //se crea una nueva tarea1
                val newTask1= Task(
                    //pero esta vez le pasamos un id ya que la tarea ya existe, es el id que viene
                    //desde el observer para saber cual actualizar
                    id= idTask,
                    title=title,
                    descripcion = description,
                    date= date,
                    priority = priority,
                    state=state
                )
                //pero esta vez actualizamos con los valores de newtask1
                viewModel.updateTask(newTask1)
                Toast.makeText(context,"Tarea actualizada",Toast.LENGTH_LONG).show()

            }
        }


    }
}