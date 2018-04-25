package tech.babako.passget.Adapters;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import tech.babako.passget.R;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder> {
    private String[] offerTitles, offerDetails;
    private int[] offerImages;
    private Context context;
    private final OfferItemClickListener offerItemClickListener;

    public OffersAdapter(OfferItemClickListener clickListener) {
        offerItemClickListener = clickListener;
    }

    @Override
    public OffersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer, parent, false);
        context = parent.getContext();
        return new OffersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OffersViewHolder holder, int position) {
        holder.offerTitle.setText(offerTitles[position]);
        holder.offerDetail.setText(offerDetails[position]);
        holder.offerImage.setImageDrawable(context.getDrawable(offerImages[position]));

        ViewCompat.setTransitionName(holder.offerImage,"animation_offer"+position);
    }

    @Override
    public int getItemCount() {
        if (offerTitles == null)
            return 0;
        return offerDetails.length;
    }

    public void setData(String[] titles, String[] details, int[] images) {
        offerTitles = titles;
        offerDetails = details;
        offerImages = images;
        notifyDataSetChanged();
    }

    class OffersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView offerTitle, offerDetail;
        ImageView offerImage;

        OffersViewHolder(View itemView) {
            super(itemView);
            offerTitle = itemView.findViewById(R.id.offer_title);
            offerDetail = itemView.findViewById(R.id.offer_details);
            offerImage = itemView.findViewById(R.id.offer_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            offerItemClickListener.onOfferClick(position, offerImage);
        }
    }

    public interface OfferItemClickListener {
        void onOfferClick(int index, ImageView imageView);
    }
}
