package com.wsrest;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    EditText editCelsius;
    TextView txtResult;
    Button btnEnviar;
    //declaramos variables String
    String celsius, fahren;
    String TAG="MARCOS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //obtenemos referencia de los elementos
        editCelsius= (EditText)findViewById(R.id.editCelsius);
        txtResult=(TextView)findViewById(R.id.tv_result);
        btnEnviar=(Button)findViewById(R.id.btnEnviar);

        //Evento del boton al hacer click

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validar que no venga vacio
                if (editCelsius.getText().length() != 0 && editCelsius.getText().toString() != "") {
                    //obtener el valor de edit
                    celsius = editCelsius.getText().toString();
                    //Crear instancia para AsyncCallWS
                    AsyncCallWS task = new AsyncCallWS();
                    //ejecuta
                    task.execute();

                } else {
                    txtResult.setText("Ingrese ° Celsius");
                }

            }
        });
    }

    //Consumir Servicio Web
    public void getFahrenheit(String celsius) throws ClientProtocolException, IOException, XmlPullParserException {
        //Creacion de transporte
        HttpClient httpClient = new DefaultHttpClient();

        //Ingreso de URL de servicio REST
        HttpPost post = new HttpPost("http://www.w3schools.com/xml/tempconvert.asmx/CelsiusToFahrenheit");
        //Tipo de datos
        post.setHeader("content-type", "application/x-www-form-urlencoded");
        //
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("Celsius", editCelsius.getText().toString()));
        post.setEntity(new UrlEncodedFormEntity(pairs));
        //Respuesta
        HttpResponse resp = httpClient.execute(post);
        //Obteniendo response en un String
        String respStr = EntityUtils.toString(resp.getEntity());
        //Parsear documento de respuesta
        ParserXML parser = new ParserXML();
        fahren = parser.Parsear(respStr);

    }

    //Tarea Asincronica
    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            Log.i(TAG, "doInBackground");
            try {
                getFahrenheit(celsius);
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            txtResult.setText(fahren + "° F");
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            txtResult.setText("Convirtiendo...");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
