package com.knu.salmon.api.domain.chat.service;

import com.knu.salmon.api.domain.Image.entity.FoodImage;
import com.knu.salmon.api.domain.Image.repository.FoodImageRepository;
import com.knu.salmon.api.domain.chat.dto.ChatDto;
import com.knu.salmon.api.domain.chat.entity.Chat;
import com.knu.salmon.api.domain.chat.repository.ChatRepository;
import com.knu.salmon.api.domain.customer.entity.Customer;
import com.knu.salmon.api.domain.food.entity.Food;
import com.knu.salmon.api.domain.food.repository.FoodRepository;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.member.entity.type.MemberType;
import com.knu.salmon.api.domain.member.repository.MemberRepository;
import com.knu.salmon.api.domain.message.dto.MessageDto;
import com.knu.salmon.api.domain.message.service.MessageService;
import com.knu.salmon.api.domain.shop.entity.Shop;
import com.knu.salmon.api.domain.shop.repository.ShopRepository;
import com.knu.salmon.api.global.error.custom.FoodException;
import com.knu.salmon.api.global.error.custom.MemberException;
import com.knu.salmon.api.global.error.custom.ShopException;
import com.knu.salmon.api.global.error.errorcode.MemberErrorCode;
import com.knu.salmon.api.global.error.errorcode.custom.FoodErrorCode;
import com.knu.salmon.api.global.error.errorcode.custom.ShopErrorCode;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;
    private final ShopRepository shopRepository;
    private final FoodRepository foodRepository;
    private final MessageService messageService;
    private final FoodImageRepository foodImageRepository;
    public ApiDataResponse createChat(Long foodId, Long customerId, PrincipalDetails principalDetails) {

        // Find food
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new FoodException(FoodErrorCode.NO_EXIST_FOOD_EXCEPTION));


        // Get customer (the person requesting the chat)
        Customer customer = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION)).getCustomer();

        if(customer.getId() != customerId) {
            throw new IllegalArgumentException("고객 ID가 일치하지 않습니다. 잘못된 요청입니다.");
        }
        // Find shop
        Shop shop = shopRepository.findById(foodId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.NO_EXIST_SHOP_EXCEPTION));

        // Find existing chat or create new chat
        Optional<Chat> chatOptional = chatRepository.findByShopAndCustomer(shop, customer);


        if (chatOptional.isPresent()) {

            Chat chat = chatOptional.get();
            List<MessageDto> messages = messageService.getPreviousMessages(chat);

            return ApiDataResponse.builder()
                    .status(true)
                    .code(200)
                    .message("이전 메시지 불러오기 성공!")
                    .data(messages)
                    .build();
        } else {
            // No existing chat, create a new one
            Chat newChat = Chat.builder()
                    .shop(shop)
                    .customer(customer)
                    .food(food)
                    .build();

            chatRepository.save(newChat);

            return ApiDataResponse.builder()
                    .status(true)
                    .code(200)
                    .message("새 채팅룸 생성 성공!")
                    .data(null)
                    .build();
        }
    }

    public ApiDataResponse<List<ChatDto>> getAllChatsByMember(PrincipalDetails principalDetails) {

        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        List<Chat> chatList = new ArrayList<>();

        if(member.getMemberType() == MemberType.SHOP){
            chatList = chatRepository.findAllByShop(member.getShop());
        } else{
            chatList = chatRepository.findAllByCustomer(member.getCustomer());
        }

        List<ChatDto> responseDtoList = new ArrayList<>();

        for(Chat chat: chatList){
            Food food  = chat.getFood();
            FoodImage foodImage = foodImageRepository.findAllByFoodId(food.getId()).get(0);
            String imageUrl = foodImage.getImageUrl();

            String opponentName ;
            if(member.getMemberType().equals(MemberType.SHOP)){
                opponentName = chat.getCustomer().getNickname();
            }
            else{
                opponentName = chat.getShop().getShopName();
            }

            ChatDto chatDto = ChatDto.builder()
                    .foodId(food.getId())
                    .chatId(chat.getChatId())
                    .foodTitle(food.getTitle())
                    .opponentName(opponentName)
                    .imageUrl(imageUrl)
                    .build();
            responseDtoList.add(chatDto);

        }

        return ApiDataResponse.<List<ChatDto>>builder()
                .status(true)
                .code(200)
                .message("채팅 리스트 반환 성공!")
                .data(responseDtoList)
                .build();
    }



}
