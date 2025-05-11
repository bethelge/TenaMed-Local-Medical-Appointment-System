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




module.exports = {
  registerDoctor,
  loginDoctor,
};
