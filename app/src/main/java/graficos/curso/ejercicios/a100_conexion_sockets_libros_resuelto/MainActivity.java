package graficos.curso.ejercicios.a100_conexion_sockets_libros_resuelto;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adaptadores.AdaptadorLibros;
import beans.Libro;
import modelo.GestionLibros;

public class MainActivity extends Activity {
    ListView lstLibros;
    EditText edtBuscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstLibros=this.findViewById(R.id.lstLibros);
        edtBuscar=this.findViewById(R.id.edtBuscar);
    }
    public void buscar(View v){

        AccesoLibros ac=new AccesoLibros();
        ac.execute(edtBuscar.getText().toString());
    }
    private class AccesoLibros extends AsyncTask<String,Void,List<Libro>> {
        @Override
        protected void onPostExecute(List<Libro> libros) {
            super.onPostExecute(libros);
            //notifica evento de exceso de libros
            if(libros.size()>3){
                Intent in=new Intent("acciones.personales.EXCESOMATERIAL");
                in.putExtra("capacidad",libros.size());
                //notificación
                MainActivity.this.sendBroadcast(in);
            }
            AdaptadorLibros adp=new AdaptadorLibros(MainActivity.this,libros);
            lstLibros.setAdapter(adp);
        }

        @Override
        protected List<Libro> doInBackground(String... args) {
            GestionLibros glibros=new GestionLibros();
            return glibros.obtenerLibros(Integer.parseInt(args[0]));
        }
    }
}
