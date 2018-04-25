package tech.babako.passget.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import tech.babako.passget.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private String[] dateData, costsData, storeData;
    private String json;
    private final HistoryItemClickListener historyItemClickListener;


    public HistoryAdapter(HistoryItemClickListener historyItemClickListener) {
        this.historyItemClickListener = historyItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.date.setText(dateData[position]);
        holder.cost.setText(costsData[position]);
        holder.store.setText(storeData[position]);
        if(storeData[position].toLowerCase().equals("mor") || storeData[position].toLowerCase().equals("mavi")){
            holder.img_store.setImageResource(R.drawable.mor_logo);
        } else if(storeData[position].toLowerCase().equals("kara")){
            holder.img_store.setImageResource(R.drawable.kara_logo);
        } else if(storeData[position].toLowerCase().equals("avakado")){
            holder.img_store.setImageResource(R.drawable.avakado_logo);
        }
    }

    @Override
    public int getItemCount() {
        if (dateData == null)
            return 0;
        return dateData.length;
    }

    public void setData(String[][] history, String json) {
        dateData = history[0];
        costsData = history[1];
        storeData = history[2];
        this.json = json;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView date, cost, store;
        ImageView img_store;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tv_history_date);
            cost = itemView.findViewById(R.id.tv_history_cost);
            store = itemView.findViewById(R.id.tv_history_store);
            img_store = itemView.findViewById(R.id.img_history_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            try {
                historyItemClickListener.onHistoryClick(position, json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public interface HistoryItemClickListener {
        void onHistoryClick(int position, String data) throws JSONException;
    }
}
