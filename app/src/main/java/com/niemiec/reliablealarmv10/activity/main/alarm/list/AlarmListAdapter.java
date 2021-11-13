package com.niemiec.reliablealarmv10.activity.main.alarm.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.main.observer.Observer;
import com.niemiec.reliablealarmv10.model.custom.Alarm;
import com.niemiec.reliablealarmv10.view.checkable.imageview.CheckableImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlarmListAdapter extends ArrayAdapter<Alarm> implements Observer {
    private Context context;
    private List<Alarm> alarms = new ArrayList<>();
    private LayoutInflater inflater;
    private List<ViewHolder> views;

    public AlarmListAdapter(Context context, List<Alarm> alarms) {
        super(context, android.R.layout.simple_expandable_list_item_1, alarms);
        this.context = context;
        this.alarms = alarms;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        views = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //TODO przekazuje do adaptera referencję do Subject
        //i określam funckję wyświetl??
        //W Bin wywołanie na adapterze funkcji zmiany wyświetlanych danych
        View listItem = convertView;
        ViewHolder viewHolder;



        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.alarm_list_item, parent, false);
            viewHolder = new ViewHolder(listItem);
            listItem.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listItem.getTag();
        }

        if (views.indexOf(viewHolder) < 0) {
            views.add(viewHolder);
        }

        Alarm currentAlarm = alarms.get(position);
        setValuesInViewHolder(viewHolder, currentAlarm);
        return listItem;
    }

    @SuppressLint({"Range", "SimpleDateFormat"})
    private void setValuesInViewHolder(ViewHolder viewHolder, Alarm alarm) {
        viewHolder.radioButtonCircle.setBackgroundResource(R.drawable.ic_baseline_radio_button_unchecked_24);
        viewHolder.radioButtonCircle.setChecked(false);
        viewHolder.radioButtonCircle.setVisibility(View.GONE);

        Date dateTime = alarm.alarmDateTime.getDateTime().getTime();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        viewHolder.alarmTime.setText(timeFormat.format(dateTime));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        viewHolder.alarmDate.setText(dateFormat.format(dateTime));

        viewHolder.isActive.setChecked(alarm.isActive);
    }

    @Override
    public void toggleView() {
        for (ViewHolder view : views) {
            if (view.radioButtonCircle.getVisibility() != View.GONE) {
                view.radioButtonCircle.setVisibility(View.GONE);
                view.isActive.setVisibility(View.VISIBLE);
            } else {
                view.radioButtonCircle.setVisibility(View.VISIBLE);
                view.isActive.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean showSelectedItem(int position) {
        ViewHolder viewHolder = views.get(position);
        CheckableImageView iv = viewHolder.radioButtonCircle;

        if (iv.isChecked()) {
            iv.setChecked(false);
            iv.setBackgroundResource(R.drawable.ic_baseline_radio_button_unchecked_24);
            return false;
        } else {
            iv.setChecked(true);
            iv.setBackgroundResource(R.drawable.ic_baseline_radio_button_checked_24);
            return true;
        }
    }

    static class ViewHolder {
        CheckableImageView radioButtonCircle;
        TextView alarmTime;
        TextView alarmDate;
        SwitchCompat isActive;

        public ViewHolder(View view) {
            radioButtonCircle = view.findViewById(R.id.radio_button_circle_image_view);
            alarmTime = view.findViewById(R.id.alarm_time_text_view);
            alarmDate = view.findViewById(R.id.alarm_date_text_view);
            isActive = view.findViewById(R.id.alarm_is_active_switch);
        }
    }
}
