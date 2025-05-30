const db = require('../db');

const createDoctor = (email, hashedPassword) => {
  return db.query('INSERT INTO doctors (email, password) VALUES (?, ?)', [email, hashedPassword]);
};

const findDoctorByEmail = (email) => {
  return db.query('SELECT * FROM doctors WHERE email = ?', [email]);
};
// Find doctor by ID
const findDoctorById = (id) => {
    return db.query('SELECT * FROM doctors WHERE id = ?', [id]);
  };
// Delete a doctor by ID
const deleteDoctor = async (doctorId) => {
  const sql = 'DELETE FROM doctors WHERE id = ?';
  const [result] = await db.query(sql, [doctorId]);
  return result;
};
const getAllDoctors = () => {
  return db.query('SELECT * FROM doctors');
};

  
  // Update doctor profile
  const updateDoctorProfile = (id, fullName, yearsOfExperience, shortDescription, location, specialty, language, hospital) => {
    return db.query(
      `UPDATE doctors SET 
        full_name = ?, 
        years_of_experience = ?, 
        short_description = ?, 
        location = ?, 
        specialty = ?, 
        language = ?, 
        hospital = ?, 
        updated_at = CURRENT_TIMESTAMP 
      WHERE id = ?`,
      [fullName, yearsOfExperience, shortDescription, location, specialty, language, hospital, id]
    );
  };
  
module.exports = { createDoctor, findDoctorByEmail, findDoctorById, updateDoctorProfile,deleteDoctor,  getAllDoctors};