package com.knu.salmon.api.domain.message.MessageHandler;


import com.knu.salmon.api.auth.jwt.service.JwtService;
import com.knu.salmon.api.domain.member.entity.Member;
import com.knu.salmon.api.domain.member.repository.MemberRepository;
import com.knu.salmon.api.domain.message.dto.MessageDto;
import com.knu.salmon.api.domain.message.dto.MessagePayLoad;
import com.knu.salmon.api.domain.message.service.MessageService;
import com.knu.salmon.api.global.error.custom.MemberException;
import com.knu.salmon.api.global.error.errorcode.MemberErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Tag(name = "메세지 핸들러", description = "stomp(websocket)을 통해 받은 메세지를 처리하는 핸들러")
@RequiredArgsConstructor
@Slf4j
public class MessageHandler {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @MessageMapping("/chat/private/{chatId}")
    @Operation(summary = "채팅룸의 메세지 처리", description = "채팅룸의 메세지를 처리하는 로직")
    public void handleMessage(@DestinationVariable("chatId") Long chatId,
                                     @Payload MessagePayLoad messagePayLoad) {

        log.info("message handler {} ", messagePayLoad.content());
        String senderEmail =jwtService.getEmail(messagePayLoad.Authorization());

        log.info("message handler , Authorization {} ", messagePayLoad.Authorization());
        Member member = memberRepository.findByEmail(senderEmail).orElseThrow(()-> new MemberException(MemberErrorCode.No_EXIST_EMAIL_MEMBER_EXCEPTION));
        Long senderId = member.getId();

        MessageDto messageDto = messageService.addMessage(chatId, senderId, messagePayLoad.content());
        log.info("messageDto {} ", messageDto);

        sendMessage(chatId, messageDto);
    }

    private void sendMessage(Long chatId, MessageDto messageDto) {
        messagingTemplate.convertAndSend("/topic/chat/private/" + chatId, messageDto);
    }
}
