package com.vx.vaccine.services;

import com.vx.vaccine.models.Appointment;
import com.vx.vaccine.models.AppointmentStatus;
import com.vx.vaccine.models.Users;
import com.vx.vaccine.repositories.AppointmentRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Abhiram
 */
@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    
    
//    public AppointmentService(AppointmentRepository appointmentRepository) {
//        this.appointmentRepository = appointmentRepository;
//    }
    
    public Optional<Appointment> getById(Long id){
        return appointmentRepository.findById(id);
    }
    
    public List<Appointment> getPatientAppointments(Users patient){
        return appointmentRepository.findByPatient(patient);
    }
    
    public List<Appointment> getAll(){
        return appointmentRepository.findAll();
    }
    
    public List<Appointment> getDoctorAppointments(Users doctor){
        return appointmentRepository.findByDoctor(doctor);
    }
    
    
    
//    public List<Appointment> getPatientUpcomingAppointments(Long patient){
//        return appointmentRepository.findByPatientAndAppointmentAtGreaterThan(patient, new Date());
//    }
//    
//    public List<Appointment> getDoctorUpcomingAppointments(Long doctor){
//        return appointmentRepository.findByDoctorAndAppointmentAtGreaterThan(doctor, new Date());
//    }
    
    public List<Appointment> getDoctorAppointmentsByPatientName(Users doctor, String name){
        return appointmentRepository.findByDoctorAndPatientFirstNameStartsWith(doctor, name);
    }
    
    public List<Appointment> getDoctorPendingAppointments(Users doctor, AppointmentStatus status){
        return appointmentRepository.findByDoctorAndStatus(doctor, status);
    }
    
    public List<Appointment> getPatientPendingAppointments(Users patient, AppointmentStatus status){
        return appointmentRepository.findByPatientAndStatus(patient, status);
    }
    
    public List<Appointment> getPatientUpcomingAppointments(Users patient, Date date){
        return appointmentRepository.findByPatientAndAppointmentAtGreaterThanEqual(patient, date);
    }
    
    public Appointment updateAppointment(Appointment appointment){
        return appointmentRepository.save(appointment);
    }
    
    public Appointment createAppointment(Appointment appointment, Users doctor, Users patient) {
        appointment.setCreatedAt(new Date());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setStatus(AppointmentStatus.Pending);
        return appointmentRepository.save(appointment);
    }
}
