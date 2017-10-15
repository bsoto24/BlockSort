package com.openlab.blocksort.presentation.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openlab.blocksort.data.entities.Block;
import com.openlab.blocksort.data.local.database.SQLiteManager;
import com.openlab.blocksort.presentation.activity.Activity2;
import com.openlab.blocksort.R;
import com.openlab.blocksort.data.local.session.SessionManager;
import com.openlab.blocksort.util.ActivityUtils;

import java.util.ArrayList;

/**
 * Created by Bryam Soto on 13/10/2017.
 */

public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.BlockVH> {

    private ArrayList<Block> blocks;
    private Activity activity;
    private int LAST_USE;
    private SQLiteManager db;

    public BlockAdapter(ArrayList<Block> blocks, Activity activity) {
        this.blocks = blocks;
        this.activity = activity;
        db = new SQLiteManager(activity);
        LAST_USE = SessionManager.getInstance(activity).getLastUse();
    }

    @Override
    public BlockVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BlockVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_block, parent, false));
    }

    @Override
    public void onBindViewHolder(final BlockVH holder, final int position) {

        final Block block = blocks.get(position);

        holder.tvClicks.setBackgroundColor(ContextCompat.getColor(activity, block.getColor()));
        holder.tvClicks.setText(Integer.toString(block.getClicks()));

        holder.tvClicks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Actualiza los valores de los clicks y la ultima posicion*/
                block.setClicks(block.getClicks() + 1);
                LAST_USE += 1;
                SessionManager.getInstance(activity).setLastUse(LAST_USE);
                block.setLastUse(LAST_USE);

                /*Muestra el valor*/
                holder.tvClicks.setText(Integer.toString(block.getClicks()));

                /*Guarda los nuevos valores en la db*/
                db.updateBlock(block);

                /*Verifica si el item tiene mas de 3 toques para activar
                la funcion de ordenamiento cuando vuelva a abrir la aplicación*/
                if (block.getClicks() >= 3){
                    SessionManager.getInstance(activity).setTimeToSort(true);
                }

                /*Inicia la otra actividad pasando como parámetro el bloque*/
                Bundle bundle = new Bundle();
                bundle.putSerializable("block", block);
                ActivityUtils.nextActivity(activity, bundle, Activity2.class, false);

                /*Inicia la animación de transicion*/
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

    }

    @Override
    public int getItemCount() {
        return blocks.size();
    }

    public class BlockVH extends RecyclerView.ViewHolder {

        TextView tvClicks;

        public BlockVH(View itemView) {
            super(itemView);

            tvClicks = (TextView) itemView.findViewById(R.id.tv_clicks);

        }
    }
}
