package com.wy.gj.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BuildingSetting.
 */
@Entity
@Table(name = "building_setting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BuildingSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "building_id")
    private Long buildingId;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "building_address")
    private String buildingAddress;

    @Column(name = "building_number")
    private Long buildingNumber;

    @Column(name = "unit_number")
    private Long unitNumber;

    @Column(name = "household_number")
    private Long householdNumber;

    @Column(name = "count_tier")
    private Long countTier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public Long getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(Long buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public Long getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Long unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Long getHouseholdNumber() {
        return householdNumber;
    }

    public void setHouseholdNumber(Long householdNumber) {
        this.householdNumber = householdNumber;
    }

    public Long getCountTier() {
        return countTier;
    }

    public void setCountTier(Long countTier) {
        this.countTier = countTier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BuildingSetting buildingSetting = (BuildingSetting) o;
        if(buildingSetting.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, buildingSetting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BuildingSetting{" +
            "id=" + id +
            ", buildingId='" + buildingId + "'" +
            ", buildingName='" + buildingName + "'" +
            ", buildingAddress='" + buildingAddress + "'" +
            ", buildingNumber='" + buildingNumber + "'" +
            ", unitNumber='" + unitNumber + "'" +
            ", householdNumber='" + householdNumber + "'" +
            ", countTier='" + countTier + "'" +
            '}';
    }
}
