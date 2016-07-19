package com.wy.gj.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wy.gj.domain.Owner;
import com.wy.gj.repository.OwnerRepository;
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
 * REST controller for managing Owner.
 */
@RestController
@RequestMapping("/api")
public class OwnerResource {

    private final Logger log = LoggerFactory.getLogger(OwnerResource.class);
        
    @Inject
    private OwnerRepository ownerRepository;
    
    /**
     * POST  /owners : Create a new owner.
     *
     * @param owner the owner to create
     * @return the ResponseEntity with status 201 (Created) and with body the new owner, or with status 400 (Bad Request) if the owner has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/owners",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) throws URISyntaxException {
        log.debug("REST request to save Owner : {}", owner);
        if (owner.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("owner", "idexists", "A new owner cannot already have an ID")).body(null);
        }
        Owner result = ownerRepository.save(owner);
        return ResponseEntity.created(new URI("/api/owners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("owner", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owners : Updates an existing owner.
     *
     * @param owner the owner to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated owner,
     * or with status 400 (Bad Request) if the owner is not valid,
     * or with status 500 (Internal Server Error) if the owner couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/owners",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Owner> updateOwner(@RequestBody Owner owner) throws URISyntaxException {
        log.debug("REST request to update Owner : {}", owner);
        if (owner.getId() == null) {
            return createOwner(owner);
        }
        Owner result = ownerRepository.save(owner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("owner", owner.getId().toString()))
            .body(result);
    }

    /**
     * GET  /owners : get all the owners.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of owners in body
     */
    @RequestMapping(value = "/owners",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Owner> getAllOwners() {
        log.debug("REST request to get all Owners");
        List<Owner> owners = ownerRepository.findAll();
        return owners;
    }

    /**
     * GET  /owners/:id : get the "id" owner.
     *
     * @param id the id of the owner to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the owner, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/owners/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Owner> getOwner(@PathVariable Long id) {
        log.debug("REST request to get Owner : {}", id);
        Owner owner = ownerRepository.findOne(id);
        return Optional.ofNullable(owner)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /owners/:id : delete the "id" owner.
     *
     * @param id the id of the owner to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/owners/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        log.debug("REST request to delete Owner : {}", id);
        ownerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("owner", id.toString())).build();
    }

}
