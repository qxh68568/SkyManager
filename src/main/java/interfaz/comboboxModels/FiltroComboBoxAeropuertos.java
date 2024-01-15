
package interfaz.comboboxModels;

import dto.Aeropuerto;
import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class FiltroComboBoxAeropuertos extends DefaultComboBoxModel<Aeropuerto> {

    private List<Aeropuerto> datosOriginales;

    public FiltroComboBoxAeropuertos(List<Aeropuerto> datos) {
        super(datos.toArray(new Aeropuerto[0]));
        this.datosOriginales = new ArrayList<>(datos);
    }

    public void filtrarDatos(String texto) {
        removeAllElements();
        List<Aeropuerto> filtrados = datosOriginales.stream()
                .filter(aeropuerto -> aeropuerto.getNombre().toLowerCase().contains(texto.toLowerCase()))
                .collect(Collectors.toList());
        for (Aeropuerto aeropuerto : filtrados) {
            addElement(aeropuerto);
        }
    }

    public ListCellRenderer crearRenderizador() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Aeropuerto aeropuerto) {
                    setText(aeropuerto.getNombre());
                }
                return this;
            }
        };
    }
}
