
package com.example.tenamed.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tena.data.model.DoctorProfileRequest
import com.example.tena.data.model.DoctorProfileResponse
import com.example.tena.data.network.RetrofitInstance
import com.example.tena.viewmodel.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DoctorProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {


    val doctorId = authViewModel.doctorId
    println("doctorId from viewModel = $doctorId")
// This remains the same as it is observed from the ViewModel
    val scrollState = rememberScrollState()

    var fullName by remember { mutableStateOf("") }
    var yearsOfExperience by remember { mutableStateOf("") }
    var shortDescription by remember { mutableStateOf("") }
    var acceptedTerms by remember { mutableStateOf(false) }

    var locations by remember { mutableStateOf(listOf("Addis Ababa", "Gondar", "Mekelle")) }
    var specialties by remember { mutableStateOf(listOf("Cardiology", "Dermatology", "Pediatrics")) }
    var languages by remember { mutableStateOf(listOf("Amharic", "English", "Oromo")) }
    var hospitals by remember { mutableStateOf(listOf("St. Paul's", "Tikur Anbessa", "Betezata")) }

    var selectedLocation by remember { mutableStateOf("") }
    var selectedSpecialty by remember { mutableStateOf("") }
    var selectedLanguage by remember { mutableStateOf("") }
    var selectedHospital by remember { mutableStateOf("") }

    // Fetching the data for the dropdowns
    LaunchedEffect(Unit) {
        try {
            val response = RetrofitInstance.api.getAllDoctors()
            if (response.isSuccessful) {
                val doctorList = response.body()
                doctorList?.let {
                    val fetchedLocations = it.mapNotNull { d -> d.location }.distinct()
                    val fetchedSpecialties = it.mapNotNull { d -> d.specialty }.distinct()
                    val fetchedLanguages = it.mapNotNull { d -> d.language }.distinct()
                    val fetchedHospitals = it.mapNotNull { d -> d.hospital }.distinct()

                    if (fetchedLocations.isNotEmpty()) locations = fetchedLocations
                    if (fetchedSpecialties.isNotEmpty()) specialties = fetchedSpecialties
                    if (fetchedLanguages.isNotEmpty()) languages = fetchedLanguages
                    if (fetchedHospitals.isNotEmpty()) hospitals = fetchedHospitals
                }
            } else {
                println("Failed to fetch doctors: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            println("Error fetching doctors: ${e.localizedMessage}")
        }
    }

    // Layout for the Doctor Profile screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome, Dr!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(
            "Let's complete your profile so patients can find and book you easily.",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 8.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Icon(
            imageVector = Icons.Default.CloudUpload,
            contentDescription = "Upload Pic",
            tint = Color.Gray,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownField("Location", locations, selectedLocation) { selectedLocation = it }
        DropdownField("Specialty", specialties, selectedSpecialty) { selectedSpecialty = it }

        OutlinedTextField(
            value = yearsOfExperience,
            onValueChange = { yearsOfExperience = it },
            label = { Text("Years of Experience") },
            modifier = Modifier.fillMaxWidth()
        )

        DropdownField("Languages Spoken", languages, selectedLanguage) { selectedLanguage = it }
        DropdownField("Hospital/Clinic Name", hospitals, selectedHospital) { selectedHospital = it }

        Icon(
            imageVector = Icons.Default.CloudUpload,
            contentDescription = "Upload Qualifications",
            tint = Color(0xFF00BCD4),
            modifier = Modifier
                .size(32.dp)
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = shortDescription,
            onValueChange = { shortDescription = it },
            label = { Text("Short description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = acceptedTerms, onCheckedChange = { acceptedTerms = it })
            Text("I accept the terms ", modifier = Modifier.padding(start = 4.dp))
            Text("Read our T&Cs", color = Color(0xFF00BCD4), fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val profileRequest = DoctorProfileRequest(
                    fullName = fullName,
                    location = selectedLocation,
                    specialty = selectedSpecialty,
                    yearsOfExperience = yearsOfExperience.toIntOrNull() ?: 0,
                    language = selectedLanguage,
                    hospital = selectedHospital,
                    description = shortDescription
                )

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val doctorIdInt = doctorId?.toIntOrNull()
                        println("Doctor ID to update: $doctorIdInt")

                        if (doctorIdInt != null) {
                            val response = RetrofitInstance.api.updateDoctorProfile(doctorIdInt, profileRequest)

                            withContext(Dispatchers.Main) {
                                println("Response code: ${response.code()}")
                                if (response.isSuccessful) {
                                    println("Success: ${response.body()?.message}")
                                } else {
                                    println("Error body: ${response.errorBody()?.string()}")
                                }

                                // ✅ Navigate to next page regardless of success/failure
                                navController.navigate("doctor_profile_after_setup") {
                                    popUpTo("update_doctor_profile") { inclusive = true }
                                }
                            }
                        } else {
                            println("Doctor ID is null or invalid.")
                            withContext(Dispatchers.Main) {
                                navController.navigate("doctor_profile_after_setup") {
                                    popUpTo("update_doctor_profile") { inclusive = true }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            println("Network Exception: ${e.localizedMessage}")

                            // ✅ Still navigate even on exception
                            navController.navigate("doctor_profile_after_setup") {
                                popUpTo("update_doctor_profile") { inclusive = true }
                            }
                        }
                    }
                }

            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4))
        ) {
            Text("Save Profile")
        }


    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onOptionSelected(selectionOption)
                        expanded = false
                    }
                )
            }
        }
    }
}