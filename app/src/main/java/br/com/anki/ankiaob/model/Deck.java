package br.com.anki.ankiaob.model;

import java.io.Serializable;

/**
 * Created by cassio on 16/06/2017.
 */

public class Deck implements Serializable{
    private Long deckId;
    private String deckName;

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    @Override
    public String toString() {
        return getDeckId() + " - " +getDeckName();
    }
}
