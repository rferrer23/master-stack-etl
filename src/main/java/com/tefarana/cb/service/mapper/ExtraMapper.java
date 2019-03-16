package com.tefarana.cb.service.mapper;

import com.tefarana.cb.domain.*;
import com.tefarana.cb.service.dto.ExtraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Extra and its DTO ExtraDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExtraMapper extends EntityMapper<ExtraDTO, Extra> {



    default Extra fromId(Long id) {
        if (id == null) {
            return null;
        }
        Extra extra = new Extra();
        extra.setId(id);
        return extra;
    }
}
