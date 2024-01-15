package logica;

import dto.Aeropuerto;
import dto.VueloDiario;
import dto.Vuelos;
import java.awt.TextField;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.JFormattedTextField;
import static logica.CargaDatos.listaVuelosDiarios;

public class GestionVuelosDiarios {

    public static void agregarVueloDiario(
            TextField campoCodVuelo,
            TextField campoDiasOpera,
            JFormattedTextField campoHoraSalida,
            JFormattedTextField campoHoraLlegada,
            TextField campoNumPlazas) throws ParseException {
        try {
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

            String codVuelo = campoCodVuelo.getText();
            String diasOpera = campoDiasOpera.getText();
            Date horaSalida = formatoHora.parse(campoHoraSalida.getText());
            Date horaLlegada = formatoHora.parse(campoHoraLlegada.getText());
            String numPlazasOcupadas = campoNumPlazas.getText();
            float Precio = 60;

            Calendar inicio = Calendar.getInstance();
            inicio.set(2024, Calendar.JANUARY, 1); // Fecha de inicio
            Calendar fin = Calendar.getInstance();
            fin.set(2024, Calendar.DECEMBER, 31); // Fecha de fin

            // Mapeo de días de la semana
            Map<Character, Integer> mapeoDias = Map.of(
                    'L', Calendar.MONDAY,
                    'M', Calendar.TUESDAY,
                    'X', Calendar.WEDNESDAY,
                    'J', Calendar.THURSDAY,
                    'V', Calendar.FRIDAY,
                    'S', Calendar.SATURDAY,
                    'D', Calendar.SUNDAY
            );

            for (; inicio.before(fin); inicio.add(Calendar.DATE, 1)) {
                Date fecha = inicio.getTime();
                int diaDeLaSemana = inicio.get(Calendar.DAY_OF_WEEK);
                for (char dia : diasOpera.toCharArray()) {
                    if (mapeoDias.get(dia) == diaDeLaSemana) {
                        VueloDiario nuevoVuelo = new VueloDiario(
                                codVuelo,
                                fecha, // Usando la fecha directamente
                                horaSalida,
                                horaLlegada,
                                numPlazasOcupadas,
                                Precio);
                        listaVuelosDiarios.add(nuevoVuelo);
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir el número de plazas: " + e.getMessage());
            // Manejar aquí el error de conversión del número de plazas
        }

        System.out.println("Imprimiendo datos de lista de vuelosDiarios");
        for (VueloDiario vuelos : listaVuelosDiarios) {
            System.out.println(vuelos);
        }
        
        GuardaDatos.guardarVuelos(".\\src\\main\\resources\\csv\\vuelos.csv");
        GuardaDatos.guardarVuelosDiarios(".\\src\\main\\resources\\csv\\vuelosDiarios.csv");
    }

    public static List<VueloDiario> getVuelosdiarios() {
        return listaVuelosDiarios;
    }

    public class BuscadorDeMunicipios {

        public static String[] obtenerInformacionAeropuertos(List<Vuelos> listaVuelos, List<Aeropuerto> listaAeropuertos, String codVuelo) {
            String codIataOrigen = "";
            String codIataDestino = "";

            String nombreAeropuertoOrigen = "";
            String nombreAeropuertoDestino = "";

            // Buscar en listaVuelos el que coincide con codVuelo
            for (Vuelos vuelo : listaVuelos) {
                if (vuelo.getCodVuelo().equals(codVuelo)) {
                    codIataOrigen = vuelo.getCodIataOrigen();
                    codIataDestino = vuelo.getCodIataDestino();
                    break;
                }
            }

            String codMunicipioOrigen = "";
            String codMunicipioDestino = "";

            // Buscar los aeropuertos y obtener los códigos de municipio y nombres
            for (Aeropuerto aeropuerto : listaAeropuertos) {
                if (aeropuerto.getCodIata().equals(codIataOrigen)) {
                    codMunicipioOrigen = String.valueOf(aeropuerto.getCodMunicipio());
                    nombreAeropuertoOrigen = aeropuerto.getNombre();
                }
                if (aeropuerto.getCodIata().equals(codIataDestino)) {
                    codMunicipioDestino = String.valueOf(aeropuerto.getCodMunicipio());
                    nombreAeropuertoDestino = aeropuerto.getNombre();
                }
            }

            return new String[]{
                codMunicipioOrigen,
                codMunicipioDestino,
                nombreAeropuertoOrigen,
                nombreAeropuertoDestino
            };
        }
    }

}
