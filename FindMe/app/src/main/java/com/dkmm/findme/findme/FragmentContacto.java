package com.dkmm.findme.findme;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dkmm.findme.findme.DB.Contacto;
import com.dkmm.findme.findme.DB.ManejoContactos;

/**
 * Created by kevin on 09/02/2017.
 */

public class FragmentContacto extends Fragment {


    private Contacto contacto;
    private ManejoContactos manejoContactos;
    private ListView lv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contacto = new Contacto();
        manejoContactos = new ManejoContactos(this.getActivity());



        return inflater.inflate(R.layout.contacto_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ListView) view.findViewById(R.id.listViewContacto);

        ContactoAdapter contactoAdapter = new ContactoAdapter(this.getActivity(),manejoContactos.obtenerContacto());
        lv.setAdapter(contactoAdapter);





    }
}
