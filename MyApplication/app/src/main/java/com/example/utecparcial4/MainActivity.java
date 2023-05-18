package com.example.utecparcial4;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.example.utecparcial4.Adapters.ClienteAdapter;
import com.example.utecparcial4.Helper.ClientesRepository;
import com.example.utecparcial4.Models.Cliente;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listaClientes;
    private ClientesRepository clientesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaClientes = findViewById(R.id.listaClientes);
        clientesRepository = new ClientesRepository(this);

        // Obtén la lista de clientes desde el repositorio
        List<Cliente> lista = clientesRepository.obtenerListaClientes();

        // Crea un adaptador personalizado para el ListView y asigna la lista de clientes
        ClienteAdapter adapter = new ClienteAdapter(this, lista);

        // Asigna el adaptador al ListView
        listaClientes.setAdapter(adapter);

        // Configura la barra de herramientas
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_client) {
            // Abre la actividad AgregarClienteActivity
            Intent intent = new Intent(MainActivity.this, AgregarClienteActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Actualiza la lista de clientes cuando se haya agregado uno nuevo
            List<Cliente> lista = clientesRepository.obtenerListaClientes();
            ClienteAdapter adapter = (ClienteAdapter) listaClientes.getAdapter();
            adapter.actualizarLista(lista);
        }
    }
}