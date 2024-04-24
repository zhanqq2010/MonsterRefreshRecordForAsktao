package com.douhuang.mrrecord.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.douhuang.mrrecord.R;
import com.douhuang.mrrecord.entity.MonsterRecord;

import java.util.List;

// RecyclerViewAdapter.java
public class MonsterRefreshRecordRecyclerViewAdapter extends RecyclerView.Adapter<MonsterRefreshRecordRecyclerViewAdapter.MonsterRefreshRecordViewHolder> {
    private final List<MonsterRecord> monsterList;

    private static final int BASE_ITEM_TYPE_HEADER = 100000;

    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat mHeaderViews = new SparseArrayCompat<>();

    private SparseArrayCompat mFootViews = new SparseArrayCompat<>();



    public MonsterRefreshRecordRecyclerViewAdapter(List<MonsterRecord> monsterList) {
        this.monsterList = monsterList;
    }

    @NonNull
    @Override
    public MonsterRefreshRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monster_refresh_record, parent, false);
        return new MonsterRefreshRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonsterRefreshRecordViewHolder holder, int position) {
        // Bind data to views

        final int pos = position;
        MonsterRecord record = monsterList.get(position);
        if (record == null) {
            return;
        }
//        holder.tvIndex.setText(String.valueOf(record.getIndex()));
        holder.tvCircuitName.setText(String.valueOf(record.getCircuit()));
        holder.tvMonsterName.setText(record.getMonsterName());
        holder.tvRefreshTime.setText(String.valueOf(record.getMonsterRefreshTime()));

        // Set click listener for delete button
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle delete button click for the specific item
                monsterList.remove(pos);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return monsterList.size();
    }

    public static class MonsterRefreshRecordViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex;
        TextView tvMonsterName;
        TextView tvCircuitName;
        TextView tvRefreshTime;
        Button btnDelete;

        public MonsterRefreshRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tvIndex);
            tvMonsterName = itemView.findViewById(R.id.tvMonsterName);
            tvRefreshTime = itemView.findViewById(R.id.tvRefreshTime);
            tvCircuitName = itemView.findViewById(R.id.tvCircuitName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }



}
