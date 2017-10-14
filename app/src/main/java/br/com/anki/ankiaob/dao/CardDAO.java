package br.com.anki.ankiaob.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.anki.ankiaob.model.Card;
import br.com.anki.ankiaob.model.Deck;
import br.com.anki.ankiaob.model.MaiorEMenorId;

/**
 * Created by cassio on 18/06/2017.
 */

public class CardDAO extends SQLiteOpenHelper {
    public CardDAO(Context context) {
        super(context, "Anki", null, 13);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Cards (cardId INTEGER, deckId INTEGER NOT NULL, cardFront TEXT NOT NULL, cardBack TEXT NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Cards";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insertCard(Card card) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = new ContentValues();
        data.put("deckId", card.getDeckId());
        data.put("cardFront", card.getCardFront());
        data.put("cardBack", card.getCardBack());
        db.insert("Cards", null, data);

    }

    public List<Card> getAllCards(Long idSelected) {

        String sql = "SELECT * FROM Cards WHERE deckId = ?;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(idSelected)});

        List<Card> cards = new ArrayList<Card>();
        while (c.moveToNext()){
            Card card = new Card();
            card.setCardId(c.getLong(c.getColumnIndex("cardId")));
            card.setDeckId(c.getLong(c.getColumnIndex("deckId")));
            card.setCardFront(c.getString(c.getColumnIndex("cardFront")));
            card.setCardBack(c.getString(c.getColumnIndex("cardBack")));

            cards.add(card);
        }
        c.close();

        return cards;
    }

    public List<Long> getAllIdCards(Long idSelected) {

        String sql = "SELECT cardId FROM Cards WHERE deckId = ?;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(idSelected)});

        List<Long> ids = new ArrayList<Long>();
        while (c.moveToNext()){
            ids.add(c.getLong(c.getColumnIndex("cardId")));
        }
        c.close();

        return ids;
    }

    public Card findCardById(int idCarta, Long idBaralho){
        String sql = "SELECT * FROM Cards WHERE cardId = ? AND deckId = ?;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(idCarta), String.valueOf(idBaralho)});
        Card card = new Card();

        while (c.moveToNext()){
            card.setCardId(c.getLong(c.getColumnIndex("cardId")));
            card.setDeckId(-c.getLong(c.getColumnIndex("deckId")));
            card.setCardFront(c.getString(c.getColumnIndex("cardFront")));
            card.setCardBack(c.getString(c.getColumnIndex("cardBack")));
        }
        c.close();

        return card;

    }

}
