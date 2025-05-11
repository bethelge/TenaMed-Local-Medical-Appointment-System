const express = require('express');
const router = express.Router();
const { registerPatient, loginPatient,deletePatientAccount } = require('../controllers/patientController');

// Register route
router.post('/register', registerPatient);

// Login route
router.post('/login', loginPatient);

//delete route
router.delete('/:id', deletePatientAccount);


module.exports = router;