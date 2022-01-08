package com.example.alarmsoundview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alarmsoundview.R;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class AlarmSoundViewBuilder {
    private final Context context;
    private TextView description;
    private TextView name;
    private TypedArray options;

    public AlarmSoundViewBuilder(Context context, TypedArray options) {
        this.context = context;
        this.options = options;
        createDescriptionTextView();
        createNameTextView();
    }

//    @SuppressLint("RtlHardcoded")
    private void createNameTextView() {
        name = new TextView(context);
        LinearLayout.LayoutParams params = createNameTextViewParams();
        name.setLayoutParams(params);

        name.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        name.setGravity(Gravity.RIGHT);
//        params.gravity = Gravity.RIGHT;
    }

    private LinearLayout.LayoutParams createNameTextViewParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.rightMargin = 20;
        return params;
    }

    private void createDescriptionTextView() {
        description = new TextView(context);
        LinearLayout.LayoutParams params = createDescriptionTextViewParams();
        description.setLayoutParams(params);
        description.setText(options.getString(R.styleable.AlarmSoundView_descriptionText));
    }

    private LinearLayout.LayoutParams createDescriptionTextViewParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //params.weight = 1;
        params.width = (int) options.getDimension(R.styleable.AlarmSoundView_descriptionWidth, R.dimen.defaultValue);
        params.gravity = Gravity.START;
        return params;
    }

    public TextView getDescriptionTextView() {
        return description;
    }

    public TextView getNameTextView() {
        return name;
    }
}
