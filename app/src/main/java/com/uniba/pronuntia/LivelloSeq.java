package com.uniba.pronuntia;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LivelloSeq#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LivelloSeq extends Fragment {

    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LivelloSeq() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LivelloSeq.
     */
    // TODO: Rename and change types and number of parameters
    public static LivelloSeq newInstance(String param1, String param2) {
        LivelloSeq fragment = new LivelloSeq();
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

    private TextView parola1Text, parola2Text, parola3Text, titolo, giudizio;
    private Button parla, aiuto, record;
    private TextToSpeech tts;
    private int punteggio;

    private String parola1, parola2, parola3;
    private static final String TAG = "LivelloSeq";
    private boolean isRight = false;
    private int canClick = 3;
    private boolean isDone = false;

    private int numeroAiuti = 0;
    private int corretti = 0;
    private int sbagliati = 0;

    private boolean isRecording = false;
    private MediaRecorder mediaRecorder;
    private String audioFilePath;
    private TextView registrazione;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_livello_seq, container, false);


        titolo = view.findViewById(R.id.livello);
        parola1Text = view.findViewById(R.id.parola1);
        parola2Text = view.findViewById(R.id.parola2);
        parola3Text = view.findViewById(R.id.parola3);
        giudizio = view.findViewById(R.id.giudizio);
        parla = view.findViewById(R.id.speakButton);
        record = view.findViewById(R.id.recordButton);
        aiuto = view.findViewById(R.id.aiuto);
        registrazione = view.findViewById(R.id.registrazioneText);


        parola1 = getArguments().getString("Parola1");
        parola2 = getArguments().getString("Parola2");
        parola3 = getArguments().getString("Parola3");


        titolo.setText(getArguments().getString("Titolo"));
        punteggio = getArguments().getInt("Punteggio");

        parola1Text.setText(parola1);
        parola2Text.setText(parola2);
        parola3Text.setText(parola3);


        aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canClick != 0){
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

                                try {
                                    tts.speak(parola1.toString(), TextToSpeech.QUEUE_ADD, null);
                                    TimeUnit.SECONDS.sleep(1);
                                    tts.speak(parola2.toString(), TextToSpeech.QUEUE_ADD, null);
                                    TimeUnit.SECONDS.sleep(1);
                                    tts.speak(parola3.toString(), TextToSpeech.QUEUE_ADD, null);

                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
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

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startRecording();
            }
        });

        parla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(audioFilePath!=null){
                    speak(view, parola1, parola2, parola3);
                }else{
                    Toast.makeText(getActivity(), "Registra prima la risposta", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    public void speak(View view, String parola1, String parola2, String parola3 ){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Parla");
        intent.putExtra("parola1", parola1);
        intent.putExtra("parola2", parola2);
        intent.putExtra("parola3", parola3);

        startActivityForResult(intent, 100);
    }

    private void startRecording() {

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
            showRecordingDialog();
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

    private void showRecordingDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Registrazione in corso");

        builder.setPositiveButton("Interrompi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopRecording();
                dialog.dismiss();
            }
        });

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (isRecording) {
                    stopRecording();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecording) {
                    startRecording();
                } else {
                    dialog.show();
                }
            }
        });
    }

    private void stopRecording() {

        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            record.setText("Registra");
            registrazione.setText("Ok");
        }
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
            String[] content = {parola1, parola2, parola3};


            String[] inputSplitted = input.split(" ");

            inputSplitted = manageArray(inputSplitted);

            Log.d(TAG, "onActivityResult: " + inputSplitted[0]);
            Log.d(TAG, "onActivityResult: " + inputSplitted[1]);
            Log.d(TAG, "onActivityResult: " + inputSplitted[2]);


            for(int i = 0; i<content.length;i++){
                if(inputSplitted[i].toUpperCase().equals(content[i].toUpperCase())){
                    giudizio.setText("Giusto");
                    isRight = true;

                }else{
                    giudizio.setText("Sbagliato");
                    isRight = false;
                    break;
                }
            }

            if(isRight){
                punteggio += 10;
                isDone = true;
                corretti++;
            }else{
                punteggio -= 3;
                isDone = true;
                sbagliati++;
            }

            passResultToActivity(punteggio, isDone, numeroAiuti, corretti, sbagliati, audioFilePath);
        }
    }

    private String[] manageArray(String[] input){

        if(input.length<3){
            String[] newArray = new String[3];
            for (int i = 0; i < input.length; i++) {
                newArray[i] = input[i];
            }

            for (int i = input.length; i < 3; i++) {
                newArray[i] = " ";
            }
            return newArray;

        }else if(input.length>3){

            return Arrays.copyOfRange(input, 0, 3);

        }else{
            return input;
        }

    }


    private void passResultToActivity(int points, boolean isDone, int numeroAiuti, int corretti, int sbagliati, String path) {
        if (getActivity() instanceof OnDataPassListener) {
            ((OnDataPassListener) getActivity()).onDataPass(points, isDone, numeroAiuti, corretti, sbagliati, path);
        }
    }
}