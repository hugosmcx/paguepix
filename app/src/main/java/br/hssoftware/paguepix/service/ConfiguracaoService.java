package br.hssoftware.paguepix.service;

import android.content.Context;

import br.hssoftware.paguepix.model.ConfiguracaoModel;
import br.hssoftware.paguepix.repository.ConfiguracaoRepository;

public class ConfiguracaoService {

    private ConfiguracaoRepository configuracaoRepository;
    private String message;

    public ConfiguracaoService(Context context){
        configuracaoRepository = new ConfiguracaoRepository(context);
    }
    
    public ConfiguracaoModel getConfiguracao(){
        return configuracaoRepository.getConfiguracao();
    }

    public boolean setConfiguracao(ConfiguracaoModel config){
        if(config.getId() != 1){
            message = "Id deve ser 1";
            return false;
        }else if(config.getChave().isEmpty()){
            message = "A chave não pode ser vazia";
            return false;
        }else if(config.getCidade().isEmpty() || config.getCidade().length() > 15){
            message = "A cidade deve ser informada com no máximo 15 caracteres";
            return false;
        }else if(!config.getCidade().matches("^[A-Za-z0-9 ]+$")){
            message = "A cidade deve conter apenas letras sem acento, números e espaços";
            return false;
        }else if(config.getEstabelecimento().isEmpty() || config.getEstabelecimento().length() > 25){
            message = "O nome do estabelecimento deve ser informado com no máximo 25 caracteres";
            return false;
        }else if(!config.getEstabelecimento().matches("^[A-Za-z0-9 ]+$")){
            message = "O estabelecimento deve conter apenas letras sem acento, números e espaços";
            return false;
        }else if(!config.getPrefixo().matches("^[A-Za-z0-9 ]+$")){
            message = "O prefixo deve conter apenas letras sem acento, números";
            return false;
        }else if(config.getPrefixo().contains(" ")){
            message = "O prefixo pode conter espaços";
            return false;
        }else if(!config.getSufixo().matches("^[A-Za-z0-9 ]+$")){
            message = "O sufixo deve conter apenas letras sem acento, números";
            return false;
        }else if(config.getSufixo().contains(" ")){
            message = "O sufixo pode conter espaços";
            return false;
        }else if((config.getPrefixo().length() + config.getSufixo().length()) > 14){
            message = "A soma do Prefixo e do Sufixo não pode conter mais que 14 caracteres";
            return false;
        }else{
            ConfiguracaoModel cfg = new ConfiguracaoModel();
            cfg.setId(config.getId());
            cfg.setChave(config.getChave().trim());
            cfg.setEstabelecimento(config.getEstabelecimento().trim());
            cfg.setCidade(config.getCidade().trim());
            cfg.setIdentificacao(config.isIdentificacao());
            cfg.setPrefixo(config.getPrefixo().trim());
            cfg.setSufixo(config.getSufixo().trim());
            cfg.setSequencial(config.getSequencial());
            salvar(cfg);
            message = "";
            return true;
        }
    }

    public String getMessage(){
        return this.message;
    }

    private void salvar(ConfiguracaoModel config){
        configuracaoRepository.setConfiguracao(config);
    }

}
