package com.example.industrio.screens.AccountNavScreens.ProfileScreen.CompanyForm

data class CompanyInfo(val name: String="",
                      val address: String="",
                      val email: String="",
                      val phone: String="",
                      val startTime: String="",
                      val endTime: String="",
                      val latitude: String="",
                      val longitude: String="")

data class CompanyDocuments(val imageUrl: String="",
                            val proofUrl: String="")
