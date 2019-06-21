package com.example.macbookair.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapterTwo extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemname;
    private final ArrayList<Integer> itemprice;
    private final ArrayList<Float> itemrate;
    private final ArrayList<String> itembusy;

    public CustomListAdapterTwo(Activity context, ArrayList<String> itemname, ArrayList<Integer> itemprice ,ArrayList<Float> itemrate ,
                                ArrayList<String> itembusy) {
        super(context, R.layout.list, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.itemprice=itemprice;
        this.itemrate=itemrate;
        this.itembusy=itembusy;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_two, null,true);



        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        TextView txtPrice = (TextView) rowView.findViewById(R.id.item2);
        RatingBar ratingBar = (RatingBar) rowView.findViewById(R.id.rating);
        ImageView img = (ImageView) rowView.findViewById(R.id.icontwo) ;


        txtTitle.setText(itemname.get(position));
        txtPrice.setText(String.valueOf(itemprice.get(position)) + " LE/HR ");
        ratingBar.setRating(itemrate.get(position));

        if (itembusy.get(position).equals("red"))
        {
         img.setImageResource(R.color.red);
        }
        if (itembusy.get(position).equals("green"))
        {
            img.setImageResource(R.color.green);

        }

        return rowView;

    }
}