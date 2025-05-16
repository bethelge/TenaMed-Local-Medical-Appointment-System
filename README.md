
# TenaMed – Local Medical Appointment Booking System

TenaMed is a mobile application designed to help users find, book, and manage medical appointments with specialized physicians. The system streamlines the appointment scheduling process, reducing long wait times and improving patient access to healthcare providers. Built on a local REST API backend, the system ensures data security, efficient performance, and a smooth user experience.

## Key Features

### Patient & Appointment Management
- Users can create and manage personal patient profiles.
- Full CRUD (Create, Read, Update, Delete) operations for patient records.
- Patients can book, reschedule, and cancel appointments with healthcare providers.


### Appointment Scheduling & Management
- Patients can select a date, time, and doctor for an appointment.
- Clinics can approve, reschedule, or cancel appointments.

### Authentication & Authorization
- Secure user registration and login.
- Role-based access for patients, doctors, and clinic admins.

### Testing & Reliability
- Comprehensive widget, unit, and integration tests to ensure system stability.

### Development & Version Control
- Developed using local development tools.
- Follows best practices in GitHub version control.

## Problem It Solves
TenaMed helps people easily find and schedule visits to local health centers without the hassle of long wait times, ensuring efficient appointment management and better access to healthcare.
Project Structure - TenaMed
The TenaMed Local Medical Appointment System Android app is organized in a clean and modular way, separating concerns between UI, data, network, and repository layers.

## 📁 File Structure Overview
The TenaMed Local Medical Appointment System Android app is organized in a clean and modular way, separating concerns between UI, data, network, and repository layers.

app/
├── src/
│   ├── main/
│   │   ├── java/com/example/tenamed/
│   │   │   ├── data/
│   │   │   │   ├── model/
│   │   │   │   ├── network/
│   │   │   │   └── repository/
│   │   │   ├── ui/
│   │   │   │   ├── screen/
│   │   │   │   └── theme/
│   │   │   └── MainActivity.kt
│   │   ├── res/
│   │   │   ├── drawable/
│   │   │   ├── mipmap/
│   │   │   ├── values/
│   │   │   └── xml/
│   │   └── AndroidManifest.xml
├── build.gradle.kts
├── proguard-rules.pro
└── ...


## Group Members

| Full Name           | ID           |
|--------------------|--------------|
| Bemnet Bisrat      | UGR/1559/15  |
| Bethel Gelan       | UGR/5776/15  |
| Efrata Wolde       | UGR/1245/15  |
| Meklit Workineh    | UGR/3445/15  |
| Metaniya Shiferaw  | UGR/8633/15  |

