package com.example.adgal.astateliving.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adgal.astateliving.R;
import com.example.adgal.astateliving.fragments.RequestDriveFragment;
import com.example.adgal.astateliving.model.Drive;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by ADGAL on 4/29/2017.
 */

public class DriveAdRecyclerViewAdapter extends RecyclerView.Adapter<DriveAdRecyclerViewAdapter.ViewHolder>  {

    private List<Drive> drives;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName;
        TextView tvFrom;
        TextView tvTo;
        private Drive drive;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name_ad);
            tvFrom = (TextView) itemView.findViewById(R.id.tv_from);
            tvTo = (TextView) itemView.findViewById(R.id.tv_to);
            itemView.setOnClickListener(this);
        }

        public void bind(Drive drive) {
            this.drive = drive;
            tvName.setText(drive.getUid());
            tvFrom.setText(drive.getFrom());
            tvTo.setText(drive.getTo());
        }

        @Override
        public void onClick(View view) {
            AppCompatActivity context = (AppCompatActivity) view.getContext();
            String adCreaterEmail = drive.getEmail();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            String myEmail = firebaseAuth.getCurrentUser().getEmail();
            RequestDriveFragment requestDriveFragment;
            if(adCreaterEmail.equals(myEmail)){
                requestDriveFragment = RequestDriveFragment.newInstance(drive);
                FragmentManager fragmentManager = context.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, requestDriveFragment).commit();
            }
        }
    }

    public DriveAdRecyclerViewAdapter(List<Drive> drives) {
        this.drives = drives;
    }

    @Override
    public DriveAdRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drive_ad_item, parent, false);

        return new DriveAdRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DriveAdRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.bind(drives.get(position));
    }

    @Override
    public int getItemCount() {
        return drives.size();
    }

    public void updateList(List<Drive> drives) {
        // Allow recyclerview animations to complete normally if we already know about data changes
        if (drives.size() != this.drives.size() || !this.drives.containsAll(drives)) {
            this.drives = drives;
            notifyDataSetChanged();
        }
    }

    public void removeItem(int position) {
        drives.remove(position);
        notifyItemRemoved(position);
    }

    public Drive getItem(int position) {
        return drives.get(position);
    }
}