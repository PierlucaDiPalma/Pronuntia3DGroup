package com.uniba.pronuntia;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LivelloDen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LivelloDen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LivelloDen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LivelloDen.
     */
    // TODO: Rename and change types and number of parameters
    public static LivelloDen newInstance(String param1, String param2) {
        LivelloDen fragment = new LivelloDen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ImageView immagine;
    private String parola;
    private Button aiuto, parla;
    private TextView titolo, contenuto;
    private int punteggio;
    private int canClick = 3;
    private TextToSpeech tts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_livello_den, container, false);

        immagine = view.findViewById(R.id.imageEx);
        contenuto = view.findViewById(R.id.contenuto);
        aiuto = view.findViewById(R.id.playButton);
        parla = view.findViewById(R.id.speakButton);
        titolo = view.findViewById(R.id.livello);

        if(getArguments() != null){
            titolo.setText(getArguments().getString("Titolo"));
            punteggio = getArguments().getInt("Punteggio");
            byte[] byteArray = getArguments().getByteArray("Immagine");
            Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            immagine.setImageBitmap(image);

            parola = getArguments().getString("Aiuto");

        }

        parla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak(view);
            }
        });

        aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canClick != 0) {
                    tts = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int i) {
                            if (i == TextToSpeech.SUCCESS) {
                                tts.setLanguage(Locale.ITALIAN);
                                tts.setSpeechRate(1f);

                                for (Voice voice : tts.getVoices()) {
                                    if (voice.getName().contains("it-it-x") && voice.getName().contains("male")) {
                                        tts.setVoice(voice);
                                        break;
                                    }
                                }


                                tts.speak(parola.toString(), TextToSpeech.QUEUE_ADD, null);
                            }
                        }
                    });
                    canClick--;
                }else{
                    Toast.makeText(getContext(), "Aiuti esauriti", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }




    public void speak(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Parla");
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK){
            String input = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
            if(input.toUpperCase().equals(parola.toUpperCase())) {
                contenuto.setText("Giusto");
                punteggio += 10;

            }else{
                contenuto.setText("Sbagliato");
                punteggio-=3;

            }
            passResultToActivity(punteggio);
        }
    }


    private void passResultToActivity(int points) {
        if (getActivity() instanceof OnDataPassListener) {
            ((OnDataPassListener) getActivity()).onDataPass(points);
        }
    }
}