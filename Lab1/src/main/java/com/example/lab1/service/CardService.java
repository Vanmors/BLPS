package com.example.lab1.service;

import com.example.lab1.dto.CardDTO;
import com.example.lab1.entity.Card;
import com.example.lab1.entity.Person;
import com.example.lab1.repository.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardService {

    private final CardRepository repository;


    public Card create(CardDTO cardDTO) {
        Card card = Card.builder().
                personId(cardDTO.getPersonId()).
                numberOfCard(cardDTO.getNumberOfCard()).
                build();
        return repository.save(card);
    }

    public List<Card> findAll() {
        return repository.findAll();
    }

    public Card update(Card card) {
        return repository.save(card);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
