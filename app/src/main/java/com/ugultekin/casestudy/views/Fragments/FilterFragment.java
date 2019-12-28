package com.ugultekin.casestudy.views.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ugultekin.casestudy.R;
import com.ugultekin.casestudy.adapter.CustomAdapter;
import com.ugultekin.casestudy.api.ApiClient;
import com.ugultekin.casestudy.helper.DialogHelper;
import com.ugultekin.casestudy.interfaces.ApiInterface;
import com.ugultekin.casestudy.interfaces.CustomItemClickListener;
import com.ugultekin.casestudy.model.Card;
import com.ugultekin.casestudy.views.Activities.KarakterDetayActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ugultekin.casestudy.views.Activities.KarakterDetayActivity.KARAKTER_DETAY_REQUEST;

public class FilterFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "FilterFragment";

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private ArrayList<Card> cardList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.filter_fragment, container, false);

        Spinner spinner_filter = view.findViewById(R.id.spinner_filter);

        setHasOptionsMenu(true);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_classs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_filter.setAdapter(adapter);
        spinner_filter.setOnItemSelectedListener(this);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_filter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String filter = adapterView.getItemAtPosition(i).toString();
//        Toast.makeText(adapterView.getContext(), filter , Toast.LENGTH_SHORT).show();

        getCardListFromRestApi(filter);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private void getCardListFromRestApi(String filter) {

        progressDialog = createProgressDialog(getActivity());

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


        Call<List<Card>> call = apiInterface.getCardClass(filter);
        call.enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {

                progressDialog.dismiss();
                if (response.body() != null) {
                    cardList = new ArrayList<>(response.body());
                    customAdapter = new CustomAdapter(getActivity(), cardList, new CustomItemClickListener() {
                        @Override
                        public void onItemClick(Card card, int position) {

                            Log.d(TAG, "onResponse: " + card.getName());

                            Intent intent = new Intent(getActivity(), KarakterDetayActivity.class);
                            intent.putExtra(KarakterDetayActivity.EXTRA_IMAGE, card.getImg());
                            intent.putExtra(KarakterDetayActivity.EXTRA_NAME, card.getName());
                            intent.putExtra(KarakterDetayActivity.EXTRA_PLAYERCLASS, card.getPlayerClass());
                            intent.putExtra(KarakterDetayActivity.EXTRA_RARITY, card.getRarity());
                            intent.putExtra(KarakterDetayActivity.EXTRA_TYPE, card.getType());
                            intent.putExtra(KarakterDetayActivity.EXTRA_COST, card.getCost());
                            intent.putExtra(KarakterDetayActivity.EXTRA_ATTACK, card.getAttack());
                            intent.putExtra(KarakterDetayActivity.EXTRA_HEALTH, card.getHealth());
                            intent.putExtra(KarakterDetayActivity.EXTRA_TEXT, card.getText());
                            intent.putExtra(KarakterDetayActivity.EXTRA_FLAVOR, card.getFlavor());

                            startActivityForResult(intent, KARAKTER_DETAY_REQUEST);

                        }
                    });
                    recyclerView.setAdapter(customAdapter);
                } else {
                    Log.d(TAG, "onResponse: CustomAdapter = Null ");
                }

            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {

                progressDialog.dismiss();
                DialogHelper.getAlertWithMessage("Hata", t.getMessage(), getActivity());
            }
        });
    }


    public ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_layout);
        return dialog;
    }
}
