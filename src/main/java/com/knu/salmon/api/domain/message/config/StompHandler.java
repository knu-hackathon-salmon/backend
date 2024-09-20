package com.knu.salmon.api.domain.message.config;

import com.knu.salmon.api.auth.jwt.service.JwtService;
import com.knu.salmon.api.domain.chat.service.ChatService;
import com.knu.salmon.api.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class StompHandler implements ChannelInterceptor {

    private final JwtService jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("stompHandler message {}",message);
        String email = null;

        // CONNECT 명령어에 대해서만 로그를 출력
        if (accessor.getCommand() == StompCommand.CONNECT || accessor.getCommand() == StompCommand.SUBSCRIBE || accessor.getCommand() == StompCommand.SEND ) {


            String accesstoken = accessor.getFirstNativeHeader("Authorization");
            log.info("Authorization received: {}", accesstoken);

            if (accesstoken != null && !accesstoken.trim().isEmpty() && accesstoken.startsWith("Bearer ")) {
                String bearerToken = accesstoken.trim().substring(7);

                if (jwtService.validateToken(bearerToken)) {
                    email = jwtService.getEmail(bearerToken);


                    accessor.addNativeHeader("senderEmail", email);


                   } else {


                    log.info("Invalid access token.");
                }
            } else {
                log.info("Invalid token format.");
            }
        }


        return message;
    }

}