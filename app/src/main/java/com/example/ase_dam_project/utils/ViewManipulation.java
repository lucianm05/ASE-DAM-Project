package com.example.ase_dam_project.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ase_dam_project.R;

public class ViewManipulation {
    public static void addTextViewValue(View view, int textViewId, String value) {
        TextView textView = view.findViewById(textViewId);

        if(Validations.isStringValid(value)) {
            textView.setText(value);
        } else {
            textView.setText(R.string.dash);
        }
    }

    public static void addImageViewUrl(View view, int imageViewId, String url) {
        ImageView imageView = view.findViewById(imageViewId);

        if(Validations.isStringValid(url)) {
            Glide.with(view).load(url).into(imageView);
        }
    }
}
