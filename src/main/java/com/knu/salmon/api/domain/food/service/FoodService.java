package com.knu.salmon.api.domain.food.service;

import com.knu.salmon.api.domain.Image.entity.FoodImage;
import com.knu.salmon.api.domain.Image.repository.FoodImageRepository;
import com.knu.salmon.api.domain.Image.service.FoodImageService;
import com.knu.salmon.api.domain.food.dto.request.CreateFoodDto;
import com.knu.salmon.api.domain.food.dto.request.FoodMapNearRequestDto;
import com.knu.salmon.api.domain.food.dto.request.FoodMyLocationRequestDto;
import com.knu.salmon.api.domain.food.dto.request.UpdateFoodDto;
import com.knu.salmon.api.domain.food.dto.response.FoodDetailResponseDto;
import com.knu.salmon.api.domain.food.dto.response.FoodMapNearResponseDto;
import com.knu.salmon.api.domain.food.dto.response.FoodOverviewResponseDto;
import com.knu.salmon.api.domain.food.entity.Food;
import com.knu.salmon.api.domain.food.repository.FoodRepository;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.member.repository.MemberRepository;
import com.knu.salmon.api.domain.shop.entity.Shop;
import com.knu.salmon.api.domain.shop.repository.ShopRepository;
import com.knu.salmon.api.domain.wish.repository.WishRepository;
import com.knu.salmon.api.global.error.custom.FoodException;
import com.knu.salmon.api.global.error.custom.MemberException;
import com.knu.salmon.api.global.error.errorcode.MemberErrorCode;
import com.knu.salmon.api.global.error.errorcode.custom.FoodErrorCode;
import com.knu.salmon.api.global.spec.response.ApiBasicResponse;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodService {
    private static final Logger log = LoggerFactory.getLogger(FoodService.class);
    private final FoodRepository foodRepository;
    private final MemberRepository memberRepository;
    private final FoodImageService foodImageService;
    private final FoodImageRepository foodImageRepository;
    private final ShopRepository shopRepository;
    private final WishRepository wishRepository;

    public ApiDataResponse<FoodDetailResponseDto> createFood(MultipartFile[] files, CreateFoodDto createFoodDto, PrincipalDetails principalDetails){
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));
        Shop shop = shopRepository.findByMemberId(member.getId());

        Food food = Food.builder()
                .title(createFoodDto.getTitle())
                .stock(createFoodDto.getStock())
                .price(createFoodDto.getPrice())
                .content(createFoodDto.getContent())
                .expiration(createFoodDto.getExpiration())
                .latitude(shop.getLatitude())
                .longitude(shop.getLongitude())
                .likesCount(0)
                .shop(shop)
                .trading(true)
                .build();
        foodRepository.save(food);

        shop.getFoodList().add(food);
        shopRepository.save(shop);

        foodImageService.uploadToBoardImages(files, food);

        List<String> list = food.getImages().stream().map(FoodImage::getImageUrl).toList();
        for (String s : list) {

            log.info("images urls: {}", s);
        }

        return ApiDataResponse.<FoodDetailResponseDto>builder()
                .status(true)
                .code(200)
                .message("글 생성을 성공! 추가 된 데이터는 다음과 같습니다")
                .data(FoodDetailResponseDto.fromFood(food, food.getShop()))
                .build();
    }

    public ApiDataResponse<FoodDetailResponseDto> getFoodDetail(Long foodId){
        Food food =  foodRepository.findById(foodId)
                .orElseThrow(() -> new FoodException(FoodErrorCode.NO_EXIST_FOOD_EXCEPTION));

        return ApiDataResponse.<FoodDetailResponseDto>builder()
                .status(true)
                .code(200)
                .message("글 불러오기 성공! 불러 온 데이터는 다음과 같습니다")
                .data(FoodDetailResponseDto.fromFood(food, food.getShop()))
                .build();
    }

    public ApiDataResponse<Map<String, List<FoodOverviewResponseDto>>> getFoodOverview(FoodMyLocationRequestDto dto){
        Map<String, List<FoodOverviewResponseDto>> map = new HashMap<>();
        List<Food> foodList = foodRepository.findAllWithShop();
        List<FoodOverviewResponseDto> dataList = new ArrayList<>();

        for (Food food : foodList) {
            LocalDateTime createdAt = food.getCreatedAt();
            String remainingTime = getTimeDifference(createdAt);
            double distance = calculateDistance(dto.getLatitude(), dto.getLongitude(), food.getShop().getLatitude(), food.getShop().getLongitude());
            //Boolean wish = wishRepository.existsByCustomerIdAndFoodId()

            dataList.add(FoodOverviewResponseDto.fromFood(food, remainingTime, distance));
        }

        // 1. 최신 순으로 정렬
        List<FoodOverviewResponseDto> latestData = dataList.stream()
                .sorted(Comparator.comparing(FoodOverviewResponseDto::getFoodId).reversed())
                .toList();

        // 2. 찜한 순으로 정렬 (likesCount를 기준으로 내림차순 정렬)
        List<FoodOverviewResponseDto> mostLikedData = dataList.stream()
                .sorted(Comparator.comparing(FoodOverviewResponseDto::getLikesCount).reversed())
                .toList();

        // 3. 가까운 순으로 정렬 (현재 사용자 위치와의 거리를 계산)
        List<FoodOverviewResponseDto> nearestData = dataList.stream()
                .sorted(Comparator.comparing(FoodOverviewResponseDto::getDistance))
                .toList();

        map.put("latestData", latestData);
        map.put("mostLikedData", mostLikedData);
        map.put("nearestData", nearestData);

        return ApiDataResponse.<Map<String, List<FoodOverviewResponseDto>>>builder()
                .status(true)
                .code(200)
                .message("음식 리스트 반환 성공! 불러 온 데이터는 다음과 같습니다")
                .data(map)
                .build();
    }

    public static String getTimeDifference(LocalDateTime createdAt) {
        // 현재 시간
        LocalDateTime now = LocalDateTime.now();

        // 두 시간 사이의 차이 계산
        Duration duration = Duration.between(createdAt, now);

        // 분 차이
        long minutes = duration.toMinutes();
        // 시간 차이
        long hours = duration.toHours();
        // 일 차이
        long days = duration.toDays();

        if (minutes < 60) {
            // 1시간 미만이면 분으로 표시
            return minutes + "분 전";
        } else if (hours < 24) {
            // 1시간 이상 24시간 미만이면 시간으로 표시
            return hours + "시간 전";
        } else {
            // 24시간 이상이면 일수로 표시
            return days + "일 전";
        }
    }

    public static double calculateDistance(double memberLat, double memberLng, double shopLat, double shopLng) {

        double EARTH_RADIUS = 6371; // 지구의 반지름 (단위: km)

        // 위도와 경도를 라디안으로 변환
        double latDistance = Math.toRadians(shopLat - memberLat);
        double lonDistance = Math.toRadians(shopLng - memberLng);

        // Haversine 공식 적용
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(memberLat)) * Math.cos(Math.toRadians(shopLat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 지구의 반지름을 곱하여 두 지점 사이의 거리(km) 계산
        return EARTH_RADIUS * c;
    }


    @Transactional
    public ApiDataResponse<FoodDetailResponseDto> updateFood(UpdateFoodDto updateFoodDto, MultipartFile[] newImageList, PrincipalDetails principalDetails, Long foodId){
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new FoodException(FoodErrorCode.NO_EXIST_FOOD_EXCEPTION));

        if(!food.getShop().getMember().equals(member)){
            throw new MemberException(MemberErrorCode.NO_OWNER_EXCEPTION);
        }

        food.getImages().clear();
        foodImageRepository.deleteAllByFood(food);

        foodImageRepository.deleteByFood(food);

        log.info("삭제 하려는 food Id {}" + food.getId());

        foodImageService.uploadToBoardImages(newImageList, food);

        food.updateFood(updateFoodDto);

        foodRepository.save(food);

        return ApiDataResponse.<FoodDetailResponseDto>builder()
                .status(true)
                .code(200)
                .message("음식 정보 업데이트 성공! 업데이트 한 이후 데이터는 다음과 같습니다")
                .data(FoodDetailResponseDto.fromFood(food, food.getShop()))
                .build();
    }


    @Transactional
    public ApiBasicResponse deleteFood(PrincipalDetails principalDetails, Long foodId) {
        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new FoodException(FoodErrorCode.NO_EXIST_FOOD_EXCEPTION));

        if(!food.getShop().getMember().equals(member)){
            throw new MemberException(MemberErrorCode.NO_OWNER_EXCEPTION);
        }

        foodRepository.delete(food);

        return ApiBasicResponse.builder()
                .status(true)
                .code(200)
                .message("음식 삭제 성공")
                .build();
    }

    public ApiDataResponse<List<FoodMapNearResponseDto>> getMapNear(FoodMapNearRequestDto foodMapNearRequestDto) {

        double neLat = foodMapNearRequestDto.getNeLat();
        double neLng = foodMapNearRequestDto.getNeLng();
        double swLat = foodMapNearRequestDto.getSwLat();
        double swLng = foodMapNearRequestDto.getSwLng();
        double userLat = foodMapNearRequestDto.getUserLat();
        double userLng = foodMapNearRequestDto.getUserLng();

        List<Food> foods = foodRepository.findFoodsInBoundsSortedByDistance(neLat, neLng, swLat, swLng, userLat, userLng);
        List<FoodMapNearResponseDto> responseDtos = foods.stream().map(food -> FoodMapNearResponseDto.fromFood(food)).toList();

        return ApiDataResponse.<List<FoodMapNearResponseDto>>builder()
                .status(true)
                .code(200)
                .message("가까운 순으로 데이터 리턴")
                .data(responseDtos)
                .build();
    }

    public ApiDataResponse<List<FoodMapNearResponseDto>> getFoodsInBox(FoodMapNearRequestDto foodMapNearRequestDto) {

        double neLat = foodMapNearRequestDto.getNeLat();
        double neLng = foodMapNearRequestDto.getNeLng();
        double swLat = foodMapNearRequestDto.getSwLat();
        double swLng = foodMapNearRequestDto.getSwLng();

        List<Food> foods = foodRepository.findFoodsInArea(neLat, neLng, swLat, swLng);
        List<FoodMapNearResponseDto> responseDtos = foods.stream().map(food -> FoodMapNearResponseDto.fromFood(food)).toList();

        return ApiDataResponse.<List<FoodMapNearResponseDto>>builder()
                .status(true)
                .code(200)
                .message("box안에 데이터 리턴")
                .data(responseDtos)
                .build();
    }
}
