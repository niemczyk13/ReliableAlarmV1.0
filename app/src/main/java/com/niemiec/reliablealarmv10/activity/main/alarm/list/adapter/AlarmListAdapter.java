package com.niemiec.reliablealarmv10.activity.main.alarm.list.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alarmschedule.view.alarm.schedule.text.DateTextGenerator;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.main.alarm.list.AlarmListListener;
import com.niemiec.reliablealarmv10.activity.main.alarm.list.adapter.data.AlarmsList;
import com.niemiec.reliablealarmv10.model.custom.Alarm;
import com.niemiec.reliablealarmv10.view.checkable.imageview.CheckableImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AlarmListAdapter extends ArrayAdapter<Alarm> {
    private final Context context;
    //private LayoutInflater inflater;
    private AlarmListListener alarmListContainer;
    private AlarmsList alarmsList;
    private TypeView typeView;

    private int deleteItem = 0;



    public AlarmListAdapter(Context context, AlarmsList alarmsList, AlarmListListener container) {
        super(context, android.R.layout.simple_expandable_list_item_1, alarmsList.getAlarms());
        this.context = context;
        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.alarmListContainer = container;
        typeView = TypeView.NORMAL;
        this.alarmsList = alarmsList;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        ViewHolder viewHolder;

        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.alarm_list_item, parent, false);
            viewHolder = new ViewHolder(listItem);
            listItem.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listItem.getTag();
        }

        setValuesInViewHolder(viewHolder, alarmsList.get(position), alarmsList.isSelected(position));
        return listItem;
    }

    //TODO skrócić metodę
    @SuppressLint({"Range", "SimpleDateFormat"})
    private void setValuesInViewHolder(ViewHolder viewHolder, Alarm alarm, boolean isSelected) {
        viewHolder.radioButtonCircle.setBackgroundResource(R.drawable.ic_baseline_radio_button_unchecked_24);
        viewHolder.radioButtonCircle.setChecked(false);

        Date dateTime = alarm.alarmDateTime.getDateTime().getTime();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        viewHolder.alarmTime.setText(timeFormat.format(dateTime));
        if (alarm.alarmDateTime.isSchedule()) {
            viewHolder.alarmDate.setText(DateTextGenerator.generate(alarm.alarmDateTime.getWeek()));
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            viewHolder.alarmDate.setText(dateFormat.format(dateTime));
        }

        viewHolder.isActive.setChecked(alarm.isActive);
        viewHolder.isActive.setOnClickListener(this::isActiveClick);

        if (typeView == TypeView.NORMAL) {
            viewHolder.radioButtonCircle.setVisibility(View.GONE);
            viewHolder.isActive.setVisibility(View.VISIBLE);
        } else if (typeView == TypeView.DELETE) {
            if (isSelected) {
                viewHolder.radioButtonCircle.setBackgroundResource(R.drawable.ic_baseline_radio_button_checked_24);
                viewHolder.radioButtonCircle.setChecked(true);
            } else {
                viewHolder.radioButtonCircle.setBackgroundResource(R.drawable.ic_baseline_radio_button_unchecked_24);
                viewHolder.radioButtonCircle.setChecked(false);
            }
            viewHolder.radioButtonCircle.setVisibility(View.VISIBLE);
            viewHolder.isActive.setVisibility(View.GONE);
        }
    }

    private void isActiveClick(View view) {
        SwitchCompat switchCompat = (SwitchCompat) view;
        LinearLayout linearLayout = (LinearLayout) switchCompat.getParent();
        ListView lv = (ListView) linearLayout.getParent();
        int position = lv.getPositionForView(linearLayout);

        alarmListContainer.switchOnOffClick(alarmsList.get(position).id);

    }

    public List<Alarm> getSelectedAlarms() {
        return alarmsList.getSelectedAlarms();
    }

    public void showDeleteList() {
        typeView = TypeView.DELETE;
        this.notifyDataSetChanged();
    }

    public void showMainList() {
        typeView = TypeView.NORMAL;
        alarmsList.clearSelected();
        this.notifyDataSetChanged();
    }

    public Alarm getAlarm(int position) {
        return alarmsList.get(position);
    }

    public void checkOnUncheckAlarm(int position) {
        alarmsList.checkOrUncheckAlarm(position);
        this.notifyDataSetChanged();
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

    private enum TypeView {
        NORMAL, DELETE;
    }
}
