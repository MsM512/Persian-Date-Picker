package com.salehi.persiandatepicker;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.salehi.persiandatepicker.api.PersianPickerDate;
import com.salehi.persiandatepicker.api.PersianPickerListener;
import com.salehi.persiandatepicker.date.PersianDateImpl;
import com.salehi.persiandatepicker.util.PersianCalendar;
import com.salehi.persiandatepicker.util.PersianHelper;

import java.util.Date;

public class PersianDatePickerBSH extends BottomSheetDialogFragment {

    public static final int THIS_YEAR = -1;
    public static final int THIS_MONTH = -2;
    public static final int THIS_DAY = -3;
    public static final int NO_TITLE = 0;
    public static final int DAY_MONTH_YEAR = 1;
    public static final int WEEKDAY_DAY_MONTH_YEAR = 2;
    public static final int MONTH_YEAR = 3;
    public static Typeface typeFace;
    //    private final Context context;
    private final PersianPickerDate initDate = new PersianDateImpl();
    private String positiveButtonString = "تایید";
    private String negativeButtonString = "انصراف";
    private Listener listener;
    private PersianPickerListener persianPickerListener;
    private int maxYear = 0;
    private int maxMonth = 0;
    private int maxDay = 0;
    private int minYear = 0;
    private String todayButtonString = "امروز";
    private boolean todayButtonVisibility = false;
    private int actionColor = Color.GRAY;
    private int actionTextSize = 12;
    private int negativeTextSize = 12;
    private int todayTextSize = 12;
    private int backgroundColor = Color.WHITE;
    private int titleColor = Color.parseColor("#111111");
    private boolean cancelable = true;
    private boolean forceMode;
    private boolean isShowDayPicker = true;
    private int pickerBackgroundColor;
    private int pickerBackgroundDrawable;
    private int titleType = 0;
    private View v;
    private PersianDatePicker datePickerView;
    private TextView dateText, top_title;
    private AppCompatButton positiveButton;
    private AppCompatButton negativeButton;
    private ImageView cancel;
    private AppCompatButton todayButton;
    private LinearLayout container;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dialog_picker, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cancel = v.findViewById(R.id.cancel);
        top_title = v.findViewById(R.id.title);
        datePickerView = v.findViewById(R.id.datePicker);
        dateText = v.findViewById(R.id.dateText);
        positiveButton = v.findViewById(R.id.positive_button);
        negativeButton = v.findViewById(R.id.negative_button);
        todayButton = v.findViewById(R.id.today_button);
        container = v.findViewById(R.id.container);
        TextView t_mounth = v.findViewById(R.id.t_mounth);
        TextView t_day = v.findViewById(R.id.t_day);
        TextView t_year = v.findViewById(R.id.t_year);

        container.setBackgroundColor(backgroundColor);
        dateText.setTextColor(titleColor);
        datePickerView.setDayVisibility(isShowDayPicker);

        if (pickerBackgroundColor != 0) {
            datePickerView.setBackgroundColor(pickerBackgroundColor);
        } else if (pickerBackgroundDrawable != 0) {
            datePickerView.setBackgroundDrawable(pickerBackgroundDrawable);
        }

        PersianDateImpl persianDate = new PersianDateImpl();

        if (maxYear > 0) {
            datePickerView.setMaxYear(maxYear);
        } else if (maxYear == THIS_YEAR) {
            maxYear = persianDate.getPersianYear();
            datePickerView.setMaxYear(maxYear);
        }

        if (maxMonth > 0) {
            datePickerView.setMaxMonth(maxMonth);
        } else if (maxMonth == THIS_MONTH) {
            maxMonth = persianDate.getPersianMonth();
            datePickerView.setMaxMonth(maxMonth);
        }

        if (maxDay > 0) {
            datePickerView.setMaxDay(maxDay);
        } else if (maxDay == THIS_DAY) {
            maxDay = persianDate.getPersianDay();
            datePickerView.setMaxDay(maxDay);
        }

        if (minYear > 0) {
            datePickerView.setMinYear(minYear);
        } else if (minYear == THIS_YEAR) {
            minYear = persianDate.getPersianYear();
            datePickerView.setMinYear(minYear);
        }

        if (initDate != null) {
            int initYear = initDate.getPersianYear();
            if (initYear > maxYear || initYear < minYear) {
                Log.e("PERSIAN CALENDAR", "init year is more/less than minYear/maxYear");
                if (forceMode) {
                    datePickerView.setDisplayPersianDate(initDate);
                }
            } else {
                datePickerView.setDisplayPersianDate(initDate);
            }

        }

        if (typeFace != null) {
            Log.e("typeFace", "is set");
            dateText.setTypeface(typeFace);
            positiveButton.setTypeface(typeFace);
            negativeButton.setTypeface(typeFace);
            todayButton.setTypeface(typeFace);
            datePickerView.setTypeFace(typeFace);
            top_title.setTypeface(typeFace);
            t_mounth.setTypeface(typeFace);
            t_year.setTypeface(typeFace);
            t_day.setTypeface(typeFace);
        }

        positiveButton.setTextSize(actionTextSize);
        negativeButton.setTextSize(negativeTextSize);
        todayButton.setTextSize(todayTextSize);

        positiveButton.setTextColor(actionColor);
        negativeButton.setTextColor(actionColor);
        todayButton.setTextColor(actionColor);

        positiveButton.setText(positiveButtonString);
        negativeButton.setText(negativeButtonString);
        todayButton.setText(todayButtonString);

//        if (todayButtonVisibility) {
//            todayButton.setVisibility(View.VISIBLE);
//        }

        updateView(dateText, datePickerView.getPersianDate());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        datePickerView.setOnDateChangedListener(new PersianDatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(int newYear, int newMonth, int newDay) {
                updateView(dateText, datePickerView.getPersianDate());
            }
        });

        this.setCancelable(cancelable);

        negativeButton.setOnClickListener(view1 -> {
            if (listener != null) {
                listener.onDismissed();
            }

            if (persianPickerListener != null) {
                persianPickerListener.onDismissed();
            }
            dismiss();
        });

        positiveButton.setOnClickListener(view2 -> {

            if (listener != null) {
                listener.onDateSelected(datePickerView.getDisplayPersianDate());
            }

            if (persianPickerListener != null) {
                persianPickerListener.onDateSelected(datePickerView.getPersianDate());
            }
            dismiss();
        });

        todayButton.setOnClickListener(view3 -> {

            datePickerView.setDisplayDate(new Date());

            if (maxYear > 0) {
                datePickerView.setMaxYear(maxYear);
            }

            if (minYear > 0) {
                datePickerView.setMinYear(minYear);
            }

            dateText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateView(dateText, datePickerView.getPersianDate());
                }
            }, 100);
        });

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                dismiss();
                return true;
            }
            return false;
        });
        return dialog;
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Deprecated
    public PersianDatePickerBSH setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    public PersianDatePickerBSH setListener(PersianPickerListener listener) {
        this.persianPickerListener = listener;
        return this;
    }

    public PersianDatePickerBSH setMaxYear(int maxYear) {
        this.maxYear = maxYear;
        return this;
    }

    public PersianDatePickerBSH setMaxMonth(int maxMonth) {
        if (maxMonth > 12) {
            throw new RuntimeException("max month is not valid");
        }
        this.maxMonth = maxMonth;
        return this;
    }

    public PersianDatePickerBSH setMaxDay(int maxDay) {
        if (maxDay > 31) {
            throw new RuntimeException("max day is not valid");
        }
        this.maxDay = maxDay;
        return this;
    }

    public PersianDatePickerBSH setTypeFace(Typeface typeFace) {
        PersianDatePickerBSH.typeFace = typeFace;
        return this;
    }

    public PersianDatePickerBSH setMinYear(int minYear) {
        this.minYear = minYear;
        return this;
    }

    public PersianDatePickerBSH setInitDate(Long timestamp) {
        this.initDate.setDate(timestamp);
        return this;
    }

    public PersianDatePickerBSH setInitDate(Date date) {
        this.initDate.setDate(date);
        return this;
    }

    public PersianDatePickerBSH setInitDate(int persianYear, int persianMonth, int persianDay) {
        this.initDate.setDate(persianYear, persianMonth, persianDay);
        this.forceMode = true;
        return this;
    }

    public PersianDatePickerBSH setInitDate(PersianPickerDate initDate) {
        return setInitDate(initDate, true);
    }

    public PersianDatePickerBSH setInitDate(PersianPickerDate initDate, boolean force) {
        this.forceMode = force;
        this.initDate.setDate(initDate.getTimestamp());
        return this;
    }

    @Deprecated
    public PersianDatePickerBSH setInitDate(PersianCalendar initDate) {
        return setInitDate(initDate, false);
    }

    @Deprecated
    public PersianDatePickerBSH setInitDate(PersianCalendar initDate, boolean force) {
        this.forceMode = force;
        this.initDate.setDate(
                initDate.getPersianYear(),
                initDate.getPersianMonth(),
                initDate.getPersianDay()
        );
        return this;
    }

    public PersianDatePickerBSH setPositiveButtonString(String positiveButtonString) {
        this.positiveButtonString = positiveButtonString;
        return this;
    }

    public PersianDatePickerBSH setPositiveButtonResource(@StringRes int positiveButton) {
        this.positiveButtonString = getContext().getString(positiveButton);
        return this;
    }

    public PersianDatePickerBSH setTodayButtonVisible(boolean todayButtonVisiblity) {
        this.todayButtonVisibility = todayButtonVisiblity;
        return this;
    }

    public PersianDatePickerBSH setTodayButton(String todayButton) {
        this.todayButtonString = todayButton;
        return this;
    }

    public PersianDatePickerBSH setTodayButtonResource(@StringRes int todayButton) {
        this.todayButtonString = getContext().getString(todayButton);
        return this;
    }

    public PersianDatePickerBSH setShowDayPicker(boolean showDayPicker) {
        this.isShowDayPicker = showDayPicker;
        return this;
    }

    public PersianDatePickerBSH setTodayTextSize(int sizeInt) {
        this.todayTextSize = sizeInt;
        return this;
    }

    public PersianDatePickerBSH setNegativeButton(String negativeButton) {
        this.negativeButtonString = negativeButton;
        return this;
    }

    public PersianDatePickerBSH setNegativeButtonResource(@StringRes int negativeButton) {
        this.negativeButtonString = getContext().getString(negativeButton);
        return this;
    }

    public PersianDatePickerBSH setNegativeTextSize(int sizeInt) {
        this.negativeTextSize = sizeInt;
        return this;
    }

    public PersianDatePickerBSH setActionTextColor(@ColorInt int colorInt) {
        this.actionColor = colorInt;
        return this;
    }

    public PersianDatePickerBSH setActionTextColorResource(@ColorRes int colorInt) {
        this.actionColor = ContextCompat.getColor(getContext(), colorInt);
        return this;
    }

    public PersianDatePickerBSH setActionTextSize(int sizeInt) {
        this.actionTextSize = sizeInt;
        return this;
    }

    public PersianDatePickerBSH setAllButtonsTextSize(int sizeInt) {
        this.actionTextSize = sizeInt;
        this.negativeTextSize = sizeInt;
        this.todayTextSize = sizeInt;
        return this;
    }

    public PersianDatePickerBSH setCanCancel(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public PersianDatePickerBSH setBackgroundColor(@ColorInt int bgColor) {
        this.backgroundColor = bgColor;
        return this;
    }

    public PersianDatePickerBSH setTitleColor(@ColorInt int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public PersianDatePickerBSH setPickerBackgroundColor(@ColorInt int color) {
        this.pickerBackgroundColor = color;
        return this;
    }

    public PersianDatePickerBSH setPickerBackgroundDrawable(@DrawableRes int drawableBg) {
        this.pickerBackgroundDrawable = drawableBg;
        return this;
    }

    public PersianDatePickerBSH setTitleType(int titleType) {
        this.titleType = titleType;
        return this;
    }

    private void updateView(TextView dateText, PersianPickerDate persianDate) {
        String date;
        switch (titleType) {
            case NO_TITLE:
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) dateText.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 30);
                dateText.setLayoutParams(layoutParams);
                break;
            case DAY_MONTH_YEAR:
                date = persianDate.getPersianDay() + " " +
                        persianDate.getPersianMonthName() + " " +
                        persianDate.getPersianYear();

                dateText.setText(PersianHelper.toPersianNumber(date));
                break;
            case WEEKDAY_DAY_MONTH_YEAR:
                date = persianDate.getPersianDayOfWeekName() + " " +
                        persianDate.getPersianDay() + " " +
                        persianDate.getPersianMonthName() + " " +
                        persianDate.getPersianYear();
                dateText.setText(PersianHelper.toPersianNumber(date));
                break;
            case MONTH_YEAR:
                date = persianDate.getPersianMonthName() + " " +
                        persianDate.getPersianYear();

                dateText.setText(PersianHelper.toPersianNumber(date));
                break;
            default:
                Log.d("PersianDatePickerDialog", "never should be here");
                break;
        }

    }

}
