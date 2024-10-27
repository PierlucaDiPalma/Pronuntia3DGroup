package com.uniba.pronuntia;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
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
    private Button aiuto, parla, record;
    private TextView titolo, contenuto;
    private int punteggio;
    private int canClick = 3;
    private TextToSpeech tts;
    private boolean isDone = false;
    private int numeroAiuti = 0;
    private int corretti = 0;
    private int sbagliati = 0;
    private Esercizio esercizio;
    private boolean isRecording = false;
    private MediaRecorder mediaRecorder;
    private String audioFilePath;

    private DBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_livello_den, container, false);

        immagine = view.findViewById(R.id.imageEx);
        contenuto = view.findViewById(R.id.contenuto);
        aiuto = view.findViewById(R.id.playButton);
        parla = view.findViewById(R.id.speakButton);
        record = view.findViewById(R.id.recordButton);
        titolo = view.findViewById(R.id.livello);

        db = new DBHelper(getActivity());

        if(getArguments() != null){
            /*titolo.setText(getArguments().getString("Titolo"));
            byte[] byteArray = getArguments().getByteArray("Immagine");
            Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            immagine.setImageBitmap(image);


            parola = getArguments().getString("Aiuto");
*/
            punteggio = getArguments().getInt("Punteggio");
            esercizio = getArguments().getParcelable("esercizio");
            titolo.setText(esercizio.getName());
            parola = esercizio.getAiuto();
            String imagePath = esercizio.getImmagine1();

            Log.d("LivelloDen", "PATH: " + imagePath);

            if (imagePath != null) {
                immagine.setImageURI(Uri.parse(imagePath));
            }
        }

        parla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(audioFilePath!=null){
                    speak(view);
                }else{
                    Toast.makeText(getActivity(), "Registra prima la risposta", Toast.LENGTH_SHORT).show();
                }

            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
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
                    numeroAiuti++;
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

    private void startRecording() {
        // Definisci un nome personalizzato per il file audio
        String fileName = "registrazione_personalizzata_" + System.currentTimeMillis() + ".3gp";
        audioFilePath = getActivity().getExternalFilesDir(null).getAbsolutePath() + "/" + fileName;

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(audioFilePath);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            record.setText("Interrompi registrazione");

            record.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopRecording();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        isRecording = false;
        record.setText("Registra");

        // Salva il percorso nel database
        db.saveAudio(audioFilePath);
        Toast.makeText(getActivity(), "Audio salvato nel database", Toast.LENGTH_SHORT).show();

/*
        new Handler().postDelayed(() -> {
            MediaPlayer player = new MediaPlayer();
            try {
                player.setDataSource(audioFilePath);
                player.prepare();
                player.start();
                Toast.makeText(getActivity(), "Riproduzione in corso", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.e("Playback Error", "Errore durante la riproduzione dell'audio", e);
            }
        }, 1000);*/
    }

    @Override
    public void onDestroy() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK){
            String input = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);

            if(input.toUpperCase().equals(parola.toUpperCase())) {
                contenuto.setText("Giusto");
                punteggio += 10;
                isDone = true;
                corretti++;
            }else{
                contenuto.setText("Sbagliato");
                punteggio-=3;
                isDone = true;
                sbagliati++;
            }

            parla.setEnabled(false);
            passResultToActivity(punteggio, isDone, numeroAiuti, corretti, sbagliati, audioFilePath);
        }
    }


    private void passResultToActivity(int points, boolean isDone, int numeroAiuti, int corretti, int sbagliati, String path) {
        if (getActivity() instanceof OnDataPassListener) {
            ((OnDataPassListener) getActivity()).onDataPass(points, isDone, numeroAiuti, corretti, sbagliati, path);
        }
    }
}