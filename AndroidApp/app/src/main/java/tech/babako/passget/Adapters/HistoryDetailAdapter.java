package tech.babako.passget.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import tech.babako.passget.R;

public class HistoryDetailAdapter extends RecyclerView.Adapter<HistoryDetailAdapter.HistoryDetailViewHolder> {
    private String[] nameData,idData,costData;

    @Override
    public HistoryDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_detail, parent, false);
        HistoryDetailViewHolder vh = new HistoryDetailViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(HistoryDetailViewHolder holder, int position) {
        holder.name.setText(nameData[position]);
        holder.id.setText(idData[position]);
        holder.cost.setText(costData[position]+ "₺");
        int hirka = R.drawable.ae9efd29;
        if (nameData[position].equals(("Martin Kot Pantolon"))) {
            int kot = R.drawable.kot;
            holder.product_image.setImageResource(kot);
        } else if (nameData[position].equals("V Yaka T-Shirt")) {
            int tshirt = R.drawable.c3647e63;
            holder.product_image.setImageResource(tshirt);
        } else
            holder.product_image.setImageResource(hirka);
    }

    @Override
    public int getItemCount() {
        if (nameData == null) return 0;
        return nameData.length;
    }

    public void setData(String[] name, String[] cost, String[] id) {
        nameData = name;
        costData = cost;
        idData = id;
        notifyDataSetChanged();
    }

    class HistoryDetailViewHolder extends RecyclerView.ViewHolder{
        TextView name, id, cost;
        ImageView product_image;

        public HistoryDetailViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_history_detail_item_name_value);
            id = itemView.findViewById(R.id.tv_history_detail_item_id_value);
            cost = itemView.findViewById(R.id.tv_history_detail_item_cost_value);
            product_image = itemView.findViewById(R.id.img_history_detail_item);
        }


    }
}
