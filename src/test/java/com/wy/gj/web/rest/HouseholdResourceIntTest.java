package com.wy.gj.web.rest;

import com.wy.gj.CgApp;
import com.wy.gj.domain.Household;
import com.wy.gj.repository.HouseholdRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HouseholdResource REST controller.
 *
 * @see HouseholdResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CgApp.class)
@WebAppConfiguration
@IntegrationTest
public class HouseholdResourceIntTest {


    private static final Long DEFAULT_HOUSEHOLD_ID = 1L;
    private static final Long UPDATED_HOUSEHOLD_ID = 2L;
    private static final String DEFAULT_HOUSEHOLD_ADDRESS = "AAAAA";
    private static final String UPDATED_HOUSEHOLD_ADDRESS = "BBBBB";
    private static final String DEFAULT_HOUSEHOLD_AREA = "AAAAA";
    private static final String UPDATED_HOUSEHOLD_AREA = "BBBBB";

    private static final Long DEFAULT_HOUSEHOLD_PROPERTYFEE = 1L;
    private static final Long UPDATED_HOUSEHOLD_PROPERTYFEE = 2L;

    private static final Long DEFAULT_HOUSEHOLD_GARBAGEFEE = 1L;
    private static final Long UPDATED_HOUSEHOLD_GARBAGEFEE = 2L;

    private static final Long DEFAULT_LIGHT_AND_WATER = 1L;
    private static final Long UPDATED_LIGHT_AND_WATER = 2L;

    private static final Long DEFAULT_PRESENT_VALUE = 1L;
    private static final Long UPDATED_PRESENT_VALUE = 2L;
    private static final String DEFAULT_HOME_OWNERS_NAME = "AAAAA";
    private static final String UPDATED_HOME_OWNERS_NAME = "BBBBB";

    private static final Long DEFAULT_HOME_OWNERS_PHONE = 1L;
    private static final Long UPDATED_HOME_OWNERS_PHONE = 2L;

    @Inject
    private HouseholdRepository householdRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHouseholdMockMvc;

    private Household household;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HouseholdResource householdResource = new HouseholdResource();
        ReflectionTestUtils.setField(householdResource, "householdRepository", householdRepository);
        this.restHouseholdMockMvc = MockMvcBuilders.standaloneSetup(householdResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        household = new Household();
        household.setHouseholdId(DEFAULT_HOUSEHOLD_ID);
        household.setHouseholdAddress(DEFAULT_HOUSEHOLD_ADDRESS);
        household.setHouseholdArea(DEFAULT_HOUSEHOLD_AREA);
        household.setHouseholdPropertyfee(DEFAULT_HOUSEHOLD_PROPERTYFEE);
        household.setHouseholdGarbagefee(DEFAULT_HOUSEHOLD_GARBAGEFEE);
        household.setLightAndWater(DEFAULT_LIGHT_AND_WATER);
        household.setPresentValue(DEFAULT_PRESENT_VALUE);
        household.setHomeOwnersName(DEFAULT_HOME_OWNERS_NAME);
        household.setHomeOwnersPhone(DEFAULT_HOME_OWNERS_PHONE);
    }

    @Test
    @Transactional
    public void createHousehold() throws Exception {
        int databaseSizeBeforeCreate = householdRepository.findAll().size();

        // Create the Household

        restHouseholdMockMvc.perform(post("/api/households")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(household)))
                .andExpect(status().isCreated());

        // Validate the Household in the database
        List<Household> households = householdRepository.findAll();
        assertThat(households).hasSize(databaseSizeBeforeCreate + 1);
        Household testHousehold = households.get(households.size() - 1);
        assertThat(testHousehold.getHouseholdId()).isEqualTo(DEFAULT_HOUSEHOLD_ID);
        assertThat(testHousehold.getHouseholdAddress()).isEqualTo(DEFAULT_HOUSEHOLD_ADDRESS);
        assertThat(testHousehold.getHouseholdArea()).isEqualTo(DEFAULT_HOUSEHOLD_AREA);
        assertThat(testHousehold.getHouseholdPropertyfee()).isEqualTo(DEFAULT_HOUSEHOLD_PROPERTYFEE);
        assertThat(testHousehold.getHouseholdGarbagefee()).isEqualTo(DEFAULT_HOUSEHOLD_GARBAGEFEE);
        assertThat(testHousehold.getLightAndWater()).isEqualTo(DEFAULT_LIGHT_AND_WATER);
        assertThat(testHousehold.getPresentValue()).isEqualTo(DEFAULT_PRESENT_VALUE);
        assertThat(testHousehold.getHomeOwnersName()).isEqualTo(DEFAULT_HOME_OWNERS_NAME);
        assertThat(testHousehold.getHomeOwnersPhone()).isEqualTo(DEFAULT_HOME_OWNERS_PHONE);
    }

    @Test
    @Transactional
    public void getAllHouseholds() throws Exception {
        // Initialize the database
        householdRepository.saveAndFlush(household);

        // Get all the households
        restHouseholdMockMvc.perform(get("/api/households?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(household.getId().intValue())))
                .andExpect(jsonPath("$.[*].householdId").value(hasItem(DEFAULT_HOUSEHOLD_ID.intValue())))
                .andExpect(jsonPath("$.[*].householdAddress").value(hasItem(DEFAULT_HOUSEHOLD_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].householdArea").value(hasItem(DEFAULT_HOUSEHOLD_AREA.toString())))
                .andExpect(jsonPath("$.[*].householdPropertyfee").value(hasItem(DEFAULT_HOUSEHOLD_PROPERTYFEE.intValue())))
                .andExpect(jsonPath("$.[*].householdGarbagefee").value(hasItem(DEFAULT_HOUSEHOLD_GARBAGEFEE.intValue())))
                .andExpect(jsonPath("$.[*].lightAndWater").value(hasItem(DEFAULT_LIGHT_AND_WATER.intValue())))
                .andExpect(jsonPath("$.[*].presentValue").value(hasItem(DEFAULT_PRESENT_VALUE.intValue())))
                .andExpect(jsonPath("$.[*].homeOwnersName").value(hasItem(DEFAULT_HOME_OWNERS_NAME.toString())))
                .andExpect(jsonPath("$.[*].homeOwnersPhone").value(hasItem(DEFAULT_HOME_OWNERS_PHONE.intValue())));
    }

    @Test
    @Transactional
    public void getHousehold() throws Exception {
        // Initialize the database
        householdRepository.saveAndFlush(household);

        // Get the household
        restHouseholdMockMvc.perform(get("/api/households/{id}", household.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(household.getId().intValue()))
            .andExpect(jsonPath("$.householdId").value(DEFAULT_HOUSEHOLD_ID.intValue()))
            .andExpect(jsonPath("$.householdAddress").value(DEFAULT_HOUSEHOLD_ADDRESS.toString()))
            .andExpect(jsonPath("$.householdArea").value(DEFAULT_HOUSEHOLD_AREA.toString()))
            .andExpect(jsonPath("$.householdPropertyfee").value(DEFAULT_HOUSEHOLD_PROPERTYFEE.intValue()))
            .andExpect(jsonPath("$.householdGarbagefee").value(DEFAULT_HOUSEHOLD_GARBAGEFEE.intValue()))
            .andExpect(jsonPath("$.lightAndWater").value(DEFAULT_LIGHT_AND_WATER.intValue()))
            .andExpect(jsonPath("$.presentValue").value(DEFAULT_PRESENT_VALUE.intValue()))
            .andExpect(jsonPath("$.homeOwnersName").value(DEFAULT_HOME_OWNERS_NAME.toString()))
            .andExpect(jsonPath("$.homeOwnersPhone").value(DEFAULT_HOME_OWNERS_PHONE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHousehold() throws Exception {
        // Get the household
        restHouseholdMockMvc.perform(get("/api/households/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHousehold() throws Exception {
        // Initialize the database
        householdRepository.saveAndFlush(household);
        int databaseSizeBeforeUpdate = householdRepository.findAll().size();

        // Update the household
        Household updatedHousehold = new Household();
        updatedHousehold.setId(household.getId());
        updatedHousehold.setHouseholdId(UPDATED_HOUSEHOLD_ID);
        updatedHousehold.setHouseholdAddress(UPDATED_HOUSEHOLD_ADDRESS);
        updatedHousehold.setHouseholdArea(UPDATED_HOUSEHOLD_AREA);
        updatedHousehold.setHouseholdPropertyfee(UPDATED_HOUSEHOLD_PROPERTYFEE);
        updatedHousehold.setHouseholdGarbagefee(UPDATED_HOUSEHOLD_GARBAGEFEE);
        updatedHousehold.setLightAndWater(UPDATED_LIGHT_AND_WATER);
        updatedHousehold.setPresentValue(UPDATED_PRESENT_VALUE);
        updatedHousehold.setHomeOwnersName(UPDATED_HOME_OWNERS_NAME);
        updatedHousehold.setHomeOwnersPhone(UPDATED_HOME_OWNERS_PHONE);

        restHouseholdMockMvc.perform(put("/api/households")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedHousehold)))
                .andExpect(status().isOk());

        // Validate the Household in the database
        List<Household> households = householdRepository.findAll();
        assertThat(households).hasSize(databaseSizeBeforeUpdate);
        Household testHousehold = households.get(households.size() - 1);
        assertThat(testHousehold.getHouseholdId()).isEqualTo(UPDATED_HOUSEHOLD_ID);
        assertThat(testHousehold.getHouseholdAddress()).isEqualTo(UPDATED_HOUSEHOLD_ADDRESS);
        assertThat(testHousehold.getHouseholdArea()).isEqualTo(UPDATED_HOUSEHOLD_AREA);
        assertThat(testHousehold.getHouseholdPropertyfee()).isEqualTo(UPDATED_HOUSEHOLD_PROPERTYFEE);
        assertThat(testHousehold.getHouseholdGarbagefee()).isEqualTo(UPDATED_HOUSEHOLD_GARBAGEFEE);
        assertThat(testHousehold.getLightAndWater()).isEqualTo(UPDATED_LIGHT_AND_WATER);
        assertThat(testHousehold.getPresentValue()).isEqualTo(UPDATED_PRESENT_VALUE);
        assertThat(testHousehold.getHomeOwnersName()).isEqualTo(UPDATED_HOME_OWNERS_NAME);
        assertThat(testHousehold.getHomeOwnersPhone()).isEqualTo(UPDATED_HOME_OWNERS_PHONE);
    }

    @Test
    @Transactional
    public void deleteHousehold() throws Exception {
        // Initialize the database
        householdRepository.saveAndFlush(household);
        int databaseSizeBeforeDelete = householdRepository.findAll().size();

        // Get the household
        restHouseholdMockMvc.perform(delete("/api/households/{id}", household.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Household> households = householdRepository.findAll();
        assertThat(households).hasSize(databaseSizeBeforeDelete - 1);
    }
}
