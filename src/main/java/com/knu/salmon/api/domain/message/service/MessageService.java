package com.knu.salmon.api.domain.message.service;


import com.knu.salmon.api.domain.Image.entity.FoodImage;
import com.knu.salmon.api.domain.chat.dto.ChatDto;
import com.knu.salmon.api.domain.chat.entity.Chat;
import com.knu.salmon.api.domain.chat.repository.ChatRepository;
import com.knu.salmon.api.domain.chat.service.ChatService;
import com.knu.salmon.api.domain.food.dto.response.FoodOverviewResponseDto;
import com.knu.salmon.api.domain.food.entity.Food;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.member.entity.PrincipalDetails;
import com.knu.salmon.api.domain.member.repository.MemberRepository;
import com.knu.salmon.api.domain.member.service.MemberService;
import com.knu.salmon.api.domain.message.dto.MessageDto;
import com.knu.salmon.api.domain.message.entity.Message;
import com.knu.salmon.api.domain.message.repository.MessageRepository;
import com.knu.salmon.api.global.error.custom.ChatException;
import com.knu.salmon.api.global.error.custom.MemberException;
import com.knu.salmon.api.global.error.errorcode.MemberErrorCode;
import com.knu.salmon.api.global.error.errorcode.custom.ChatErrorCode;
import com.knu.salmon.api.global.spec.response.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final ChatRepository chatRepository;

    public List<MessageDto> getPreviousMessages(Chat chat){
        List<MessageDto> responseDtoList = new ArrayList<>();

        List<Message> messageList = messageRepository.findAllByChat(chat);

        for(Message message : messageList){
            MessageDto messageDto = MessageDto.builder()
                    .messageId(message.getId())
                    .chatId(chat.getChatId())
                    .createTime(message.getCreatedAt())
                    .senderEmail(message.getSender().getEmail())
                    .content(message.getContent())
                    .build();

            responseDtoList.add(messageDto);
        }

        return responseDtoList;
    }

    public ApiDataResponse<List<MessageDto>> getMessages(Long chatId, PrincipalDetails principalDetails){

        Member member = memberRepository.findByEmail(principalDetails.getEmail())
                .orElseThrow(() -> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));

        // chatId에 해당하는 채팅을 가져옴
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException(ChatErrorCode.NO_EXIST_CHAT_EXCEPTION));

        // 해당 채팅의 고객이나 상점의 회원이 현재 로그인된 회원과 동일한지 확인
        if (!chat.getCustomer().getMember().equals(member) &&
                !chat.getShop().getMember().equals(member)) {
            throw new MemberException(MemberErrorCode.NO_OWNER_EXCEPTION); // 예외 처리
        }

        List<MessageDto> responseDtoList = new ArrayList<>();
        List<Message> messageList = messageRepository.findAllByChat(chat);

        for (Message message : messageList) {
            MessageDto messageDto = MessageDto.builder()
                    .messageId(message.getId())
                    .chatId(chatId)
                    .createTime(message.getCreatedAt())
                    .senderEmail(message.getSender().getEmail())
                    .content(message.getContent())
                    .build();

            responseDtoList.add(messageDto);
        }

        return ApiDataResponse.<List<MessageDto>>builder()
                .status(true)
                .code(200)
                .message("메시지 리스트 반환 성공!")
                .data(responseDtoList)
                .build();
    }




    public MessageDto addMessage(Long chatId, Long senderId, String content) {
        Chat chat = chatRepository.getById(chatId);
        Member member = memberRepository.getById(senderId);

        Message message = Message.builder()
                .sender(member)
                .chat(chat)
                .content(content)
                .build();

        Message newMessage = messageRepository.save(message);

        return MessageDto.builder()
                .messageId(message.getId())
                .chatId(message.getId())
                .createTime(newMessage.getCreatedAt())
                .content(newMessage.getContent())
                .senderEmail(member.getEmail())
                .build();
    }

}
