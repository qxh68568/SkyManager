
package interfaz.tablemodels;


import dto.Vuelos;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class VuelosTableModel extends AbstractTableModel {

    private final List<Vuelos> listaVuelos;
    private final String[] columnNames = {"Código", "Iata Origen", "Iata Destino", "Num Plazas", "Hora Llegada", "Hora Salida", "Días Opera"};

    public VuelosTableModel(List<Vuelos> listaVueloses) {
        this.listaVuelos = listaVueloses;
    }

    @Override
    public int getRowCount() {
        return listaVuelos.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vuelos vuelos = listaVuelos.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> vuelos.getCodVuelo();
            case 1 -> vuelos.getCodIataOrigen();
            case 2 -> vuelos.getCodIataDestino();
            case 3 -> vuelos.getNumPlazas();
            case 4 -> vuelos.getHoraSalida();
            case 5 -> vuelos.getHoraLlegada();
            case 6 -> vuelos.getDiasOpera();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

}


