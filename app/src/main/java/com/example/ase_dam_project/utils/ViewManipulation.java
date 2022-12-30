package com.example.ase_dam_project.utils;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ase_dam_project.R;
import com.google.android.material.textfield.TextInputEditText;

public class ViewManipulation {
    public static void addTextViewValue(View view, int textViewId, String value) {
        TextView textView = view.findViewById(textViewId);

        if(Validations.isValidString(value)) {
            textView.setText(value);
        } else {
            textView.setText(R.string.dash);
        }
    }

    public static void addImageViewUrl(View view, int imageViewId, String url) {
        ImageView imageView = view.findViewById(imageViewId);

        if(Validations.isValidString(url)) {
            Glide.with(view).load(url).into(imageView);
        }
    }

    public static String getTietString(TextInputEditText tiet) {
        Editable text = tiet.getText();

        if(text == null) return null;

        return text.toString().trim();
    }

    public static long getTietLong(TextInputEditText tiet) {
        String value = ViewManipulation.getTietString(tiet);
        Log.i("TIET VALUE", value);
        if(value == null || value.isEmpty()) {
            Log.i("TIET VALUE", "IS INVALID");

            return 0;
        }

        return Long.parseLong(value);
    }
}
