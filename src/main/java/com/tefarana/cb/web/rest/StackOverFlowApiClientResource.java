package com.tefarana.cb.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tefarana.cb.service.StackOverFlowApiClientService;
import com.tefarana.cb.web.rest.errors.BadRequestAlertException;
import com.tefarana.cb.web.rest.util.HeaderUtil;
import com.tefarana.cb.web.rest.util.PaginationUtil;
import com.tefarana.cb.service.dto.StackOverFlowApiClientDTO;
import com.tefarana.cb.service.dto.StackOverFlowApiClientCriteria;
import com.tefarana.cb.service.StackOverFlowApiClientQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StackOverFlowApiClient.
 */
@RestController
@RequestMapping("/api")
public class StackOverFlowApiClientResource {

    private final Logger log = LoggerFactory.getLogger(StackOverFlowApiClientResource.class);

    private static final String ENTITY_NAME = "stackOverFlowApiClient";

    private final StackOverFlowApiClientService stackOverFlowApiClientService;

    private final StackOverFlowApiClientQueryService stackOverFlowApiClientQueryService;

    public StackOverFlowApiClientResource(StackOverFlowApiClientService stackOverFlowApiClientService, StackOverFlowApiClientQueryService stackOverFlowApiClientQueryService) {
        this.stackOverFlowApiClientService = stackOverFlowApiClientService;
        this.stackOverFlowApiClientQueryService = stackOverFlowApiClientQueryService;
    }

    /**
     * POST  /stack-over-flow-api-clients : Create a new stackOverFlowApiClient.
     *
     * @param stackOverFlowApiClientDTO the stackOverFlowApiClientDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stackOverFlowApiClientDTO, or with status 400 (Bad Request) if the stackOverFlowApiClient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stack-over-flow-api-clients")
    @Timed
    public ResponseEntity<StackOverFlowApiClientDTO> createStackOverFlowApiClient(@Valid @RequestBody StackOverFlowApiClientDTO stackOverFlowApiClientDTO) throws URISyntaxException {
        log.debug("REST request to save StackOverFlowApiClient : {}", stackOverFlowApiClientDTO);
        if (stackOverFlowApiClientDTO.getId() != null) {
            throw new BadRequestAlertException("A new stackOverFlowApiClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StackOverFlowApiClientDTO result = stackOverFlowApiClientService.save(stackOverFlowApiClientDTO);
        return ResponseEntity.created(new URI("/api/stack-over-flow-api-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stack-over-flow-api-clients : Updates an existing stackOverFlowApiClient.
     *
     * @param stackOverFlowApiClientDTO the stackOverFlowApiClientDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stackOverFlowApiClientDTO,
     * or with status 400 (Bad Request) if the stackOverFlowApiClientDTO is not valid,
     * or with status 500 (Internal Server Error) if the stackOverFlowApiClientDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stack-over-flow-api-clients")
    @Timed
    public ResponseEntity<StackOverFlowApiClientDTO> updateStackOverFlowApiClient(@Valid @RequestBody StackOverFlowApiClientDTO stackOverFlowApiClientDTO) throws URISyntaxException {
        log.debug("REST request to update StackOverFlowApiClient : {}", stackOverFlowApiClientDTO);
        if (stackOverFlowApiClientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StackOverFlowApiClientDTO result = stackOverFlowApiClientService.save(stackOverFlowApiClientDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stackOverFlowApiClientDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stack-over-flow-api-clients : get all the stackOverFlowApiClients.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of stackOverFlowApiClients in body
     */
    @GetMapping("/stack-over-flow-api-clients")
    @Timed
    public ResponseEntity<List<StackOverFlowApiClientDTO>> getAllStackOverFlowApiClients(StackOverFlowApiClientCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StackOverFlowApiClients by criteria: {}", criteria);
        Page<StackOverFlowApiClientDTO> page = stackOverFlowApiClientQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stack-over-flow-api-clients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
    * GET  /stack-over-flow-api-clients/count : count all the stackOverFlowApiClients.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/stack-over-flow-api-clients/count")
    @Timed
    public ResponseEntity<Long> countStackOverFlowApiClients (StackOverFlowApiClientCriteria criteria) {
        log.debug("REST request to count StackOverFlowApiClients by criteria: {}", criteria);
        return ResponseEntity.ok().body(stackOverFlowApiClientQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /stack-over-flow-api-clients/:id : get the "id" stackOverFlowApiClient.
     *
     * @param id the id of the stackOverFlowApiClientDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stackOverFlowApiClientDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stack-over-flow-api-clients/{id}")
    @Timed
    public ResponseEntity<StackOverFlowApiClientDTO> getStackOverFlowApiClient(@PathVariable Long id) {
        log.debug("REST request to get StackOverFlowApiClient : {}", id);
        Optional<StackOverFlowApiClientDTO> stackOverFlowApiClientDTO = stackOverFlowApiClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stackOverFlowApiClientDTO);
    }

    /**
     * DELETE  /stack-over-flow-api-clients/:id : delete the "id" stackOverFlowApiClient.
     *
     * @param id the id of the stackOverFlowApiClientDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stack-over-flow-api-clients/{id}")
    @Timed
    public ResponseEntity<Void> deleteStackOverFlowApiClient(@PathVariable Long id) {
        log.debug("REST request to delete StackOverFlowApiClient : {}", id);
        stackOverFlowApiClientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
