package com.wy.gj.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wy.gj.domain.BuildingSetting;
import com.wy.gj.repository.BuildingSettingRepository;
import com.wy.gj.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BuildingSetting.
 */
@RestController
@RequestMapping("/api")
public class BuildingSettingResource {

    private final Logger log = LoggerFactory.getLogger(BuildingSettingResource.class);
        
    @Inject
    private BuildingSettingRepository buildingSettingRepository;
    
    /**
     * POST  /building-settings : Create a new buildingSetting.
     *
     * @param buildingSetting the buildingSetting to create
     * @return the ResponseEntity with status 201 (Created) and with body the new buildingSetting, or with status 400 (Bad Request) if the buildingSetting has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/building-settings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BuildingSetting> createBuildingSetting(@RequestBody BuildingSetting buildingSetting) throws URISyntaxException {
        log.debug("REST request to save BuildingSetting : {}", buildingSetting);
        if (buildingSetting.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("buildingSetting", "idexists", "A new buildingSetting cannot already have an ID")).body(null);
        }
        BuildingSetting result = buildingSettingRepository.save(buildingSetting);
        return ResponseEntity.created(new URI("/api/building-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("buildingSetting", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /building-settings : Updates an existing buildingSetting.
     *
     * @param buildingSetting the buildingSetting to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated buildingSetting,
     * or with status 400 (Bad Request) if the buildingSetting is not valid,
     * or with status 500 (Internal Server Error) if the buildingSetting couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/building-settings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BuildingSetting> updateBuildingSetting(@RequestBody BuildingSetting buildingSetting) throws URISyntaxException {
        log.debug("REST request to update BuildingSetting : {}", buildingSetting);
        if (buildingSetting.getId() == null) {
            return createBuildingSetting(buildingSetting);
        }
        BuildingSetting result = buildingSettingRepository.save(buildingSetting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("buildingSetting", buildingSetting.getId().toString()))
            .body(result);
    }

    /**
     * GET  /building-settings : get all the buildingSettings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of buildingSettings in body
     */
    @RequestMapping(value = "/building-settings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BuildingSetting> getAllBuildingSettings() {
        log.debug("REST request to get all BuildingSettings");
        List<BuildingSetting> buildingSettings = buildingSettingRepository.findAll();
        return buildingSettings;
    }

    /**
     * GET  /building-settings/:id : get the "id" buildingSetting.
     *
     * @param id the id of the buildingSetting to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buildingSetting, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/building-settings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BuildingSetting> getBuildingSetting(@PathVariable Long id) {
        log.debug("REST request to get BuildingSetting : {}", id);
        BuildingSetting buildingSetting = buildingSettingRepository.findOne(id);
        return Optional.ofNullable(buildingSetting)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /building-settings/:id : delete the "id" buildingSetting.
     *
     * @param id the id of the buildingSetting to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/building-settings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBuildingSetting(@PathVariable Long id) {
        log.debug("REST request to delete BuildingSetting : {}", id);
        buildingSettingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("buildingSetting", id.toString())).build();
    }

}
