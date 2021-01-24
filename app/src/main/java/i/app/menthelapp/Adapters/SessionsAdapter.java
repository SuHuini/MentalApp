package i.app.menthelapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import i.app.menthelapp.R;
import i.app.menthelapp.Session;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.ViewHolder> {

    private Context context;
    private List<Session> mSessions;
    String TAG = "Sessions";

    public SessionsAdapter(Context context, List<Session> mSessions) {
        this.context = context;
        this.mSessions = mSessions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_session, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Session sess = mSessions.get(position);
        //sess = mSessions.get(position);
        holder.clientName.setText(sess.getClientName());
        holder.counName.setText(sess.getCounName());
        holder.date.setText(sess.getDate());
        Log.d(TAG, "onBindViewHolderExecuted!!!");
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, MessageActivity.class);
//                intent.putExtra("userId", user.getId());
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {

        return mSessions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView clientName;
        public TextView counName;
        public TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clientName = itemView.findViewById(R.id.clientName);
            counName = itemView.findViewById(R.id.counName);
            date = itemView.findViewById(R.id.date);
        }
    }

}
