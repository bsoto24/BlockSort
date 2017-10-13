package com.openlab.blocksort.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.openlab.blocksort.database.LocalData;
import com.openlab.blocksort.R;
import com.openlab.blocksort.database.SQLiteManager;
import com.openlab.blocksort.session.SessionManager;
import com.openlab.blocksort.adapter.BlockAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvBlocks;
    private SessionManager sessionMananger;
    private SQLiteManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionMananger = SessionManager.getInstance(this);

        /*Inicializa los datos de los bloques*/
        if (!sessionMananger.isLoadData()) {
            sessionMananger.setLoadData(true);
            LocalData.load(this);
        }

        db = new SQLiteManager(this);

        /*Configuracion el ReciclerView*/
        rvBlocks = (RecyclerView) findViewById(R.id.rv_blocks);
        rvBlocks.setLayoutManager(new LinearLayoutManager(this));
        /*Configuracion del adaptador, pasando como parametro la lista de bloques registrados en la db*/
        rvBlocks.setAdapter(new BlockAdapter(db.getBlock(), this));
    }

}
