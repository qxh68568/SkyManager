
package logica;

import dto.Aerolinea;
import dto.VueloDiario;
import dto.Vuelos;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import static logica.CargaDatos.listaAerolineas;
import static logica.CargaDatos.listaVuelos;


public class GuardaDatos {
    
    public static void guardarAerolineas(String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Aerolinea aerolinea : listaAerolineas) {
                String linea = aerolinea.getPrefijo()+ "," +
                               aerolinea.getCodigo()+ "," +
                               aerolinea.getNombre()+ "," + 
                               aerolinea.getDireccion()+ "," +
                               aerolinea.getMunicipio()+ "," +
                               aerolinea.getTfnoInfoPasajero()+ "," +
                               aerolinea.getTfnoInfoAeropuerto();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
        }
    }
    
    public static void guardarVuelos(String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Vuelos vuelo : listaVuelos) {
                String linea = vuelo.getCodVuelo()+ "," +
                               vuelo.getCodIataOrigen()+ "," +
                               vuelo.getCodIataDestino()+ "," + 
                               vuelo.getNumPlazas()+ "," +
                               vuelo.getHoraSalida()+ "," +
                               vuelo.getHoraLlegada()+ "," +
                               vuelo.getDiasOpera();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
        }
    }
    
    public static void guardarVuelosDiarios(String rutaArchivo) {
    SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
        for (VueloDiario vuelodiario : CargaDatos.listaVuelosDiarios) {
            String fechaVuelo = formatoFecha.format(vuelodiario.getFechaVuelo());
            String horaSalidaReal = formatoHora.format(vuelodiario.getHoraSalidaReal());
            String horaLlegadaReal = formatoHora.format(vuelodiario.getHoraLlegadaReal());

            String linea = vuelodiario.getCodVuelo() + "," +
                           fechaVuelo + "," +
                           horaSalidaReal + "," + 
                           horaLlegadaReal + "," +
                           vuelodiario.getNumPlazasOcupadas() + "," +
                           vuelodiario.getPrecio() + ",";
            bw.write(linea);
            bw.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error al guardar vuelos diarios");
    }
    }
 
}
