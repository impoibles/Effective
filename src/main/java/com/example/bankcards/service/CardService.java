package com.example.bankcards.service;


import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.dto.TransferRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.InsufficientFundsException;
import com.example.bankcards.exception.TransferException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.CardRepo;
import com.example.bankcards.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;

@Service
public class CardService {

    private final CardRepo cardRepo;
    private final UserRepo userRepo;
    private final CardMapper cardMapper;
    @Autowired
    CardService(CardRepo cardRepo, UserRepo userRepo, CardMapper cardMapper){
        this.cardRepo=cardRepo;
        this.userRepo = userRepo;
        this.cardMapper = cardMapper;
    }

    @Transactional
    public CardDTO.CardResponse createCard(CardDTO.CardRequest request) {
        Card card = cardMapper.toEntity(request); // Использование маппера
        Card savedCard = cardRepo.save(card);
        return cardMapper.toResponse(savedCard);
    }

    @Transactional()
    public Card getCardById(Long id) {
        return cardRepo.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card not found with id: " + id));
    }

    // Read (all)
    @Transactional()
    public List<Card> getAllCards() {
        return cardRepo.findAll();
    }

    // Update
    @Transactional
    public Card updateCard(Long id, Card updatedCard) {
        Card existingCard = getCardById(id);
        existingCard.setNumber(updatedCard.getNumber());
        existingCard.setStatus(updatedCard.getStatus());
        existingCard.setDate_out(updatedCard.getDate_out());
        existingCard.setBalance(updatedCard.getBalance());
        existingCard.setUser(existingCard.getUser());

        return cardRepo.save(existingCard);
    }

    // Delete
    @Transactional
    public void deleteCard(Long id) {
        cardRepo.deleteById(id);
    }
    @Transactional
    public void transferBetweenCards(TransferRequest request) {
        Card fromCard = cardRepo.findById(request.getFromCardId())
                .orElseThrow(() -> new CardNotFoundException("Карта отправителя не найдена"));
        Card toCard = cardRepo.findById(request.getToCardId())
                .orElseThrow(() -> new CardNotFoundException("Карта получателя не найдена"));

        // Проверяем, что карты принадлежат одному пользователю
        if (fromCard.getUser().getId()!=(toCard.getUser().getId())) {
            throw new TransferException("Карты принадлежат разным пользователям");
        }

        // Проверяем достаточность средств
        if (fromCard.getBalance() - request.getAmount() < 0) {
            throw new InsufficientFundsException("Недостаточно средств на карте");
        }


        fromCard.setBalance(fromCard.getBalance() - request.getAmount());
        toCard.setBalance(toCard.getBalance() + request.getAmount());

        cardRepo.save(fromCard);
        cardRepo.save(toCard);
    }
}
