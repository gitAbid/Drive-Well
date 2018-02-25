package com.drivewell.drivewell.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.model.DriverModel;

import java.util.List;

import cn.nekocode.badge.BadgeDrawable;

/**
 * Created by abid on 1/19/18.
 */

public class AdapterRankingLeaderBoard extends RecyclerView.Adapter<AdapterRankingLeaderBoard.ViewHolder> {
    private Context context;
    private List<DriverModel> driversList;
    boolean cardStatus=true;

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
        holder.mPoints.setText(String.valueOf(driversList.get(position).getPoints()+" points"));
    /*    final BadgeDrawable drawable =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_NUMBER)
                        .number(9)
                        .build();

        final BadgeDrawable drawable2 =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                        .badgeColor(0xff336699)
                        .text1("VIP")
                        .build();

        SpannableString spannableString =
                new SpannableString(TextUtils.concat(
                        "TextView ",
                        drawable.toSpannable(),
                        " ",
                        drawable2.toSpannable(),
                        " "
                ));*/
        if (position==0){
            holder.mRanking.setText("1st");
        }else if (position==1){
            holder.mRanking.setText("2nd");
        }else if (position==2){
            holder.mRanking.setText("3rd");
        }else {
            holder.mRanking.setText((position+1)+"th");
        }


        holder.mRankCard.setOnClickListener(e->{
           if (cardStatus==false){
               holder.mQuickInfoCard.setVisibility(View.VISIBLE);
               cardStatus=true;
           }else {
               cardStatus=false;
               holder.mQuickInfoCard.setVisibility(View.GONE);
           }
        });
    }


    @Override
    public int getItemCount() {
        return driversList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mFirstName,mPoints,mRanking;
        private CardView mQuickInfoCard,mRankCard;
        public ViewHolder(View itemView) {
            super(itemView);
            mFirstName=itemView.findViewById(R.id.tvLeaderboardMemberName);
            mPoints=itemView.findViewById(R.id.tvLeaderboardMemberPoints);
            mRanking=itemView.findViewById(R.id.tvLeaderboardMemberPosition);
            mQuickInfoCard=itemView.findViewById(R.id.cvRankingCardInfo);
            mRankCard=itemView.findViewById(R.id.cvRankingCard);

        }
    }
}
