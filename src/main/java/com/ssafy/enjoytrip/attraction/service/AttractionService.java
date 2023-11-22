package com.ssafy.enjoytrip.attraction.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.attraction.dto.AttractionDto;
import com.ssafy.enjoytrip.attraction.entity.Attraction;
import com.ssafy.enjoytrip.attraction.entity.AttractionCategory;
import com.ssafy.enjoytrip.attraction.exception.AttractionNotFoundException;
import com.ssafy.enjoytrip.attraction.repository.AttractionRepository;
import com.ssafy.enjoytrip.attraction.repository.AttractionRepositoryCustom;
import com.ssafy.enjoytrip.common.dto.response.CommonResponse;
import com.ssafy.enjoytrip.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionService {
	private final AttractionRepository attractionRepository;
	private final AttractionRepositoryCustom attractionRepositoryCustom;

	@Transactional
	public CommonResponse create(AttractionDto attractionDto) {
		Attraction attraction = Attraction.toAttraction(attractionDto);
		return new CommonResponse(true, "Success to create attraction", attractionRepository.save(attraction));
	}

	public CommonResponse getItems(List<AttractionCategory> categories, String keyword, double latitude, double longitude, Pageable pageable) {
		return new CommonResponse(true, "Success to get board.",
			attractionRepositoryCustom.findDynamicQueryAdvance(categories, keyword, latitude, longitude, pageable));
	}

	@Transactional
	public CommonResponse update(Long id, AttractionDto attractionDto) {
		Optional<Attraction> optionalAttraction = attractionRepository.findById(id);

		if (optionalAttraction.isPresent()) {
			Attraction existAttraction = optionalAttraction.get();
			existAttraction.update(attractionDto);
			return new CommonResponse(true, "Success to update Attraction.",
				attractionRepository.save(existAttraction));
		}
		throw new AttractionNotFoundException(String.format("관광지(%s)를 찾을 수 없습니다.", id));
	}

	@Transactional
	public CommonResponse delete(Long id) {
		Optional<Attraction> optionalAttraction = attractionRepository.findById(id);

		if (optionalAttraction.isPresent()) {
			Attraction existAttraction = optionalAttraction.get();
			//existAttraction.delete();
			return new CommonResponse(true, "Success to delete Attraction.",
				attractionRepository.save(existAttraction));
		}
		throw new AttractionNotFoundException(String.format("관광지(%s)를 찾을 수 없습니다.", id));
	}

	@Transactional
	public CommonResponse like(Long id, User user) {
		Optional<Attraction> optionalAttraction = attractionRepository.findById(id);

		if (optionalAttraction.isPresent()) {
			Attraction attraction = optionalAttraction.get();

			if(attraction.getAttraction_users().contains(user))
				attraction.getAttraction_users().remove(user);
			else
				attraction.getAttraction_users().add(user);
			return new CommonResponse(true, "Success to like Attraction", attractionRepository.save(attraction));
		}

		throw new AttractionNotFoundException(String.format("관광지(%s)를 찾을 수 없습니다.", id));
	}
}
