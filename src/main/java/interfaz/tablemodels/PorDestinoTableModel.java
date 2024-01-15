package interfaz.tablemodels;

import dto.Aeropuerto;
import dto.VueloDiario;
import dto.Vuelos;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static logica.CargaDatos.listaAeropuertos;
import static logica.CargaDatos.listaVuelos;

public class PorDestinoTableModel extends AbstractTableModel {

    private String aeropuertoDestino;
    
    private List<VueloDiario> listaVuelosDiarios;
    private List<VueloDiario> listaVuelosDiariosFiltradosAeropuerto;
    private List<VueloDiario> listaVuelosDiariosFiltradosAeropuertoFecha;
    private List<VueloDiario> listaVuelosDiariosFiltradosAeropuertoFechaDestino;
    private final String[] columnNames = {"Código", "Fecha salida", "Hora salida", "Hora llegada", "Plazas ocupadas", "Precio"};

    public PorDestinoTableModel(List<VueloDiario> listaVuelos) {
        Collections.sort(listaVuelos, Comparator.comparing(VueloDiario::getHoraSalidaReal));
        this.listaVuelosDiarios = listaVuelos;
        this.listaVuelosDiariosFiltradosAeropuerto = listaVuelos;
        this.listaVuelosDiariosFiltradosAeropuertoFecha = listaVuelos;
        this.listaVuelosDiariosFiltradosAeropuertoFechaDestino = listaVuelos;
    }

    @Override
    public int getRowCount() {
        return listaVuelosDiariosFiltradosAeropuertoFechaDestino.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        VueloDiario vueloDiario = listaVuelosDiariosFiltradosAeropuertoFechaDestino.get(rowIndex);
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

            Calendar calendario = Calendar.getInstance();
            calendario.setTime(fecha);
            Date fechaInicio = calendario.getTime();
            calendario.add(Calendar.DAY_OF_YEAR, 7);
            Date fechaFin = calendario.getTime();

            listaVuelosDiariosFiltradosAeropuertoFecha = listaVuelosDiariosFiltradosAeropuerto.stream()
                    .filter(vueloDiario -> !vueloDiario.getFechaVuelo().before(fechaInicio) && !vueloDiario.getFechaVuelo().after(fechaFin))
                    .sorted(Comparator.comparing(VueloDiario::getFechaVuelo).thenComparing(VueloDiario::getHoraSalidaReal))
                    .collect(Collectors.toList());
        } else {
            listaVuelosDiariosFiltradosAeropuertoFecha = new ArrayList<>(listaVuelosDiariosFiltradosAeropuerto);
        }
        if (aeropuertoDestino != null && !aeropuertoDestino.isEmpty()) {
            filtrarPorDestino(aeropuertoDestino);
        } else {
            fireTableDataChanged();
        }
    }

    public void inicializarListaFiltradaPorDestino() {
    listaVuelosDiariosFiltradosAeropuertoFechaDestino = new ArrayList<>();
    fireTableDataChanged();
}
    
    public void filtrarPorDestino(String destino) {
        this.aeropuertoDestino = destino;
        if (destino != null && !destino.isEmpty()) {
            // Mapeo de nombres de aeropuertos a códigos IATA
            Map<String, String> codigoIataPorNombreAeropuerto = listaAeropuertos.stream()
                    .collect(Collectors.toMap(Aeropuerto::getNombre, Aeropuerto::getCodIata));

            // Obtener el código IATA para el nombre del aeropuerto de destino
            String codigoIataDestino = codigoIataPorNombreAeropuerto.get(destino);

            if (codigoIataDestino != null) {
                // Obtener los códigos de vuelo que coinciden con el código IATA de destino
                Set<String> codigosVueloDestino = listaVuelos.stream()
                        .filter(vuelo -> vuelo.getCodIataDestino().equals(codigoIataDestino))
                        .map(Vuelos::getCodVuelo)
                        .collect(Collectors.toSet());

                // Filtrar la lista de VueloDiario por estos códigos de vuelo
                listaVuelosDiariosFiltradosAeropuertoFechaDestino = listaVuelosDiariosFiltradosAeropuertoFecha.stream()
                        .filter(vueloDiario -> codigosVueloDestino.contains(vueloDiario.getCodVuelo()))
                        .collect(Collectors.toList());
            } else {
                listaVuelosDiariosFiltradosAeropuertoFechaDestino = new ArrayList<>(listaVuelosDiariosFiltradosAeropuertoFecha);
            }
        } else {
            listaVuelosDiariosFiltradosAeropuertoFechaDestino = new ArrayList<>(listaVuelosDiariosFiltradosAeropuertoFecha);
        }
        fireTableDataChanged();
    }

}
