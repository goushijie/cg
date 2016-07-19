package com.wy.gj.repository;

import com.wy.gj.domain.BuildingSetting;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BuildingSetting entity.
 */
@SuppressWarnings("unused")
public interface BuildingSettingRepository extends JpaRepository<BuildingSetting,Long> {

}
