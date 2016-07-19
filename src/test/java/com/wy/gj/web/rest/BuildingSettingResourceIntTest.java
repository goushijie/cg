package com.wy.gj.web.rest;

import com.wy.gj.CgApp;
import com.wy.gj.domain.BuildingSetting;
import com.wy.gj.repository.BuildingSettingRepository;

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
 * Test class for the BuildingSettingResource REST controller.
 *
 * @see BuildingSettingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CgApp.class)
@WebAppConfiguration
@IntegrationTest
public class BuildingSettingResourceIntTest {


    private static final Long DEFAULT_BUILDING_ID = 1L;
    private static final Long UPDATED_BUILDING_ID = 2L;
    private static final String DEFAULT_BUILDING_NAME = "AAAAA";
    private static final String UPDATED_BUILDING_NAME = "BBBBB";
    private static final String DEFAULT_BUILDING_ADDRESS = "AAAAA";
    private static final String UPDATED_BUILDING_ADDRESS = "BBBBB";

    private static final Long DEFAULT_BUILDING_NUMBER = 1L;
    private static final Long UPDATED_BUILDING_NUMBER = 2L;

    private static final Long DEFAULT_UNIT_NUMBER = 1L;
    private static final Long UPDATED_UNIT_NUMBER = 2L;

    private static final Long DEFAULT_HOUSEHOLD_NUMBER = 1L;
    private static final Long UPDATED_HOUSEHOLD_NUMBER = 2L;

    private static final Long DEFAULT_COUNT_TIER = 1L;
    private static final Long UPDATED_COUNT_TIER = 2L;

    @Inject
    private BuildingSettingRepository buildingSettingRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBuildingSettingMockMvc;

    private BuildingSetting buildingSetting;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BuildingSettingResource buildingSettingResource = new BuildingSettingResource();
        ReflectionTestUtils.setField(buildingSettingResource, "buildingSettingRepository", buildingSettingRepository);
        this.restBuildingSettingMockMvc = MockMvcBuilders.standaloneSetup(buildingSettingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        buildingSetting = new BuildingSetting();
        buildingSetting.setBuildingId(DEFAULT_BUILDING_ID);
        buildingSetting.setBuildingName(DEFAULT_BUILDING_NAME);
        buildingSetting.setBuildingAddress(DEFAULT_BUILDING_ADDRESS);
        buildingSetting.setBuildingNumber(DEFAULT_BUILDING_NUMBER);
        buildingSetting.setUnitNumber(DEFAULT_UNIT_NUMBER);
        buildingSetting.setHouseholdNumber(DEFAULT_HOUSEHOLD_NUMBER);
        buildingSetting.setCountTier(DEFAULT_COUNT_TIER);
    }

    @Test
    @Transactional
    public void createBuildingSetting() throws Exception {
        int databaseSizeBeforeCreate = buildingSettingRepository.findAll().size();

        // Create the BuildingSetting

        restBuildingSettingMockMvc.perform(post("/api/building-settings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(buildingSetting)))
                .andExpect(status().isCreated());

        // Validate the BuildingSetting in the database
        List<BuildingSetting> buildingSettings = buildingSettingRepository.findAll();
        assertThat(buildingSettings).hasSize(databaseSizeBeforeCreate + 1);
        BuildingSetting testBuildingSetting = buildingSettings.get(buildingSettings.size() - 1);
        assertThat(testBuildingSetting.getBuildingId()).isEqualTo(DEFAULT_BUILDING_ID);
        assertThat(testBuildingSetting.getBuildingName()).isEqualTo(DEFAULT_BUILDING_NAME);
        assertThat(testBuildingSetting.getBuildingAddress()).isEqualTo(DEFAULT_BUILDING_ADDRESS);
        assertThat(testBuildingSetting.getBuildingNumber()).isEqualTo(DEFAULT_BUILDING_NUMBER);
        assertThat(testBuildingSetting.getUnitNumber()).isEqualTo(DEFAULT_UNIT_NUMBER);
        assertThat(testBuildingSetting.getHouseholdNumber()).isEqualTo(DEFAULT_HOUSEHOLD_NUMBER);
        assertThat(testBuildingSetting.getCountTier()).isEqualTo(DEFAULT_COUNT_TIER);
    }

    @Test
    @Transactional
    public void getAllBuildingSettings() throws Exception {
        // Initialize the database
        buildingSettingRepository.saveAndFlush(buildingSetting);

        // Get all the buildingSettings
        restBuildingSettingMockMvc.perform(get("/api/building-settings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(buildingSetting.getId().intValue())))
                .andExpect(jsonPath("$.[*].buildingId").value(hasItem(DEFAULT_BUILDING_ID.intValue())))
                .andExpect(jsonPath("$.[*].buildingName").value(hasItem(DEFAULT_BUILDING_NAME.toString())))
                .andExpect(jsonPath("$.[*].buildingAddress").value(hasItem(DEFAULT_BUILDING_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].buildingNumber").value(hasItem(DEFAULT_BUILDING_NUMBER.intValue())))
                .andExpect(jsonPath("$.[*].unitNumber").value(hasItem(DEFAULT_UNIT_NUMBER.intValue())))
                .andExpect(jsonPath("$.[*].householdNumber").value(hasItem(DEFAULT_HOUSEHOLD_NUMBER.intValue())))
                .andExpect(jsonPath("$.[*].countTier").value(hasItem(DEFAULT_COUNT_TIER.intValue())));
    }

    @Test
    @Transactional
    public void getBuildingSetting() throws Exception {
        // Initialize the database
        buildingSettingRepository.saveAndFlush(buildingSetting);

        // Get the buildingSetting
        restBuildingSettingMockMvc.perform(get("/api/building-settings/{id}", buildingSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(buildingSetting.getId().intValue()))
            .andExpect(jsonPath("$.buildingId").value(DEFAULT_BUILDING_ID.intValue()))
            .andExpect(jsonPath("$.buildingName").value(DEFAULT_BUILDING_NAME.toString()))
            .andExpect(jsonPath("$.buildingAddress").value(DEFAULT_BUILDING_ADDRESS.toString()))
            .andExpect(jsonPath("$.buildingNumber").value(DEFAULT_BUILDING_NUMBER.intValue()))
            .andExpect(jsonPath("$.unitNumber").value(DEFAULT_UNIT_NUMBER.intValue()))
            .andExpect(jsonPath("$.householdNumber").value(DEFAULT_HOUSEHOLD_NUMBER.intValue()))
            .andExpect(jsonPath("$.countTier").value(DEFAULT_COUNT_TIER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBuildingSetting() throws Exception {
        // Get the buildingSetting
        restBuildingSettingMockMvc.perform(get("/api/building-settings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuildingSetting() throws Exception {
        // Initialize the database
        buildingSettingRepository.saveAndFlush(buildingSetting);
        int databaseSizeBeforeUpdate = buildingSettingRepository.findAll().size();

        // Update the buildingSetting
        BuildingSetting updatedBuildingSetting = new BuildingSetting();
        updatedBuildingSetting.setId(buildingSetting.getId());
        updatedBuildingSetting.setBuildingId(UPDATED_BUILDING_ID);
        updatedBuildingSetting.setBuildingName(UPDATED_BUILDING_NAME);
        updatedBuildingSetting.setBuildingAddress(UPDATED_BUILDING_ADDRESS);
        updatedBuildingSetting.setBuildingNumber(UPDATED_BUILDING_NUMBER);
        updatedBuildingSetting.setUnitNumber(UPDATED_UNIT_NUMBER);
        updatedBuildingSetting.setHouseholdNumber(UPDATED_HOUSEHOLD_NUMBER);
        updatedBuildingSetting.setCountTier(UPDATED_COUNT_TIER);

        restBuildingSettingMockMvc.perform(put("/api/building-settings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBuildingSetting)))
                .andExpect(status().isOk());

        // Validate the BuildingSetting in the database
        List<BuildingSetting> buildingSettings = buildingSettingRepository.findAll();
        assertThat(buildingSettings).hasSize(databaseSizeBeforeUpdate);
        BuildingSetting testBuildingSetting = buildingSettings.get(buildingSettings.size() - 1);
        assertThat(testBuildingSetting.getBuildingId()).isEqualTo(UPDATED_BUILDING_ID);
        assertThat(testBuildingSetting.getBuildingName()).isEqualTo(UPDATED_BUILDING_NAME);
        assertThat(testBuildingSetting.getBuildingAddress()).isEqualTo(UPDATED_BUILDING_ADDRESS);
        assertThat(testBuildingSetting.getBuildingNumber()).isEqualTo(UPDATED_BUILDING_NUMBER);
        assertThat(testBuildingSetting.getUnitNumber()).isEqualTo(UPDATED_UNIT_NUMBER);
        assertThat(testBuildingSetting.getHouseholdNumber()).isEqualTo(UPDATED_HOUSEHOLD_NUMBER);
        assertThat(testBuildingSetting.getCountTier()).isEqualTo(UPDATED_COUNT_TIER);
    }

    @Test
    @Transactional
    public void deleteBuildingSetting() throws Exception {
        // Initialize the database
        buildingSettingRepository.saveAndFlush(buildingSetting);
        int databaseSizeBeforeDelete = buildingSettingRepository.findAll().size();

        // Get the buildingSetting
        restBuildingSettingMockMvc.perform(delete("/api/building-settings/{id}", buildingSetting.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BuildingSetting> buildingSettings = buildingSettingRepository.findAll();
        assertThat(buildingSettings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
