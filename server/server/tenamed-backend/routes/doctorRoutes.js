const express = require('express');
const router = express.Router();
const authenticateDoctor = require('../middleware/authMiddleware');


const {
  registerDoctor,
  loginDoctor,
  getAllDoctors,
  getDoctorById,
  updateDoctorProfile,
  deleteDoctorAccount,
} = require('../controllers/doctorController');

// Register and login routes
router.delete('/me', authenticateDoctor,deleteDoctorAccount);
router.post('/register', registerDoctor);
router.post('/login', loginDoctor);

// Get all doctors
router.get('/', getAllDoctors);

// Route to update doctor profile
router.put('/doctor/:id', updateDoctorProfile);

// Route to get doctor by ID
router.get('/:id', getDoctorById);

//route to delete doctor account
router.delete('/:id', deleteDoctorAccount);

module.exports = router;
