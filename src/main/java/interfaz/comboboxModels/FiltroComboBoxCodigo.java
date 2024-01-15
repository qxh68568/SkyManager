
package interfaz.comboboxModels;

import dto.Vuelos;
import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class FiltroComboBoxCodigo extends DefaultComboBoxModel<Vuelos> {
    
    private List<Vuelos> datosOriginales;
    
    public FiltroComboBoxCodigo(List<Vuelos> datos) {
        super(datos.toArray(new Vuelos[0]));
        this.datosOriginales = new ArrayList<>(datos);
    }

    public void filtrarDatos(String texto) {
        removeAllElements();
        List<Vuelos> filtrados = datosOriginales.stream()
                .filter(vuelo -> vuelo.getCodVuelo().toLowerCase().contains(texto.toLowerCase()))
                .collect(Collectors.toList());
        for (Vuelos vuelo : filtrados) {
            addElement(vuelo);
        }
    }

    public ListCellRenderer crearRenderizador() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Vuelos codigo) {
                    setText(codigo.getCodVuelo());
                }
                return this;
            }
        };
    }

}
