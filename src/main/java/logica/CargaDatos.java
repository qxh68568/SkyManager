package logica;

import dto.Aerolinea;
import dto.Aeropuerto;
import dto.Municipios;
import dto.VueloDiario;
import dto.Vuelos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CargaDatos {

    public static List<Aeropuerto> listaAeropuertos = new ArrayList<>();
        
    public static void cargarAeropuertos(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    int codMunicipio = Integer.parseInt(datos[2]);
                    Aeropuerto aeropuerto = new Aeropuerto(datos[0], datos[1], codMunicipio);
                    listaAeropuertos.add(aeropuerto);
                }
            }
        } catch (IOException e) {
        }
    }

    public static List<Aerolinea> listaAerolineas = new ArrayList<>();
        
    public static void cargarAerolineas(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 7) {
                    Aerolinea aerolinea = new Aerolinea(
                            datos[0], 
                            datos[1], 
                            datos[2], 
                            datos[3], 
                            datos[4], 
                            datos[5], 
                            datos[6]);
                    listaAerolineas.add(aerolinea);
                }
            }
        } catch (IOException e) {
        }
    }
    
    public static List<Vuelos> listaVuelos = new ArrayList<>();
        
    public static void cargarVuelos(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 7) {
                    Vuelos vuelo = new Vuelos(
                            datos[0], 
                            datos[1], 
                            datos[2], 
                            datos[3], 
                            datos[4], 
                            datos[5], 
                            datos[6]);
                    listaVuelos.add(vuelo);
                }
            }
        } catch (IOException e) {
        }
    }
    
    public static List<VueloDiario> listaVuelosDiarios = new ArrayList<>();

    public static void cargarVuelosDiarios(String rutaArchivo) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 6) {
                    Date fecha = formatoFecha.parse(datos[1]);
                    Date horaSalidaReal = formatoHora.parse(datos[2]);
                    Date horaLlegadaReal = formatoHora.parse(datos[3]);
                    float precio = Float.parseFloat(datos[5]);
                    VueloDiario vueloDiario = new VueloDiario(
                            datos[0], 
                            fecha, 
                            horaSalidaReal, 
                            horaLlegadaReal, 
                            datos[4], 
                            precio);
                    listaVuelosDiarios.add(vueloDiario);
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error al cargar vuelos diarios: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    public static List<Municipios> listaMunicipios = new ArrayList<>();
        
    public static void cargarMunicipios(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    int codMunicipio = Integer.parseInt(datos[0]);
                    Municipios municipios = new Municipios(codMunicipio, datos[1]);
                    listaMunicipios.add(municipios);
                }
            }
        } catch (IOException e) {
        }
    }
    
}

