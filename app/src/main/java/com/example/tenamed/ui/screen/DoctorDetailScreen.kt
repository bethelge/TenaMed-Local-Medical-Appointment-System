package com.example.tenamed..ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.tena.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
@Composable
fun DoctorDetailScreen(
    navController: NavController,
    doctorName: String = "Dr. Doc Doc",
    specialization: String = "Optometrist",
    biography: String = "Dr. Doc Doc is a licensed optometrist with a passion for providing personalized eye care. " +
            "With years of experience in diagnosing and treating vision problems, Dr. Doc is committed to helping patients see clearly and maintain healthy eyes."
) {
    Scaffold(
        topBar = {
//            CenterAlignedTopAppBar(
//                title = { Text("Doctor Detail") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            // Doctor Card
            Card(
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
                    Image(
                        painter = painterResource(id = R.drawable.doctors), // Ensure the image exists
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(doctorName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(specialization, fontSize = 14.sp, color = Color.Gray)
                    }

                    Icon(Icons.Default.MoreVert, contentDescription = "More Options")
                }
            }

            Spacer(modifier = Modifier.height(32.dp)) // Give space after biography



            // Biography Section
            Text("Biography", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(biography, fontSize = 14.sp)
            Button(
                onClick = {
                    navController.navigate("book_placeholder")

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4))
            ) {
                Text("Book Appointment")
            }
        }
    }
}
