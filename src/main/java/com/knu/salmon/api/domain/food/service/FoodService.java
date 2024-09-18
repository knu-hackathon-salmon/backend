package com.knu.salmon.api.domain.food.service;

import com.knu.salmon.api.domain.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodService {
    private final FoodRepository foodRepository;


}
