package br.com.anki.ankiaob.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.anki.ankiaob.model.Deck;

/**
 * Created by cassio on 16/06/2017.
 */

public class DeckDAO extends SQLiteOpenHelper {

    public DeckDAO(Context context) {
        super(context, "Anki", null, 13);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Decks (deckId INTEGER PRIMARY KEY AUTOINCREMENT, deckName TEXT NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Decks";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insertDeck(Deck deck) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = new ContentValues();
        data.put("deckName", deck.getDeckName());
        db.insert("Decks", null, data);
    }

    public List<Deck> findDecks() {
        String sql = "SELECT * FROM Decks;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Deck> decks = new ArrayList<Deck>();
        while (c.moveToNext()){
            Deck deck = new Deck();
            deck.setDeckId(c.getLong(c.getColumnIndex("deckId")));
            deck.setDeckName(c.getString(c.getColumnIndex("deckName")));
            decks.add(deck);
        }
        c.close();

        return decks;
    }

    public void deleteDeck(Deck deck) {
        SQLiteDatabase db = getWritableDatabase();
        String [] params = {deck.getDeckId().toString()};
        db.delete("Decks", "deckId = ?", params);
    }
}
