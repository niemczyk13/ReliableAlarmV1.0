package com.example.alarmsoundview.activity.sound.select;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alarmsoundview.R;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class SelectSoundAdapter extends BaseAdapter {
    private final AlarmBuiltInSoundData data;
    private final LayoutInflater inflater;

    public SelectSoundAdapter(SelectSoundActivity selectSoundActivity, AlarmBuiltInSoundData data) {
        this.data = data;
        inflater = (LayoutInflater) ((Context) selectSoundActivity).getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.getSize();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        BuiltInSound sound = data.get(position);

        if (view == null) {
            view = inflater.inflate(R.layout.select_sound_list_item, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        setValueInViewHolder(viewHolder, sound);

        return view;
    }

    private void setValueInViewHolder(ViewHolder viewHolder, BuiltInSound sound) {
        viewHolder.check.setImageResource(getImageResource(sound.isChecked()));
        viewHolder.name.setText(sound.getName());
    }

    private int getImageResource(boolean checked) {
        if (checked) {
            return R.drawable.ic_baseline_check_box_24;
        } else {
            return R.drawable.ic_baseline_check_box_outline_blank_24;
        }
    }

    static class ViewHolder {
        ImageView check;
        TextView name;

        public ViewHolder(View view) {
            check = view.findViewById(R.id.check_image_view);
            name = view.findViewById(R.id.name_text_view);
        }
    }
}
