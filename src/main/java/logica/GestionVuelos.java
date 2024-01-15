package logica;

import com.mashape.unirest.http.exceptions.UnirestException;
import dto.Temperaturas;
import dto.Vuelos;
import interfaz.SkyManager;
import java.awt.TextField;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import static logica.CargaDatos.listaVuelos;

public class GestionVuelos {

    public static void agregarVuelo(
            TextField campoCodVuelo,
            TextField campoIataOrigen,
            TextField campoIataDestino,
            TextField campoPlazas,
            JFormattedTextField campoHoraSalida,
            JFormattedTextField campoHoraLlegada,
            TextField campoDiasOpera) {
        try {
            String codVuelo = campoCodVuelo.getText();
            String codIataOrigen = campoIataOrigen.getText();
            String codIataDestino = campoIataDestino.getText();
            String numPlazas = campoPlazas.getText();
            String horaSalida = campoHoraSalida.getText();
            String horaLlegada = campoHoraLlegada.getText();
            String diasOpera = campoDiasOpera.getText();

            Vuelos nuevoVuelo = new Vuelos(codVuelo, codIataOrigen, codIataDestino, numPlazas, horaSalida, horaLlegada, diasOpera);
            listaVuelos.add(nuevoVuelo);

        } catch (NumberFormatException e) {
            // Manejar el error, por ejemplo, mostrando un mensaje si el código no es un número válido
        }
    }

    public static String[] devolverCodigo(String codigoBuscado) {
        for (Vuelos vuelo : listaVuelos) {
            if (vuelo.getCodVuelo().equals(codigoBuscado)) {
                return new String[]{
                    vuelo.getCodVuelo(),
                    vuelo.getCodIataOrigen(),
                    vuelo.getCodIataDestino(),
                    vuelo.getNumPlazas(),
                    vuelo.getHoraSalida(),
                    vuelo.getHoraLlegada(),
                    vuelo.getDiasOpera()
                };
            }
        }
        return null;
    }

    public static void eliminarVuelo(String codigoVuelo) {
        listaVuelos.removeIf(vuelo -> vuelo.getCodVuelo().equals(codigoVuelo));
    }

    public static void modificarVuelo(String codigo, String iataOrigen, String iataLlegada, String numPlazas, String horaSalida, String horaLlegada, String diasOpera) {
        for (Vuelos vuelo : listaVuelos) {
            if (vuelo.getCodVuelo().equals(codigo)) {
                vuelo.setCodIataOrigen(iataOrigen);
                vuelo.setCodIataDestino(iataLlegada);
                vuelo.setNumPlazas(numPlazas);
                vuelo.setHoraSalida(horaSalida);
                vuelo.setHoraLlegada(horaLlegada);
                vuelo.setDiasOpera(diasOpera);
                return;
            }
        }
    }

    public static boolean validarAltaVuelo(TextField campoCodVuelo, TextField campoIataOrigen, TextField campoIataDestino,
            TextField campoNumPlazas, JFormattedTextField campoHoraSalida, JFormattedTextField campoHoraLlegada,
            TextField campoDiasOpera) {

        if (!validarCodigoVuelo(campoCodVuelo.getText())) {
            JOptionPane.showMessageDialog(null, "Código de vuelo no válido. Debe ser un código de aerolinea seguido de hasta 4 dígitos.",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validarIata(campoIataOrigen.getText()) || !validarIata(campoIataDestino.getText())) {
            JOptionPane.showMessageDialog(null, "Código IATA no válido. Debe consistir en tres letras mayúsculas.",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validarNumPlazas(campoNumPlazas.getText())) {
            JOptionPane.showMessageDialog(null, "Número de plazas no válido. Debe ser un número entre 1 y 1000.",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validarHora(campoHoraSalida.getText()) || !validarHora(campoHoraLlegada.getText())) {
            JOptionPane.showMessageDialog(null, "Formato de hora no válido. Debe ser HH:mm.",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validarDiasOpera(campoDiasOpera.getText())) {
            JOptionPane.showMessageDialog(null, "Días de operación no válidos. Deben ser caracteres en mayúsculas de 'LMXJVSD', sin repetición y entre 1 y 7 caracteres.",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private static boolean validarCodigoVuelo(String codigo) {
        return codigo.matches("^[A-Z][A-Za-z0-9][0-9]{1,4}$");
    }

    private static boolean validarIata(String iata) {
        return iata.matches("^[A-Z]{3}$");
    }

    private static boolean validarNumPlazas(String numPlazas) {
        try {
            int numero = Integer.parseInt(numPlazas);
            return numero >= 1 && numero <= 1000;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean validarHora(String hora) {
        return hora.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");
    }

    private static boolean validarDiasOpera(String dias) {
        return dias.matches("^[LMXJVSD]{1,7}$") && dias.length() == dias.chars().distinct().count();
    }

    public static Temperaturas buscarTemperaturas(String codMunicipio) {
        try {
            Temperaturas t = ServiceREST.serviceSearch(codMunicipio);
            System.out.println("Maxima: " + t.getTempMax());
            System.out.println("Minima: " + t.getTempMin());
            return t;
        } catch (UnirestException ex) {
            Logger.getLogger(SkyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    

}
