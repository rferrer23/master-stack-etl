package com.tefarana.cb.service.impl;

import com.tefarana.cb.service.StackOverFlowApiClientService;
import com.tefarana.cb.domain.StackOverFlowApiClient;
import com.tefarana.cb.repository.StackOverFlowApiClientRepository;
import com.tefarana.cb.service.dto.StackOverFlowApiClientDTO;
import com.tefarana.cb.service.mapper.StackOverFlowApiClientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing StackOverFlowApiClient.
 */
@Service
@Transactional
public class StackOverFlowApiClientServiceImpl implements StackOverFlowApiClientService {

    private final Logger log = LoggerFactory.getLogger(StackOverFlowApiClientServiceImpl.class);

    private final StackOverFlowApiClientRepository stackOverFlowApiClientRepository;

    private final StackOverFlowApiClientMapper stackOverFlowApiClientMapper;

    public StackOverFlowApiClientServiceImpl(StackOverFlowApiClientRepository stackOverFlowApiClientRepository, StackOverFlowApiClientMapper stackOverFlowApiClientMapper) {
        this.stackOverFlowApiClientRepository = stackOverFlowApiClientRepository;
        this.stackOverFlowApiClientMapper = stackOverFlowApiClientMapper;
    }

    /**
     * Save a stackOverFlowApiClient.
     *
     * @param stackOverFlowApiClientDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StackOverFlowApiClientDTO save(StackOverFlowApiClientDTO stackOverFlowApiClientDTO) {
        log.debug("Request to save StackOverFlowApiClient : {}", stackOverFlowApiClientDTO);

        StackOverFlowApiClient stackOverFlowApiClient = stackOverFlowApiClientMapper.toEntity(stackOverFlowApiClientDTO);
        stackOverFlowApiClient = stackOverFlowApiClientRepository.save(stackOverFlowApiClient);
        return stackOverFlowApiClientMapper.toDto(stackOverFlowApiClient);
    }

    /**
     * Get all the stackOverFlowApiClients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StackOverFlowApiClientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StackOverFlowApiClients");
        return stackOverFlowApiClientRepository.findAll(pageable)
            .map(stackOverFlowApiClientMapper::toDto);
    }

    /**
     * Get all the StackOverFlowApiClient with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<StackOverFlowApiClientDTO> findAllWithEagerRelationships(Pageable pageable) {
        return stackOverFlowApiClientRepository.findAllWithEagerRelationships(pageable).map(stackOverFlowApiClientMapper::toDto);
    }
    

    /**
     * Get one stackOverFlowApiClient by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StackOverFlowApiClientDTO> findOne(Long id) {
        log.debug("Request to get StackOverFlowApiClient : {}", id);
        return stackOverFlowApiClientRepository.findOneWithEagerRelationships(id)
            .map(stackOverFlowApiClientMapper::toDto);
    }

    /**
     * Delete the stackOverFlowApiClient by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StackOverFlowApiClient : {}", id);
        stackOverFlowApiClientRepository.deleteById(id);
    }
}
