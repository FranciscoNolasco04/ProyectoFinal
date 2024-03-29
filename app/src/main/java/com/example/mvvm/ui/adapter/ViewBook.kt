package com.example.mvvm.ui.adapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvm.data.models.Book
import com.example.mvvm.data.models.BookRepositoryDao
import com.example.mvvm.data.service.BookService
import com.example.mvvm.databinding.ItemHotelBinding
import com.example.mvvm.ui.dialogs.DialogoBorrar
import com.example.mvvm.ui.MainActivity
import com.example.mvvm.ui.dialogs.DialogoEditar

class ViewBook(
    view: View,
) : RecyclerView.ViewHolder(view) {

    private var mainActivity: MainActivity
    private var repositoryDao: BookRepositoryDao
    var binding: ItemHotelBinding

    init {
        binding = ItemHotelBinding.bind(view)
        // Inicializa el ViewModel y el RepositoryDao aquí
        mainActivity = view.context as MainActivity
        repositoryDao = BookRepositoryDao()
    }

    fun renderize(book: Book) {
        binding.nameTextView.text = book.nombre
        binding.cityTextView.text = book.descripcion

        Glide.with(itemView.context)
            .load(book.imagen)
            .centerCrop()
            .into(binding.iconImageView)

        setOnClickListener(adapterPosition, book)
    }

    fun setOnClickListener(position: Int, book: Book) {
        binding.btnEdit.setOnClickListener {
            val fragmentManager = (itemView.context as AppCompatActivity).supportFragmentManager
            val dialogFragment = DialogoEditar(mainActivity.bookViewModel, position, book, repositoryDao)
            dialogFragment.show(fragmentManager, "editarDialog")
            dialogFragment.isCancelable = false
        }

        binding.btnDelete.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val fragmentManager = (itemView.context as AppCompatActivity).supportFragmentManager
                val dialogFragment = DialogoBorrar(repositoryDao, mainActivity.bookViewModel)

                val args = Bundle()
                args.putInt("position", position)
                dialogFragment.arguments = args

                dialogFragment.show(fragmentManager, "confirmacionDialog")
                dialogFragment.isCancelable = false
            }
        }



        binding.btnLike.setOnClickListener {
        }
    }


}
