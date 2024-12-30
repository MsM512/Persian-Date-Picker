package com.salehi.persiandatepicker.app.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.salehi.persiandatepicker.PersianDatePickerBSH;
import com.salehi.persiandatepicker.api.PersianPickerDate;
import com.salehi.persiandatepicker.api.PersianPickerListener;
import com.salehi.persiandatepicker.app.R;
import com.salehi.persiandatepicker.util.PersianCalendarUtils;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Date Picker";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/iransans.ttf");

        Button btn = findViewById(R.id.show);
        btn.setOnClickListener(v -> {
            PersianDatePickerBSH picker = new PersianDatePickerBSH()
                    .setPositiveButtonString("تایید")
                    .setTodayButtonVisible(true)
                    .setInitDate(1403, 1, 1)
                    .setActionTextColor(Color.WHITE)
                    .setTypeFace(typeface)
                    .setTitleType(PersianDatePickerBSH.NO_TITLE)
                    .setListener(new PersianPickerListener() {
                        @Override
                        public void onDateSelected(@NotNull PersianPickerDate persianPickerDate) {
                            Log.d(TAG, "onDateSelected: " + persianPickerDate.getTimestamp());//675930448000
                            Log.d(TAG, "onDateSelected: " + persianPickerDate.getGregorianDate());//Mon Jun 03 10:57:28 GMT+04:30 1991
                            Log.d(TAG, "onDateSelected: " + persianPickerDate.getPersianLongDate());// دوشنبه  13  خرداد  1370
                            Log.d(TAG, "onDateSelected: " + persianPickerDate.getPersianMonthName());//خرداد
                            Log.d(TAG, "onDateSelected: " + PersianCalendarUtils.isPersianLeapYear(persianPickerDate.getPersianYear()));//true
                            Toast.makeText(getApplicationContext(), persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onDismissed() {

                        }
                    });

            picker.show(getSupportFragmentManager(), "date");
        });
    }
}
