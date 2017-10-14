package br.com.anki.ankiaob.model;

import java.io.Serializable;

/**
 * Created by cassio on 18/06/2017.
 */

public class Card implements Serializable{

    private Long cardId;
    private Long deckId;
    private String cardFront;
    private String cardBack;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    public String getCardFront() {
        return cardFront;
    }

    public void setCardFront(String cardFront) {
        this.cardFront = cardFront;
    }

    public String getCardBack() {
        return cardBack;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
    }


}
