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
import com.example.room_mvvm.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 * Acá es donde estarán las instancias de lo creado anteriormente.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    /** Se instancia el viewmodel
     * se crea la variable que hereda del viewmodel y se pone by activityViemodels() */
    private val viewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        /** En esta funcion podemos crear una nueva tarea, hacemos una variable que es del tipo
         * Task, importamos la clase y llenamos segun lo puesto en la clase original*/
        val newTask= Task(
            title = "RoomMVVM",
            descripcion = "descripcion",
            date = "29-07-2023",
            priority = 1,
            state = false
        )

        //para insertar, llamamos al viewmodel a la funcion insertar tarea y le pasamos la newtask
        viewModel.insertTask(newTask)

        //probando en el app inspector se ha insertado correctamente la tarea.


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}