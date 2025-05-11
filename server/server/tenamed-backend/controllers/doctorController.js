const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const db = require('../db');

// Import all doctorModel functions once
const {
  createDoctor,
  findDoctorByEmail,
  findDoctorById,
  updateDoctorProfile: updateDoctorProfileInDB
} = require('../models/doctorModel');

// Register Doctor
const registerDoctor = async (req, res) => {
  const { email, password, confirmPassword } = req.body;

  if (!email || !password || !confirmPassword) {
    return res.status(400).json({ error: 'All fields are required' });
  }

  if (password !== confirmPassword) {
    return res.status(400).json({ error: 'Passwords do not match' });
  }

  try {
    const [existing] = await findDoctorByEmail(email);
    if (existing.length > 0) {
      return res.status(400).json({ error: 'Email already registered' });
    }

    const hashedPassword = await bcrypt.hash(password, 10);
    await createDoctor(email, hashedPassword);

    res.status(201).json({ message: 'Doctor registered successfully' });
  } catch (err) {
    console.error('Registration error:', err);
    res.status(500).json({ error: 'Server error' });
  }
};

// Login Doctor
const loginDoctor = async (req, res) => {
  const { email, password } = req.body;

  if (!email || !password) {
    return res.status(400).json({ error: 'Email and password are required' });
  }

  try {
    const [rows] = await findDoctorByEmail(email);
    if (rows.length === 0) {
      return res.status(404).json({ error: 'User not found' });
    }

    const doctor = rows[0];
    const isMatch = await bcrypt.compare(password, doctor.password);
    if (!isMatch) {
      return res.status(401).json({ error: 'Invalid credentials' });
    }

    const token = jwt.sign(
      { id: doctor.id, role: 'doctor' },
      process.env.JWT_SECRET || 'defaultSecret',
      { expiresIn: '1h' }
    );

    res.json({ message: 'Login successful', token });
  } catch (err) {
    console.error('Login error:', err);
    res.status(500).json({ error: 'Server error' });
  }
};

// Get all doctors
const getAllDoctors = async (req, res) => {
  try {
    const [results] = await db.query("SELECT id, email, full_name, short_description, years_of_experience, location, specialty, language, hospital FROM doctors WHERE full_name IS NOT NULL");
    res.status(200).json(results);
  } catch (err) {
    console.error("Error fetching doctors:", err);
    res.status(500).json({ error: "Database error" });
  }
};

// Get Doctor by ID
const getDoctorById = async (req, res) => {
  const { id } = req.params;

  try {
    const [doctor] = await findDoctorById(id);
    if (doctor.length === 0) {
      return res.status(404).json({ error: 'Doctor not found' });
    }
    res.status(200).json(doctor[0]);
  } catch (err) {
    console.error('Error fetching doctor:', err);
    res.status(500).json({ error: 'Server error' });
  }
};

// Update Doctor Profile
const updateDoctorProfile = async (req, res) => {
  const { id } = req.params;
  const { fullName, yearsOfExperience, shortDescription, location, specialty, language, hospital } = req.body;

  if (!fullName || !yearsOfExperience || !shortDescription || !location || !specialty || !language || !hospital) {
    return res.status(400).json({ error: 'All fields are required' });
  }

  try {
    const [existingDoctor] = await findDoctorById(id);
    if (existingDoctor.length === 0) {
      return res.status(404).json({ error: 'Doctor not found' });
    }

    await updateDoctorProfileInDB(id, fullName, yearsOfExperience, shortDescription, location, specialty, language, hospital);
    console.log('Doctor updated:', id, fullName, yearsOfExperience, shortDescription, location, specialty, language, hospital);

    res.status(200).json({ message: 'Profile updated successfully' });
  } catch (err) {
    console.error('Error updating profile:', err);
    res.status(500).json({ error: 'Server error' });
  }
};

const { deleteDoctor } = require('../models/doctorModel');

const deleteDoctorAccount = async (req, res) => {
  try {
    const doctorId = req.user.id; // Logged-in user's ID

    const result = await deleteDoctor(doctorId);

    if (result.affectedRows === 0) {
      return res.status(404).json({ error: 'Doctor not found' });
    }

    res.status(200).json({ message: 'Doctor account deleted successfully' });
  } catch (err) {
    console.error('Error deleting doctor:', err);
    res.status(500).json({ error: 'Failed to delete doctor account' });
  }
};


module.exports = {
  registerDoctor,
  loginDoctor,
  getAllDoctors,
  getDoctorById,
  updateDoctorProfile,
  deleteDoctorAccount,
};
