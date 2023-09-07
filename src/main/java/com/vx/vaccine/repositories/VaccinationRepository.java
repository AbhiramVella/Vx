package com.vx.vaccine.repositories;

import com.vx.vaccine.models.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Abhiram
 */
@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Long>{
}
