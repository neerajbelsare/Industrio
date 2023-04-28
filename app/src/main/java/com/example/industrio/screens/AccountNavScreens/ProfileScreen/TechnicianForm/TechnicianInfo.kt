package com.example.industrio.screens.AccountNavScreens.ProfileScreen.TechnicianForm

data class TechnicianInfo(val name: String="",
                      val speciality: String="",
                      val email: String="",
                      val phone: String="",
                      val startTime: String="",
                      val endTime: String="",
                      val experience: String="",
                      val qualification: String="")

data class TechnicianDocuments(val qualificationUrl: String="",
                            val identityUrl: String="",
                            val profileUrl: String="")