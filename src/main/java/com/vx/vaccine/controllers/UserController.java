package com.vx.vaccine.controllers;

import com.vx.vaccine.models.Appointment;
import com.vx.vaccine.models.AppointmentStatus;
import com.vx.vaccine.models.UserStatus;
import com.vx.vaccine.models.UserType;
import com.vx.vaccine.models.Users;
import com.vx.vaccine.models.Vaccination;
import com.vx.vaccine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.vx.vaccine.repositories.VaccinationRepository;
import com.vx.vaccine.services.AppointmentService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Abhiram
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private VaccinationRepository vaccinationRepository;


    @GetMapping("/doctors")
    public String viewListOfDoctors(Model model) {
        List<Users> doctors = userService.getUserByType(UserType.Doctor);
        model.addAttribute("doctors", doctors);
        return "doctors_list";
    }
    
    @GetMapping("/profile")
    public String viewProfile(Model model) {
        Users user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        return "profile";
    }
    
    @GetMapping("/vaccines")
    public String viewVaccines(Model model) {
        Users user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Appointment> appointments = appointmentService.getPatientAppointments(user);
        if(user.getUserType() == UserType.Doctor){
            appointments = appointmentService.getDoctorAppointments(user);
        }
        model.addAttribute("appointments", appointments);
        model.addAttribute("user", user);
        return "patient_vaccines";
    }
    
    @GetMapping("/book_appointment")
    public String viewBookAppointment(Model model) {
        List<Users> doctors = userService.getUserByTypeAndStatus(UserType.Doctor, UserStatus.Active);
        List<Vaccination> vaccinations = vaccinationRepository.findAll();
        
        model.addAttribute("vaccinations", vaccinations);
        model.addAttribute("doctors",doctors);
        model.addAttribute("appointment", new Appointment());
        return "book_appointment";
    }
    
    @GetMapping("/doctor_appointments")
    public String viewDoctorAppointment(Model model, @RequestParam(required = false) String firstName) {
        Users user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Appointment> appointments = appointmentService.getDoctorAppointments(user);
        if(firstName != null){
           appointments = appointmentService.getDoctorAppointmentsByPatientName(user,firstName);
        }
        model.addAttribute("appointments", appointments);
        
        return "doctor_appointments";
    }


    @PostMapping("/book_appointment")
    public String newBooking(@Valid Appointment appointment, Model model){
        
        Users currentUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        Users doctor = appointment.getDoctor();
        appointmentService.createAppointment(appointment, doctor, currentUser);
        return "redirect:dashboard";
    }
    
    @PostMapping("/update_appointment_status")
    public String updateAppointmentStatus(Model model,@Valid @RequestBody StatusUpdateForm statusUpdateForm ){
        
        Users currentUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        Optional<Appointment> appointmentObj = appointmentService.getById(statusUpdateForm.getId());
        if(!appointmentObj.isPresent()){
            model.addAttribute("error", "Update failed!");
        }else{
            Appointment appointment = appointmentObj.get();
            appointment.setStatus(AppointmentStatus.valueOf(statusUpdateForm.getStatus()));

            appointmentService.updateAppointment(appointment);

            List<Appointment> appointments = appointmentService.getDoctorPendingAppointments(currentUser, AppointmentStatus.Pending);
            model.addAttribute("appointments", appointments);
        }
        
        return "doctor_appointments";
    }
    
    @PostMapping("/update_user_status")
    public String updateUserStatus(Model model,@Valid @RequestBody StatusUpdateForm statusUpdateForm ){
        
        Users currentUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        Optional<Users> userObj = userService.getUserById(statusUpdateForm.getId());
        if(!userObj.isPresent()){
            model.addAttribute("error", "Update failed!");
        }else{
            Users user = userObj.get();
            user.setStatus(UserStatus.valueOf(statusUpdateForm.getStatus()));

            userService.updateUser(user);

            List<Users> doctors = userService.getUserByTypeAndStatus(UserType.Doctor, UserStatus.VerificationPending);
            model.addAttribute("doctors", doctors);
        }
        
        return "doctors";
    }
}
