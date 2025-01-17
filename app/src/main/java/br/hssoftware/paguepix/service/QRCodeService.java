package br.hssoftware.paguepix.service;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import br.hssoftware.paguepix.model.ConfiguracaoModel;
import br.hssoftware.paguepix.repository.ConfiguracaoRepository;
import br.hssoftware.paguepix.util.CRCGenerate;

public class QRCodeService {

    private Bitmap qrcode;
    private String qrcodeString;
    private String message;
    private String indentCode;

    private ConfiguracaoRepository configuracaoRepository;

    public QRCodeService(Context context){
        configuracaoRepository = new ConfiguracaoRepository(context);
    }

    public boolean generate(double valor){
        if(valor < 0.01 || valor > 9999.99){
            message = "O valor para o PIX é de R$0,01 até R$9999,99";
            return false;
        }

        ConfiguracaoModel config = configuracaoRepository.getConfiguracao();
        String identificacao;
        if(config.isIdentificacao()){
            identificacao = config.getPrefixo() + config.getSequencial() + config.getSufixo();
        }else{
            identificacao = "***";
        }
        indentCode = identificacao;
        identificacao = (identificacao.length() < 10 ? "0" : "") + identificacao.length() + identificacao;
        identificacao = "05" + identificacao;
        identificacao = (identificacao.length() < 10 ? "0" : "") + identificacao.length() + identificacao;
        String valorStr = String.format("%.2f", valor);
        valorStr = valorStr.replace(",", ".");

        String mercado = "0014BR.GOV.BCB.PIX";
        mercado += "01" + (config.getChave().length() < 10 ? "0" : "") + config.getChave().length() + config.getChave();

        String qrc = "";
        qrc += "000201";
        qrc += "26" + mercado.length() + mercado;
        qrc += "52040000";
        qrc += "5303986";
        qrc += "54" + (valorStr.length() < 10 ? "0" : "") + valorStr.length() + valorStr;
        qrc += "5802BR";
        qrc += "59" + (config.getEstabelecimento().length() < 10 ? "0" : "") + config.getEstabelecimento().length() + config.getEstabelecimento();
        qrc += "60" + (config.getCidade().length() < 10 ? "0" : "") + config.getCidade().length() + config.getCidade();
        qrc += "62" + identificacao;
        qrc += "6304";
        qrc += CRCGenerate.Calculate(qrc).toUpperCase();
        qrcodeString = qrc;
        config.setSequencial(config.getSequencial() + 1);
        configuracaoRepository.setConfiguracao(config);
        try{
            BarcodeEncoder barcode = new BarcodeEncoder();
            qrcode = barcode.encodeBitmap(qrc, BarcodeFormat.QR_CODE, 800, 800);
        }catch (Exception e){
            message = e.getMessage();
            return false;
        }
        return true;
    }

    public Bitmap getQRCode(){
        return this.qrcode;
    }

    public String getQRCodeString(){
        return this.qrcodeString;
    }

    public String getMessage(){
        return this.message;
    }

    public String getIndentCode(){
        return this.indentCode;
    }

}
