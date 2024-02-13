package com.example.lab1.controller;


import com.example.lab1.dto.CardDTO;
import com.example.lab1.entity.Card;
import com.example.lab1.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController()
@AllArgsConstructor
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    @PostMapping()
    public ResponseEntity<Card> create(@RequestBody CardDTO cardDTO) {
        Card card;
        try {
            card = cardService.create(cardDTO);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Card>> readAll() {
        return new ResponseEntity<>(cardService.findAll(), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Card> update(@RequestBody Card card) {
        return new ResponseEntity<>(cardService.update(card), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        cardService.delete(id);
        return HttpStatus.OK;
    }
}
