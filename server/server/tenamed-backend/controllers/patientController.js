const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const { createPatient, findPatientByEmail } = require('../models/patientModel');

// Register Patient
const registerPatient = async (req, res) => {
  const { email, password, confirmPassword } = req.body;
  console.log('Register request received:', req.body); 

  if (!email || !password || !confirmPassword) {
    return res.status(400).json({ error: 'All fields are required' });
  }

  if (password !== confirmPassword) {
    return res.status(400).json({ error: 'Passwords do not match' });
  }

  try {
    const [existing] = await findPatientByEmail(email);
    if (existing.length > 0) {
      return res.status(400).json({ error: 'Email already registered' });
    }

    const hashedPassword = await bcrypt.hash(password, 10);
    await createPatient(email, hashedPassword);

    res.status(201).json({ message: 'Patient registered successfully' });
  } catch (err) {
    console.error('Registration error:', err);
    res.status(500).json({ error: 'Server error' });
  }
};

// Login Patient
const loginPatient = async (req, res) => {
  const { email, password } = req.body;
  console.log('Login request received:', req.body);
  if (!email || !password) {
    return res.status(400).json({ error: 'Email and password are required' });
  }

  try {
    const [rows] = await findPatientByEmail(email);
    if (rows.length === 0) {
      return res.status(404).json({ error: 'User not found' });
    }

    const patient = rows[0];
    const isMatch = await bcrypt.compare(password, patient.password);
    if (!isMatch) {
      return res.status(401).json({ error: 'Invalid credentials' });
    }

    const token = jwt.sign(
      { id: patient.id, role: 'patient' },
      process.env.JWT_SECRET || 'defaultSecret', // fallback for dev
      { expiresIn: '1h' }
    );

    res.json({ message: 'Login successful', token });
  } catch (err) {
    console.error('Login error:', err);
    res.status(500).json({ error: 'Server error' });
  }
};
const { deletePatient } = require('../models/patientModel');



module.exports = { registerPatient, loginPatient };
