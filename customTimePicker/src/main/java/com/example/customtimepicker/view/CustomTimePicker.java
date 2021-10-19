package com.example.customtimepicker.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;

import com.example.customtimepicker.R;
import com.example.customtimepicker.view.validator.HourValidator;
import com.example.customtimepicker.view.validator.MinuteValidator;

public class CustomTimePicker extends TimePicker {
    private View radialPicker;
    private TextView timePickerHour;
    private TextView timePickerMinute;
    private EditText editText;
    private ImageButton keyboardImageButton;
    private KeyboardInputListener keyboardInputListener;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public CustomTimePicker(Context context) {
        super(context);
        setProperties();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public CustomTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setProperties();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setProperties() {
        super.setIs24HourView(true);
        getViewsFromTimePicker();
        hideDefaultKeyboardImageButton();
        showNewKeyboardImageButton();
        createEditText();
        setOnClickListenerToKeyboardImageButton();
        addEditTextChangeListener();
        setTimePickerHourOnTouchListener();
        setTimePickerMinuteOnTouchListener();
    }

    private void getViewsFromTimePicker() {
        radialPicker = (View) getViewFromTimePicker("radial_picker");
        //radialPicker.setBackgroundColor(Color.BLACK);
        //radialPicker.setDrawingCacheBackgroundColor(Color.RED);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        timePickerHour = (TextView) getViewFromTimePicker("hours");
        timePickerMinute = (TextView) getViewFromTimePicker("minutes");
    }

    private Object getViewFromTimePicker(String name) {
        super.getContext().getResources();
        int id = Resources.getSystem().getIdentifier(name, "id", "android");
        return super.findViewById(id);
    }

    private void hideDefaultKeyboardImageButton() {
        View imageButton = (View) getViewFromTimePicker("toggle_mode");
        imageButton.setVisibility(View.GONE);
    }

    private void showNewKeyboardImageButton() {
        RelativeLayout timeHeader = (RelativeLayout) getViewFromTimePicker("time_header");
        LinearLayout linearLayout = createLinearLayoutToTimeHeader();
        keyboardImageButton = createKeyboardImageButton();
        linearLayout.addView(keyboardImageButton);
        timeHeader.addView(linearLayout);
    }

    private LinearLayout createLinearLayoutToTimeHeader() {
        LinearLayout linearLayout = new LinearLayout(super.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setGravity(Gravity.END);
        return linearLayout;
    }

    private ImageButton createKeyboardImageButton() {
        ImageButton button = new ImageButton(super.getContext());
        button.setImageResource(R.drawable.ic_baseline_keyboard_24);
        button.setColorFilter(Color.WHITE);
        //button.setDrawingCacheBackgroundColor(Color.BLACK);
        //button.setBackgroundColor(Color.BLACK);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 60, 10, 0);
        button.setLayoutParams(params);
        return button;
    }

    private void createEditText() {
        editText = new EditText(super.getContext());
        editText.setWidth(1);
        editText.setHeight(1);
        editText.setTextSize(1);
        editText.setMaxLines(1);
        editText.setMaxEms(2);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1,1);
        editText.setLayoutParams(params);
        editText.requestFocus();
        editText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        editText.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        RelativeLayout timeHeader = (RelativeLayout) getViewFromTimePicker("time_header");
        timeHeader.addView(editText);
    }

    private void setOnClickListenerToKeyboardImageButton() {
        keyboardImageButton.setOnClickListener(view -> {
            editText.setActivated(true);
            editText.requestFocus();
            showKeyboard();
        });
    }

    private void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) super.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addEditTextChangeListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (timePickerHour.isActivated()) {
                    introducedHour();
                } else {
                    introducedMinute();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void introducedHour() {
        String hour = editText.getText().toString();
        String result = HourValidator.checkTheCorrectnessOfTheEnteredHour(hour);
        if (timeIsIncomplete(result)) {
            updateHourDataForIncompleteTime(result);
        } else if (timeIsComplete(result)){
            updateHourDataForCompleteTime(result);
        }
    }

    private boolean timeIsIncomplete(String result) {
        return result.length() == 1;
    }

    private void updateHourDataForIncompleteTime(String result) {
        if (notEquals(timePickerHour, editText)) {
            updateChangeFields(timePickerHour, editText, result);
        }
        editText.setSelection(1);
    }

    private boolean notEquals(TextView timePickerHour, EditText editText) {
        return !timePickerHour.getText().toString().equals(editText.getText().toString());
    }

    private void updateChangeFields(TextView tv, EditText et, String result) {
        if (!result.equals(et.getText().toString())) {
            et.selectAll();
            et.setText(result);
        }
        if (!result.equals(tv.getText().toString())) {
            tv.setText(result);
        }
    }

    private boolean timeIsComplete(String result) {
        return result.length() == 2;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateHourDataForCompleteTime(String result) {
        CustomTimePicker.super.setHour(Integer.parseInt(result));
        editText.selectAll();
        timePickerHour.setActivated(false);
        timePickerMinute.setActivated(true);
        timePickerMinute.callOnClick();
        if (keyboardInputListener != null)
            keyboardInputListener.onChange();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void introducedMinute() {
        String minute = editText.getText().toString();
        String result = MinuteValidator.checkTheCorrectnessOfTheEnteredMinute(minute);
        if (timeIsIncomplete(result)) {
            updateMinuteDataForIncompleteTime(result);
        } else if (timeIsComplete(result)) {
            updateMinuteDataForCompleteTime(result);
        }
    }

    private void updateMinuteDataForIncompleteTime(String result) {
        if (notEquals(timePickerMinute, editText)) {
            updateChangeFields(timePickerMinute, editText, result);
        }
        editText.setSelection(1);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateMinuteDataForCompleteTime(String result) {
        CustomTimePicker.super.setMinute(Integer.parseInt(result));
        editText.selectAll();
        if (keyboardInputListener != null)
            keyboardInputListener.onChange();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    private void setTimePickerHourOnTouchListener() {
        timePickerHour.setOnTouchListener((view, motionEvent) -> {
            String minute = timePickerMinute.getText().toString();
            if (minute.isEmpty()) {
                CustomTimePicker.super.setMinute(CustomTimePicker.super.getMinute());
            } else if (minute.length() == 1) {
                CustomTimePicker.super.setMinute(Integer.parseInt(timePickerMinute.getText().toString()));
                if (keyboardInputListener != null)
                    keyboardInputListener.onChange();
            }
            editText.selectAll();
            return false;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    private void setTimePickerMinuteOnTouchListener() {
        timePickerMinute.setOnTouchListener((view, motionEvent) -> {
            String hour = timePickerHour.getText().toString();
            if (hour.isEmpty()) {
                CustomTimePicker.super.setHour(CustomTimePicker.super.getHour());
            } else if (timeIsIncomplete(hour)) {
                CustomTimePicker.super.setHour(Integer.parseInt(timePickerHour.getText().toString()));
                if (keyboardInputListener != null)
                    keyboardInputListener.onChange();
            }
            editText.selectAll();
            return false;
        });
    }

    public int getHour() {
        return super.getHour();
    }

    public int getMinute() {
        return super.getMinute();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void viewHour(int hour) {
        CustomTimePicker.super.setHour(hour);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void viewMinute(int minute) {
        CustomTimePicker.super.setMinute(minute);
    }

    public void addKeyboardInputListener(KeyboardInputListener keyboardInputListener) {
        this.keyboardInputListener = keyboardInputListener;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void addClockFaceClickListener(ClockFaceClickListener clockFaceClickListener) {
        radialPicker.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                clockFaceClickListener.onClick();
                editText.setText("");
                editText.selectAll();
            }
            return false;
        });
    }

    public interface KeyboardInputListener {
        void onChange();
    }

    public interface ClockFaceClickListener {
        void onClick();
    }
}
