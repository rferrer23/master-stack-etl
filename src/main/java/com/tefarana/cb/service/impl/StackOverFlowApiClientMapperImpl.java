package com.tefarana.cb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tefarana.cb.domain.StackOverFlowApiClient;
import com.tefarana.cb.service.dto.StackOverFlowApiClientDTO;
import com.tefarana.cb.service.mapper.StackOverFlowApiClientMapper;

@Service
public class StackOverFlowApiClientMapperImpl implements StackOverFlowApiClientMapper {

	@Override
	public StackOverFlowApiClient toEntity(StackOverFlowApiClientDTO dto) {
		StackOverFlowApiClient result = new StackOverFlowApiClient();
		result.setActive(dto.isActive());
		result.setFirstPeriod(dto.getFirstPeriod());
		result.setLastPeriod(dto.getLastPeriod());
		result.setNextSendTime(dto.getNextSendTime());
		result.setSecret(dto.getSecret());
		result.setTags(dto.getTags());
		result.setUsers(dto.getUsers());
		return result;
	}

	@Override
	public StackOverFlowApiClientDTO toDto(StackOverFlowApiClient entity) {
		StackOverFlowApiClientDTO result = new StackOverFlowApiClientDTO();
		result.setActive(entity.isActive());
		result.setFirstPeriod(entity.getFirstPeriod());
		result.setLastPeriod(entity.getLastPeriod());
		result.setNextSendTime(entity.getNextSendTime());
		result.setSecret(entity.getSecret());
		result.setTags(entity.getTags());
		result.setUsers(entity.getUsers());
		result.setId(entity.getId());
		return result;
	}

	@Override
	public List<StackOverFlowApiClient> toEntity(List<StackOverFlowApiClientDTO> dtoList) {
		if(!CollectionUtils.isEmpty(dtoList)) {
			return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public List<StackOverFlowApiClientDTO> toDto(List<StackOverFlowApiClient> entityList) {
		if(!CollectionUtils.isEmpty(entityList)) {
			return entityList.stream().map(this::toDto).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

}
