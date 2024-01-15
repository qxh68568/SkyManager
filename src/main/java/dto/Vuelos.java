
package dto;

public class Vuelos {
    private String codVuelo;
    private String codIataOrigen;
    private String codIataDestino;
    private String numPlazas;
    private String horaSalida;
    private String horaLlegada;
    private String diasOpera;

    public Vuelos(String codVuelo, String codIataOrigen, String codIataDestino, String numPlazas, String horaSalida, String horaLlegada, String diasOpera) {
        this.codVuelo = codVuelo;
        this.codIataOrigen = codIataOrigen;
        this.codIataDestino = codIataDestino;
        this.numPlazas = numPlazas;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.diasOpera = diasOpera;
    }

    public String getCodVuelo() {
        return codVuelo;
    }

    public void setCodVuelo(String codVuelo) {
        this.codVuelo = codVuelo;
    }

    public String getCodIataOrigen() {
        return codIataOrigen;
    }

    public void setCodIataOrigen(String codIataOrigen) {
        this.codIataOrigen = codIataOrigen;
    }

    public String getCodIataDestino() {
        return codIataDestino;
    }

    public void setCodIataDestino(String codIataDestino) {
        this.codIataDestino = codIataDestino;
    }

    public String getNumPlazas() {
        return numPlazas;
    }

    public void setNumPlazas(String numPlazas) {
        this.numPlazas = numPlazas;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getDiasOpera() {
        return diasOpera;
    }

    public void setDiasOpera(String diasOpera) {
        this.diasOpera = diasOpera;
    }

    @Override
    public String toString() {
        return "Vuelos{" + "codVuelo=" + codVuelo + ", codIataOrigen=" + codIataOrigen + ", codIataDestino=" + codIataDestino + ", numPlazas=" + numPlazas + ", horaSalida=" + horaSalida + ", horaLlegada=" + horaLlegada + ", diasOpera=" + diasOpera + '}';
    }

}
