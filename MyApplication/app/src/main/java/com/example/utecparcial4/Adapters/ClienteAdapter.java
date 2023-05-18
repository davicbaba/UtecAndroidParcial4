package com.example.utecparcial4.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.utecparcial4.Models.Cliente;
import com.example.utecparcial4.R;

import java.util.List;

public class ClienteAdapter extends ArrayAdapter<Cliente> {
    private Context context;
    private List<Cliente> listaClientes;

    public ClienteAdapter(Context context, List<Cliente> listaClientes) {
        super(context, 0, listaClientes);
        this.context = context;
        this.listaClientes = listaClientes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item_cliente, parent, false);
        }

        Cliente cliente = listaClientes.get(position);

        TextView txtIdCliente = itemView.findViewById(R.id.txtIdCliente);
        TextView txtNombreApellido = itemView.findViewById(R.id.txtNombreApellido);

        txtIdCliente.setText(String.valueOf(cliente.getIdCliente()));
        txtNombreApellido.setText(cliente.getNombre() + " " + cliente.getApellidos());

        return itemView;
    }

    public void actualizarLista(List<Cliente> lista) {
        clear();
        addAll(lista);
        notifyDataSetChanged();
    }
}
