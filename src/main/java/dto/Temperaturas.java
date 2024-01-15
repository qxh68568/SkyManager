
package dto;

public class Temperaturas {
    private String codMunicipio;
    private int tempMax;
    private int tempMin;

    public Temperaturas(String codMunicipio, int tempMax, int tempMin) {
        this.codMunicipio = codMunicipio;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }

    public String getCodMunicipio() {
        return codMunicipio;
    }

    public void setCodMunicipio(String codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    public int getTempMax() {
        return tempMax;
    }

    public void setTempMax(int tempMax) {
        this.tempMax = tempMax;
    }

    public int getTempMin() {
        return tempMin;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }

    @Override
    public String toString() {
        return "Temperaturas{" + "codMunicipio=" + codMunicipio + ", tempMax=" + tempMax + ", tempMin=" + tempMin + '}';
    }
    
    
    
    
}
