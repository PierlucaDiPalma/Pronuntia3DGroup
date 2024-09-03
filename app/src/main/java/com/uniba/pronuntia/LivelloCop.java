package com.uniba.pronuntia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_livello_cop, container, false);

        titolo = view.findViewById(R.id.livello);
        contenuto = view.findViewById(R.id.contenuto);
        immagine1 = view.findViewById(R.id.img1);
        immagine2 = view.findViewById(R.id.img2);

        if(getArguments() != null){

            titolo.setText(getArguments().getString("Titolo"));
            contenuto.setText(getArguments().getString("Aiuto"));

            byte[] byteArray = getArguments().getByteArray("Immagine1");
            Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            immagine1.setImageBitmap(image);

            byteArray = getArguments().getByteArray("Immagine2");
            image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            immagine2.setImageBitmap(image);


        }


        // Inflate the layout for this fragment
        return view;
    }
}