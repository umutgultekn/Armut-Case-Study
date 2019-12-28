package com.ugultekin.casestudy.views.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ugultekin.casestudy.views.Activities.KarakterDetayActivity;
import com.ugultekin.casestudy.R;
import com.ugultekin.casestudy.adapter.CustomAdapter;
import com.ugultekin.casestudy.api.ApiClient;
import com.ugultekin.casestudy.helper.DialogHelper;
import com.ugultekin.casestudy.interfaces.ApiInterface;
import com.ugultekin.casestudy.interfaces.CustomItemClickListener;
import com.ugultekin.casestudy.model.Card;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ugultekin.casestudy.views.Activities.KarakterDetayActivity.KARAKTER_DETAY_REQUEST;


public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private ArrayList<Card> cardList;
    private EditText edit_text_search;
    private String search;
    private ImageButton btn_search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_fragment, container, false);

        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_search);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getCardListFromRestApi("Saron");

        btn_search = (ImageButton) view.findViewById(R.id.btn_search);

        edit_text_search = (EditText) view.findViewById(R.id.edit_text_search);

        edit_text_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

//                String text = editable.toString().toLowerCase();
//
//                int search_lenght = text.length();
//
//                if (search_lenght > 2) {
//
//                    search = editable.toString();
//
//                    getCardListFromRestApi(editable.toString());
//
//                }
//                else {
//
//                    Toast.makeText(getActivity(), "Arama yapabilmek için 3 veya daha fazla karakter girmelisiniz!", Toast.LENGTH_SHORT).show();
//                }
            }
        });


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = edit_text_search.getText().toString();

                if (text.length() > 2) {

                    getCardListFromRestApi(text);

                    filter(text);

                } else {
                    Toast.makeText(getActivity(), "Arama yapabilmek için 3 veya daha fazla karakter girmelisiniz!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        setupUI(view.findViewById(R.id.linearLayout));
        return view;
    }

    private static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }


    private void filter(String text) {
        customAdapter.getFilter().filter(text);
    }


    private void getCardListFromRestApi(String search) {


        progressDialog = createProgressDialog(getActivity());

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


        Call<List<Card>> call = apiInterface.getCards(search);
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
