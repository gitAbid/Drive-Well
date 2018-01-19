package com.drivewell.drivewell.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.model.DriverModel;

import java.util.List;

/**
 * Created by abid on 1/19/18.
 */

public class AdapterRankingLeaderBoard extends RecyclerView.Adapter<AdapterRankingLeaderBoard.ViewHolder> {
    private Context context;
    private List<DriverModel> driversList;

    public AdapterRankingLeaderBoard(Context context, List<DriverModel> driversList) {
        this.context = context;
        this.driversList = driversList;
    }

    public AdapterRankingLeaderBoard(Context context) {
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.ranking_adapter_leaderboard_member_card,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mFirstName.setText(driversList.get(position).getFirstname());
        holder.mPoints.setText(String.valueOf(driversList.get(position).getPoints()));
    }

    @Override
    public int getItemCount() {
        return driversList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mFirstName,mPoints;
        public ViewHolder(View itemView) {
            super(itemView);
            mFirstName=itemView.findViewById(R.id.tvLeaderboardMemberName);
            mPoints=itemView.findViewById(R.id.tvLeaderboardMemberPoints);

        }
    }
}
