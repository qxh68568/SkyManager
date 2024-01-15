package logica;

import dto.Aerolinea;
import java.awt.TextField;
import javax.swing.JOptionPane;
import static logica.CargaDatos.listaAerolineas;

public class GestionAerolineas {

    public static void agregarAerolinea(TextField campoPrefijo, TextField campoCodigo, TextField campoNombre, TextField campoDireccion, TextField campoMunicipio, TextField campoTfnoPasajero, TextField campoTfnoAeropuerto) {
        try {
            String prefijo = campoPrefijo.getText();
            String codigo = campoCodigo.getText();
            String nombre = campoNombre.getText();
            String direccion = campoDireccion.getText();
            String municipio = campoMunicipio.getText();
            String tfnoPasajero = campoTfnoPasajero.getText();
            String tfnoAeropuerto = campoTfnoAeropuerto.getText();

            Aerolinea nuevaAerolinea = new Aerolinea(prefijo, codigo, nombre, direccion, municipio, tfnoPasajero, tfnoAeropuerto);
            listaAerolineas.add(nuevaAerolinea);

            System.out.println("Imprimiendo datos de lista actualizada de aerolineas");
            for (Aerolinea aerolinea : listaAerolineas) {
                System.out.println(aerolinea);
            }

        } catch (NumberFormatException e) {
            // Manejar el error, por ejemplo, mostrando un mensaje si el código no es un número válido
        }
    }

    public static Aerolinea AerolineaPorPrefijo(int prefijo) {
        return listaAerolineas.stream()
                .filter(aerolinea -> Integer.valueOf(prefijo).equals(aerolinea.getPrefijo()))
                .findFirst()
                .orElse(null);
    }

    public static String[] devolverPrefijos(String prefijoBuscado) {
        for (Aerolinea aerolinea : listaAerolineas) {
            if (aerolinea.getPrefijo().equals(prefijoBuscado)) {
                return new String[]{
                    String.valueOf(aerolinea.getPrefijo()),
                    aerolinea.getCodigo(),
                    aerolinea.getNombre(),
                    aerolinea.getDireccion(),
                    aerolinea.getMunicipio(),
                    aerolinea.getTfnoInfoPasajero(),
                    aerolinea.getTfnoInfoAeropuerto()
                };
            }
        }
        return null;
    }

    public static void eliminarAerolinea(String prefijoAerolinea) {
        listaAerolineas.removeIf(aerolinea -> aerolinea.getPrefijo().equals(prefijoAerolinea));
    }

    public static void modificarAerolinea(String prefijo, String codigo, String nombre, String direccion, String municipio, String tfnoPasajero, String tfnoAeropuerto) {
        for (Aerolinea aerolinea : listaAerolineas) {
            if (aerolinea.getPrefijo().equals(prefijo)) {
                aerolinea.setCodigo(codigo);
                aerolinea.setNombre(nombre);
                aerolinea.setDireccion(direccion);
                aerolinea.setMunicipio(municipio);
                aerolinea.setTfnoInfoPasajero(tfnoPasajero);
                aerolinea.setTfnoInfoAeropuerto(tfnoAeropuerto);
                return;
            }
        }
    }

    public static boolean validarAltaAerolinea(TextField campoPrefijo, TextField campoCodigo, TextField campoNombre,
            TextField campoDireccion, TextField campoMunicipio,
            TextField campoTfnoPasajero, TextField campoTfnoAeropuerto) {

        if (!validarPrefijo(campoPrefijo.getText())) {
            JOptionPane.showMessageDialog(null, "El prefijo no es válido. Debe ser un número de máximo 999.",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validarCodigo(campoCodigo.getText())) {
            JOptionPane.showMessageDialog(null, "El código no es válido. Debe ser de dos caracteres: una letra mayúscula seguida de una letra mayúscula o un número.",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Aquí puedes agregar validaciones para campoNombre, campoDireccion y campoMunicipio si es necesario
        if (!validarTelefono(campoTfnoPasajero.getText())) {
            JOptionPane.showMessageDialog(null, "El teléfono del pasajero no es válido. Debe comenzar con un código de país de tres dígitos seguido de hasta 12 dígitos más.",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validarTelefono(campoTfnoAeropuerto.getText())) {
            JOptionPane.showMessageDialog(null, "El teléfono del aeropuerto no es válido. Debe comenzar con un código de país de tres dígitos seguido de hasta 12 dígitos más.",
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true; // Todos los datos son válidos
    }

    private static boolean validarPrefijo(String prefijo) {
        try {
            int numero = Integer.parseInt(prefijo);
            return numero <= 999;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean validarCodigo(String codigo) {
        return codigo.matches("^[A-Z]([A-Z]|[0-9])$");
    }

    private static boolean validarTelefono(String telefono) {
        return telefono.matches("^\\d{3}-?\\d{0,12}$");
    }

}
