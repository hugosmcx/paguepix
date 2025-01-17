package br.hssoftware.paguepix.model;

public class ConfiguracaoModel {
    private int id;
    private String chave;
    private String estabelecimento;
    private String cidade;
    private boolean identificacao;
    private String prefixo;
    private String sufixo;
    private int sequencial;

    public ConfiguracaoModel() {
    }

    public ConfiguracaoModel(int id, String chave, String estabelecimento, String cidade, boolean identificacao, String prefixo, String sufixo, int sequencial) {
        this.id = id;
        this.chave = chave;
        this.estabelecimento = estabelecimento;
        this.cidade = cidade;
        this.identificacao = identificacao;
        this.prefixo = prefixo;
        this.sufixo = sufixo;
        this.sequencial = sequencial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public boolean isIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(boolean identificacao) {
        this.identificacao = identificacao;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    public String getSufixo() {
        return sufixo;
    }

    public void setSufixo(String sufixo) {
        this.sufixo = sufixo;
    }

    public int getSequencial() {
        return sequencial;
    }

    public void setSequencial(int sequencial) {
        this.sequencial = sequencial;
    }
}
