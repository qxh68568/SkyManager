
package dto;


public class Aerolinea {
    private String prefijo;
    private String codigo;
    private String nombre;
    private String direccion;
    private String municipio;
    private String tfnoInfoPasajero;
    private String tfnoInfoAeropuerto;

    public Aerolinea(String prefijo, String codigo, String nombre, String direccion, String municipio, String tfnoInfoPasajero, String tfnoInfoAeropuerto) {
        this.prefijo = prefijo;
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.municipio = municipio;
        this.tfnoInfoPasajero = tfnoInfoPasajero;
        this.tfnoInfoAeropuerto = tfnoInfoAeropuerto;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getTfnoInfoPasajero() {
        return tfnoInfoPasajero;
    }

    public String getTfnoInfoAeropuerto() {
        return tfnoInfoAeropuerto;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setTfnoInfoPasajero(String tfnoInfoPasajero) {
        this.tfnoInfoPasajero = tfnoInfoPasajero;
    }

    public void setTfnoInfoAeropuerto(String tfnoInfoAeropuerto) {
        this.tfnoInfoAeropuerto = tfnoInfoAeropuerto;
    }

    @Override
    public String toString() {
        return "Aerolinea{" + "prefijo=" + prefijo + ", codigo=" + codigo + ", nombre=" + nombre + ", direccion=" + direccion + ", municipio=" + municipio + ", tfnoInfoPasajero=" + tfnoInfoPasajero + ", tfnoInfoAeropuerto=" + tfnoInfoAeropuerto + '}';
    }
    
    
    
}
