package com.example.iossenac.patrimonio;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.TextView;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 1;
    private static String DB_FILE_NAME = "estoque4";
    public DatabaseHelper(Context context) {
        super(context, DB_FILE_NAME, null, DATABASE_VERSION);
    }
    //Create database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE salas ( " +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sala INT, " +
                " cadeiras INT," +
                " mesas INT,"+
                " projetores INT )";
        db.execSQL(sql);
    }
    //Atualizar Banco
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == oldVersion + 1) {

        }
    }
    //Insere dados na tabela
    public void insertData(Patrimonio patrimonio){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("INSERT INTO salas (sala, cadeiras, mesas, projetores) " +
                "VALUES (?,?,?,?)");
        stmt.bindLong(1, patrimonio.getSala());
        stmt.bindLong(2, patrimonio.getCadeiras());
        stmt.bindLong(3, patrimonio.getMesas());
        stmt.bindLong(4, patrimonio.getProjetores());
        stmt.execute();
        stmt.close();
        db.close();
    }
    //Atualiza dados na tabela
    public void updateData(Patrimonio patrimonio){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("UPDATE salas SET sala=?, cadeiras=?, mesas=?, projetores=? "+
                "WHERE id = ?");
        stmt.bindLong(1, patrimonio.getSala());
        stmt.bindLong(2, patrimonio.getCadeiras());
        stmt.bindLong(3, patrimonio.getMesas());
        stmt.bindLong(4, patrimonio.getProjetores());
        stmt.bindLong(5, patrimonio.getId());
        stmt.execute();
        stmt.close();
        db.close();
    }
    //Busca por todos os dados da tabela
    public List<Patrimonio> getSalas() {
        List<Patrimonio> salas = new ArrayList<Patrimonio>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT id, sala, cadeiras, mesas, projetores from salas ORDER BY sala ASC";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Patrimonio pat = new Patrimonio();
            pat.setId(cursor.getInt(0));
            pat.setSala(cursor.getInt(1));
            pat.setCadeiras(cursor.getInt(2));
            pat.setMesas(cursor.getInt(3));
            pat.setProjetores(cursor.getInt(4));
            salas.add(pat);
        }
        db.close();
        return salas;
    }

    //Soma o total de cadeiras na tabela
    public Integer TotalCadeiras(){
        int totalcadeiras =0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT SUM(cadeiras) from salas";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            totalcadeiras = cursor.getInt(0);
        }

        return totalcadeiras;
    }


    //Deleta dado atraves do id
    public void deleteData(int patId){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("DELETE FROM salas WHERE id = ?");
        stmt.bindLong(1, patId);
        stmt.execute();
        stmt.close();
        db.close();
    }
    //Busca por determinado id na tabela
    public Patrimonio getPatrimonioById(int patId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT id, sala, cadeiras, mesas, projetores FROM salas WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(patId)});
        cursor.moveToFirst();
        Patrimonio pat = new Patrimonio();
        pat.setId(cursor.getInt(0));
        pat.setSala(cursor.getInt(1));
        pat.setCadeiras(cursor.getInt(2));
        pat.setMesas(cursor.getInt(3));
        pat.setProjetores(cursor.getInt(4));
        db.close();
        return pat;
    }
}
