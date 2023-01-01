package com.example.ase_dam_project.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.example.ase_dam_project.R;

import java.util.Map;
import java.util.Random;

public class Chart extends View {
    private final Context context;
    private final Map<String, Integer> source;
    private final Paint paint;
    private final Random random;

    public Chart(Context context, Map<String, Integer> source) {
        super(context);
        this.context = context;
        this.source = source;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setColor(Color.BLACK);
        this.random = new Random();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(source.isEmpty()) return;

        float barWidth = getWidth() / source.size();
        int maxValue = getMaxValue();

        int currentBarPosition = 0;
        for(String label : source.keySet()) {
            int value = source.get(label);
            Log.i("VALUE", String.valueOf(value));
            int randomRed = 1 + random.nextInt(254);
            int randomGreen = 1 + random.nextInt(254);
            int randomBlue = 1 + random.nextInt(254);
            int color = Color.argb(100, randomRed, randomGreen, randomBlue);
            paint.setColor(color);

            float x1 = currentBarPosition * barWidth;
            float y1 = (1 - (float) (value) / maxValue) * getHeight();
            float x2 = x1 + barWidth;
            float y2 = getHeight();
            canvas.drawRect(x1, y1, x2, y2, paint);

            paint.setColor(Color.BLACK);
            paint.setTextSize((float) (0.25 * barWidth));
            float x = (float) ((currentBarPosition + 0.5) * barWidth);
            float y = (float) (0.95 * getHeight());
            canvas.rotate(270, x , y);
            canvas.drawText(context.getString(R.string.chart_legend_template, label, value), x, y, paint);
            canvas.rotate(-270, x, y);

            currentBarPosition++;
        }
    }

    private int getMaxValue() {
        int max = 0;
        for(Integer value: source.values()) {
            if(value > max) {
                max = value;
            }
        }

        return max;
    }
}
