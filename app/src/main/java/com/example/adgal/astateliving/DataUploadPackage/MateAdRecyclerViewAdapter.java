package com.example.adgal.astateliving.DataUploadPackage;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adgal.astateliving.R;
import com.example.adgal.astateliving.fragments.RoomMateFragment;
import com.example.adgal.astateliving.model.RoomMate;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MateAdRecyclerViewAdapter extends RecyclerView.Adapter<MateAdRecyclerViewAdapter.ViewHolder> {

    private List<RoomMate> roomMates;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView;
        TextView descriptionTextView;
        TextView rentTextView;
        private RoomMate roomMate;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.note_title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.note_description);
            rentTextView = (TextView) itemView.findViewById(R.id.etRent);
            itemView.setOnClickListener(this);
        }

        public void bind(RoomMate roomMate) {
            this.roomMate = roomMate;
            titleTextView.setText(roomMate.getTitle());
            descriptionTextView.setText(roomMate.getDescription());
//            rentTextView.setText(Integer.toString(roomMate.getRent()));
        }

        @Override
        public void onClick(View view) {
            AppCompatActivity context = (AppCompatActivity) view.getContext();
            String email = roomMate.getGoogleUid();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            String myEmail = firebaseAuth.getCurrentUser().getEmail();
            if(email.equals(myEmail)){
                RoomMateFragment roomMateFragment = RoomMateFragment.newInstance(roomMate);
                FragmentManager fragmentManager = context.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, roomMateFragment).commit();
            }
            else
                Toast.makeText(view.getContext(), "note email="+email+"now user="+myEmail,Toast.LENGTH_LONG).show();
        }
    }

    public MateAdRecyclerViewAdapter(List<RoomMate> roomMates) {
        this.roomMates = roomMates;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(roomMates.get(position));
    }

    @Override
    public int getItemCount() {
        return roomMates.size();
    }

    public void updateList(List<RoomMate> roomMates) {
        // Allow recyclerview animations to complete normally if we already know about data changes
        if (roomMates.size() != this.roomMates.size() || !this.roomMates.containsAll(roomMates)) {
            this.roomMates = roomMates;
            notifyDataSetChanged();
        }
    }

    public void removeItem(int position) {
        roomMates.remove(position);
        notifyItemRemoved(position);
    }

    public RoomMate getItem(int position) {
        return roomMates.get(position);
    }
}
