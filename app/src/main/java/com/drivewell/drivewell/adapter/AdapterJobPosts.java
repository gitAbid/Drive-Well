package com.drivewell.drivewell.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.model.JobsDataModel;
import com.drivewell.drivewell.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by abid on 3/26/18.
 */

public class AdapterJobPosts extends RecyclerView.Adapter<AdapterJobPosts.ViewHolder> {

    private Context context;
    private List<JobsDataModel> jobs;

    public AdapterJobPosts(Context context, List<JobsDataModel> jobs) {
        this.context = context;
        this.jobs = jobs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterJobPosts.ViewHolder(LayoutInflater.from(context).inflate(R.layout.jobs_posting_card_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.get().load(jobs.get(position).getUser_image()).placeholder(R.drawable.no_avatar).into(holder.profile);
        holder.mTitle.setText(jobs.get(position).getTitle());
        holder.mDescription.setText(jobs.get(position).getDescription());
        holder.mLocation.setText(jobs.get(position).getLocation());
        holder.mUserName.setText(jobs.get(position).getUser_name());
        holder.mJobCategory.setText(jobs.get(position).getJob_category());
        holder.mPostTime.setText(Utils.getTimeAgo(Long.parseLong(jobs.get(position).getTime_stamp())));
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle,mDescription,mLocation,mUserName,mJobCategory,mPostTime;
        CircleImageView profile;
        public ViewHolder(View itemView) {
            super(itemView);
            mTitle=itemView.findViewById(R.id.tvJobPostTitle);
            mDescription=itemView.findViewById(R.id.tvJobPostDescription);
            mLocation=itemView.findViewById(R.id.tvJobPostLocation);
            mUserName=itemView.findViewById(R.id.tvJobPostUserName);
            mJobCategory=itemView.findViewById(R.id.tvJobPostType);
            mPostTime=itemView.findViewById(R.id.tvJobPostTime);
            profile=itemView.findViewById(R.id.civJobPostUserProfileImage);
        }
    }
}
