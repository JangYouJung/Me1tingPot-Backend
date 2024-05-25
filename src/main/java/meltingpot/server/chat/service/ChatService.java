package meltingpot.server.chat.service;

import lombok.RequiredArgsConstructor;
import meltingpot.server.chat.dto.ChatMessageCreateRequest;
import meltingpot.server.chat.dto.ChatMessageCreateResponse;
import meltingpot.server.domain.entity.chat.ChatMessage;
import meltingpot.server.domain.entity.chat.ChatRoom;
import meltingpot.server.domain.repository.chat.ChatMessageRepository;
import meltingpot.server.domain.repository.chat.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public ChatMessageCreateResponse createChatMessage(Long chatRoomId, ChatMessageCreateRequest request) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("error"));

        ChatMessage newChatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .content(request.content())
                .userId(request.userId())
                .build();

        ChatMessage chatMessageEntity = chatMessageRepository.save(newChatMessage);
        return new ChatMessageCreateResponse(chatMessageEntity.getId());
    }


}
