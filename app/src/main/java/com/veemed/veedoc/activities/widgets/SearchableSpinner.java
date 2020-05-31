package com.veemed.veedoc.activities.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.veemed.veedoc.R;
import java.util.ArrayList;
import java.util.List;

public class SearchableSpinner extends LinearLayout implements View.OnClickListener {

    private TextView tvValues;
    private View parentView;
    private List<KeyValuePair> items;

    public SearchableSpinner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.activity_searchable_spinner2, null);
        addView(parentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tvValues = parentView.findViewById(R.id.tvValues);
        setClickable(true);
        setOnClickListener(this);
    }

    public void setItems(List<KeyValuePair> items) {
        this.items = items;
    }

    public List<KeyValuePair> getSelectedItems() {
        List<KeyValuePair> toReturn = new ArrayList<>();
        for(KeyValuePair p: items) {
            if(p.isChecked()) {
                toReturn.add(p);
            }
        }

        return toReturn;
    }

    @Override
    public void onClick(View view) {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        SearchableSpinnerDialog newFragment = new SearchableSpinnerDialog(items);
        newFragment.show(activity.getSupportFragmentManager(), "TAG");
    }

    public void setError(String string) {
        tvValues.setError(string);
    }
}
