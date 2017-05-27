package com.example.iossenac.patrimonio;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class UIElements {
    public EditText editSala, editCadeiras, editMesas, editProjetores;
    TextView patmsg, patId, patSala;
    ListView salas;
    Button saveButton, updateButton;
    public UIElements(Activity activity) {
        editSala = (EditText)activity.findViewById(R.id.sala);
        editCadeiras = (EditText) activity.findViewById(R.id.cadeiras);
        editMesas = (EditText) activity.findViewById(R.id.mesas);
        editProjetores = (EditText) activity.findViewById(R.id.projetores);
        patmsg = (TextView) activity.findViewById(R.id.patmsg);
        salas = (ListView) activity.findViewById(R.id.salas);
        saveButton = (Button) activity.findViewById(R.id.save_btn);
        updateButton = (Button) activity.findViewById(R.id.update_btn);
        patId = (TextView) activity.findViewById(R.id.patId);
        patSala = (TextView) activity.findViewById(R.id.patSala);
    }
    public void resetInputComponents() {
        editSala.setText("");
        editSala.requestFocus();
        editCadeiras.setText("");
        editMesas.setText("");
        editProjetores.setText("");
    }

    public void hideInputComponents() {
        editSala.setVisibility(View.GONE);
        editCadeiras.setVisibility(View.GONE);
        editMesas.setVisibility(View.GONE);
        editProjetores.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
    }
    public void showInputComponents() {
        editSala.setVisibility(View.VISIBLE);
        editSala.requestFocus();
        editCadeiras.setVisibility(View.VISIBLE);
        editMesas.setVisibility(View.VISIBLE);
        editProjetores.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.VISIBLE);
    }
    public void hideUpdateSpecificComponents(){
        updateButton.setVisibility(View.GONE);
        patId.setVisibility(View.GONE);
        patSala.setVisibility(View.GONE);
    }
    public void showUpdateSpecificComponents() {
        updateButton.setVisibility(View.VISIBLE);
        patId.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.GONE);
    }
    public void hideListViewComponents() {
        salas.setVisibility(View.GONE);
        patId.setVisibility(View.GONE);

    }
    public void showListViewComponents() {
        salas.setVisibility(View.VISIBLE);
        salas.requestFocus();
    }
}