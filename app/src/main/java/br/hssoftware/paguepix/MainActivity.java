package br.hssoftware.paguepix;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.hssoftware.paguepix.model.ConfiguracaoModel;
import br.hssoftware.paguepix.service.ConfiguracaoService;
import br.hssoftware.paguepix.service.QRCodeService;

public class MainActivity extends AppCompatActivity {

    TextView textViewTitulo;

    ConstraintLayout constraintLayoutFormulario;
    TextView textViewValor;
    EditText editTextValor;
    Button buttonGerar;
    TextView textViewAbrirConfiguracao;

    ConstraintLayout constraintLayoutQRCode;
    TextView textViewTituloQRCode;
    TextView textViewQRCodeValor;
    ImageView imageViewQRCode;
    TextView textViewQRCode;
    TextView textViewIdentificacaoPagamento;
    Button buttonNovoCodigo;
    Button buttonCompartilharPix;

    ConstraintLayout constraintLayoutConfiguracao;
    TextView textViewTituloConfiguracao;
    LinearLayout linearLayoutConfiguracao;
    EditText editTextChave;
    EditText editTextEstabelecimento;
    EditText editTextCidade;
    CheckBox checkBoxIdentificacao;
    EditText editTextPrefixo;
    EditText editTextSufixo;
    EditText editTextSequencial;
    Button buttonSalvarConfiguracao;

    QRCodeService qrCodeService;
    ConfiguracaoService configuracaoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        qrCodeService = new QRCodeService(MainActivity.this);
        configuracaoService = new ConfiguracaoService(MainActivity.this);

        textViewTitulo = findViewById(R.id.textViewTitulo);

        constraintLayoutFormulario = findViewById(R.id.constraintLayoutFormulario);
        textViewValor = findViewById(R.id.textViewValor);
        editTextValor = findViewById(R.id.editTextValor);
        buttonGerar = findViewById(R.id.buttonGerar);
        textViewAbrirConfiguracao = findViewById(R.id.textViewAbrirConfiguracao);

        constraintLayoutQRCode = findViewById(R.id.constraintLayoutQRCode);
        textViewQRCodeValor = findViewById(R.id.textViewQRCodeValor);
        textViewTituloQRCode = findViewById(R.id.textViewTituloQRCode);
        imageViewQRCode = findViewById(R.id.imageViewQRCode);
        textViewQRCode = findViewById(R.id.textViewQRCode);
        textViewIdentificacaoPagamento = findViewById(R.id.textViewIdentificacaoPagamento);
        buttonNovoCodigo = findViewById(R.id.buttonNovoCodigo);
        buttonCompartilharPix = findViewById(R.id.buttonCompartilharPix);

        constraintLayoutConfiguracao = findViewById(R.id.constraintLayoutConfiguracao);
        textViewTituloConfiguracao = findViewById(R.id.textViewTituloConfiguracao);
        linearLayoutConfiguracao = findViewById(R.id.linearLayoutConfiguracao);
        editTextChave = findViewById(R.id.editTextChave);
        editTextEstabelecimento = findViewById(R.id.editTextEstabelecimento);
        editTextCidade = findViewById(R.id.editTextCidade);
        checkBoxIdentificacao = findViewById(R.id.checkBoxIdentificacao);
        editTextPrefixo = findViewById(R.id.editTextPrefixo);
        editTextSufixo = findViewById(R.id.editTextSufixo);
        editTextSequencial = findViewById(R.id.editTextSequencial);
        buttonSalvarConfiguracao = findViewById(R.id.buttonSalvarConfiguracao);

        buttonGerar.setOnClickListener(v -> {
            ocultarTeclado(v);
            gerarQRCode();
        });

        textViewQRCode.setOnClickListener(v -> {
            copiarTexto();
        });

        buttonNovoCodigo.setOnClickListener(v -> {
            ocultarTeclado(v);
            abrirFormulario();
        });

        buttonCompartilharPix.setOnClickListener(v -> {
            compartilharPix();
        });

        textViewAbrirConfiguracao.setOnClickListener(v -> {
            ocultarTeclado(v);
            abrirConfiguracoes();
        });

        checkBoxIdentificacao.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ocultarTeclado(buttonView);
            editTextPrefixo.setEnabled(isChecked);
            editTextSufixo.setEnabled(isChecked);
            editTextSequencial.setEnabled(isChecked);
        });

        buttonSalvarConfiguracao.setOnClickListener(v -> {
            ocultarTeclado(v);
            salvarConfiguracoes();
        });
        abrirFormulario();
    }

    private void compartilharPix() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, textViewQRCode.getText().toString());
        startActivity(Intent.createChooser(shareIntent, "Compartilhar Pix Copia e Cola"));
    }

    private void copiarTexto() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("QRCode", textViewQRCode.getText().toString());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(MainActivity.this, "QRCode copiado!", Toast.LENGTH_LONG).show();
    }

    public void abrirConfiguracoes(){
        ConfiguracaoModel config = configuracaoService.getConfiguracao();
        editTextChave.setText(config.getChave());
        editTextEstabelecimento.setText(config.getEstabelecimento());
        editTextCidade.setText(config.getCidade());
        checkBoxIdentificacao.setChecked(config.isIdentificacao());
        editTextPrefixo.setText(config.getPrefixo());
        editTextSufixo.setText(config.getSufixo());
        editTextSequencial.setText(String.valueOf(config.getSequencial()));
        constraintLayoutFormulario.setVisibility(View.GONE);
        constraintLayoutQRCode.setVisibility(View.GONE);
        constraintLayoutConfiguracao.setVisibility(View.VISIBLE);
    }

    public void salvarConfiguracoes(){
        ConfiguracaoModel config = configuracaoService.getConfiguracao();
        config.setChave(editTextChave.getText().toString());
        config.setEstabelecimento(editTextEstabelecimento.getText().toString());
        config.setCidade(editTextCidade.getText().toString());
        config.setIdentificacao(checkBoxIdentificacao.isChecked());
        config.setPrefixo(editTextPrefixo.getText().toString());
        config.setSufixo(editTextSufixo.getText().toString());
        config.setSequencial(Integer.parseInt(editTextSequencial.getText().toString()));
        if(configuracaoService.setConfiguracao(config)){
            abrirFormulario();
        }else{
            Toast.makeText(MainActivity.this, configuracaoService.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void abrirFormulario(){
        editTextValor.setText("");
        constraintLayoutFormulario.setVisibility(View.VISIBLE);
        constraintLayoutQRCode.setVisibility(View.GONE);
        constraintLayoutConfiguracao.setVisibility(View.GONE);
        editTextValor.setFocusable(true);
    }

    public void gerarQRCode(){
        double valor = 0;
        try {
            valor = Double.parseDouble(editTextValor.getText().toString());
        }catch (Exception e){}
        if(valor < 0.01){
            Toast.makeText(MainActivity.this, "Informe um valor maior que 0.00", Toast.LENGTH_LONG).show();
        }else{
            if(qrCodeService.generate(valor)){
                abrirQRCode(valor, qrCodeService.getQRCode(), qrCodeService.getQRCodeString(), qrCodeService.getIndentCode());
            }else{
                Toast.makeText(MainActivity.this, qrCodeService.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void abrirQRCode(double valor, Bitmap qrCode, String qrCodeString, String identCode) {
        textViewQRCodeValor.setText("R$" + String.format("%.2f", valor));
        imageViewQRCode.setImageBitmap(qrCode);
        textViewQRCode.setText(qrCodeString);
        textViewIdentificacaoPagamento.setText(identCode);
        constraintLayoutFormulario.setVisibility(View.GONE);
        constraintLayoutQRCode.setVisibility(View.VISIBLE);
        constraintLayoutConfiguracao.setVisibility(View.GONE);
    }

    public void ocultarTeclado(View v){
        InputMethodManager im = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        if(constraintLayoutFormulario.getVisibility() != View.VISIBLE){
            constraintLayoutFormulario.setVisibility(View.VISIBLE);
            constraintLayoutQRCode.setVisibility(View.GONE);
            constraintLayoutConfiguracao.setVisibility(View.GONE);
        }else {
            super.onBackPressed();
        }
    }
}