package com.dkmm.findme.findme;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dkmm.findme.findme.DB.Contacto;
import com.dkmm.findme.findme.DB.ManejoContactos;

/**
 * Created by kevin on 09/02/2017.
 */

public class DialogContacto extends DialogFragment {

    public DialogContacto() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createSimpleDialog();
    }

    public Contacto contacto;
    EditText nombre;
    EditText telefono;
    EditText celular;
    EditText correo;
     ManejoContactos manejoContactos;


    /**
     * Crea un diálogo de alerta sencillo
     * @return Nuevo diálogo
     */
    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_contacto, null);
        builder.setView(v);
        manejoContactos = new ManejoContactos(this.getActivity());

        Button accionButton = (Button) v.findViewById(R.id.guardarBoton);

        nombre = (EditText) v.findViewById(R.id.nombreContacto);
        telefono = (EditText) v.findViewById(R.id.telefonoContacto);
        celular = (EditText) v.findViewById(R.id.celularContacto);
        correo = (EditText) v.findViewById(R.id.correoContacto);



        accionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contacto = new Contacto();
                        contacto = new Contacto();
                        contacto.setNOMBRE_CONTACTO(nombre.getText().toString());
                        contacto.setTELEFONO_CONTACTO(telefono.getText().toString());
                        contacto.setCELULAR_CONTACTO(celular.getText().toString());
                        contacto.setCORREO_CONTACTO(correo.getText().toString());
                        if(manejoContactos.ingresarContacto(contacto)){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(),"Se ingreso el contacto", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            });
                        }else{
                            Toast.makeText(getActivity(),"Hubo un error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        return builder.create();
    }


}
