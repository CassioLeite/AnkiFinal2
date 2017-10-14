package br.com.anki.ankiaob;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.anki.ankiaob.dao.CardDAO;
import br.com.anki.ankiaob.model.Card;
import br.com.anki.ankiaob.model.Deck;

public class ShowCards extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<Card> cardList;
    private Button showAnswer;
    private Button goToNextQuestion;
    private RelativeLayout firstGroup;
    private RelativeLayout secondGroup;
    private TextView question;
    private TextView answer;
    int aleatorio;
    Long idBaralho;
    private boolean show = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cards);

        Bundle bundleObject = getIntent().getExtras();
        //cardList = (ArrayList<Card>) bundleObject.getSerializable("ID");

        idBaralho = (Long) bundleObject.getSerializable("ID");

        showAnswer = (Button) findViewById(R.id.btn_show_answer);
        showAnswer.setOnClickListener(this);

        goToNextQuestion = (Button) findViewById(R.id.btn_go_to_next_question);
        goToNextQuestion.setOnClickListener(this);

        firstGroup = (RelativeLayout) findViewById(R.id.layout_show_answer);
        secondGroup = (RelativeLayout) findViewById(R.id.layout_next_card);

        question = (TextView) findViewById(R.id.question);
        answer = (TextView) findViewById(R.id.answer);

        if(show){
            secondGroup.setVisibility(View.VISIBLE);
        }else{
            firstGroup.setVisibility(View.INVISIBLE);
        }

        loadCard();




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_show_answer:
                firstGroup.setVisibility(View.VISIBLE);
                secondGroup.setVisibility(View.INVISIBLE);
                show = true;
                break;
            case R.id.btn_go_to_next_question:
                firstGroup.setVisibility(View.INVISIBLE);
                secondGroup.setVisibility(View.VISIBLE);
                show = false;
                loadCard();
                break;
        }
    }

    private void loadCard(){
        Random random = new Random();
        aleatorio = random.nextInt(2);

        Card card = new CardDAO(this).findCardById(aleatorio, idBaralho);
        question.setText("Art. 157");
        answer.setText(" Subtrair coisa móvel alheia, para si ou para outrem, mediante grave ameaça ou violencia a pessoa, ou depois de have-la, por qualquer meio, reduzido a impossibilidade de resistencia.");

    }
}
