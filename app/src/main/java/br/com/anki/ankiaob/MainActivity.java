package br.com.anki.ankiaob;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.anki.ankiaob.dao.CardDAO;
import br.com.anki.ankiaob.dao.DeckDAO;
import br.com.anki.ankiaob.model.Card;
import br.com.anki.ankiaob.model.Deck;

public class MainActivity extends AppCompatActivity implements Serializable{


    // *** Animação INICIO ***
    FloatingActionButton add, add_deck, add_card;
    Animation FabOpen, FabClose;
    boolean isOpen = false;
    // *** Animação FIM ***
    private ListView deckList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Card c1 = new Card();
        c1.setCardId(Long.valueOf(1));
        c1.setDeckId(Long.valueOf(1));
        c1.setCardFront("Teste Frente 1");
        c1.setCardFront("Teste Verso 1");

        CardDAO dao1 = new CardDAO(MainActivity.this);
        dao1.insertCard(c1);

        Card c2 = new Card();
        c2.setCardId(Long.valueOf(2));
        c2.setDeckId(Long.valueOf(1));
        c2.setCardFront("Teste Frente 2");
        c2.setCardFront("Teste Verso 2");

        CardDAO dao2 = new CardDAO(MainActivity.this);
        dao1.insertCard(c2);

        deckList = (ListView) findViewById(R.id.list_decks);
        deckList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Implementação do clique na lista aqui

                Deck deck = (Deck) deckList.getItemAtPosition(position);
                Long idSelected = deck.getDeckId();
                //CardDAO dao = new CardDAO(MainActivity.this);
                //List<Card> cardList = dao.getAllCards(idSelected);
                //dao.close();

                Toast.makeText(MainActivity.this, idSelected + " clicado", Toast.LENGTH_LONG).show();


                Intent intentGoToShowCards = new Intent(MainActivity.this, ShowCards.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ID", (Serializable) idSelected);
                intentGoToShowCards.putExtras(bundle);
                startActivity(intentGoToShowCards);
            }
        });


        // *** Animação INICIO ***
        add = (FloatingActionButton)findViewById(R.id.action_add);
        add_card = (FloatingActionButton)findViewById(R.id.action_add_card);
        add_deck = (FloatingActionButton)findViewById(R.id.action_add_deck);
        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        // *** Animação FIM ***

        // *** Botão principal ***
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpen){
                    add_deck.startAnimation(FabClose);
                    add_card.startAnimation(FabClose);
                    add_deck.setClickable(false);
                    add_card.setClickable(false);
                    isOpen = false;
                }else{
                    add_deck.startAnimation(FabOpen);
                    add_card.startAnimation(FabOpen);
                    add_deck.setClickable(true);
                    add_card.setClickable(true);
                    isOpen = true;
                }
            }
        });


        // *** Botão de adicionar um CARTA ***
        add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ( 1 ) *** Ir para outra activity ***

                DeckDAO dao = new DeckDAO(MainActivity.this);
                List<Deck> decks = dao.findDecks();
                dao.close();

                Intent intentGoToFormCard = new Intent(MainActivity.this, FormCard.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("BARALHOS", (Serializable) decks);
                intentGoToFormCard.putExtras(bundle);
                startActivity(intentGoToFormCard);
            }
        });

        // *** Botão de adicionar um BARALHO ***
        add_deck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_form_deck, null);
                final EditText deckName = (EditText) view.findViewById(R.id.form_deck_name);

                //final FormDeckHelper deckHelper = new FormDeckHelper(deckName);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Create Deck")
                        .setView(view)
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // **** Ação de adicionar Baralho ****
                                Deck deck = new Deck();
                                String name = deckName.getText().toString();
                                deck.setDeckName(name);
                                DeckDAO dao = new DeckDAO(MainActivity.this);
                                dao.insertDeck(deck);
                                dao.close();
                                loadList();

                                Toast.makeText(MainActivity.this, deck.getDeckName() + " added!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .setCancelable(false);

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        registerForContextMenu(deckList);
    }

    private void loadList(){
        DeckDAO dao = new DeckDAO(this);
        List<Deck> decks = dao.findDecks();
        dao.close();
        ArrayAdapter<Deck> adapter = new ArrayAdapter<Deck>(this, android.R.layout.simple_list_item_1, decks);
        deckList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem delete = menu.add("Deletar");
        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Deck deck = (Deck) deckList.getItemAtPosition(info.position);
                DeckDAO dao = new DeckDAO(MainActivity.this);
                dao.deleteDeck(deck);
                dao.close();
                loadList();
                return false;
            }
        });
    }
}

