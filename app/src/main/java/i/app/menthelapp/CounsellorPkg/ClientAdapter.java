package i.app.menthelapp.CounsellorPkg;

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
import i.app.menthelapp.Client;
import i.app.menthelapp.chat.ChatActivity;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {

    private Context context;
    private List<Client> mClients;
    String TAG = "ClientAdapter";

    public ClientAdapter(Context context, List<Client> mClients) {
        this.context = context;
        this.mClients = mClients;
    }

    @NonNull
    @Override
    public ClientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_user, parent, false);
        return new ClientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientAdapter.ViewHolder holder, int position) {
        final Client client = mClients.get(position);

        holder.clientFName.setText(client.getClientFName());
        holder.clientSName.setText(client.getClientSName());
        holder.email.setText(client.getClientEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("userId", client.getId());
                context.startActivity(intent);
            }
        });


        Log.d(TAG, "onBindViewHolderExecuted!!!");

    }

    @Override
    public int getItemCount() {

        return mClients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView clientFName;
        public TextView clientSName;
        public TextView email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clientFName = itemView.findViewById(R.id.userName);
            clientSName = itemView.findViewById(R.id.userInfo1);
            email = itemView.findViewById(R.id.userInfo2);
        }
    }


}
