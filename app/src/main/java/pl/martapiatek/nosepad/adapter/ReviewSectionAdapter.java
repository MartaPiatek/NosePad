package pl.martapiatek.nosepad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pl.martapiatek.nosepad.R;
import pl.martapiatek.nosepad.model.Review;

public class ReviewSectionAdapter extends BaseAdapter {

    private static final int REVIEW_ITEM = 0;
    private static final int HEADER = 1;
    ArrayList<Object> list;
    private LayoutInflater inflater;

    public ReviewSectionAdapter(Context context, ArrayList<Object> list) {
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {

        if (list.get(position) instanceof Review) {
            return REVIEW_ITEM;
        } else {
            return HEADER;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {

            switch (getItemViewType(i)) {
                case REVIEW_ITEM:
                    view = inflater.inflate(R.layout.row_review, null);
                    break;

                case HEADER:
                    view = inflater.inflate(R.layout.row_review_header, null);
                    break;
            }
        }

        switch (getItemViewType(i)) {
            case REVIEW_ITEM:
                TextView txtBrand, txtFragrance, txtRating;
                txtBrand = (TextView) view.findViewById(R.id.txtBrand);
                txtFragrance = (TextView) view.findViewById(R.id.txtFragrance);
                txtRating = (TextView) view.findViewById(R.id.txtRating);

                final Review currentReview = (Review) list.get(i);

                txtBrand.setText(currentReview.getBrand());
                txtFragrance.setText(currentReview.getFragrance());
                txtRating.setText(String.valueOf(currentReview.getRating()));

                break;

            case HEADER:
                TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);

                txtTitle.setText((String) list.get(i));
                break;
        }
        return view;

    }
}
