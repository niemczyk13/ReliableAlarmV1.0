package com.niemiec.reliablealarmv10.fragment.alarm.list.list;

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
import com.example.globals.enums.IsChecked;
import com.example.globals.enums.TypeView;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.model.custom.Alarm;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;
import com.niemiec.reliablealarmv10.view.checkable.imageview.CheckableImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
public class AlarmListAdapter extends ArrayAdapter<Alarm> {
    private final Context context;
    private final AlarmListListener alarmListContainer;
    private final AlarmsList alarmsList;
    private TypeView typeView;


    public AlarmListAdapter(Context context, AlarmsList alarmsList, AlarmListListener container) {
        super(context, android.R.layout.simple_expandable_list_item_1, alarmsList.getAlarms());
        this.context = context;
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

    private void setValuesInViewHolder(ViewHolder viewHolder, Alarm alarm, boolean isSelected) {
        setRadioButtonCircleInViewHolder(viewHolder, IsChecked.UNCHECKED);
        setNameAndNoteInViewHolder(viewHolder, alarm);
        setIsActiveInViewHolder(viewHolder, alarm);
        setVisibilityOfRadioButtonCircle(viewHolder, isSelected);
    }

    private static void setRadioButtonCircleInViewHolder(ViewHolder viewHolder, IsChecked isChecked) {
        viewHolder.radioButtonCircle.setBackgroundResource(R.drawable.ic_baseline_radio_button_unchecked_24);
        viewHolder.radioButtonCircle.setChecked(isChecked.getValue());
    }

    private static void setNameAndNoteInViewHolder(ViewHolder viewHolder, Alarm alarm) {
        if (alarm instanceof SingleAlarmModel) {
            setNameAndNoteInViewHolderForSingleAlarmModel(viewHolder, alarm);
        } else if (alarm instanceof GroupAlarmModel) {
            setNameAndNoteInViewHolderForGroupAlarmModel(viewHolder, alarm);
        }
    }

    @SuppressLint({"Range", "SimpleDateFormat"})
    private static void setNameAndNoteInViewHolderForSingleAlarmModel(ViewHolder viewHolder, Alarm alarm) {
        Date dateTime = alarm.getAlarmDateTime().getDateTime().getTime();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        viewHolder.alarmTime.setText(timeFormat.format(dateTime));
        if (alarm.getAlarmDateTime().isSchedule()) {
            viewHolder.alarmDate.setText(DateTextGenerator.generate(alarm.getAlarmDateTime().getWeek()));
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            viewHolder.alarmDate.setText(dateFormat.format(dateTime));
        }
    }

    private static void setNameAndNoteInViewHolderForGroupAlarmModel(ViewHolder viewHolder, Alarm alarm) {
        viewHolder.alarmTime.setText("Grupowy");
        viewHolder.alarmDate.setText(alarm.getName());
    }

    private void setIsActiveInViewHolder(ViewHolder viewHolder, Alarm alarm) {
        viewHolder.isActive.setChecked(alarm.isActive());
        viewHolder.isActive.setOnClickListener(this::isActiveClick);
    }

    private void isActiveClick(View view) {
        SwitchCompat switchCompat = (SwitchCompat) view;
        LinearLayout linearLayout = (LinearLayout) switchCompat.getParent();
        ListView lv = (ListView) linearLayout.getParent();
        int position = lv.getPositionForView(linearLayout);

        alarmListContainer.switchOnOffClick(alarmsList.get(position));

    }

    private void setVisibilityOfRadioButtonCircle(ViewHolder viewHolder, boolean isSelected) {
        if (typeView == TypeView.NORMAL) {
            viewHolder.radioButtonCircle.setVisibility(View.GONE);
            viewHolder.isActive.setVisibility(View.VISIBLE);
        } else if (typeView == TypeView.DELETE) {
            setVisibilityOfRadioButtonCircleForDeleteViewType(viewHolder, isSelected);
        }
    }

    private static void setVisibilityOfRadioButtonCircleForDeleteViewType(ViewHolder viewHolder, boolean isSelected) {
        if (isSelected) {
            viewHolder.radioButtonCircle.setBackgroundResource(R.drawable.ic_baseline_radio_button_checked_24);
            viewHolder.radioButtonCircle.setChecked(true);
        } else {
            setRadioButtonCircleInViewHolder(viewHolder, IsChecked.UNCHECKED);
        }
        viewHolder.radioButtonCircle.setVisibility(View.VISIBLE);
        viewHolder.isActive.setVisibility(View.GONE);
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
            alarmTime = view.findViewById(R.id.alarm_title_text_view);
            alarmDate = view.findViewById(R.id.alarm_description_text_view);
            isActive = view.findViewById(R.id.alarm_is_active_switch);
        }
    }
}
