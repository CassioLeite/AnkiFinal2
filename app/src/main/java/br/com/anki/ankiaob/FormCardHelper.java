package br.com.anki.ankiaob;

import android.widget.EditText;
import android.widget.Spinner;

import br.com.anki.ankiaob.model.Card;

/**
 * Created by cassio on 18/06/2017.
 */

public class FormCardHelper {

    private final Spinner formDeckId;
    private final EditText formCardFront;
    private final EditText formCardBack;


    public FormCardHelper(FormCard activity){
        formDeckId = (Spinner) activity.findViewById(R.id.spn_deck_id);
        formCardFront = (EditText) activity.findViewById(R.id.card_front);
        formCardBack = (EditText) activity.findViewById(R.id.card_back);
    }

    public Card getCard() {
        Card card = new Card();
        card.setDeckId(formDeckId.getSelectedItemId());
        card.setCardFront(formCardFront.getText().toString());
        card.setCardBack(formCardBack.getText().toString());

        return card;
    }
}
