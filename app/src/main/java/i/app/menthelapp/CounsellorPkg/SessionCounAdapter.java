package i.app.menthelapp.CounsellorPkg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import i.app.menthelapp.Adapters.SessionsAdapter;
import i.app.menthelapp.R;
import i.app.menthelapp.Session;

public class SessionCounAdapter extends RecyclerView.Adapter<SessionCounAdapter.ViewHolder> {

    private Context context;
    private List<Session> mSessions;
    String TAG = "Sessions";

    public SessionCounAdapter(Context context, List<Session> mSessions) {
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
    public void onBindViewHolder(@NonNull SessionCounAdapter.ViewHolder holder, int position) {
        Session sess = mSessions.get(position);
        //sess = mSessions.get(position);
        holder.clientName.setText(sess.getClientName());
        holder.counName.setText(sess.getCounName());
        holder.date.setText(sess.getDate());
        Log.d(TAG, "onBindViewHolderExecuted!!!");

    }

    @Override
    public int getItemCount() {

        return mSessions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView clientName;
        public TextView counName;
        public TextView date;


//      Button att1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clientName = itemView.findViewById(R.id.clientName);
            counName = itemView.findViewById(R.id.counName);
            date = itemView.findViewById(R.id.date);
//            //rg = itemView.findViewById(R.id.rg_attending);
//            att = itemView.findViewById(R.id.btn_att);
//            att1 =  itemView.findViewById(R.id.btn_att1);

        }
    }

}
