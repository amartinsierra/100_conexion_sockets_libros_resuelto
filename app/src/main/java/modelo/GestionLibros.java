package modelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import beans.Libro;

/**
 * Created by antonio on 26/09/2017.
 */

public class GestionLibros {

    public List<Libro> obtenerLibros(int tema) {
        ArrayList<Libro> libros=new ArrayList<>();
        String cad = "", aux;

        try {Socket sc = new Socket("169.254.13.49", 9000);
             PrintStream salida = new PrintStream(sc.getOutputStream());
             BufferedReader bf=new BufferedReader(new InputStreamReader(sc.getInputStream()));



            salida.println(tema);


            //vamos leyendo las líneas enviadas por el servicio y las unimos
            //en la variable cad
            while ((aux = bf.readLine()) != null) {
                cad += aux + "\n";
            }
            //parseamos la cadena JSON
            JSONArray jsonArray = new JSONArray(cad);
            //recorremos el array de json y cada objeto lo convertimos en un Contacto
            //para añadirlo al arraylist de contactos que se entregará como resultado
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject ob = jsonArray.getJSONObject(i);
                libros.add(new Libro(ob.getString("producto"), ob.getString("seccion"),ob.getDouble("precio")));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return libros;
    }
}
