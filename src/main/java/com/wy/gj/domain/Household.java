package com.wy.gj.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Household.
 */
@Entity
@Table(name = "household")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Household implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "household_id")
    private Long householdId;

    @Column(name = "household_address")
    private String householdAddress;

    @Column(name = "household_area")
    private String householdArea;

    @Column(name = "household_propertyfee")
    private Long householdPropertyfee;

    @Column(name = "household_garbagefee")
    private Long householdGarbagefee;

    @Column(name = "light_and_water")
    private Long lightAndWater;

    @Column(name = "present_value")
    private Long presentValue;

    @Column(name = "home_owners_name")
    private String homeOwnersName;

    @Column(name = "home_owners_phone")
    private Long homeOwnersPhone;

    @OneToMany(mappedBy = "household")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "household")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Owner> owners = new HashSet<>();

    @OneToMany(mappedBy = "household")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<News> news = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }

    public String getHouseholdAddress() {
        return householdAddress;
    }

    public void setHouseholdAddress(String householdAddress) {
        this.householdAddress = householdAddress;
    }

    public String getHouseholdArea() {
        return householdArea;
    }

    public void setHouseholdArea(String householdArea) {
        this.householdArea = householdArea;
    }

    public Long getHouseholdPropertyfee() {
        return householdPropertyfee;
    }

    public void setHouseholdPropertyfee(Long householdPropertyfee) {
        this.householdPropertyfee = householdPropertyfee;
    }

    public Long getHouseholdGarbagefee() {
        return householdGarbagefee;
    }

    public void setHouseholdGarbagefee(Long householdGarbagefee) {
        this.householdGarbagefee = householdGarbagefee;
    }

    public Long getLightAndWater() {
        return lightAndWater;
    }

    public void setLightAndWater(Long lightAndWater) {
        this.lightAndWater = lightAndWater;
    }

    public Long getPresentValue() {
        return presentValue;
    }

    public void setPresentValue(Long presentValue) {
        this.presentValue = presentValue;
    }

    public String getHomeOwnersName() {
        return homeOwnersName;
    }

    public void setHomeOwnersName(String homeOwnersName) {
        this.homeOwnersName = homeOwnersName;
    }

    public Long getHomeOwnersPhone() {
        return homeOwnersPhone;
    }

    public void setHomeOwnersPhone(Long homeOwnersPhone) {
        this.homeOwnersPhone = homeOwnersPhone;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<Owner> getOwners() {
        return owners;
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
    }

    public Set<News> getNews() {
        return news;
    }

    public void setNews(Set<News> news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Household household = (Household) o;
        if(household.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, household.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Household{" +
            "id=" + id +
            ", householdId='" + householdId + "'" +
            ", householdAddress='" + householdAddress + "'" +
            ", householdArea='" + householdArea + "'" +
            ", householdPropertyfee='" + householdPropertyfee + "'" +
            ", householdGarbagefee='" + householdGarbagefee + "'" +
            ", lightAndWater='" + lightAndWater + "'" +
            ", presentValue='" + presentValue + "'" +
            ", homeOwnersName='" + homeOwnersName + "'" +
            ", homeOwnersPhone='" + homeOwnersPhone + "'" +
            '}';
    }
}
