package iam.abdoulkader.pozotaf.listener;

import android.view.View;

import iam.abdoulkader.pozotaf.data.Resto;


/**
 * Created by root on 31/03/18.
 */

public interface RestoViewClickListener {

    public void onRestoClickListener(int position, View view, Resto resto);
    public void onRestoLongClickListener(int position, View view, Resto resto);
}
