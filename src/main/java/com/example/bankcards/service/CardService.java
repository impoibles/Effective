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

    @Autowired
    CardService(CardRepo cardRepo, UserRepo userRepo){
        this.cardRepo=cardRepo;
        this.userRepo = userRepo;

    }

    @Transactional
    public Card createCard(CardDTO.CardRequest request) {
        // Находим пользователя
        User user = userRepo.findById(request.getUser_id())
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        // Создаем карту
        Card card = new Card();
        card.setNumber(request.getNumber());
        card.setBalance(request.getBalance());
        card.setDate_out(request.getDate_out());
        card.setStatus(request.getStatus());
        card.setUser(user); // Связываем с пользователем

        return cardRepo.save(card);
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
    public Card updateCard(Long id,CardDTO.CardRequest cardRequest) {

        Card existingCard = getCardById(id);
        existingCard.setNumber(cardRequest.getNumber());
        existingCard.setStatus(cardRequest.getStatus());
        existingCard.setDate_out(cardRequest.getDate_out());
        existingCard.setBalance(cardRequest.getBalance());


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
