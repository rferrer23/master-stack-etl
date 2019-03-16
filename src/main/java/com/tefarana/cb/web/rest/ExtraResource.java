package com.tefarana.cb.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tefarana.cb.service.ExtraService;
import com.tefarana.cb.web.rest.errors.BadRequestAlertException;
import com.tefarana.cb.web.rest.util.HeaderUtil;
import com.tefarana.cb.service.dto.ExtraDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Extra.
 */
@RestController
@RequestMapping("/api")
public class ExtraResource {

    private final Logger log = LoggerFactory.getLogger(ExtraResource.class);

    private static final String ENTITY_NAME = "extra";

    private final ExtraService extraService;

    public ExtraResource(ExtraService extraService) {
        this.extraService = extraService;
    }

    /**
     * POST  /extras : Create a new extra.
     *
     * @param extraDTO the extraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new extraDTO, or with status 400 (Bad Request) if the extra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/extras")
    @Timed
    public ResponseEntity<ExtraDTO> createExtra(@Valid @RequestBody ExtraDTO extraDTO) throws URISyntaxException {
        log.debug("REST request to save Extra : {}", extraDTO);
        if (extraDTO.getId() != null) {
            throw new BadRequestAlertException("A new extra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExtraDTO result = extraService.save(extraDTO);
        return ResponseEntity.created(new URI("/api/extras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /extras : Updates an existing extra.
     *
     * @param extraDTO the extraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated extraDTO,
     * or with status 400 (Bad Request) if the extraDTO is not valid,
     * or with status 500 (Internal Server Error) if the extraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/extras")
    @Timed
    public ResponseEntity<ExtraDTO> updateExtra(@Valid @RequestBody ExtraDTO extraDTO) throws URISyntaxException {
        log.debug("REST request to update Extra : {}", extraDTO);
        if (extraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExtraDTO result = extraService.save(extraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, extraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /extras : get all the extras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of extras in body
     */
    @GetMapping("/extras")
    @Timed
    public List<ExtraDTO> getAllExtras() {
        log.debug("REST request to get all Extras");
        return extraService.findAll();
    }

    /**
     * GET  /extras/:id : get the "id" extra.
     *
     * @param id the id of the extraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the extraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/extras/{id}")
    @Timed
    public ResponseEntity<ExtraDTO> getExtra(@PathVariable Long id) {
        log.debug("REST request to get Extra : {}", id);
        Optional<ExtraDTO> extraDTO = extraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(extraDTO);
    }

    /**
     * DELETE  /extras/:id : delete the "id" extra.
     *
     * @param id the id of the extraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/extras/{id}")
    @Timed
    public ResponseEntity<Void> deleteExtra(@PathVariable Long id) {
        log.debug("REST request to delete Extra : {}", id);
        extraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
