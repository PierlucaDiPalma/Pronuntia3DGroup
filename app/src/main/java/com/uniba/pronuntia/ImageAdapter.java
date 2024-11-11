package com.uniba.pronuntia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ImageAdapter extends BaseAdapter {

    private final Context context;
    private final int[] images = {
            R.drawable.baseline_checklist_rtl_24,
            R.drawable.baseline_border_color_24,
            R.drawable.baseline_calendar_month_24,
            R.drawable.baseline_person_add_alt_1_24
    };

    private final String[] titles = {
            "Correggi esercizi",
            "Personalizza percorso",
            "Appuntamenti",
            "Richiedi terapia"
    };

    public ImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.action);


        imageView.setImageResource(images[position]);
        textView.setText(titles[position]);


        return convertView;
    }
}
