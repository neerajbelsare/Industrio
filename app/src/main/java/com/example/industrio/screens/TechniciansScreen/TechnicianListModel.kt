package com.example.industrio.screens.TechniciansScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.industrio.screens.AccountNavScreens.ProfileScreen.TechnicianForm.Technician
import com.example.industrio.screens.AccountNavScreens.ProfileScreen.TechnicianForm.TechnicianInfo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.UUID

class TechnicianListModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val questionsCollection = firestore.collection("technicians")

    private val _technicians = MutableLiveData<List<Technician>>()
    val technicians: LiveData<List<Technician>> get() = _technicians

    init {
        fetchTechnicians()
    }

    fun fetchTechnicians() {
        questionsCollection
            .orderBy("rating", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val technicianList = mutableListOf<Technician>()
                for (document in documents) {
                    val technician = document.toObject(Technician::class.java)
                    technicianList.add(technician)
                }
                _technicians.value = technicianList
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }
}