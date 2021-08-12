package com.firthuns.ejerc02blocnotas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firthuns.ejerc02blocnotas.modelos.Nota;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //int requestCode
    private final int EDIT_NOTA = 2;
    private final int CREAR_ACTIVITY = 1;
    // Variables Vista
    private LinearLayout contenedor;
    private Button btnAgregar;

    // Variables para los modelos
    private ArrayList<Nota> listaNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contenedor = findViewById(R.id.contenedorMain);
        btnAgregar = findViewById(R.id.btnAgregarMain);
        listaNotas = new ArrayList<>(); // Inicializamos el Arraylist<>()


        /* Abre una nueva actividad y se mantiene a la espera de recibir contestación      */
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CrearActivity.class);
                startActivityForResult(intent, CREAR_ACTIVITY);
            }
        });
    } // de aqui nos vamos a crear el layaaout del CrearActivity

    /*
      La funcion onActivityResult, recibe la info de CrearActivity y la procesa
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
// en el siguiente condicional, verifico con el requescode de donde me proviene la info ademas
        // con el resultcode ademas de indica que la infor que viene esta OK
        if (requestCode == CREAR_ACTIVITY && resultCode == RESULT_OK) {
            //data , me seguro de que no venga vacio
            if (data != null) {
                // recupero la infor del Bundle
                final Nota nota = data.getExtras().getParcelable("NOTA");
                listaNotas.add(nota);
                final int posicion = listaNotas.size()-1;
                // TextView
                TextView txtTitulo = new TextView(this);
                txtTitulo.setText(nota.getTitulo());
                txtTitulo.setTextColor(Color.BLUE);
                txtTitulo.setTextSize(24);
                /*Parte que me gestionara que cuando realice el clic sobre el titulo que quiera
                * odificar ésta, me direccionara hacia la actividad EditNotaActivity*/
                txtTitulo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("POS", posicion);
                        bundle.putParcelable("NOTA", nota);
                        Intent intent = new Intent(MainActivity.this, EditNotaActivity.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, EDIT_NOTA);
                    }
                });
                // Params Layout  para organizar la disposicion del titulo y el boton eliminar, al poner cero al width,
                // estamos dividiendo la pantalla en 4 partes, 3 para el textView y 1 para el boton

                LinearLayout.LayoutParams paramsTXT = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,3);
                paramsTXT.setMargins(10,10,10,10);
                txtTitulo.setLayoutParams(paramsTXT);
                // Button
                Button btnEliminar = new Button(this);
                btnEliminar.setText("Eliminar");
                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listaNotas.remove(posicion);
                        repintarNotas();
                    }
                });
                // Params Layout
                LinearLayout.LayoutParams paramsBTN = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
                paramsBTN.setMargins(10,10,10,10);
                btnEliminar.setLayoutParams(paramsBTN);
                // Linear Horizontal
                LinearLayout contenedorNota = new LinearLayout(this);
                contenedorNota.setOrientation(LinearLayout.HORIZONTAL);
                contenedorNota.setBackgroundColor(Color.BLACK);
                // Agregar los elementos a la vista
                contenedorNota.addView(txtTitulo);
                contenedorNota.addView(btnEliminar);

                contenedor.addView(contenedorNota);
            }
        }
        if (requestCode == EDIT_NOTA && resultCode==RESULT_OK) {
            if (data != null){
                int posicion = data.getExtras().getInt("POS");
                Nota nota = data.getExtras().getParcelable("NOTA");
                listaNotas.get(posicion).setTitulo(nota.getTitulo());
                listaNotas.get(posicion).setContenido(nota.getContenido());
                repintarNotas();
            }
        }
    }// fin onActivityResult

    private void repintarNotas() {
        contenedor.removeAllViews();
        for (int i = 0; i < listaNotas.size(); i++) {
            final Nota nota = listaNotas.get(i);

            final int posicion = i;
            // TextView
            TextView txtTitulo = new TextView(this);
            txtTitulo.setText(nota.getTitulo());
            txtTitulo.setTextColor(Color.BLUE);
            txtTitulo.setTextSize(24);
            /*Parte que me gestionara que cuando realice el clic sobre el titulo que quiera
             * odificar ésta, me direccionara hacia la actividad EditNotaActivity*/
            txtTitulo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("POS", posicion);
                    bundle.putParcelable("NOTA", nota);
                    Intent intent = new Intent(MainActivity.this, EditNotaActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, EDIT_NOTA);
                }
            });
            // Params Layout
            LinearLayout.LayoutParams paramsTXT = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,3);
            paramsTXT.setMargins(10,10,10,10);
            txtTitulo.setLayoutParams(paramsTXT);
            // Button
            Button btnEliminar = new Button(this);
            btnEliminar.setText("Eliminar");
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listaNotas.remove(posicion);
                    repintarNotas();
                }
            });
            // Params Layout
            LinearLayout.LayoutParams paramsBTN = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
            paramsBTN.setMargins(10,10,10,10);
            btnEliminar.setLayoutParams(paramsBTN);
            // Linear Horizontal
            LinearLayout contenedorNota = new LinearLayout(this);
            contenedorNota.setOrientation(LinearLayout.HORIZONTAL);

            // Agregar los elementos a la vista
            contenedorNota.addView(txtTitulo);
            contenedorNota.addView(btnEliminar);

            contenedor.addView(contenedorNota);
        }
    }
}