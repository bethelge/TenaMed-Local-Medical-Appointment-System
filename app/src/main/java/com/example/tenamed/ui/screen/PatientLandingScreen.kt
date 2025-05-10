package com.example.tenamed.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tenamed.R
import com.example.tenamed.viewmodel.AuthViewModel
import com.example.tenamed.data.model.DoctorGetResponse

@Composable
fun PatientLandingScreen(navController: NavController, viewModel: AuthViewModel) {
    val categories = listOf(
        "Optometrist", "Dentist", "Cardiologist", "Neurologist",
        "Pediatrician", "Dermatologist", "Orthopedic", "Psychiatrist", "Surgeon"
    )

    var searchQuery by remember { mutableStateOf("") }

    // ✅ Ensure doctors are fetched
    LaunchedEffect(Unit) {
        viewModel.fetchAllDoctors()
    }

    // ✅ Observe doctors from viewModel
    val doctorList by remember { derivedStateOf { viewModel.doctorList } }

    val filteredDoctors = doctorList.filter {
        it.full_name.contains(searchQuery, ignoreCase = true) ||
                it.short_description.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Let's find your doctor, Name, Specialization") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        // Horizontal scrollable categories
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                FilterChip(
                    selected = false,
                    onClick = { /* TODO: Filter by category */ },
                    label = { Text(category) },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color(0xFFB2EBF2),
                        labelColor = Color.Black
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Doctor cards list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredDoctors) { doctor ->
                Card(
                    onClick = { navController.navigate("doctor_detail") },
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Temporary static image
                        Image(
                            painter = painterResource(id = R.drawable.doc),
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(doctor.full_name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(doctor.short_description, fontSize = 14.sp, color = Color.Gray)
                        }

                        Icon(Icons.Default.MoreVert, contentDescription = "More Options")
                    }
                }
            }
        }
    }
}
