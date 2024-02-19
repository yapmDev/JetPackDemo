package com.example.jetpackdemo

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val list_ = getFruitList().toMutableStateList()

    val list : MutableList<String>  get() = list_

    fun onRemove(item: String) {
        list_.remove(item)
    }

    private fun getFruitList(): MutableList<String> {
        return mutableListOf("Manzana", "Naranja", "Melón", "Guayaba", "Platano","Manzana", "Naranja", "Melón", "Guayaba", "Platano","Platano","Manzana", "Naranja", "Melón", "Guayaba", "Platano")
    }
}