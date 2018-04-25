package tech.babako.passget.Fragments;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import tech.babako.passget.Adapters.OffersAdapter;
import tech.babako.passget.OfferDetail;
import tech.babako.passget.R;


public class OffersFragment extends Fragment implements OffersAdapter.OfferItemClickListener {

    RecyclerView rv_offers;
    OffersAdapter offersAdapter;
    String[] titles = {"MOR'dan 25% indirim", "KARA'dan +3 taksit fırsatı", "AVAKADO mağazalarında taksit erteleme fırsatı!"}, details = {"250₺ üzeri her alışverişe 25% indirim", "VASA ve Mister kartlara özel fırsat", "Şimdi alın öderseniz Ekim'e kadar ödeyin"};
    int[] images = {R.drawable.mor_logo, R.drawable.kara_logo, R.drawable.avakado_logo};

    public OffersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        rv_offers = view.findViewById(R.id.rv_offers);
        rv_offers.setHasFixedSize(false);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv_offers.setLayoutManager(llm);

        offersAdapter = new OffersAdapter(this);
        rv_offers.setAdapter(offersAdapter);
        offersAdapter.setData(titles, details, images);

        return view;
    }

    @Override
    public void onOfferClick(int index, ImageView imageView) {
        Intent intent = new Intent(getActivity(), OfferDetail.class);
        intent.putExtra(Intent.EXTRA_TITLE, titles[index]);
        intent.putExtra(Intent.EXTRA_TEXT, details[index]);
        intent.putExtra("offer_image", images[index]);
        intent.putExtra("TRANSITION",ViewCompat.getTransitionName(imageView));
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(),imageView, ViewCompat.getTransitionName(imageView));
        startActivity(intent, options.toBundle());
    }
}
