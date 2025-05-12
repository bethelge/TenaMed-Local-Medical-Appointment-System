const express = require('express');
const router = express.Router();
const { bookAppointment, fetchAppointments,deleteAppointment,updateAppointment } = require('../controllers/appointmentController');

router.post('/book', bookAppointment);
router.get('/fetch', fetchAppointments);
router.delete('/delete/:appointmentId', deleteAppointment); 
router.put('/update/:appointmentId', updateAppointment);
module.exports = router;
