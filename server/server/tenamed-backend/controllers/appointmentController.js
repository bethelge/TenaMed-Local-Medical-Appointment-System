const moment = require('moment');
const { createAppointment, getAppointments,deleteAppointmentById,updateAppointmentById } = require('../models/appointmentModel');

// Controller to book an appointment
const bookAppointment = async (req, res) => {
  try {
    const { patientId, doctorId, date, time, note } = req.body;

    // Basic validation
    if (!patientId || !doctorId || !date || !time || !note) {
      return res.status(400).json({ error: 'All fields are required' });
    }

    // Format time from '10:00 AM' to '10:00:00'
    const formattedTime = moment(time, ['h:mm A']).format('HH:mm:ss');

    console.log('Attempting to book appointment...');

    const result = await createAppointment(patientId, doctorId, date, formattedTime, note);

    console.log('Appointment booked, sending response...');
    res.status(201).json({
      message: 'Appointment booked successfully',
      appointmentId: result.insertId,
    });

  } catch (err) {
    console.error('Error booking appointment:', err);
    res.status(500).json({ error: 'Failed to book appointment' });
  }
};

// Controller to fetch appointments by doctorId and/or patientId
const fetchAppointments = async (req, res) => {
  try {
    const { doctorId, patientId } = req.query;

    const results = await getAppointments(doctorId, patientId);
    res.status(200).json(results);

  } catch (err) {
    console.error('Error fetching appointments:', err);
    res.status(500).json({ error: 'Failed to fetch appointments' });
  }
};
// Controller to delete an appointment by ID
const deleteAppointment = async (req, res) => {
  try {
    const { appointmentId } = req.params;

    if (!appointmentId) {
      return res.status(400).json({ error: 'Appointment ID is required' });
    }

    const result = await deleteAppointmentById(appointmentId);

    if (result.affectedRows === 0) {
      return res.status(404).json({ error: 'Appointment not found' });
    }

    res.status(200).json({ message: 'Appointment deleted successfully' });

  } catch (err) {
    console.error('Error deleting appointment:', err);
    res.status(500).json({ error: 'Failed to delete appointment' });
  }
};
// Controller to update an appointment
const updateAppointment = async (req, res) => {
  try {
    const { appointmentId } = req.params;
    const { date, time, note } = req.body;

    if (!date || !time || !note) {
      return res.status(400).json({ error: 'Date, time, and note are required' });
    }

    const formattedTime = moment(time, ['h:mm A'], true);
    if (!formattedTime.isValid()) {
      return res.status(400).json({ error: 'Invalid time format. Use HH:mm AM/PM' });
    }

    const timeFormatted = formattedTime.format('HH:mm:ss');

    const result = await updateAppointmentById(appointmentId, date, timeFormatted, note);

    if (result.affectedRows === 0) {
      return res.status(404).json({ error: 'Appointment not found' });
    }

    res.status(200).json({ message: 'Appointment updated successfully' });
  } catch (err) {
    console.error('Error updating appointment:', err);
    res.status(500).json({ error: 'Failed to update appointment' });
  }
};



module.exports = {
  bookAppointment,
  fetchAppointments,
  deleteAppointment,
  updateAppointment
};
