package com.example.trsmis.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trsmis.R;
import com.example.trsmis.databinding.ItemTrsmisBinding;
import com.example.trsmis.model.Trsmis;
import com.example.trsmis.ui.adapter.viewholder.TrsmisViewHolder;
import com.example.trsmis.ui.listener.TrsmisItemClickListener;

import java.util.ArrayList;

public class TrsmisAdapter extends RecyclerView.Adapter<TrsmisViewHolder> {

    private ArrayList<Trsmis> mModels;
    private int mTrsmisCnt;
    private TrsmisItemClickListener mListener;

    public TrsmisAdapter() { //TODO
        if (mModels == null) {
            mModels = new ArrayList<>();
        }
        mTrsmisCnt = 0;
    }

    public void setListener(TrsmisItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public TrsmisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTrsmisBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_trsmis, parent, false);
        return new TrsmisViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrsmisViewHolder holder, int position) {
        holder.onBindView(mModels.get(position), mTrsmisCnt - position, mListener);
    }

    public void addItem(ArrayList<Trsmis> models) { //TODO
        mModels.addAll(models);
        notifyItemRangeChanged(mModels.size() - 1, models.size());
    }

    public void removeAllItem() { //TODO
        mModels.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }
}
