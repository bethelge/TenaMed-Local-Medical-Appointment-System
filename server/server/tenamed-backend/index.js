const express = require('express');
const cors = require('cors');
const db = require('./db'); 
const doctorRoutes = require('./routes/doctorRoutes');
const patientRoutes = require('./routes/patientRoutes');
const appointmentRoutes = require('./routes/appointmentRoutes');

const app = express();
const port = 5000;

app.use(cors());
app.use(express.json());

// ✅ Use doctorRoutes under a base path
app.use('/api/doctors', doctorRoutes);
// use patientRoutes under a base path
app.use('/api/patients', patientRoutes);
// use appointmentRoutes under a base path
app.use('/api/appointments', appointmentRoutes);

// Optional test route to confirm DB works
app.get('/test-db', async (req, res) => {
  try {
    const [rows] = await db.query('SELECT * FROM doctors');
    res.json(rows);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

app.listen(port, () => {
  console.log(`Server running on http://localhost:${port}`);
});
