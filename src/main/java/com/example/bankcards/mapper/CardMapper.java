package com.example.bankcards.mapper;

import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.entity.Card;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {
    CardDTO.CardResponse toResponse(Card card);
    Card toEntity(CardDTO.CardRequest request);
}
