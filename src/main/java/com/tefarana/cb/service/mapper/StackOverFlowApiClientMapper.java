package com.tefarana.cb.service.mapper;

import com.tefarana.cb.domain.*;
import com.tefarana.cb.service.dto.StackOverFlowApiClientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StackOverFlowApiClient and its DTO StackOverFlowApiClientDTO.
 */
@Mapper(componentModel = "spring", uses = {ExtraMapper.class})
public interface StackOverFlowApiClientMapper extends EntityMapper<StackOverFlowApiClientDTO, StackOverFlowApiClient> {



    default StackOverFlowApiClient fromId(Long id) {
        if (id == null) {
            return null;
        }
        StackOverFlowApiClient stackOverFlowApiClient = new StackOverFlowApiClient();
        stackOverFlowApiClient.setId(id);
        return stackOverFlowApiClient;
    }
}
