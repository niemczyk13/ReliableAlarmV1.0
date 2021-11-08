package com.niemiec.reliablealarmv10.activity.main.alarm.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.niemiec.reliablealarmv10.R;

import androidx.appcompat.widget.SwitchCompat;

public class AlarmListAdapter extends BaseAdapter implements Filterable {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    static class ViewHolder {
        ImageView radioButtonCircle;
        TextView alarmTime;
        TextView alarmDate;
        SwitchCompat isActive;

        public ViewHolder(View view) {
            radioButtonCircle = view.findViewById(R.id.radio_button_circle_image_view);
            alarmTime = view.findViewById(R.id.alarm_time_text_view);
            alarmDate = view.findViewById(R.id.alarm_date_time_view);
            isActive = view.findViewById(R.id.alarm_is_active_switch);
        }
    }
}
