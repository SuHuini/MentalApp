package i.app.menthelapp.chat;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import i.app.menthelapp.Counsellor;
import i.app.menthelapp.R;

public class CounsellorAdapter extends RecyclerView.Adapter<CounsellorAdapter.ViewHolder> {

    private Context context;
    private List<Counsellor> mCounsellors;
    String TAG = "Users";


    public CounsellorAdapter( Context context, List<Counsellor> mCounsellors) {
        this.context = context;
        this.mCounsellors = mCounsellors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Counsellor user = mCounsellors.get(position);
        holder.username.setText(user.getCounFName());
        Log.d(TAG, "onBindViewHolderExecuted!!!");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("userId", user.getCounEmail());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return mCounsellors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
        }
    }

}
