package com.example.github_commits.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.github_commits.R;
import com.example.github_commits.databinding.ItemCommitBinding;
import com.example.github_commits.domain.CommitResponse;
import com.example.github_commits.utils.TimeUtils;


import java.util.ArrayList;

import java.util.List;

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.CommitVH> {

    private final List<CommitResponse> mCommitResponse;
    private final Context mContext;
    private final ItemClickListener listener;

    public CommitAdapter(Context context, ItemClickListener itemClickListener) {
        this.mContext = context;
        this.listener = itemClickListener;
        this.mCommitResponse = new ArrayList<>();
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public CommitVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCommitBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_commit, parent, false);
        return new CommitVH(binding);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull CommitVH holder, int position) {
        holder.bind(mCommitResponse.get(position));
    }

    @Override
    public int getItemCount() {
        return mCommitResponse.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItem(CommitResponse item) {
        mCommitResponse.add(item);
        notifyDataSetChanged();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void clear() {
        mCommitResponse.clear();
        notifyDataSetChanged();
    }

    class CommitVH extends RecyclerView.ViewHolder {
        private final ItemCommitBinding mBinding;

        public CommitVH(@NonNull ItemCommitBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        void bind(final CommitResponse item) {
            if (item == null)
                return;
            String message = item.getCommit().getMessage();
            if (!message.isEmpty()) {
                String commit = message.substring(0, message.indexOf('('));
                mBinding.tvCommitTitle.setText(commit);
            }
            mBinding.tvCommitterName.setText(item.getCommit().getAuthor().getName());
            String date = item.getCommit().getAuthor().getDate();
            String time = TimeUtils.getFormattedTime(date);
            mBinding.tvCommitTime.setText(time);

            if (mContext != null)
                Glide.with(mContext)
                        .load(item.getAuthor().getAvatarUrl())
                        .centerCrop()
                        .transform(new RoundedCorners(600))
                        .placeholder(R.drawable.ic_user_avatar)
                        .into(mBinding.ivCommitterPic);
            mBinding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onGetItem(item);
                }
            });
        }
    }

    public interface ItemClickListener {
        void onGetItem(CommitResponse item);
    }
}

