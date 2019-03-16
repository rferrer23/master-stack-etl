package com.tefarana.cb.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tefarana.cb.domain.StackOverFlowApiClient;

/**
 * Spring Data  repository for the StackOverFlowApiClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StackOverFlowApiClientRepository extends JpaRepository<StackOverFlowApiClient, Long>, JpaSpecificationExecutor<StackOverFlowApiClient> {

    @Query(value = "select distinct stack_over_flow_api_client from StackOverFlowApiClient stack_over_flow_api_client left join fetch stack_over_flow_api_client.extras",
        countQuery = "select count(distinct stack_over_flow_api_client) from StackOverFlowApiClient stack_over_flow_api_client")
    Page<StackOverFlowApiClient> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct stack_over_flow_api_client from StackOverFlowApiClient stack_over_flow_api_client left join fetch stack_over_flow_api_client.extras")
    List<StackOverFlowApiClient> findAllWithEagerRelationships();

    @Query("select stack_over_flow_api_client from StackOverFlowApiClient stack_over_flow_api_client left join fetch stack_over_flow_api_client.extras where stack_over_flow_api_client.id =:id")
    Optional<StackOverFlowApiClient> findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query("select stack_over_flow_api_client from StackOverFlowApiClient stack_over_flow_api_client where stack_over_flow_api_client.nextSendTime <= :nextSendTime")
    List<StackOverFlowApiClient> findAllWithCreationDateTimeBefore(
      @Param("nextSendTime") LocalDate nextSendTime);

}
