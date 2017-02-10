package com.dkmm.findme.findme.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by kevin on 09/02/2017.
 */

public class ManejoContactos {

    private FindMeDBHelper miContaCtosLocal;
    private SQLiteDatabase db;

    public ManejoContactos(Context context){
        miContaCtosLocal =  new FindMeDBHelper(context, "CONTACTOS", null, 1);
    }

    public boolean ingresarContacto(Contacto contacto){
        try{
            db = miContaCtosLocal.getWritableDatabase();
            db.execSQL("INSERT INTO CONTACTOS (NOMBRE_CONTACTO, TELEFONO_CONTACTO, CELULAR_CONTACTO, CORREO_CONTACTO) " +
                    "VALUES ('" + contacto.getNOMBRE_CONTACTO() + "', " +
                    "'"+ contacto.getTELEFONO_CONTACTO() +"'," +
                    "'"+ contacto.getCELULAR_CONTACTO() +"'," +
                    "'"+ contacto.getCORREO_CONTACTO() +"')");
            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public ArrayList<Contacto> obtenerContacto(){
        db = miContaCtosLocal.getWritableDatabase();
        Contacto auxContacto;
        ArrayList<Contacto> contactos = new ArrayList<Contacto>();
        Cursor c = db.rawQuery(" SELECT * from  CONTACTOS", null);
        while(c.moveToNext()){
            auxContacto = new Contacto();
            auxContacto.setNOMBRE_CONTACTO(c.getString(0));
            auxContacto.setTELEFONO_CONTACTO(c.getString(1));
            auxContacto.setCELULAR_CONTACTO(c.getString(2));
            auxContacto.setCORREO_CONTACTO(c.getString(3));
            contactos.add(auxContacto);
        }

        return contactos;

    }
}
