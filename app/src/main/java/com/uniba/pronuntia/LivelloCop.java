package com.uniba.pronuntia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LivelloCop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LivelloCop extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LivelloCop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LivelloCop.
     */
    // TODO: Rename and change types and number of parameters
    public static LivelloCop newInstance(String param1, String param2) {
        LivelloCop fragment = new LivelloCop();
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

    private TextView titolo, contenuto;
    private ImageView immagine1, immagine2;
    private Button aiuto;
    private TextToSpeech tts;
    private int punteggio;
    private int canCLick = 3;
    private boolean isDone = false;
    private int numeroAiuti = 0;
    private int corretti = 0;
    private int sbagliati = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_livello_cop, container, false);

        titolo = view.findViewById(R.id.livello);
        contenuto = view.findViewById(R.id.contenuto);
        aiuto = view.findViewById(R.id.aiuto);
        String parola;

        Random random = new Random();
        int rand = random.nextInt(2);

        //Mescola casualmente le immagini
        immagine1 = view.findViewById(R.id.img1);
        immagine2 = view.findViewById(R.id.img2);

        ArrayList<byte[]> immagini = new ArrayList<>();

        if(getArguments() != null){

            titolo.setText(getArguments().getString("Titolo"));
            parola = getArguments().getString("Aiuto");
            contenuto.setText(getArguments().getString("Aiuto"));
            punteggio = getArguments().getInt("Punteggio");
            byte[] correctImage = getArguments().getByteArray("Immagine1");
            byte[] incorrectImage = getArguments().getByteArray("Immagine2");

            if(rand == 0) {
                immagini.add(correctImage);
                immagini.add(incorrectImage);
            }else{
                immagini.add(incorrectImage);
                immagini.add(correctImage);
            }


            Bitmap image = BitmapFactory.decodeByteArray(immagini.get(0), 0, immagini.get(0).length);
            immagine1.setImageBitmap(image);

            image = BitmapFactory.decodeByteArray(immagini.get(1), 0, immagini.get(1).length);
            immagine2.setImageBitmap(image);

            immagine1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer(immagini.get(0), correctImage, contenuto);
                }
            });

            immagine2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer(immagini.get(1), correctImage, contenuto);
                }
            });

            aiuto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(canCLick != 0){
                        tts = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
                            @Override
                            public void onInit(int i) {
                                if(i==TextToSpeech.SUCCESS){
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
                        numeroAiuti++;
                    }else{
                        Toast.makeText(getContext(), "Aiuti esauriti", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        // Inflate the layout for this fragment
        return view;
    }

    private void checkAnswer(byte[] selectedImageUrl, byte[] correctImage, TextView giudizio) {
        if (selectedImageUrl.equals(correctImage)) {
            giudizio.setText("Giusto!");
            punteggio +=  10;
            immagine1.setClickable(false);
            immagine2.setClickable(false);
            isDone = true;
            corretti++;
        } else {
            giudizio.setText("Sbagliato!");
            punteggio -= 3;
            immagine1.setClickable(false);
            immagine2.setClickable(false);
            isDone = true;
            sbagliati++;
        }

        passResultToActivity(punteggio, isDone, numeroAiuti, corretti, sbagliati);
    }

    private void passResultToActivity(int points, boolean isDone, int numeroAiuti, int corretti, int sbagliati) {
        if (getActivity() instanceof OnDataPassListener) {
            ((OnDataPassListener) getActivity()).onDataPass(points, isDone, numeroAiuti, corretti, sbagliati);
        }
    }
}