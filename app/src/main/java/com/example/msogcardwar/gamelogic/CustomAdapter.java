package com.example.msogcardwar.gamelogic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msogcardwar.R;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private final String[] scoresList;
    private final int[] icons;
    private final LayoutInflater inflater;

    public CustomAdapter(Context applicationContext, String[] scoresList, int[] icons) {
        this.scoresList = scoresList;
        this.icons = icons;
        this.inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return scoresList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = this.inflater.inflate(R.layout.listview_activity, null);
        TextView country = (TextView) view.findViewById(R.id.winners_LBL_scores);
        ImageView icon = (ImageView) view.findViewById(R.id.icons_IMG_winners);
        country.setText(scoresList[i]);
        icon.setImageResource(icons[i]);
        return view;
    }
}
