package com.dkmm.findme.findme;

/**
 * Created by kevin on 09/02/2017.
 */

public class Contacto {
    private String NOMBRE_CONTACTO ;
    private String TELEFONO_CONTACTO;
    private String CELULAR_CONTACTO;
    private String CORREO_CONTACTO ;

    public Contacto(String NOMBRE_CONTACTO, String TELEFONO_CONTACTO, String CELULAR_CONTACTO, String CORREO_CONTACTO) {
        this.NOMBRE_CONTACTO = NOMBRE_CONTACTO;
        this.TELEFONO_CONTACTO = TELEFONO_CONTACTO;
        this.CELULAR_CONTACTO = CELULAR_CONTACTO;
        this.CORREO_CONTACTO = CORREO_CONTACTO;
    }

    public Contacto() {
    }

    public String getNOMBRE_CONTACTO() {
        return NOMBRE_CONTACTO;
    }

    public void setNOMBRE_CONTACTO(String NOMBRE_CONTACTO) {
        this.NOMBRE_CONTACTO = NOMBRE_CONTACTO;
    }

    public String getTELEFONO_CONTACTO() {
        return TELEFONO_CONTACTO;
    }

    public void setTELEFONO_CONTACTO(String TELEFONO_CONTACTO) {
        this.TELEFONO_CONTACTO = TELEFONO_CONTACTO;
    }

    public String getCELULAR_CONTACTO() {
        return CELULAR_CONTACTO;
    }

    public void setCELULAR_CONTACTO(String CELULAR_CONTACTO) {
        this.CELULAR_CONTACTO = CELULAR_CONTACTO;
    }

    public String getCORREO_CONTACTO() {
        return CORREO_CONTACTO;
    }

    public void setCORREO_CONTACTO(String CORREO_CONTACTO) {
        this.CORREO_CONTACTO = CORREO_CONTACTO;
    }
}
