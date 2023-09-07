package com.vx.vaccine.controllers;

import com.vx.vaccine.models.Appointment;
import com.vx.vaccine.models.AppointmentStatus;
import com.vx.vaccine.models.UserStatus;
import com.vx.vaccine.models.Users;
import com.vx.vaccine.models.UserType;
import com.vx.vaccine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.vx.vaccine.services.AppointmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Abhiram
 */

@Controller
public class CommonController {
    
    @Autowired
    private UserService userService;
    
     @Autowired
    private AppointmentService appointmentService;

    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String viewLoginPage() {

        return "login";
    }
    
    @GetMapping("/register_success")
    public String resiterSuccess() {
        return "register_success";
    }
    

    @GetMapping("/dashboard")
    public String dashboard(Model model) throws ParseException {
        Users user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user.getStatus() != UserStatus.Active){
          return "not_active";
        }
        Date currentDate = new Date();
        SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
        String date = mdyFormat.format(currentDate);
        
        List<Appointment> appointments = appointmentService.getPatientUpcomingAppointments(user, mdyFormat.parse(date));
        if(user.getUserType() == UserType.Doctor){
            appointments = appointmentService.getDoctorPendingAppointments(user, AppointmentStatus.Pending);
        }
        
        if(user.getUserType() == UserType.Admin){
            List<Users> doctors = userService.getUserByTypeAndStatus(UserType.Doctor, UserStatus.VerificationPending);
            model.addAttribute("doctors", doctors);
        }
        model.addAttribute("appointments", appointments);
        model.addAttribute("user", user);
        return "dashboard";

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword())
            );

            return ResponseEntity.ok("dashboard");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed!");
        }
    }

    @GetMapping("/patient/register")
    public String patientRegisterPage(Model model) {
        Users user = new Users();
        user.setUserType(UserType.Patient);
        model.addAttribute("name", "Patient");
        model.addAttribute("loginLink", "/login?userType=patient");
        model.addAttribute("userType", UserType.Patient);
        model.addAttribute("user", user);
        return "patient_register";
    }

    @GetMapping("/doctor/register")
    public String doctorRegisterPage(Model model) {
        Users user = new Users();
        user.setUserType(UserType.Doctor);
        model.addAttribute("name", "Doctor");
        model.addAttribute("loginLink", "/login?userType=doctor");
        model.addAttribute("userType", UserType.Doctor);
        model.addAttribute("user", user);
        return "doctor_register";
    }

    @PostMapping("/signup")
    public String newUser(@Valid Users user, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        
        System.out.println("My param: " + user.getUserType());
        System.out.println("My param: " + user.getStatus());

        if(user.getUserType().toString() == "Patient" || user.getUserType()==UserType.Patient){
            user.setStatus(UserStatus.Active);
        }else{
            user.setStatus(UserStatus.VerificationPending);
        }
        userService.createUser(user);
        return "register_success";
    }
}
