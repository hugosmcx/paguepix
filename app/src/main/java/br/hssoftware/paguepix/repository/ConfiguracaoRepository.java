package br.hssoftware.paguepix.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.hssoftware.paguepix.model.ConfiguracaoModel;

public class ConfiguracaoRepository {
    private SQLiteDatabase db;

    public ConfiguracaoRepository(Context context){
        DBHelper dbHelper = new DBHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public ConfiguracaoModel getConfiguracao(){
        List<ConfiguracaoModel> configs = new ArrayList<>();
        Cursor cur = db.query("configuracao", null, "id = ?", new String[]{"1"}, null, null, null);
        while (cur.moveToNext()){
            ConfiguracaoModel config = new ConfiguracaoModel();
            config.setId(cur.getInt(cur.getColumnIndexOrThrow("id")));
            config.setChave(cur.getString(cur.getColumnIndexOrThrow("chave")));
            config.setCidade(cur.getString(cur.getColumnIndexOrThrow("cidade")));
            config.setEstabelecimento(cur.getString(cur.getColumnIndexOrThrow("estabelecimento")));
            config.setIdentificacao(cur.getInt(cur.getColumnIndexOrThrow("identificacao")) == 1);
            config.setPrefixo(cur.getString(cur.getColumnIndexOrThrow("prefixo")));
            config.setSufixo(cur.getString(cur.getColumnIndexOrThrow("sufixo")));
            config.setSequencial(cur.getInt(cur.getColumnIndexOrThrow("sequencial")));
            configs.add(config);
        }
        cur.close();
        return configs.isEmpty() ? null : configs.get(0);
    }

    public void setConfiguracao(ConfiguracaoModel config){
        ContentValues values = new ContentValues();
        values.put("chave", config.getChave());
        values.put("cidade", config.getCidade());
        values.put("estabelecimento", config.getEstabelecimento());
        values.put("identificacao", (config.isIdentificacao() ? 1 : 0));
        values.put("prefixo", config.getPrefixo());
        values.put("sufixo", config.getSufixo());
        values.put("sequencial", config.getSequencial());
        db.update("configuracao", values, "id = ?", new String[]{"1"});
    }

}
