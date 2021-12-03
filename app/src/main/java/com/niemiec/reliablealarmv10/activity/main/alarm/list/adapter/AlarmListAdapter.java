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
    private List<ViewHolder> views;
    private AlarmListListener alarmListContainer;
    private AlarmsList alarmsList;
    private TypeView typeView;

    private int deleteItem = 0;



    public AlarmListAdapter(Context context, AlarmsList alarmsList, AlarmListListener container) {
        super(context, android.R.layout.simple_expandable_list_item_1, alarmsList.getAlarms());
        this.context = context;
        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        views = new ArrayList<>();
        this.alarmListContainer = container;
        typeView = TypeView.NORMAL;
        this.alarmsList = alarmsList;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            views.clear();
        }
        View listItem = convertView;
        ViewHolder viewHolder;

        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.alarm_list_item, parent, false);
            viewHolder = new ViewHolder(listItem);
            listItem.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listItem.getTag();
        }

        //TODO
        //views.clear();
        //if (views.indexOf(viewHolder) < 0) {
            views.add(viewHolder);
        System.out.println("VIEWS SIZE: " + views.size());
        //}

        Alarm currentAlarm = alarmsList.get(position);
        setValuesInViewHolder(viewHolder, currentAlarm);
        setVisibilityToViewsInViewHolder();
        return listItem;
    }

    //TODO - robić to w taki sposób? wtedy trzeba załadować listę od początku
    private void setVisibilityToViewsInViewHolder() {
        if (typeView == TypeView.NORMAL) {

        } else if (typeView == TypeView.DELETE) {

        }
    }

    //TODO - gdy włączę alarm to aktualizacja w bazie i animacja przenosząca alarm do góry

    @SuppressLint({"Range", "SimpleDateFormat"})
    private void setValuesInViewHolder(ViewHolder viewHolder, Alarm alarm) {
        if (typeView == TypeView.NORMAL) {

        } else if (typeView == TypeView.DELETE) {
            //TODO - sprawdzamy czy zaznaczony i odpowiedni przycisk radio
            ++deleteItem;
        }
        System.out.println("TYPE VIEW: " + typeView + ", delete: " + deleteItem);

        viewHolder.radioButtonCircle.setBackgroundResource(R.drawable.ic_baseline_radio_button_unchecked_24);
        viewHolder.radioButtonCircle.setChecked(false);
        viewHolder.radioButtonCircle.setVisibility(View.GONE);

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
    }

    private void isActiveClick(View view) {
        SwitchCompat switchCompat = (SwitchCompat) view;
        LinearLayout linearLayout = (LinearLayout) switchCompat.getParent();
        ListView lv = (ListView) linearLayout.getParent();
        int position = lv.getPositionForView(linearLayout);

        alarmListContainer.switchOnOffClick(alarmsList.get(position).id);

        //TODO ewentualna aktualizacja listy z animacją
        //alarmsList.get(position).isActive = !alarmsList.get(position).isActive;
    }

    public void toggleView() {
        for (ViewHolder view : views) {
            if (view.radioButtonCircle.getVisibility() != View.GONE) {
                view.radioButtonCircle.setVisibility(View.GONE);
                view.isActive.setVisibility(View.VISIBLE);
            } else {
                view.radioButtonCircle.setVisibility(View.VISIBLE);
                view.radioButtonCircle.setBackgroundResource(R.drawable.ic_baseline_radio_button_unchecked_24);
                view.isActive.setVisibility(View.GONE);
            }
        }
    }

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

    //TODO
    public List<Alarm> getSelectedAlarms() {

        return null;
    }

    //TODO
    public void showDeleteList() {
        for (ViewHolder view : views) {
            view.radioButtonCircle.setVisibility(View.VISIBLE);
            view.radioButtonCircle.setBackgroundResource(R.drawable.ic_baseline_radio_button_unchecked_24);
            view.isActive.setVisibility(View.GONE);
        }
        typeView = TypeView.DELETE;
    }

    public void showMainList() {
        for (ViewHolder view : views) {
            view.radioButtonCircle.setVisibility(View.GONE);
            view.isActive.setVisibility(View.VISIBLE);
            //selectedAlarms.clear();
        }
        typeView = TypeView.NORMAL;
    }

    public Alarm getAlarm(int position) {
        return alarmsList.get(position);
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
