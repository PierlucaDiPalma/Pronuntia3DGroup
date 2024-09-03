package com.uniba.pronuntia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LivelloSeq#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LivelloSeq extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LivelloSeq() {
        // Required empty public constructor
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

    private TextView parola1Text, parola2Text, parola3Text, titolo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_livello_seq, container, false);


        titolo = view.findViewById(R.id.livello);
        parola1Text = view.findViewById(R.id.parola1);
        parola2Text = view.findViewById(R.id.parola2);
        parola3Text = view.findViewById(R.id.parola3);

        String parola1, parola2, parola3;

        if(getArguments() != null){
            titolo.setText(getArguments().getString("Titolo"));

            parola1Text.setText(getArguments().getString("Parola1"));
            parola2Text.setText(getArguments().getString("Parola2"));
            parola3Text.setText(getArguments().getString("Parola3"));

        }

        return view;
    }
}