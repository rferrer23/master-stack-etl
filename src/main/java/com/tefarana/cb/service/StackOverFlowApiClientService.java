package com.tefarana.cb.service;

import com.tefarana.cb.service.dto.StackOverFlowApiClientDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing StackOverFlowApiClient.
 */
public interface StackOverFlowApiClientService {

    /**
     * Save a stackOverFlowApiClient.
     *
     * @param stackOverFlowApiClientDTO the entity to save
     * @return the persisted entity
     */
    StackOverFlowApiClientDTO save(StackOverFlowApiClientDTO stackOverFlowApiClientDTO);

    /**
     * Get all the stackOverFlowApiClients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StackOverFlowApiClientDTO> findAll(Pageable pageable);

    /**
     * Get all the StackOverFlowApiClient with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<StackOverFlowApiClientDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" stackOverFlowApiClient.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StackOverFlowApiClientDTO> findOne(Long id);

    /**
     * Delete the "id" stackOverFlowApiClient.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
