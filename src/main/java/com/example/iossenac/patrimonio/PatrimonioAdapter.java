package com.example.iossenac.patrimonio;

import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;



public class PatrimonioAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Patrimonio> salas;
    Context context = null;
    UIElements ui;
    public PatrimonioAdapter(Context context, List<Patrimonio> salas, UIElements ui) {
        this.salas = salas;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.ui = ui;
    }

    //Infla colocando os valor trazidos do banco

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final Patrimonio salas = (Patrimonio) getItem(position);
        if (view == null) {
            view = inflater.inflate(R.layout.relatorio_patrimonio, null);
        }

        //TextView id = (TextView) view.findViewById(R.id.id);
        //id.setText(String.valueOf(salas.getId()));
        TextView sala = (TextView) view.findViewById(R.id.sala);
        sala.setText(String.valueOf(salas.getSala()));
        TextView cadeiras = (TextView) view.findViewById(R.id.cadeiras);
        cadeiras.setText(String.valueOf(salas.getCadeiras()));
        TextView mesas = (TextView) view.findViewById(R.id.mesas);
        mesas.setText(String.valueOf(salas.getMesas()));
        TextView projetores = (TextView) view.findViewById(R.id.projetores);
        projetores.setText(String.valueOf(salas.getProjetores()));

        //Botao de atualizar
        Button updateButton = (Button)view.findViewById(R.id.update_btn);
        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ui.showInputComponents();
                ui.showUpdateSpecificComponents();
                ui.hideListViewComponents();
                ui.patId.setText("ID:" + salas.getId());
                ui.patSala.setText("Sala:" + salas.getSala());
                ui.editSala.setText(String.valueOf(salas.getSala()));
                ui.editCadeiras.setText(String.valueOf(salas.getCadeiras()));
                ui.editMesas.setText(String.valueOf(salas.getMesas()));
                ui.editProjetores.setText(String.valueOf(salas.getProjetores()));
            }
        });

        //Botao de deletar
        Button deleteButton = (Button)view.findViewById(R.id.delete_btn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                doubleButtonAlert(context, salas.getId(), position);
            }
        });
        return view;
    }


    private void doubleButtonAlert(final Context context, final int patId, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("Mensagem")
                .setCancelable(false)
                .setMessage("Você quer mesmo deletar esta sala?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        DatabaseHelper dbHelper = new DatabaseHelper(context);
                        dbHelper.deleteData(patId);
                        salas.remove(position);
                        notifyDataSetChanged();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public Object getItem(int position) {
        return salas.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getCount() {
        return salas.size();
    }
    public void setSalas(List<Patrimonio> data) {
        salas.clear();
        salas.addAll(data);
        notifyDataSetChanged();
    }
}