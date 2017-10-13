package com.openlab.blocksort.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.openlab.blocksort.model.Block;
import com.openlab.blocksort.R;

public class BlockActivity extends AppCompatActivity {

    private Block block;
    private LinearLayout lyBlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);
        showToolbar("", true);
        lyBlock = (LinearLayout) findViewById(R.id.ly_block);

        /*Recibe el bloque que fue pasado como parámetro para pintar la pantalla con su color*/
        block = (Block) getIntent().getExtras().getSerializable("block");
        lyBlock.setBackgroundColor(ContextCompat.getColor(this, block.getColor()));

    }

    /**
     * Método de configuracion del toolbar
     * @param title
     * @param upButton
     */
    public void showToolbar(String title, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
