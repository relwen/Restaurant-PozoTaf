package iam.abdoulkader.pozotaf.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.thebrownarrow.customfont.CustomFontButton;
import com.thebrownarrow.customfont.CustomFontTextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import iam.abdoulkader.pozotaf.R;
import iam.abdoulkader.pozotaf.data.Resto;
import iam.abdoulkader.pozotaf.listener.RestoViewClickListener;
import iam.abdoulkader.pozotaf.service.PozotafWebService;
import iam.abdoulkader.pozotaf.util.CommonIntents;

/**
 * Created by root on 31/03/18.
 */

public class RestoViewAdapter extends RecyclerView.Adapter<RestoViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Resto> mRestos;
    private ViewFlipper mViewFlipper;
    private static RestoViewClickListener mRestoViewClickListener;

    public RestoViewAdapter(Context mContext, ArrayList<Resto> mRestos,
                            ViewFlipper mViewFlipper, RestoViewClickListener listener) {
        this.mContext = mContext;
        this.mRestos = mRestos;
        this.mViewFlipper = mViewFlipper;
        RestoViewAdapter.mRestoViewClickListener = listener;

        if(mRestos.size() > 0)
            this.mViewFlipper.setDisplayedChild(2);
        else
            this.mViewFlipper.setDisplayedChild(1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.resto_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Resto resto = mRestos.get(position);
        holder.bind(resto);
    }

    @Override
    public int getItemCount() {
        return mRestos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {


        private final ImageView image;
        private final CustomFontTextView name;
        private final CustomFontTextView desc;
        private final CustomFontButton phone1;
        private final CustomFontButton phone2;
        private final CustomFontButton email;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            name = (CustomFontTextView) itemView.findViewById(R.id.name);
            desc = (CustomFontTextView) itemView.findViewById(R.id.desc);
            phone1 = (CustomFontButton) itemView.findViewById(R.id.phone1);
            phone2 = (CustomFontButton) itemView.findViewById(R.id.phone2);
            email = (CustomFontButton) itemView.findViewById(R.id.email);

            if(RestoViewAdapter.mRestoViewClickListener != null) {
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

        }

        public void bind(final Resto resto) {
            Glide.with(mContext).load(PozotafWebService.ENDPOINT + PozotafWebService.TARGET + "/" + resto.image).into(image);
            name.setText(resto.name);
            desc.setText(resto.desc);
            phone1.setText(resto.phone1);
            phone2.setText(resto.phone2);
            email.setText(resto.email);
            if(resto.phone1.isEmpty()) {
                phone1.setVisibility(View.GONE);
            } else {
                phone1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CommonIntents.dialPhoneNumber(mContext, resto.phone1);
                    }
                });
            }
            if (resto.phone2.isEmpty()) {
                phone2.setVisibility(View.GONE);
            } else {
                phone2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CommonIntents.dialPhoneNumber(mContext, resto.phone2);
                    }
                });
            }
            if (resto.email.isEmpty()) {
                email.setVisibility(View.GONE);
            }  else {
                email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String[] emails = {resto.email};
                        CommonIntents.composeEmail(mContext, emails, "");
                    }
                });
            }

            /*navigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + resto.latitude + "," + resto.longitude);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    mContext.startActivity(mapIntent);
                }
            });*/
        }


        @Override
        public void onClick(View view) {
            mRestoViewClickListener.onRestoClickListener(getAdapterPosition(), view, mRestos.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            mRestoViewClickListener.onRestoLongClickListener(getAdapterPosition(), view, mRestos.get(getAdapterPosition()));
            return false;
        }
    }
}
