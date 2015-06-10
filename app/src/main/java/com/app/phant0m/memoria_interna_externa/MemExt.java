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


public class MemExt extends ActionBarActivity implements View.OnClickListener {
    private EditText ETarchivo;
    private Button BtGuardar;
    private Button BtAbrir;
    private static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_ext);

        ETarchivo = (EditText) findViewById(R.id.ETarchivo);
        BtGuardar = (Button) findViewById(R.id.BtGuardar);
        BtAbrir = (Button) findViewById(R.id.BtAbrir);
        BtGuardar.setOnClickListener(this);
        BtAbrir.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mem_ext, menu);
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
        File sdCard, directory, file = null;
        try {
            //Se valida para ver si el dispositivo tiene memoria externa
            if (Environment.getExternalStorageState().equals("mounted")) {
//Se obtiene el directorio de la memoria externa
                sdCard = Environment.getExternalStorageDirectory();


                if (v.equals(BtGuardar)) {
                    String str = ETarchivo.getText().toString();
                    // Clase que permite grabar texto en un archivo
                    FileOutputStream fout = null;
                    try {
// instanciamos un onjeto File para crear un nuevo directorio en la memoria externa
                        directory = new File(sdCard.getAbsolutePath() + "/Mis archivos");
                        // se crea el nuevo directorio donde se cerara el archivo
                        directory.mkdirs();
                        // creamos el archivo en el nuevo directorio creado
                        file = new File(directory, "MiArchivo.txt");
                        fout = new FileOutputStream(file);
                        // Convierte un stream de caracteres en un stream de bytes
                        OutputStreamWriter ows = new OutputStreamWriter(fout);
                        ows.write(str);
                        // Escribe en el buffer la cadena de texto
                        ows.flush(); // Volca lo que hay en el buffer al archivo
                        ows.close();
                        // Cierra el archivo de texto
                        Toast.makeText(getBaseContext(), "El archivo se ha guardado exitosamente", Toast.LENGTH_SHORT).show();
                        ETarchivo.setText("");


                    } catch (IOException e) {
                        Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
                    }
                }
                if (v.equals(BtAbrir)) {
                    try {
//Obtenemos el direcorio donde se encuentra nuestro archivo a leer
                        directory = new File(sdCard.getAbsolutePath() + "/Mis archivos");
//Creamos un objeto File de nuestro archivo a leer
                        file = new File(directory, "MiArchivo.txt");
//Creamos un objeto de la clase FileInputStream
//el cual representa un stream del archivo que vamos a leer
                        FileInputStream fin = new FileInputStream(file);
//Creaos un objeto InputStreamReader que nos permitira
//leer el stream del archivo abierto
                        InputStreamReader isr = new InputStreamReader(fin);
                        char[] inputBuffer = new char[READ_BLOCK_SIZE];
                        String str = "";
// Se lee el archivo de texto mientras no se llegue al final de él
                        int charRead;
                        while ((charRead = isr.read(inputBuffer)) > 0) {
// Se lee por bloques de 100 caracteres ya que se desconoce el tamaño del texto Y se va copiando a una cadena de texto
                            String strRead = String.copyValueOf(inputBuffer, 0, charRead);
                            str += strRead;
                            inputBuffer = new char[READ_BLOCK_SIZE];
                        }
// Se muestra el texto leido en la caje de texto
                        ETarchivo.setText(str);
                        isr.close();
                        Toast.makeText(getBaseContext(), "El archivo ha sido mostrado", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }

            else{
                Toast.makeText(getBaseContext(), "Error al encontrar el almacenamiento externo", Toast.LENGTH_LONG).show();
            }
        }

        catch (Exception e) {
        }

    }

}
