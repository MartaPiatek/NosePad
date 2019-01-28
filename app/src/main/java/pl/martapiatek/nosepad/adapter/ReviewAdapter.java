package pl.martapiatek.nosepad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pl.martapiatek.nosepad.R;
import pl.martapiatek.nosepad.model.Review;

public class ReviewAdapter extends ArrayAdapter<Review> {

    private ArrayList<Review> list;
    private Context context;
    private TextView txtBrand, txtFragrance, txtRating;

    public ReviewAdapter(Context context, ArrayList<Review> list) {
        super(context, 0, list);
        this.context = context;
        this.list = new ArrayList<>();
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.row_review, parent, false
            );
        }

        final Review currentReview = getItem(position);

        txtBrand = (TextView) listItemView.findViewById(R.id.txtBrand);
        txtFragrance = (TextView) listItemView.findViewById(R.id.txtFragrance);
        txtRating = (TextView) listItemView.findViewById(R.id.txtRating);

        txtBrand.setText(currentReview.getBrand());
        txtFragrance.setText(currentReview.getFragrance());
        txtRating.setText(String.valueOf(currentReview.getRating()));

        return listItemView;

    }
}
