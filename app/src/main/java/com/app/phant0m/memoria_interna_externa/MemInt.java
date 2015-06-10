package com.app.phant0m.memoria_interna_externa;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MemInt extends ActionBarActivity implements View.OnClickListener {
    private EditText ETarchivo;
    private Button BtGuardar, BtAbrir;
    private static final int READ_BLOCK_SIZE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_int);

        ETarchivo = (EditText) findViewById(R.id.ETarchivoo);
        BtAbrir = (Button) findViewById(R.id.BtAbrir);
        BtGuardar = (Button) findViewById(R.id.BtGuardar);
        BtAbrir.setOnClickListener(this);
        BtGuardar.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mem_int, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(BtGuardar)){ String str = ETarchivo.getText().toString();
//Esta clase que permite grabar texto en un archivo
            FileOutputStream fout=null;
            try {
//Este metodo escribe y abre un archivo con un nombre especifica, La constante MODE_WORLD_READABLE indica que este arvhivo lo puedeleer cualquier apllicacion
                fout = openFileOutput("archivoTexto.txt", MODE_WORLD_READABLE);
//Convierte un stream de caracteres en un stream de bytes
                OutputStreamWriter ows = new OutputStreamWriter(fout);
                ows.write(str);
//Escribe en el buffer la cadena de texto
                ows.flush();
//Volca lo que hay en el buffer al archivo
                ows.close();
//Cierra el archivo de texto
            } catch (IOException e) {

                e.printStackTrace();
            }
            Toast.makeText(getBaseContext(),"El archivo se ha almacenado exitosamente", Toast.LENGTH_SHORT).show();
            ETarchivo.setText("");
        }
        if (v.equals(BtAbrir))
        {
            try {
//Se lee el archivo de texto indicado
                FileInputStream fin = openFileInput("archivoTexto.txt");
                InputStreamReader isr = new InputStreamReader(fin);
                char[] inputBuffer = new char[READ_BLOCK_SIZE	];
                String str= "";
//Se lee el archivo de texto mientras no se llegue al final de él
                int charRead; while ((charRead=isr.read(inputBuffer))>0) {
//Se lee por bloques de 100 caracteres //ya que se desconoce el tamaño del texto //Y se va copiando a una cadena de texto
                    String strRead = String.copyValueOf(inputBuffer, 0, charRead);
                    str += strRead;
                    inputBuffer = new char [READ_BLOCK_SIZE];
                }
//Se muestra el texto leido en la caje de texto
                ETarchivo.setText(str);
                isr.close();
                Toast.makeText(getBaseContext(),"El archivo ha sido mostrado", Toast.LENGTH_SHORT).show();
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}

