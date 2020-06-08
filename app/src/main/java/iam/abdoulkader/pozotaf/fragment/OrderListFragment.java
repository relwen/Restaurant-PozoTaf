package iam.abdoulkader.pozotaf.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.adapter.CartOrderViewAdapter;
import iam.abdoulkader.pozotaf.data.CartOrder;
import iam.abdoulkader.pozotaf.listener.CartOrderViewClickListener;
import iam.abdoulkader.pozotaf.util.CommonTask;
import iam.abdoulkader.pozotaf.util.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderListFragment extends BaseFragment {

    private ViewFlipper mCartOrderVF;
    private RecyclerView mCartOrderRV;
    private ArrayList<CartOrder> mCartOrders;
    private CartOrderViewAdapter mCartOrderAdapter;

    public OrderListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCartOrders = savedInstanceState.getParcelableArrayList(Constant.SAVED_FOOD_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        mContext = getContext();

        mCartOrderVF = (ViewFlipper) view.findViewById(R.id.order_viewflipper);
        mCartOrderRV = (RecyclerView) view.findViewById(R.id.order_recyclerview);

        mCartOrders = new ArrayList<>();
        mCartOrders.add(new CartOrder());
        mCartOrderAdapter = new CartOrderViewAdapter(mContext, mCartOrders, mCartOrderVF, new CartOrderViewClickListener() {
            @Override
            public void onCartOrderClickListener(int position, View view, CartOrder order) {

            }

            @Override
            public void onCartOrderLongClickListener(int position, View view, CartOrder order) {
                
            }
        });
        mCartOrderRV.setNestedScrollingEnabled(false);
        mCartOrderRV.setAdapter(mCartOrderAdapter);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}
