package br.hssoftware.paguepix.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, "paguepix.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE configuracao (id INTEGER, chave TEXT, estabelecimento TEXT, cidade TEXT, identificacao INTEGER, prefixo TEXT, sufixo TEXT, sequencial INTEGER)");
        db.execSQL("INSERT INTO configuracao (id, chave, estabelecimento, cidade, identificacao, prefixo, sufixo, sequencial) VALUES (1, '12345678900', 'LOJA', 'CIDADE', 1, 'PAG', 'LOJA', 1)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* Implementar nas próximas versões */
    }

}
