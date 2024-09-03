package com.uniba.pronuntia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button aiuto;
    private TextView titolo;
    private int punteggio = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_livello_den, container, false);

        immagine = view.findViewById(R.id.imageEx);
        aiuto = view.findViewById(R.id.playButton);
        titolo = view.findViewById(R.id.livello);

        if(getArguments() != null){
            titolo.setText(getArguments().getString("Titolo"));

            byte[] byteArray = getArguments().getByteArray("Immagine");
            Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            immagine.setImageBitmap(image);

            parola = getArguments().getString("Aiuto");

        }

        aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendPoints(punteggio);
            }
        });

        return view;
    }

    HomeListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mListener = (HomeListener) context;
    }

    public interface HomeListener{
        void sendPoints(int points);
    }
}