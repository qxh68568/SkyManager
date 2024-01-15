package interfaz.tablemodels;

import dto.Aerolinea;
import dto.Aeropuerto;
import dto.VueloDiario;
import dto.Vuelos;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static logica.CargaDatos.listaAerolineas;
import static logica.CargaDatos.listaVuelos;

public class PorAerolineaTableModel extends AbstractTableModel {

     private String aerolineaSeleccionada;
    
    private List<VueloDiario> listaVuelosDiarios;
    private List<VueloDiario> listaVuelosDiariosFiltradosAeropuerto;
    private List<VueloDiario> listaVuelosDiariosFiltradosAeropuertoFecha;
    private List<VueloDiario> listaVuelosDiariosFiltradosAeropuertoFechaAerolinea;
    private final String[] columnNames = {"Código", "Fecha salida", "Hora salida", "Hora llegada", "Plazas ocupadas", "Precio"};

    public PorAerolineaTableModel(List<VueloDiario> listaVuelos) {
        Collections.sort(listaVuelos, Comparator.comparing(VueloDiario::getHoraSalidaReal));
        this.listaVuelosDiarios = listaVuelos;
        this.listaVuelosDiariosFiltradosAeropuerto = listaVuelos;
        this.listaVuelosDiariosFiltradosAeropuertoFecha = listaVuelos;
        this.listaVuelosDiariosFiltradosAeropuertoFechaAerolinea = listaVuelos;
    }

    @Override
    public int getRowCount() {
        return listaVuelosDiariosFiltradosAeropuertoFechaAerolinea.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        VueloDiario vueloDiario = listaVuelosDiariosFiltradosAeropuertoFechaAerolinea.get(rowIndex);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

        return switch (columnIndex) {
            case 0 ->
                vueloDiario.getCodVuelo();
            case 1 ->
                formatoFecha.format(vueloDiario.getFechaVuelo());
            case 2 ->
                formatoHora.format(vueloDiario.getHoraSalidaReal());
            case 3 ->
                formatoHora.format(vueloDiario.getHoraLlegadaReal());
            case 4 ->
                vueloDiario.getNumPlazasOcupadas();
            case 5 ->
                String.format("%.0f€", vueloDiario.getPrecio());
            default ->
                null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void filtrarPorAeropuerto(Aeropuerto aeropuertoBase) {
        if (aeropuertoBase != null) {
            Set<String> codigosVueloValidos = listaVuelos.stream()
                    .filter(vuelo -> vuelo.getCodIataOrigen().equals(aeropuertoBase.getCodIata()))
                    .map(Vuelos::getCodVuelo)
                    .collect(Collectors.toSet());

            listaVuelosDiariosFiltradosAeropuerto = listaVuelosDiarios.stream()
                    .filter(vueloDiario -> codigosVueloValidos.contains(vueloDiario.getCodVuelo()))
                    .collect(Collectors.toList());
        } else {
            listaVuelosDiariosFiltradosAeropuerto = new ArrayList<>(listaVuelosDiarios);
        }
        filtrarPorFecha(new Date());
    }

    public void filtrarPorFecha(Date fecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        if (fecha != null) {
            String fechaFiltrada = formatoFecha.format(fecha);
            listaVuelosDiariosFiltradosAeropuertoFecha = listaVuelosDiariosFiltradosAeropuerto.stream()
                    .filter(vuelo -> formatoFecha.format(vuelo.getFechaVuelo()).equals(fechaFiltrada))
                    .collect(Collectors.toList());
        } else {
            listaVuelosDiariosFiltradosAeropuertoFecha = new ArrayList<>(listaVuelosDiariosFiltradosAeropuerto);
        }
        if (aerolineaSeleccionada != null && !aerolineaSeleccionada.isEmpty()) {
            filtrarPorNombreAerolinea(aerolineaSeleccionada);
        } else {
            fireTableDataChanged();
        }
    }

    public void inicializarListaFiltradaPorAerolinea() {
    listaVuelosDiariosFiltradosAeropuertoFechaAerolinea = new ArrayList<>();
    fireTableDataChanged();
}
    
public void filtrarPorNombreAerolinea(String nombreAerolinea) {
    this.aerolineaSeleccionada = nombreAerolinea;
    if (nombreAerolinea != null && !nombreAerolinea.isEmpty()) {
        Map<String, String> codigoPorNombreAerolinea = listaAerolineas.stream()
                .collect(Collectors.toMap(Aerolinea::getNombre, Aerolinea::getCodigo));

        String codigoAerolinea = codigoPorNombreAerolinea.get(nombreAerolinea);
        if (codigoAerolinea != null) {
            listaVuelosDiariosFiltradosAeropuertoFechaAerolinea = listaVuelosDiariosFiltradosAeropuertoFecha.stream()
                    .filter(vueloDiario -> vueloDiario.getCodVuelo().startsWith(codigoAerolinea))
                    .collect(Collectors.toList());
        } else {
            // Si no se encuentra el código de aerolínea, no vacíes la lista, sino que mantén los resultados previos.
            listaVuelosDiariosFiltradosAeropuertoFechaAerolinea = new ArrayList<>(listaVuelosDiariosFiltradosAeropuertoFecha);
        }
    } else {
        // Si no se selecciona una aerolínea, manten los resultados previos.
        listaVuelosDiariosFiltradosAeropuertoFechaAerolinea = new ArrayList<>(listaVuelosDiariosFiltradosAeropuertoFecha);
    }
    fireTableDataChanged();
}
}
