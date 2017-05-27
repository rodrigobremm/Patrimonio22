package com.example.iossenac.patrimonio;

import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Patrimonio>> {
    DatabaseHelper dbHelper= null;
    AlertDialog alertDialog = null;
    PatrimonioAdapter patAdapter;
    UIElements ui;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        ui = new UIElements(this);
        ui.hideUpdateSpecificComponents();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    public void savePatrimonio(View v) {
        switch (v.getId()) {
            case R.id.save_btn: {
                if(validate()) {
                    Patrimonio patrimonio = new Patrimonio();
                    patrimonio.setSala(Integer.parseInt(ui.editSala.getText().toString()));
                    patrimonio.setCadeiras(Integer.parseInt(ui.editCadeiras.getText().toString()));
                    patrimonio.setMesas(Integer.parseInt(ui.editMesas.getText().toString()));
                    patrimonio.setProjetores(Integer.parseInt(ui.editProjetores.getText().toString()));
                    dbHelper.insertData(patrimonio);
                    singleButtonAlert(this,"Sala salva com sucesso.");
                    ui.resetInputComponents();
                } else {
                    singleButtonAlert(this, "Preencha todos os campos!");
                }
            }
        }
    }
    public void updatePatrimonio(View v) {
        switch (v.getId()) {
            case R.id.update_btn:
                Patrimonio patrimonio = new Patrimonio();
                String str[] = ui.patId.getText().toString().split(":");
                patrimonio.setId(Integer.parseInt(str[1]));
                patrimonio.setSala(Integer.parseInt(ui.editSala.getText().toString()));
                patrimonio.setCadeiras(Integer.parseInt(ui.editCadeiras.getText().toString()));
                patrimonio.setMesas(Integer.parseInt(ui.editMesas.getText().toString()));
                patrimonio.setProjetores(Integer.parseInt(ui.editProjetores.getText().toString()));
                dbHelper.updateData(patrimonio);
                singleButtonAlert(this, "Sala atualizada com sucesso.");
                showListView();
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.relatorio_menu:
                showListView();
                return true;
            case R.id.add_menu:
                ui.showInputComponents();
                ui.hideUpdateSpecificComponents();
                ui.resetInputComponents();
                ui.patmsg.setText("Adicionar Sala");
                ui.hideListViewComponents();
                return true;
            case R.id.relatorio_total:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showListView(){
        hideSoftKeyboard(getCurrentFocus());
        ui.showListViewComponents();
        patAdapter = new PatrimonioAdapter(this, new ArrayList<Patrimonio>(), ui);
        ui.salas.setAdapter(patAdapter);
        Loader<List<Patrimonio>> loader = getSupportLoaderManager().initLoader(1, null, this);
        loader.forceLoad();
        ui.hideInputComponents();
        ui.hideUpdateSpecificComponents();
        ui.patmsg.setText("Relatório das salas");
    }


    @Override
    public Loader<List<Patrimonio>> onCreateLoader(int id, Bundle args) {
        return new PatrimonioLoader(this);
    }
    @Override
    public void onLoadFinished(Loader<List<Patrimonio>> loader, List<Patrimonio> data) {
        patAdapter.setSalas(data);
    }
    @Override
    public void onLoaderReset(Loader<List<Patrimonio>> loader) {
        patAdapter.setSalas(new ArrayList<Patrimonio>());
    }
    private  void singleButtonAlert(Context context, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("Mensagem")
                .setCancelable(false)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public boolean validate() {
        boolean flag = true;
        if("".equals(ui.editSala.getText().toString().trim())){
            return false;
        } else if ("".equals(ui.editCadeiras.getText().toString().trim())) {
            return false;
        } else if ("".equals(ui.editMesas.getText().toString().trim())) {
            return false;
        } else if ("".equals(ui.editProjetores.getText().toString().trim())) {
            return false;
        }
        return flag;
    }
    private void hideSoftKeyboard(View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }



}
