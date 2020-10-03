package com.ssafy.engki.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.ssafy.engki.dto.KidDto;
import com.ssafy.engki.entity.Kid;
import com.ssafy.engki.mapper.KidMapper;
import com.ssafy.engki.repository.KidRepository;

@RequiredArgsConstructor
@Service
public class KidService {
	private final KidRepository kidRepository;

	public long add(KidDto.KidRequest kidRequest) {
		Kid kid = Kid.builder()
			.parentId(kidRequest.getParentId())
			.name(kidRequest.getName())
			.birthday(kidRequest.getBirthday())
			.icon(kidRequest.getIcon())
			.build();
		return kidRepository.save(kid).getId();
	}

	public String updateIcon(long kidId, KidDto.KidIcon icon) {
		Kid kid = kidRepository.getOne(kidId);
		kid.updateIcon(icon.getIcon());
		return kidRepository.save(kid).getIcon();
	}

	public void delete(long kidId) {
		kidRepository.deleteById(kidId);
	}

	public KidDto.KidInfo get(long kidId) {
		return KidMapper.INSTANCE.toInfo(
			kidRepository.getOne(kidId)
		);
	}
}
