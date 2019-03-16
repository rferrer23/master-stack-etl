package com.tefarana.cb.service.impl;

import com.tefarana.cb.service.ExtraService;
import com.tefarana.cb.domain.Extra;
import com.tefarana.cb.repository.ExtraRepository;
import com.tefarana.cb.service.dto.ExtraDTO;
import com.tefarana.cb.service.mapper.ExtraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Extra.
 */
@Service
@Transactional
public class ExtraServiceImpl implements ExtraService {

    private final Logger log = LoggerFactory.getLogger(ExtraServiceImpl.class);

    private final ExtraRepository extraRepository;

    private final ExtraMapper extraMapper;

    public ExtraServiceImpl(ExtraRepository extraRepository, ExtraMapper extraMapper) {
        this.extraRepository = extraRepository;
        this.extraMapper = extraMapper;
    }

    /**
     * Save a extra.
     *
     * @param extraDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExtraDTO save(ExtraDTO extraDTO) {
        log.debug("Request to save Extra : {}", extraDTO);

        Extra extra = extraMapper.toEntity(extraDTO);
        extra = extraRepository.save(extra);
        return extraMapper.toDto(extra);
    }

    /**
     * Get all the extras.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExtraDTO> findAll() {
        log.debug("Request to get all Extras");
        return extraRepository.findAll().stream()
            .map(extraMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one extra by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ExtraDTO> findOne(Long id) {
        log.debug("Request to get Extra : {}", id);
        return extraRepository.findById(id)
            .map(extraMapper::toDto);
    }

    /**
     * Delete the extra by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Extra : {}", id);
        extraRepository.deleteById(id);
    }
}
