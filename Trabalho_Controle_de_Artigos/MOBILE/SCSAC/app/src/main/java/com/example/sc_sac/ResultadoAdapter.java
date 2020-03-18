package com.example.sc_sac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sc_sac.bean.Artigo;
import com.example.sc_sac.database.SacDataSource;

import java.util.List;

public class ResultadoAdapter extends RecyclerView.Adapter<ResultadoAdapter.MyViewHolder>{

    private List<Artigo> artigoList;
    private Activity activity;
    private SacDataSource dataSource;

    public ResultadoAdapter(List<Artigo> artigoList, Activity activity) {
        this.artigoList = artigoList;
        this.activity = activity;
        dataSource = new SacDataSource(activity);
    }

    @NonNull
    @Override
    public ResultadoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_resultado, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultadoAdapter.MyViewHolder holder, int position) {
        holder.nomeArtigo.setText(artigoList.get(position).getArtigoNome());
    }

    @Override
    public int getItemCount() {
        return artigoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nomeArtigo = (TextView) itemView.findViewById(R.id.artigo_title);

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    int position = getLayoutPosition();
                    bundle.putInt(DetailsArtigoActivity.ARTIGO_ID, artigoList.get(position).getArtigoId());
                    Intent i = new Intent(activity, DetailsArtigoActivity.class);
                    i.putExtras(bundle);
                    activity.startActivity(i);
                }
            });
        }
    }
}
