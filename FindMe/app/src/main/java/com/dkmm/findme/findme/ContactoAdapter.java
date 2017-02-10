package com.dkmm.findme.findme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kevin on 09/02/2017.
 */

public class ContactoAdapter extends ArrayAdapter<Contacto> {

    private Context context;
    private ArrayList<Contacto> contactos;

    public ContactoAdapter(Context context, ArrayList<Contacto> contactos) {
        super(context, 0,contactos);
        this.context = context;
        this.contactos = contactos;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(context);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_items_contacto,parent, false);
        }

        TextView nombreContacto = (TextView) convertView.findViewById(R.id.textViewNombreContacto);
        if(contactos != null){
          ///  Log.d("Tamanio",""+ (contactos.get(0).getCELULAR_CONTACTO()));
           nombreContacto.setText(contactos.get(position).getNOMBRE_CONTACTO()+"/"+contactos.get(position).getCELULAR_CONTACTO());
        }






        return convertView;
    }


}
