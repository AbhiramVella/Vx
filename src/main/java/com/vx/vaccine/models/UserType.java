package com.vx.vaccine.models;

import javax.persistence.Entity;

/**
 *
 * @author Abhiram
 */
@Entity
public enum UserType {
    Patient,
    Doctor,
    Admin
}
