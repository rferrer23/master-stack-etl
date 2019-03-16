package com.tefarana.cb.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import com.tefarana.cb.domain.Extra_;
import com.tefarana.cb.domain.StackOverFlowApiClient;
import com.tefarana.cb.domain.StackOverFlowApiClient_;
import com.tefarana.cb.repository.StackOverFlowApiClientRepository;
import com.tefarana.cb.service.dto.StackOverFlowApiClientCriteria;
import com.tefarana.cb.service.dto.StackOverFlowApiClientDTO;
import com.tefarana.cb.service.mapper.StackOverFlowApiClientMapper;

import io.github.jhipster.service.QueryService;

/**
 * Service for executing complex queries for StackOverFlowApiClient entities in the database.
 * The main input is a {@link StackOverFlowApiClientCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StackOverFlowApiClientDTO} or a {@link Page} of {@link StackOverFlowApiClientDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StackOverFlowApiClientQueryService extends QueryService<StackOverFlowApiClient> {

    private final Logger log = LoggerFactory.getLogger(StackOverFlowApiClientQueryService.class);

    private final StackOverFlowApiClientRepository stackOverFlowApiClientRepository;

    private final StackOverFlowApiClientMapper stackOverFlowApiClientMapper;

    public StackOverFlowApiClientQueryService(StackOverFlowApiClientRepository stackOverFlowApiClientRepository, StackOverFlowApiClientMapper stackOverFlowApiClientMapper) {
        this.stackOverFlowApiClientRepository = stackOverFlowApiClientRepository;
        this.stackOverFlowApiClientMapper = stackOverFlowApiClientMapper;
    }

    /**
     * Return a {@link List} of {@link StackOverFlowApiClientDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StackOverFlowApiClientDTO> findByCriteria(StackOverFlowApiClientCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StackOverFlowApiClient> specification = createSpecification(criteria);
        return stackOverFlowApiClientMapper.toDto(stackOverFlowApiClientRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StackOverFlowApiClientDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StackOverFlowApiClientDTO> findByCriteria(StackOverFlowApiClientCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StackOverFlowApiClient> specification = createSpecification(criteria);
        return stackOverFlowApiClientRepository.findAll(specification, page)
            .map(stackOverFlowApiClientMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StackOverFlowApiClientCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StackOverFlowApiClient> specification = createSpecification(criteria);
        return stackOverFlowApiClientRepository.count(specification);
    }

    /**
     * Function to convert StackOverFlowApiClientCriteria to a {@link Specification}
     */
    private Specification<StackOverFlowApiClient> createSpecification(StackOverFlowApiClientCriteria criteria) {
        Specification<StackOverFlowApiClient> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StackOverFlowApiClient_.id));
            }
            if (criteria.getUsers() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsers(), StackOverFlowApiClient_.users));
            }
            if (criteria.getFirstPeriod() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstPeriod(), StackOverFlowApiClient_.firstPeriod));
            }
            if (criteria.getLastPeriod() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastPeriod(), StackOverFlowApiClient_.lastPeriod));
            }
            if (criteria.getTags() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTags(), StackOverFlowApiClient_.tags));
            }
            if (criteria.getNextSendTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextSendTime(), StackOverFlowApiClient_.nextSendTime));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), StackOverFlowApiClient_.active));
            }
            if (criteria.getSecret() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSecret(), StackOverFlowApiClient_.secret));
            }
            if (criteria.getExtraId() != null) {
                specification = specification.and(buildSpecification(criteria.getExtraId(),
                    root -> root.join(StackOverFlowApiClient_.extras, JoinType.LEFT).get(Extra_.id)));
            }
        }
        return specification;
    }
}
