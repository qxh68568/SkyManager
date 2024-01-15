
package dto;

import java.io.Serializable;

public class Aeropuerto implements Serializable{
    
    private String codIata;
    private String nombre;
    private int codMunicipio;

    public Aeropuerto(String codIata, String nombre, int codMunicipio) {
        this.codIata = codIata;
        this.nombre = nombre;
        this.codMunicipio = codMunicipio;
    }

    public String getCodIata() {
        return codIata;
    }

    public void setCodIata(String codIata) {
        this.codIata = codIata;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodMunicipio() {
        return codMunicipio;
    }

    public void setCodMunicipio(int codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    @Override
    public String toString() {
        return "Aeropuerto{" + "codIata=" + codIata + ", nombre=" + nombre + ", codMunicipio=" + codMunicipio + '}';
    }
   
}
