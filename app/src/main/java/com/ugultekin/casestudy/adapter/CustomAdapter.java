package com.ugultekin.casestudy.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ugultekin.casestudy.R;
import com.ugultekin.casestudy.interfaces.CustomItemClickListener;
import com.ugultekin.casestudy.model.Card;
import com.ugultekin.casestudy.views.Activities.KarakterDetayActivity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    private ArrayList<Card> cardList;
    private ArrayList<Card> filteredCardList;
    private Context context;
    private CustomItemClickListener customItemClickListener;

    public CustomAdapter(Context context, ArrayList<Card> cardArrayList, CustomItemClickListener customItemClickListener) {
        this.context = context;
        this.cardList = cardArrayList;
        this.filteredCardList = cardArrayList;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for click item listener
                customItemClickListener.onItemClick(filteredCardList.get(myViewHolder.getAdapterPosition()), myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }

    private String htmlToString(String text) {


        if (text != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                return Html.fromHtml(replaceText(text), Html.FROM_HTML_MODE_LEGACY).toString();

            } else {

                return Html.fromHtml(replaceText(text)).toString();

            }
        } else {
            return "";
        }

    }

    private String replaceText(String text) {

        if (text != null) {
            return text.replace("\\n", " ");

        } else {
            return "";
        }
    }

    @Override
    public void onBindViewHolder(CustomAdapter.MyViewHolder viewHolder, int position) {


        viewHolder.cartTitle.setText(htmlToString(filteredCardList.get(position).getName()));
        viewHolder.cardText.setText(htmlToString(filteredCardList.get(position).getText()));

        // if image has not found show hearhstone.png
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.hearthstone)
                .error(R.drawable.hearthstone);

        try {
            URL url_image = new URL(filteredCardList.get(position).getImg());

            Glide.with(viewHolder.itemView)
                    .load(url_image)
                    .apply(options)
                    .into(viewHolder.img_view);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
//        return filteredCardList.size();

        // show ten item or less
        if (filteredCardList.size() > 10) {
            return 10;
        } else {
            return filteredCardList.size();
        }
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    filteredCardList = cardList;

                } else {

                    if (searchString.length() > 2) {

                        ArrayList<Card> tempFilteredList = new ArrayList<>();

                        for (Card card : cardList) {

                            // search for name
                            if (card.getName().toLowerCase().contains(searchString)) {

                                tempFilteredList.add(card);
                            }
                        }

                        filteredCardList = tempFilteredList;

                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredCardList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredCardList = (ArrayList<Card>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView cartTitle;
        private TextView cardText;
        private ImageView img_view;

        public MyViewHolder(View view) {
            super(view);
            cartTitle = (TextView) view.findViewById(R.id.card_title);
            cardText = (TextView) view.findViewById(R.id.card_text);
            img_view = (ImageView) view.findViewById(R.id.img_view);


        }
    }


}