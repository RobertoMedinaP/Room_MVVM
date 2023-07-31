package com.example.room_mvvm.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
         * observae
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

        /**Falta: que el boton guardar pueda actualizar la tarea seleccionada y que se pueda
         * eliminar una tarea y que el boton flotante del primer fragmento lleve al segundo
         * tambien podria hacer que la tarea se cree a partir de edittext
         */

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}