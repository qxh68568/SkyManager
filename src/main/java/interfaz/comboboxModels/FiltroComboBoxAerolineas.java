
package interfaz.comboboxModels;

import dto.Aerolinea;
import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class FiltroComboBoxAerolineas extends DefaultComboBoxModel<Aerolinea> {
    
    private List<Aerolinea> datosOriginales;
    
    public FiltroComboBoxAerolineas(List<Aerolinea> datos) {
        super(datos.toArray(new Aerolinea[2]));
        this.datosOriginales = new ArrayList<>(datos);
    }

    public void filtrarDatos(String texto) {
        removeAllElements();
        List<Aerolinea> filtrados = datosOriginales.stream()
                .filter(aerolinea -> aerolinea.getNombre().toLowerCase().contains(texto.toLowerCase()))
                .collect(Collectors.toList());
        for (Aerolinea aerolinea : filtrados) {
            addElement(aerolinea);
        }
    }
    
    public ListCellRenderer crearRenderizador() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Aerolinea aerolinea) {
                    setText(aerolinea.getNombre());
                }
                return this;
            }
        };
    }

}

