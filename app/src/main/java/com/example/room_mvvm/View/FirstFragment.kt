package com.example.room_mvvm.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/

        //para que el boton flotante lleve al segundo fragmento
        binding.fab2.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        /** En esta funcion podemos crear una nueva tarea, hacemos una variable que es del tipo
         * Task, importamos la clase y llenamos segun lo puesto en la clase original
         * TODO: Cada vez que corro la app estoy creando una nueva tarea*/
       /* val newTask= Task(
            title = "RoomMVVM",
            descripcion = "descripcion-state true",
            date = "31-07-2023",
            priority = 1,
            state = true
        )*/

        //para insertar, llamamos al viewmodel a la funcion insertar tarea y le pasamos la newtask
        //viewModel.insertTask(newTask)

        //probando en el app inspector se ha insertado correctamente la tarea
        //despues de crear el adapter pasamos el adapter al recycler view
        val adapter=TaskAdapter()
        binding.rvTask.adapter= adapter
        binding.rvTask.layoutManager=LinearLayoutManager(context)
        //agregamos una decoracion
        binding.rvTask.addItemDecoration(
            //agregamos un divisor de item
        DividerItemDecoration(
            //le paso el contexto
            context,
            //y el divisor con su orientacion
            DividerItemDecoration.VERTICAL
        )
        )

        /**Finalmente le paso la lista al adapter, la con livedata que se hizo en viewmodel
         * la lista al ser livedata debe ser observada por el dueño del ciclo de vida de la vista*/
        //TODO intentar sacar el operador lambda del parentesis
        viewModel.allTask.observe(viewLifecycleOwner,{

            //mientras exita un elemento en la lista los ira trayendo
            it?.let {
                adapter.update(it)
            }
        })

        /**Usando el adaptador enviaremos la tarea seleccionada al segundo fragmento, se observa
         * tal como la anterior
         */

        adapter.selectedItem().observe(viewLifecycleOwner,{
            it?.let {
                //le paso la tarea seleccionada
                viewModel.selected(it)
                //como el elemento ya esta seleccionado, activo la navegacion
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

            }
        })







    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}