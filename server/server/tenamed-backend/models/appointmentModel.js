const db = require('../db');

// Create a new appointment
const createAppointment = async (patientId, doctorId, date, time, note) => {
  const sql = `
    INSERT INTO appointments (patient_id, doctor_id, date, time, note)
    VALUES (?, ?, ?, ?, ?)
  `;
  const [result] = await db.query(sql, [patientId, doctorId, date, time, note]);
  return result;
};

// Fetch appointments (optionally filter by doctorId and/or patientId)
const getAppointments = async (doctorId, patientId) => {
  let sql = 'SELECT * FROM appointments WHERE 1=1';
  const params = [];

  if (doctorId) {
    sql += ' AND doctor_id = ?';
    params.push(doctorId);
  }
  if (patientId) {
    sql += ' AND patient_id = ?';
    params.push(patientId);
  }

  const [rows] = await db.query(sql, params);
  return rows;
};
// Delete appointment by ID
const deleteAppointmentById = async (appointmentId) => {
  const sql = 'DELETE FROM appointments WHERE id = ?';
  const [result] = await db.query(sql, [appointmentId]);
  return result;
};
// Update appointment by ID
const updateAppointmentById = async (appointmentId, date, time, note) => {
  const sql = `
    UPDATE appointments
    SET date = ?, time = ?, note = ?
    WHERE id = ?
  `;
  const [result] = await db.query(sql, [date, time, note, appointmentId]);
  return result;
};


module.exports = {
  createAppointment,
  getAppointments,
  deleteAppointmentById,
  updateAppointmentById,
};
