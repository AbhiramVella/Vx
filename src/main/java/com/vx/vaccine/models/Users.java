package com.vx.vaccine.models;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import javax.validation.constraints.NotNull; 

/**
 *
 * @author Abhiram
 */
@Entity
@Table(name = "users")
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "First name cannot be empty.")
    private String firstName;
    
    @NotNull(message = "Last name cannot be empty.")
    private String lastName;
    
    @NotNull(message = "Age cannot be empty.")
    private Integer age;
    
    @NotNull(message = "Email cannot be empty.")
    @Column(unique=true)
    private String email;
    
    @NotNull(message = "Mobile number cannot be empty.")
    private String mobileNumber;
    
    private String address;
    
    @NotNull(message = "Password cannot be empty.")
    private String password;
    
    private UserType userType;
    
    private UserStatus status;
    
//    @OneToOne(mappedBy = "patient")
//    private Appointment patientAppointment;
//    
//    @OneToOne(mappedBy = "doctor")
//    private Appointment doctorAppointment;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }
    
    public UserType getUserType() {
        return userType;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
    
}
