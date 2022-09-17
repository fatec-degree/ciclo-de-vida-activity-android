package br.com.fatec.ciclovidaactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonIniciar, buttonParar;
    TextView textViewTempo;
    EditText editText;
    CountDownTimer countDown;
    long tempoRestante;

    @Override
    protected void onStop() {
        super.onStop();
        countDown.cancel();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        countDown = new CountDownTimer(this.tempoRestante, 1000) {
            @Override
            public void onTick(long tempoRestante) {
                textViewTempo.setText(tempoRestante / 1000 + "");
                MainActivity.this.tempoRestante = tempoRestante;
            }

            @Override
            public void onFinish() {
                MainActivity.this.tempoRestante = 0;
                Toast.makeText(MainActivity.this, "Voltando ao jogo...", Toast.LENGTH_SHORT).show();
            }
        };
        countDown.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences preferences = getSharedPreferences("minhaShared", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("meuTexto", editText.getText().toString() + "");
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        associaComponentes();

        SharedPreferences preferences = getSharedPreferences("minhaShared", 0);
        String meuTexto = preferences.getString("minhaShared", "Texto não encontrado");
        editText.setText(meuTexto);

        countDown = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long tempoRestante) {
                textViewTempo.setText(tempoRestante / 1000 + "");
                MainActivity.this.tempoRestante = tempoRestante;
            }

            @Override
            public void onFinish() {
                MainActivity.this.tempoRestante = 0;
                Toast.makeText(MainActivity.this, "Voltando ao jogo...", Toast.LENGTH_SHORT).show();
            }
        };

        buttonIniciar.setOnClickListener(view -> countDown.start());
        buttonParar.setOnClickListener(view -> countDown.cancel());
    }

    public void associaComponentes(){
        buttonIniciar = findViewById(R.id.buttonIniciar);
        buttonParar = findViewById(R.id.buttonParar);
        textViewTempo = findViewById(R.id.textViewTempo);
        editText = findViewById(R.id.editText);
    }
}