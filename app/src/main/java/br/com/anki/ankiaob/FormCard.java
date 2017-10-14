package br.com.anki.ankiaob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.anki.ankiaob.dao.CardDAO;
import br.com.anki.ankiaob.model.Card;
import br.com.anki.ankiaob.model.Deck;

public class FormCard extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ArrayList<Deck> deckList;
    private FormCardHelper cardHelper;
    private Spinner deckSpinner;
    private Long idSelected;

    private EditText formCardFront;
    private EditText formCardBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_card);

        cardHelper = new FormCardHelper(this);
        formCardFront = (EditText) findViewById(R.id.card_front);
        formCardBack = (EditText) findViewById(R.id.card_back);

        Bundle bundleObject = getIntent().getExtras();
        deckList = (ArrayList<Deck>) bundleObject.getSerializable("BARALHOS");

        deckSpinner = (Spinner) findViewById(R.id.spn_deck_id);

        deckSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<Deck> decksAdapter = new ArrayAdapter<Deck>(this, android.R.layout.simple_spinner_item, deckList);
        decksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        deckSpinner.setAdapter(decksAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.menu_form_card, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_form_card_ok:
                Card card = new Card();
                card.setDeckId(idSelected);
                card.setCardFront(formCardFront.getText().toString());
                card.setCardBack(formCardBack.getText().toString());
                //Card card = cardHelper.getCard();
                CardDAO dao = new CardDAO(FormCard.this);
                dao.insertCard(card);
                dao.close();

                Toast.makeText(FormCard.this, "IdCARTA: " + card.getCardId() + "IdBaralho: " + card.getDeckId() ,Toast.LENGTH_LONG).show(); // pop up como o nome "Botão clicado!"

                finish(); // usado pra matar a activity, por que se usarmos o intent ele vai dar problemas se o usuario voltar para a tela anterior.
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Deck deck = (Deck) parent.getSelectedItem();
        idSelected = deck.getDeckId();

        Toast.makeText(FormCard.this, "Id: " + idSelected ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
