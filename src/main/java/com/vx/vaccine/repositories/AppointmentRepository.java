package com.vx.vaccine.repositories;

import com.vx.vaccine.models.Appointment;
import com.vx.vaccine.models.AppointmentStatus;
import com.vx.vaccine.models.Users;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Abhiram
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
    List<Appointment> findByPatient(Users patient);
    List<Appointment> findByDoctor(Users doctor);
   
    List<Appointment> findByDoctorAndPatientFirstNameStartsWith(Users doctorId, String name);
    List<Appointment> findByDoctorAndStatus(Users doctor, AppointmentStatus status);
    
    List<Appointment> findByPatientAndAppointmentAtGreaterThanEqual(Users patient, Date date);
    List<Appointment> findByPatientAndStatus(Users patient, AppointmentStatus status);
//    @Query("select a from Appointment a where a.appointmentAt <= :creationDateTime")
//    List<Appointment> findByPatientAndAppointmentAtGreaterThan(Long patient, Date creationDateTime);
//    List<Appointment> findByDoctorAndAppointmentAtGreaterThan(Long doctor, Date creationDateTime);
}
