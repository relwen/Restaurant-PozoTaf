package iam.abdoulkader.pozotaf.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.sql.SQLException;
import java.util.ArrayList;

import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.adapter.RestoViewAdapter;
import iam.abdoulkader.pozotaf.data.Resto;
import iam.abdoulkader.pozotaf.listener.RestoViewClickListener;
import iam.abdoulkader.pozotaf.util.CommonTask;
import iam.abdoulkader.pozotaf.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestoListFragment extends BaseFragment {
    
    private ViewFlipper mRestoVF;
    private RecyclerView mRestoRV;
    private ArrayList<Resto> mRestos;
    private RestoViewAdapter mRestoAdapter;

    public RestoListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mRestos = savedInstanceState.getParcelableArrayList(Constant.SAVED_FOOD_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resto_list, container, false);
        mContext = getContext();

        mRestoVF = (ViewFlipper) view.findViewById(R.id.resto_viewflipper);
        mRestoRV = (RecyclerView) view.findViewById(R.id.resto_recyclerview);

        mRestos = new ArrayList<>();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getRestos();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    private void getRestos() {

        try {
            mRestos = (ArrayList<Resto>) getDatabaseHelper().getRestoDao().queryForAll();
            mRestoAdapter = new RestoViewAdapter(mContext, mRestos, mRestoVF, null);
            mRestoRV.setNestedScrollingEnabled(false);
            mRestoRV.setAdapter(mRestoAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
            mRestoVF.setDisplayedChild(1);
        }
    }
}
