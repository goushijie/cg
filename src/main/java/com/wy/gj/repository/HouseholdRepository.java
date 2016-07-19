package com.wy.gj.repository;

import com.wy.gj.domain.Household;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Household entity.
 */
@SuppressWarnings("unused")
public interface HouseholdRepository extends JpaRepository<Household,Long> {

}
