
package interfaz.tablemodels;

import dto.Aerolinea;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AerolineasTableModel extends AbstractTableModel {

    private final List<Aerolinea> listaAerolineas;
    private final String[] columnNames = {"Prefijo", "Código", "Nombre", "Dirección", "Municipio", "Tfno Pasajero", "Tfno Aeropuerto"};

    public AerolineasTableModel(List<Aerolinea> listaAerolineas) {
        this.listaAerolineas = listaAerolineas;
    }

    @Override
    public int getRowCount() {
        return listaAerolineas.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Aerolinea aerolinea = listaAerolineas.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> aerolinea.getPrefijo();
            case 1 -> aerolinea.getCodigo();
            case 2 -> aerolinea.getNombre();
            case 3 -> aerolinea.getDireccion();
            case 4 -> aerolinea.getMunicipio();
            case 5 -> aerolinea.getTfnoInfoPasajero();
            case 6 -> aerolinea.getTfnoInfoAeropuerto();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

}


