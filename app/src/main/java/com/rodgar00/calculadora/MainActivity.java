package com.rodgar00.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.mariuszgromada.math.mxparser.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private boolean esNuevoNumero = true;
    private TextView textResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textResultado = findViewById(R.id.textResultado);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<Button> botonesNumericos = new ArrayList<>(Arrays.asList(
                findViewById(R.id.button0),
                findViewById(R.id.button1),
                findViewById(R.id.button2),
                findViewById(R.id.button3),
                findViewById(R.id.button4),
                findViewById(R.id.button5),
                findViewById(R.id.button6),
                findViewById(R.id.button7),
                findViewById(R.id.button8),
                findViewById(R.id.button9)
        ));

        for (final Button boton : botonesNumericos) {
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarNumero(boton.getText().toString());
                }
            });
        }

        findViewById(R.id.buttonLimpiar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        findViewById(R.id.buttonIgual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equal();
            }
        });

        findViewById(R.id.buttonComa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarNumero(".");
            }
        });

        findViewById(R.id.buttonBorrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar();
            }
        });

        findViewById(R.id.buttonNegativo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSign();
            }
        });

        findViewById(R.id.buttonPorcentaje).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarNumero("%");
            }
        });

        List<Button> botonesOperaciones = Arrays.asList(
                findViewById(R.id.buttonDividir),
                findViewById(R.id.buttonMultiplicar),
                findViewById(R.id.buttonResta),
                findViewById(R.id.buttonSuma)
        );

        for (final Button boton : botonesOperaciones) {
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarNumero(boton.getText().toString());
                }
            });
        }
    }

    private void mostrarNumero(String numero) {
        String textoActual = textResultado.getText().toString();

        if (esNuevoNumero) {
            textResultado.setText(numero);
            esNuevoNumero = false;
        } else {
            textResultado.setText(textoActual + numero);
        }
    }

    private void equal() {
        String operacionUser = textResultado.getText().toString();

        operacionUser = operacionUser.replaceAll("÷", "/");
        operacionUser = operacionUser.replaceAll("x", "*");
        operacionUser = operacionUser.replace(",", ".");
        operacionUser = operacionUser.replace("%", "/100");

        Expression exp = new Expression(operacionUser);
        double resultado = exp.calculate();

        if (Double.isNaN(resultado)) {
            textResultado.setText("Error");
        } else {
            if (resultado == (long) resultado) {
                textResultado.setText(String.valueOf((long) resultado));
            } else {
                textResultado.setText(String.valueOf(resultado));
            }
        }

        esNuevoNumero = true;
    }

    private void clear() {
        textResultado.setText("0");
        esNuevoNumero = true;
    }

    private void borrar() {
        String textoActual = textResultado.getText().toString();

        if (!textoActual.isEmpty() && !textoActual.equals("0")) {
            textoActual = textoActual.substring(0, textoActual.length() - 1);
        }
        if (textoActual.isEmpty()) {
            textoActual = "0";
        }
        textResultado.setText(textoActual);
    }

    private void toggleSign() {
        String texto = textResultado.getText().toString();
        if (!texto.equals("0")) {
            if (texto.startsWith("-")) {
                texto = texto.substring(1);
            } else {
                texto = "-" + texto;
            }
            textResultado.setText(texto);
        }
    }
}
