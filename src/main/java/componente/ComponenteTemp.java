package componente;

import java.io.Serializable;
import javax.swing.JLabel;

public class ComponenteTemp extends javax.swing.JPanel implements Serializable{

    private int municipioSalida;
    private int municipioLlegada;
    
    public ComponenteTemp() {
        initComponents();
    }

    public int getMunicipioSalida() {
        return municipioSalida;
    }

    public void setMunicipioSalida(int municipioSalida) {
        this.municipioSalida = municipioSalida;
    }

    public int getMunicipioLlegada() {
        return municipioLlegada;
    }

    public void setMunicipioLlegada(int municipioLlegada) {
        this.municipioLlegada = municipioLlegada;
    }

    public JLabel getLabelAeropuertoLlegada() {
        return labelAeropuertoLlegada;
    }

    public void setLabelAeropuertoLlegada(JLabel labelAeropuertoLlegada) {
        this.labelAeropuertoLlegada = labelAeropuertoLlegada;
    }

    public JLabel getLabelAeropuertoSalida() {
        return labelAeropuertoSalida;
    }

    public void setLabelAeropuertoSalida(JLabel labelAeropuertoSalida) {
        this.labelAeropuertoSalida = labelAeropuertoSalida;
    }

    public JLabel getLabelLlegadaMax() {
        return labelLlegadaMax;
    }

    public void setLabelLlegadaMax(JLabel labelLlegadaMax) {
        this.labelLlegadaMax = labelLlegadaMax;
    }

    public JLabel getLabelSalidaMax() {
        return labelSalidaMax;
    }

    public void setLabelSalidaMax(JLabel labelSalidaMax) {
        this.labelSalidaMax = labelSalidaMax;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelAeropuertoSalida = new javax.swing.JLabel();
        labelSalidaMax = new javax.swing.JLabel();
        labelAeropuertoLlegada = new javax.swing.JLabel();
        labelLlegadaMax = new javax.swing.JLabel();

        labelAeropuertoSalida.setText("Salida:");

        labelSalidaMax.setText("Temperaturas Max y Min");

        labelAeropuertoLlegada.setText("Llegada:");

        labelLlegadaMax.setText("Temperaturas Max y Min");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelLlegadaMax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelAeropuertoLlegada)
                    .addComponent(labelSalidaMax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelAeropuertoSalida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(labelAeropuertoSalida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelSalidaMax)
                .addGap(18, 18, 18)
                .addComponent(labelAeropuertoLlegada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelLlegadaMax)
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelAeropuertoLlegada;
    private javax.swing.JLabel labelAeropuertoSalida;
    private javax.swing.JLabel labelLlegadaMax;
    private javax.swing.JLabel labelSalidaMax;
    // End of variables declaration//GEN-END:variables
}
