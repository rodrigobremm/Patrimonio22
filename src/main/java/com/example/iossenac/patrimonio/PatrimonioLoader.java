package com.example.iossenac.patrimonio;

import java.util.List;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class PatrimonioLoader extends AsyncTaskLoader<List<Patrimonio>> {
    DatabaseHelper dbHelper= null;
    public PatrimonioLoader(Context context) {
        super(context);
        dbHelper = new DatabaseHelper(context);
    }
    @Override
    public List<Patrimonio> loadInBackground() {
        return dbHelper.getSalas();
    }
}