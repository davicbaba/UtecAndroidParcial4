package com.example.utecparcial4;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.utecparcial4.Helper.ClientesRepository;
import com.example.utecparcial4.Models.Cliente;

public class AgregarClienteActivity extends AppCompatActivity {
    private EditText editTextNombre;
    private EditText editTextApellidos;
    private EditText editTextDirecciones;
    private EditText editTextCiudad;
    private Button btnGuardar;
    private ClientesRepository clientesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);

        // Habilitar el botón de retroceso en la barra de herramientas
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Inicializar el repositorio de clientes
        clientesRepository = new ClientesRepository(this);

        // Obtener referencias a los elementos de la interfaz
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellidos = findViewById(R.id.editTextApellidos);
        editTextDirecciones = findViewById(R.id.editTextDirecciones);
        editTextCiudad = findViewById(R.id.editTextCiudad);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Configurar el clic del botón Guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCliente();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Regresar a la actividad anterior
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void guardarCliente() {
        // Obtener los valores ingresados por el usuario
        String nombre = editTextNombre.getText().toString().trim();
        String apellidos = editTextApellidos.getText().toString().trim();
        String direcciones = editTextDirecciones.getText().toString().trim();
        String ciudad = editTextCiudad.getText().toString().trim();

        // Validar los campos requeridos
        if (nombre.isEmpty() || apellidos.isEmpty() || direcciones.isEmpty() || ciudad.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un nuevo cliente con los datos ingresados
        Cliente cliente = new Cliente(0, nombre, apellidos, direcciones, ciudad);

        // Guardar el cliente en la base de datos
        clientesRepository.crearCliente(cliente);

        // Mostrar un mensaje de éxito
        Toast.makeText(this, "Cliente guardado correctamente", Toast.LENGTH_SHORT).show();

        // Finalizar la actividad
        finish();
    }
    @Override
    public void onBackPressed() {
        // Indica que se ha realizado un cambio en los datos y debe actualizarse la lista en MainActivity
        setResult(RESULT_OK);
        super.onBackPressed();
    }


}