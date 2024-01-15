
package dto;

import java.util.Date;

public class VueloDiario {
    
    private String codVuelo;
    private Date fechaVuelo;
    private Date horaSalidaReal;
    private Date horaLlegadaReal;
    private String numPlazasOcupadas;
    private float Precio;

    public VueloDiario(
            String codVuelo, 
            Date fechaVuelo, 
            Date horaSalidaReal, 
            Date horaLlegadaReal, 
            String numPlazasOcupadas, 
            float Precio) {
        this.codVuelo = codVuelo;
        this.fechaVuelo = fechaVuelo;
        this.horaSalidaReal = horaSalidaReal;
        this.horaLlegadaReal = horaLlegadaReal;
        this.numPlazasOcupadas = numPlazasOcupadas;
        this.Precio = Precio;
    }

    public String getCodVuelo() {
        return codVuelo;
    }

    public void setCodVuelo(String codVuelo) {
        this.codVuelo = codVuelo;
    }

    public Date getFechaVuelo() {
        return fechaVuelo;
    }

    public void setFechaVuelo(Date fechaVuelo) {
        this.fechaVuelo = fechaVuelo;
    }

    public Date getHoraSalidaReal() {
        return horaSalidaReal;
    }

    public void setHoraSalidaReal(Date horaSalidaReal) {
        this.horaSalidaReal = horaSalidaReal;
    }

    public Date getHoraLlegadaReal() {
        return horaLlegadaReal;
    }

    public void setHoraLlegadaReal(Date horaLlegadaReal) {
        this.horaLlegadaReal = horaLlegadaReal;
    }

    public String getNumPlazasOcupadas() {
        return numPlazasOcupadas;
    }

    public void setNumPlazasOcupadas(String numPlazasOcupadas) {
        this.numPlazasOcupadas = numPlazasOcupadas;
    }

    public float getPrecio() {
        return Precio;
    }

    public void setPrecio(float Precio) {
        this.Precio = Precio;
    }

    @Override
    public String toString() {
        return "VueloDiario{" + "codVuelo=" + codVuelo + ", fechaVuelo=" + fechaVuelo + ", horaSalidaReal=" + horaSalidaReal + ", horaLlegadaReal=" + horaLlegadaReal + ", numPlazasOcupadas=" + numPlazasOcupadas + ", Precio=" + Precio + '}';
    }

    public float calcularRecaudacion() {
        int plazasOcupadas = Integer.parseInt(numPlazasOcupadas);
        float recaudacion = this.Precio * (plazasOcupadas * 0.8f);
        return recaudacion;
    }
    
}
