package com.wy.gj.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wy.gj.domain.Household;
import com.wy.gj.repository.HouseholdRepository;
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
 * REST controller for managing Household.
 */
@RestController
@RequestMapping("/api")
public class HouseholdResource {

    private final Logger log = LoggerFactory.getLogger(HouseholdResource.class);
        
    @Inject
    private HouseholdRepository householdRepository;
    
    /**
     * POST  /households : Create a new household.
     *
     * @param household the household to create
     * @return the ResponseEntity with status 201 (Created) and with body the new household, or with status 400 (Bad Request) if the household has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/households",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Household> createHousehold(@RequestBody Household household) throws URISyntaxException {
        log.debug("REST request to save Household : {}", household);
        if (household.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("household", "idexists", "A new household cannot already have an ID")).body(null);
        }
        Household result = householdRepository.save(household);
        return ResponseEntity.created(new URI("/api/households/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("household", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /households : Updates an existing household.
     *
     * @param household the household to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated household,
     * or with status 400 (Bad Request) if the household is not valid,
     * or with status 500 (Internal Server Error) if the household couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/households",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Household> updateHousehold(@RequestBody Household household) throws URISyntaxException {
        log.debug("REST request to update Household : {}", household);
        if (household.getId() == null) {
            return createHousehold(household);
        }
        Household result = householdRepository.save(household);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("household", household.getId().toString()))
            .body(result);
    }

    /**
     * GET  /households : get all the households.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of households in body
     */
    @RequestMapping(value = "/households",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Household> getAllHouseholds() {
        log.debug("REST request to get all Households");
        List<Household> households = householdRepository.findAll();
        return households;
    }

    /**
     * GET  /households/:id : get the "id" household.
     *
     * @param id the id of the household to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the household, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/households/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Household> getHousehold(@PathVariable Long id) {
        log.debug("REST request to get Household : {}", id);
        Household household = householdRepository.findOne(id);
        return Optional.ofNullable(household)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /households/:id : delete the "id" household.
     *
     * @param id the id of the household to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/households/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHousehold(@PathVariable Long id) {
        log.debug("REST request to delete Household : {}", id);
        householdRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("household", id.toString())).build();
    }

}
