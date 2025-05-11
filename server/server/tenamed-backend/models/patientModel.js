const db = require('../db');

const createPatient = (email, hashedPassword) => {
  return db.query('INSERT INTO patients (email, password) VALUES (?, ?)', [email, hashedPassword]);
};

const findPatientByEmail = (email) => {
  return db.query('SELECT * FROM patients WHERE email = ?', [email]);
};
//Delete a patient by ID
const deletePatient = async (patientId) => {
  const sql = 'DELETE FROM patients WHERE id = ?';
  const [result] = await db.query(sql, [patientId]);
  return result;
};

module.exports = { createPatient, findPatientByEmail,deletePatient };