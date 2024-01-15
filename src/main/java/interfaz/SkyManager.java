package interfaz;

import com.mashape.unirest.http.exceptions.UnirestException;
import dto.Aerolinea;
import dto.Aeropuerto;
import dto.Temperaturas;
import dto.VueloDiario;
import dto.Vuelos;
import interfaz.comboboxModels.FiltroComboBoxAerolineas;
import interfaz.comboboxModels.FiltroComboBoxDestino;
import interfaz.comboboxModels.FiltroComboBoxAeropuertos;
import interfaz.comboboxModels.FiltroComboBoxCodigo;
import interfaz.comboboxModels.FiltroComboBoxPrefijo;
import interfaz.tablemodels.AerolineasTableModel;
import interfaz.tablemodels.LlegadasTableModel;
import interfaz.tablemodels.PorAerolineaTableModel;
import interfaz.tablemodels.PorDestinoTableModel;
import interfaz.tablemodels.RecaudacionTableModel;
import interfaz.tablemodels.SalidasTableModel;
import interfaz.tablemodels.VuelosTableModel;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import logica.CargaDatos;
import static logica.CargaDatos.listaAeropuertos;
import static logica.CargaDatos.listaVuelos;
import logica.GestionAerolineas;
import logica.GestionVuelos;
import logica.GestionVuelosDiarios;
import logica.GestionVuelosDiarios.BuscadorDeMunicipios;
import logica.GuardaDatos;
import logica.ServiceREST;

public final class SkyManager extends javax.swing.JFrame {

    private JFXPanel jfxPanel;
    private JFrame frame;

    private final CardLayout cardLayout;

    private void setHelp() {
        jfxPanel = new JFXPanel();
        frame = new JFrame("Ayuda");
        frame.setSize(new Dimension(500, 500));
        frame.add(jfxPanel);
    }

    Stack<String> history = new Stack<>();

    public SkyManager() {
        initComponents();
        cardLayout = (CardLayout) jPanel1.getLayout();
        setHelp();
        CargaDatos.cargarAeropuertos(".\\src\\main\\resources\\csv\\aeropuertos.csv");
        CargaDatos.cargarAerolineas(".\\src\\main\\resources\\csv\\aerolineas.csv");
        CargaDatos.cargarVuelos(".\\src\\main\\resources\\csv\\vuelos.csv");
        CargaDatos.cargarVuelosDiarios(".\\src\\main\\resources\\csv\\vuelosDiarios.csv");
        CargaDatos.cargarMunicipios(".\\src\\main\\resources\\csv\\municipios.csv");
        /*
        System.out.println("Imprimiendo datos de csv de aeropuertos");
        for (Aeropuerto aeropuerto : listaAeropuertos) {
            System.out.println(aeropuerto);
        }
        System.out.println("Imprimiendo datos de csv de aerolineas");
        for (Aerolinea aerolinea : listaAerolineas) {
            System.out.println(aerolinea);
        }
        System.out.println("Imprimiendo datos de csv de vuelos");
        for (Vuelos vuelos : listaVuelos) {
            System.out.println(vuelos);
        }
        System.out.println("Imprimiendo datos de csv de vuelosDiarios");
        for (VueloDiario vuelos : listaVuelosDiarios) {
            System.out.println(vuelos);
        }
          System.out.println("Imprimiendo datos de csv de municipios");
        for (Municipios municipio : listaMunicipios) {
            System.out.println(municipio);
        }*/

        getModeloTablaAerolineas();
        getModeloTablaVuelos();
        getModeloTablaSalidas();
        getModeloTablaLlegadas();
        getModeloTablaPorAerolinea();
        getModeloTablaRecaudacion();
        getModeloTablaPorDestino();
        getModeloComboBoxAeropuertos();
        getModeloComboBoxAerolineas();
        getModeloComboBoxDestino();
        getModeloComboBoxPrefijo();
        getModeloComboBoxPrefijoMod();
        getModeloComboBoxCodigo();
        getModeloComboBoxCodigoMod();

    }

    public void changePanel(String panelName) {
        history.push(panelName);
        cardLayout.show(jPanel1, panelName);
    }

    public AerolineasTableModel getModeloTablaAerolineas() {
        AerolineasTableModel modeloTabla = new AerolineasTableModel(CargaDatos.listaAerolineas);
        tablaAerolineas.setModel(modeloTabla);
        return modeloTabla;
    }

    public VuelosTableModel getModeloTablaVuelos() {
        VuelosTableModel modeloTablaVuelos = new VuelosTableModel(CargaDatos.listaVuelos);
        tablaVuelos.setModel(modeloTablaVuelos);
        return modeloTablaVuelos;
    }

    public SalidasTableModel getModeloTablaSalidas() {
        SalidasTableModel modeloTablaSalidas = new SalidasTableModel(CargaDatos.listaVuelosDiarios);
        tablaSalidas.setModel(modeloTablaSalidas);
        return modeloTablaSalidas;
    }

    public LlegadasTableModel getModeloTablaLlegadas() {
        LlegadasTableModel modeloTablaLlegadas = new LlegadasTableModel(CargaDatos.listaVuelosDiarios);
        tablaLlegadas.setModel(modeloTablaLlegadas);
        modeloTablaLlegadas.filtrarPorFecha(new Date());
        return modeloTablaLlegadas;
    }

    public PorAerolineaTableModel getModeloTablaPorAerolinea() {
        PorAerolineaTableModel modeloTablaPorAerolinea = new PorAerolineaTableModel(CargaDatos.listaVuelosDiarios);
        tablaVuelosPorAerolinea.setModel(modeloTablaPorAerolinea);
        modeloTablaPorAerolinea.filtrarPorFecha(new Date());
        return modeloTablaPorAerolinea;
    }

    public RecaudacionTableModel getModeloTablaRecaudacion() {
        RecaudacionTableModel modeloTablaRecaudacion = new RecaudacionTableModel(CargaDatos.listaVuelosDiarios);
        tablaRecaudacion.setModel(modeloTablaRecaudacion);
        modeloTablaRecaudacion.filtrarPorFecha(new Date());
        return modeloTablaRecaudacion;
    }

    public PorDestinoTableModel getModeloTablaPorDestino() {
        PorDestinoTableModel modeloTablaPorDestino = new PorDestinoTableModel(CargaDatos.listaVuelosDiarios);
        tablaVuelosPorDestino.setModel(modeloTablaPorDestino);
        modeloTablaPorDestino.filtrarPorFecha(new Date());
        return modeloTablaPorDestino;
    }

    public FiltroComboBoxAeropuertos getModeloComboBoxAeropuertos() {
        FiltroComboBoxAeropuertos modeloCombobox = new FiltroComboBoxAeropuertos(CargaDatos.listaAeropuertos);
        comboAeropuertoBase.setModel(modeloCombobox);
        comboAeropuertoBase.setRenderer(modeloCombobox.crearRenderizador());
        filtroAeropuertoBase.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String texto = filtroAeropuertoBase.getText();
                modeloCombobox.filtrarDatos(texto);
            }
        });
        comboAeropuertoBase.addItem(null);
        comboAeropuertoBase.setSelectedItem(null);
        return modeloCombobox;
    }

    public FiltroComboBoxAerolineas getModeloComboBoxAerolineas() {
        FiltroComboBoxAerolineas modeloCombobox = new FiltroComboBoxAerolineas(CargaDatos.listaAerolineas);
        comboBoxAerolineas.setModel(modeloCombobox);

        comboBoxAerolineas.setRenderer(modeloCombobox.crearRenderizador());
        comboBoxAerolineas.addItem(null);
        comboBoxAerolineas.setSelectedItem(null);
        return modeloCombobox;
    }

    public FiltroComboBoxDestino getModeloComboBoxDestino() {
        FiltroComboBoxDestino modeloCombobox = new FiltroComboBoxDestino(CargaDatos.listaAeropuertos);
        comboBoxDestino.setModel(modeloCombobox);
        comboBoxDestino.setRenderer(modeloCombobox.crearRenderizador());
        comboBoxDestino.addItem(null);
        comboBoxDestino.setSelectedItem(null);
        return modeloCombobox;
    }

    public FiltroComboBoxPrefijo getModeloComboBoxPrefijo() {
        FiltroComboBoxPrefijo modeloCombobox = new FiltroComboBoxPrefijo(CargaDatos.listaAerolineas);
        comboBoxPrefijo.setModel(modeloCombobox);
        comboBoxPrefijo.setRenderer(modeloCombobox.crearRenderizador());
        comboBoxPrefijo.addItem(null);
        comboBoxPrefijo.setSelectedItem(null);
        campoCodigo1.setText("");
        campoNombre1.setText("");
        campoDireccion1.setText("");
        campoMunicipio1.setText("");
        campoTfnoPasajero1.setText("");
        campoTfnoAeropuerto1.setText("");
        return modeloCombobox;
    }

    public FiltroComboBoxPrefijo getModeloComboBoxPrefijoMod() {
        FiltroComboBoxPrefijo modeloCombobox = new FiltroComboBoxPrefijo(CargaDatos.listaAerolineas);
        comboBoxPrefijoMod.setModel(modeloCombobox);
        comboBoxPrefijoMod.setRenderer(modeloCombobox.crearRenderizador());
        comboBoxPrefijoMod.addItem(null);
        comboBoxPrefijoMod.setSelectedItem(null);
        campoCodigo2.setText("");
        campoNombre2.setText("");
        campoDireccion2.setText("");
        campoMunicipio2.setText("");
        campoTfnoPasajero2.setText("");
        campoTfnoAeropuerto2.setText("");
        return modeloCombobox;
    }

    public FiltroComboBoxCodigo getModeloComboBoxCodigo() {
        FiltroComboBoxCodigo modeloCombobox = new FiltroComboBoxCodigo(CargaDatos.listaVuelos);
        comboBoxCodigo.setModel(modeloCombobox);
        comboBoxCodigo.setRenderer(modeloCombobox.crearRenderizador());
        comboBoxCodigo.addItem(null);
        comboBoxCodigo.setSelectedItem(null);
        campoIataOrigenBaja.setText("");
        campoIataDestBaja.setText("");
        campoPlazasBaja.setText("");
        campoHoraSalidaBaja.setText("");
        campoHoraLlegadaBaja.setText("");
        campoDiasOperaBaja.setText("");
        return modeloCombobox;
    }

    public FiltroComboBoxCodigo getModeloComboBoxCodigoMod() {
        FiltroComboBoxCodigo modeloCombobox = new FiltroComboBoxCodigo(CargaDatos.listaVuelos);
        comboBoxCodigoMod.setModel(modeloCombobox);
        comboBoxCodigoMod.setRenderer(modeloCombobox.crearRenderizador());
        comboBoxCodigoMod.addItem(null);
        comboBoxCodigoMod.setSelectedItem(null);
        campoIataOrigenMod.setText("");
        campoIataDestinoMod.setText("");
        campoPlazasMod.setText("");
        campoHoraSalidaMod.setText("");
        campoHoraLlegadaMod.setText("");
        campoDiasOperaMod.setText("");
        return modeloCombobox;
    }

    public void actualizarRecaudacion(float nuevaRecaudacion) {
        jLabelRecaudacion.setText("Recaudación Total: " + String.format("%.2f", nuevaRecaudacion) + " €");
    }

    @Override
    public Image getIconImage() {
        Image icono = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/icono.png"));
        return icono;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelMenuPrincipal = new javax.swing.JPanel();
        btnGestionAerolineas = new javax.swing.JButton();
        btnGestionVuelos = new javax.swing.JButton();
        btnInformacionVuelos = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        comboAeropuertoBase = new javax.swing.JComboBox<>();
        filtroAeropuertoBase = new javax.swing.JTextField();
        panelGestionAerolineas = new javax.swing.JPanel();
        txtTituloAerolineas = new javax.swing.JLabel();
        btnAltaAerolineas = new javax.swing.JButton();
        btnBajaAerolineas = new javax.swing.JButton();
        btnConsultaAerolineas = new javax.swing.JButton();
        btnModificacionAerolineas = new javax.swing.JButton();
        panelAltaAerolineas = new javax.swing.JPanel();
        txtAltaAerolineas = new javax.swing.JLabel();
        label1 = new java.awt.Label();
        campoPrefijo = new java.awt.TextField();
        label2 = new java.awt.Label();
        campoCodigo = new java.awt.TextField();
        label3 = new java.awt.Label();
        campoNombre = new java.awt.TextField();
        label4 = new java.awt.Label();
        campoDireccion = new java.awt.TextField();
        label5 = new java.awt.Label();
        campoMunicipio = new java.awt.TextField();
        label6 = new java.awt.Label();
        campoTfnoPasajero = new java.awt.TextField();
        label7 = new java.awt.Label();
        campoTfnoAeropuerto = new java.awt.TextField();
        btnDarAltaAerolineas = new javax.swing.JButton();
        panelBajaAerolineas = new javax.swing.JPanel();
        txtAltaAerolineas1 = new javax.swing.JLabel();
        label8 = new java.awt.Label();
        comboBoxPrefijo = new javax.swing.JComboBox<>();
        label9 = new java.awt.Label();
        campoCodigo1 = new java.awt.TextField();
        label10 = new java.awt.Label();
        campoNombre1 = new java.awt.TextField();
        label11 = new java.awt.Label();
        campoDireccion1 = new java.awt.TextField();
        label12 = new java.awt.Label();
        campoMunicipio1 = new java.awt.TextField();
        label13 = new java.awt.Label();
        campoTfnoPasajero1 = new java.awt.TextField();
        label14 = new java.awt.Label();
        campoTfnoAeropuerto1 = new java.awt.TextField();
        btnDarBajaAerolineas = new javax.swing.JButton();
        panelConsultaAerolineas = new javax.swing.JPanel();
        tablaConsultaAerolineas = new javax.swing.JScrollPane();
        tablaAerolineas = new javax.swing.JTable();
        panelModAerolineas = new javax.swing.JPanel();
        txtAltaAerolineas2 = new javax.swing.JLabel();
        label15 = new java.awt.Label();
        label16 = new java.awt.Label();
        campoCodigo2 = new java.awt.TextField();
        label17 = new java.awt.Label();
        campoNombre2 = new java.awt.TextField();
        label18 = new java.awt.Label();
        campoDireccion2 = new java.awt.TextField();
        label19 = new java.awt.Label();
        campoMunicipio2 = new java.awt.TextField();
        label20 = new java.awt.Label();
        campoTfnoPasajero2 = new java.awt.TextField();
        label21 = new java.awt.Label();
        campoTfnoAeropuerto2 = new java.awt.TextField();
        btnDarModAerolineas = new javax.swing.JButton();
        comboBoxPrefijoMod = new javax.swing.JComboBox<>();
        panelGestionVuelos = new javax.swing.JPanel();
        txtTituloVuelos = new javax.swing.JLabel();
        btnAltaVuelos = new javax.swing.JButton();
        btnBajaVuelos = new javax.swing.JButton();
        btnConsultaVuelos = new javax.swing.JButton();
        btnModificacionVuelos = new javax.swing.JButton();
        panelAltaVuelos = new javax.swing.JPanel();
        txtAltaAerolineas3 = new javax.swing.JLabel();
        label22 = new java.awt.Label();
        campoCodVuelo = new java.awt.TextField();
        label23 = new java.awt.Label();
        campoIataOrigen = new java.awt.TextField();
        label24 = new java.awt.Label();
        campoIataDestino = new java.awt.TextField();
        label25 = new java.awt.Label();
        campoNumPlazas = new java.awt.TextField();
        label26 = new java.awt.Label();
        campoHoraSalida = new javax.swing.JFormattedTextField();
        label27 = new java.awt.Label();
        campoHoraLlegada = new javax.swing.JFormattedTextField();
        label28 = new java.awt.Label();
        campoDiasOpera = new java.awt.TextField();
        btnDarAltaVuelos = new javax.swing.JButton();
        panelBajaVuelos = new javax.swing.JPanel();
        txtAltaAerolineas4 = new javax.swing.JLabel();
        label29 = new java.awt.Label();
        label30 = new java.awt.Label();
        campoIataOrigenBaja = new java.awt.TextField();
        label31 = new java.awt.Label();
        campoIataDestBaja = new java.awt.TextField();
        label32 = new java.awt.Label();
        campoPlazasBaja = new java.awt.TextField();
        label33 = new java.awt.Label();
        campoHoraSalidaBaja = new java.awt.TextField();
        label34 = new java.awt.Label();
        campoHoraLlegadaBaja = new java.awt.TextField();
        label35 = new java.awt.Label();
        campoDiasOperaBaja = new java.awt.TextField();
        btnDarBajaVuelos = new javax.swing.JButton();
        comboBoxCodigo = new javax.swing.JComboBox<>();
        panelConsultaVuelos = new javax.swing.JPanel();
        tablaConsultaVuelos = new javax.swing.JScrollPane();
        tablaVuelos = new javax.swing.JTable();
        panelModVuelos = new javax.swing.JPanel();
        txtAltaAerolineas5 = new javax.swing.JLabel();
        label36 = new java.awt.Label();
        label37 = new java.awt.Label();
        campoIataOrigenMod = new java.awt.TextField();
        label38 = new java.awt.Label();
        campoIataDestinoMod = new java.awt.TextField();
        label39 = new java.awt.Label();
        campoPlazasMod = new java.awt.TextField();
        label40 = new java.awt.Label();
        campoHoraSalidaMod = new java.awt.TextField();
        label41 = new java.awt.Label();
        campoHoraLlegadaMod = new java.awt.TextField();
        label42 = new java.awt.Label();
        campoDiasOperaMod = new java.awt.TextField();
        btnDarModVuelo = new javax.swing.JButton();
        comboBoxCodigoMod = new javax.swing.JComboBox<>();
        panelInformacionVuelos = new javax.swing.JPanel();
        txtTituloInformacion = new javax.swing.JLabel();
        btnSalidasInf = new javax.swing.JButton();
        btnLlegadasInf = new javax.swing.JButton();
        btnAerolineasInf = new javax.swing.JButton();
        btnRecaudacionInf = new javax.swing.JButton();
        btnDestinoInf = new javax.swing.JButton();
        panelSalidas = new javax.swing.JPanel();
        tablaSalidasVuelos = new javax.swing.JScrollPane();
        tablaSalidas = new javax.swing.JTable();
        calendarioSalidas = new com.toedter.calendar.JCalendar();
        componenteTemp = new componente.ComponenteTemp();
        panelLegadas = new javax.swing.JPanel();
        tablaLlegadasVuelos = new javax.swing.JScrollPane();
        tablaLlegadas = new javax.swing.JTable();
        calendarioLlegadas = new com.toedter.calendar.JCalendar();
        componenteTemp2 = new componente.ComponenteTemp();
        panelVuelosAerolinea = new javax.swing.JPanel();
        tablaVuelosAerolinea = new javax.swing.JScrollPane();
        tablaVuelosPorAerolinea = new javax.swing.JTable();
        calendarioVuelosAerolinea = new com.toedter.calendar.JCalendar();
        comboBoxAerolineas = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        panelVuelosDestino = new javax.swing.JPanel();
        tablaVuelosDestino = new javax.swing.JScrollPane();
        tablaVuelosPorDestino = new javax.swing.JTable();
        calendarioVuelosDestino = new com.toedter.calendar.JCalendar();
        comboBoxDestino = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        panelRecaudacion = new javax.swing.JPanel();
        tablaDeRecaudacion = new javax.swing.JScrollPane();
        tablaRecaudacion = new javax.swing.JTable();
        calendarioRecaudacion = new com.toedter.calendar.JCalendar();
        jLabelRecaudacion = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnVolverPrincipal = new javax.swing.JButton();
        btnSalirPrincipal = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        btnAyuda = new javax.swing.JMenu();
        jMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SkyManager");
        setIconImage(getIconImage());
        setLocation(new java.awt.Point(200, 200));
        setMinimumSize(new java.awt.Dimension(800, 600));

        jPanel1.setLayout(new java.awt.CardLayout());

        panelMenuPrincipal.setOpaque(false);

        btnGestionAerolineas.setText("Gestión Aerolineas");
        btnGestionAerolineas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionAerolineasActionPerformed(evt);
            }
        });

        btnGestionVuelos.setText("Gestión Vuelos");
        btnGestionVuelos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionVuelosActionPerformed(evt);
            }
        });

        btnInformacionVuelos.setText("Información Vuelos");
        btnInformacionVuelos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformacionVuelosActionPerformed(evt);
            }
        });

        jLabel1.setText("Aeropuerto Base:");

        comboAeropuertoBase.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboAeropuertoBase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAeropuertoBaseActionPerformed(evt);
            }
        });

        filtroAeropuertoBase.setText("escriba para filtrar");

        javax.swing.GroupLayout panelMenuPrincipalLayout = new javax.swing.GroupLayout(panelMenuPrincipal);
        panelMenuPrincipal.setLayout(panelMenuPrincipalLayout);
        panelMenuPrincipalLayout.setHorizontalGroup(
            panelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuPrincipalLayout.createSequentialGroup()
                .addContainerGap(311, Short.MAX_VALUE)
                .addGroup(panelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnInformacionVuelos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGestionVuelos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGestionAerolineas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(filtroAeropuertoBase)
                        .addComponent(comboAeropuertoBase, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelMenuPrincipalLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(311, Short.MAX_VALUE))
        );
        panelMenuPrincipalLayout.setVerticalGroup(
            panelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuPrincipalLayout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboAeropuertoBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(filtroAeropuertoBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(btnGestionAerolineas)
                .addGap(27, 27, 27)
                .addComponent(btnGestionVuelos)
                .addGap(29, 29, 29)
                .addComponent(btnInformacionVuelos)
                .addContainerGap(244, Short.MAX_VALUE))
        );

        jPanel1.add(panelMenuPrincipal, "menuPrincipal");

        txtTituloAerolineas.setText("GESTION DE AEROLINEAS");

        btnAltaAerolineas.setText("Alta");
        btnAltaAerolineas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAltaAerolineasActionPerformed(evt);
            }
        });

        btnBajaAerolineas.setText("Baja");
        btnBajaAerolineas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBajaAerolineasActionPerformed(evt);
            }
        });

        btnConsultaAerolineas.setText("Consulta");
        btnConsultaAerolineas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaAerolineasActionPerformed(evt);
            }
        });

        btnModificacionAerolineas.setText("Modificación");
        btnModificacionAerolineas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificacionAerolineasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGestionAerolineasLayout = new javax.swing.GroupLayout(panelGestionAerolineas);
        panelGestionAerolineas.setLayout(panelGestionAerolineasLayout);
        panelGestionAerolineasLayout.setHorizontalGroup(
            panelGestionAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGestionAerolineasLayout.createSequentialGroup()
                .addContainerGap(305, Short.MAX_VALUE)
                .addGroup(panelGestionAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnBajaAerolineas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAltaAerolineas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTituloAerolineas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificacionAerolineas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConsultaAerolineas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(315, Short.MAX_VALUE))
        );
        panelGestionAerolineasLayout.setVerticalGroup(
            panelGestionAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGestionAerolineasLayout.createSequentialGroup()
                .addContainerGap(156, Short.MAX_VALUE)
                .addComponent(txtTituloAerolineas)
                .addGap(18, 18, 18)
                .addComponent(btnAltaAerolineas)
                .addGap(32, 32, 32)
                .addComponent(btnBajaAerolineas)
                .addGap(29, 29, 29)
                .addComponent(btnConsultaAerolineas)
                .addGap(30, 30, 30)
                .addComponent(btnModificacionAerolineas)
                .addContainerGap(218, Short.MAX_VALUE))
        );

        jPanel1.add(panelGestionAerolineas, "gestionAerolineas");

        txtAltaAerolineas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtAltaAerolineas.setText("ALTA DE AEROLINEAS");
        txtAltaAerolineas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        label1.setText("Prefijo");

        campoPrefijo.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        label2.setText("Código");

        label3.setText("Nombre");

        label4.setText("Dirección");

        label5.setText("Municipio");

        label6.setText("Tfno Inf Pasajero");

        label7.setText("Tfno Inf Aeropuerto");

        btnDarAltaAerolineas.setText("Dar de Alta");
        btnDarAltaAerolineas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDarAltaAerolineasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAltaAerolineasLayout = new javax.swing.GroupLayout(panelAltaAerolineas);
        panelAltaAerolineas.setLayout(panelAltaAerolineasLayout);
        panelAltaAerolineasLayout.setHorizontalGroup(
            panelAltaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAltaAerolineasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelAltaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAltaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtAltaAerolineas, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelAltaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAltaAerolineasLayout.createSequentialGroup()
                                .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(campoTfnoAeropuerto, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAltaAerolineasLayout.createSequentialGroup()
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addComponent(campoPrefijo, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAltaAerolineasLayout.createSequentialGroup()
                                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66)
                                .addComponent(campoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAltaAerolineasLayout.createSequentialGroup()
                                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(campoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAltaAerolineasLayout.createSequentialGroup()
                                .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(campoDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAltaAerolineasLayout.createSequentialGroup()
                                .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(campoMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAltaAerolineasLayout.createSequentialGroup()
                                .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(campoTfnoPasajero, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(btnDarAltaAerolineas))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAltaAerolineasLayout.setVerticalGroup(
            panelAltaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAltaAerolineasLayout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addComponent(txtAltaAerolineas)
                .addGap(34, 34, 34)
                .addGroup(panelAltaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoPrefijo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelAltaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelAltaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelAltaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelAltaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelAltaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoTfnoPasajero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelAltaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoTfnoAeropuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(btnDarAltaAerolineas)
                .addContainerGap(214, Short.MAX_VALUE))
        );

        jPanel1.add(panelAltaAerolineas, "altaAerolineas");

        txtAltaAerolineas1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtAltaAerolineas1.setText("BAJA DE AEROLINEAS");
        txtAltaAerolineas1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        label8.setText("Prefijo");

        comboBoxPrefijo.setToolTipText("");
        comboBoxPrefijo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxPrefijoActionPerformed(evt);
            }
        });

        label9.setText("Código");

        campoCodigo1.setEditable(false);

        label10.setText("Nombre");

        campoNombre1.setEditable(false);

        label11.setText("Dirección");

        campoDireccion1.setEditable(false);

        label12.setText("Municipio");

        campoMunicipio1.setEditable(false);

        label13.setText("Tfno Inf Pasajero");

        campoTfnoPasajero1.setEditable(false);

        label14.setText("Tfno Inf Aeropuerto");

        campoTfnoAeropuerto1.setEditable(false);

        btnDarBajaAerolineas.setText("Dar de Baja");
        btnDarBajaAerolineas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDarBajaAerolineasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBajaAerolineasLayout = new javax.swing.GroupLayout(panelBajaAerolineas);
        panelBajaAerolineas.setLayout(panelBajaAerolineasLayout);
        panelBajaAerolineasLayout.setHorizontalGroup(
            panelBajaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBajaAerolineasLayout.createSequentialGroup()
                .addContainerGap(199, Short.MAX_VALUE)
                .addGroup(panelBajaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtAltaAerolineas1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelBajaAerolineasLayout.createSequentialGroup()
                        .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addComponent(comboBoxPrefijo, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBajaAerolineasLayout.createSequentialGroup()
                        .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(campoCodigo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelBajaAerolineasLayout.createSequentialGroup()
                        .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(campoNombre1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelBajaAerolineasLayout.createSequentialGroup()
                        .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(campoDireccion1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelBajaAerolineasLayout.createSequentialGroup()
                        .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(campoMunicipio1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelBajaAerolineasLayout.createSequentialGroup()
                        .addComponent(label13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(campoTfnoPasajero1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelBajaAerolineasLayout.createSequentialGroup()
                        .addComponent(label14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(campoTfnoAeropuerto1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelBajaAerolineasLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(btnDarBajaAerolineas)))
                .addContainerGap(166, Short.MAX_VALUE))
        );
        panelBajaAerolineasLayout.setVerticalGroup(
            panelBajaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBajaAerolineasLayout.createSequentialGroup()
                .addContainerGap(124, Short.MAX_VALUE)
                .addComponent(txtAltaAerolineas1)
                .addGap(34, 34, 34)
                .addGroup(panelBajaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxPrefijo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(panelBajaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoCodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelBajaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelBajaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelBajaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoMunicipio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelBajaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoTfnoPasajero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelBajaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoTfnoAeropuerto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(btnDarBajaAerolineas)
                .addContainerGap(154, Short.MAX_VALUE))
        );

        jPanel1.add(panelBajaAerolineas, "bajaAerolineas");

        tablaAerolineas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaConsultaAerolineas.setViewportView(tablaAerolineas);

        javax.swing.GroupLayout panelConsultaAerolineasLayout = new javax.swing.GroupLayout(panelConsultaAerolineas);
        panelConsultaAerolineas.setLayout(panelConsultaAerolineasLayout);
        panelConsultaAerolineasLayout.setHorizontalGroup(
            panelConsultaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaAerolineasLayout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addComponent(tablaConsultaAerolineas, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );
        panelConsultaAerolineasLayout.setVerticalGroup(
            panelConsultaAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaAerolineasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tablaConsultaAerolineas, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(panelConsultaAerolineas, "consultaAerolineas");

        txtAltaAerolineas2.setText("MODIFICAR AEROLINEAS");

        label15.setText("Prefijo");

        label16.setText("Código");

        label17.setText("Nombre");

        label18.setText("Dirección");

        label19.setText("Municipio");

        label20.setText("Tfno Inf Pasajero");

        label21.setText("Tfno Inf Aeropuerto");

        btnDarModAerolineas.setText("Modificar");
        btnDarModAerolineas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDarModAerolineasActionPerformed(evt);
            }
        });

        comboBoxPrefijoMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxPrefijoModActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelModAerolineasLayout = new javax.swing.GroupLayout(panelModAerolineas);
        panelModAerolineas.setLayout(panelModAerolineasLayout);
        panelModAerolineasLayout.setHorizontalGroup(
            panelModAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelModAerolineasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelModAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelModAerolineasLayout.createSequentialGroup()
                        .addComponent(label20, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(campoTfnoPasajero2, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelModAerolineasLayout.createSequentialGroup()
                        .addComponent(label19, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(campoMunicipio2, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelModAerolineasLayout.createSequentialGroup()
                        .addComponent(label18, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(campoDireccion2, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelModAerolineasLayout.createSequentialGroup()
                        .addGroup(panelModAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label17, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60)
                        .addGroup(panelModAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(campoCodigo2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(campoNombre2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboBoxPrefijoMod, javax.swing.GroupLayout.Alignment.TRAILING, 0, 318, Short.MAX_VALUE)))
                    .addGroup(panelModAerolineasLayout.createSequentialGroup()
                        .addComponent(label21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(campoTfnoAeropuerto2, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelModAerolineasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDarModAerolineas)
                .addGap(354, 354, 354))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelModAerolineasLayout.createSequentialGroup()
                .addContainerGap(337, Short.MAX_VALUE)
                .addComponent(txtAltaAerolineas2)
                .addGap(324, 324, 324))
        );
        panelModAerolineasLayout.setVerticalGroup(
            panelModAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelModAerolineasLayout.createSequentialGroup()
                .addContainerGap(106, Short.MAX_VALUE)
                .addComponent(txtAltaAerolineas2)
                .addGap(27, 27, 27)
                .addGroup(panelModAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(comboBoxPrefijoMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelModAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoCodigo2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelModAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelModAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoDireccion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelModAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoMunicipio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelModAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoTfnoPasajero2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelModAerolineasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoTfnoAeropuerto2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(btnDarModAerolineas)
                .addContainerGap(183, Short.MAX_VALUE))
        );

        jPanel1.add(panelModAerolineas, "modAerolineas");

        txtTituloVuelos.setText("GESTION DE VUELOS");

        btnAltaVuelos.setText("Alta");
        btnAltaVuelos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAltaVuelosActionPerformed(evt);
            }
        });

        btnBajaVuelos.setText("Baja");
        btnBajaVuelos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBajaVuelosActionPerformed(evt);
            }
        });

        btnConsultaVuelos.setText("Consulta");
        btnConsultaVuelos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaVuelosActionPerformed(evt);
            }
        });

        btnModificacionVuelos.setText("Modificación");
        btnModificacionVuelos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificacionVuelosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGestionVuelosLayout = new javax.swing.GroupLayout(panelGestionVuelos);
        panelGestionVuelos.setLayout(panelGestionVuelosLayout);
        panelGestionVuelosLayout.setHorizontalGroup(
            panelGestionVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGestionVuelosLayout.createSequentialGroup()
                .addContainerGap(303, Short.MAX_VALUE)
                .addGroup(panelGestionVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTituloVuelos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAltaVuelos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBajaVuelos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConsultaVuelos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificacionVuelos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(343, Short.MAX_VALUE))
        );
        panelGestionVuelosLayout.setVerticalGroup(
            panelGestionVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGestionVuelosLayout.createSequentialGroup()
                .addContainerGap(145, Short.MAX_VALUE)
                .addComponent(txtTituloVuelos)
                .addGap(34, 34, 34)
                .addComponent(btnAltaVuelos)
                .addGap(27, 27, 27)
                .addComponent(btnBajaVuelos)
                .addGap(27, 27, 27)
                .addComponent(btnConsultaVuelos)
                .addGap(27, 27, 27)
                .addComponent(btnModificacionVuelos)
                .addContainerGap(223, Short.MAX_VALUE))
        );

        jPanel1.add(panelGestionVuelos, "gestionVuelos");

        txtAltaAerolineas3.setText("ALTA DE VUELOS");

        label22.setText("Código");

        campoCodVuelo.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        label23.setText("Iata Origen");

        label24.setText("Iata Destino");

        label25.setText("Número de plazas");

        campoNumPlazas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNumPlazasActionPerformed(evt);
            }
        });

        label26.setText("Hora de salida");

        campoHoraSalida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));

        label27.setText("Hora de llegada");

        campoHoraLlegada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));

        label28.setText("Días que opera");

        btnDarAltaVuelos.setText("Dar de Alta");
        btnDarAltaVuelos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDarAltaVuelosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAltaVuelosLayout = new javax.swing.GroupLayout(panelAltaVuelos);
        panelAltaVuelos.setLayout(panelAltaVuelosLayout);
        panelAltaVuelosLayout.setHorizontalGroup(
            panelAltaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAltaVuelosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelAltaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAltaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAltaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAltaVuelosLayout.createSequentialGroup()
                                .addComponent(label22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(76, 76, 76)
                                .addComponent(campoCodVuelo, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAltaVuelosLayout.createSequentialGroup()
                                .addComponent(label23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(campoIataOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAltaVuelosLayout.createSequentialGroup()
                                .addComponent(label24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(campoIataDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAltaVuelosLayout.createSequentialGroup()
                                .addComponent(label25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(campoNumPlazas, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAltaVuelosLayout.createSequentialGroup()
                                .addComponent(label26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(campoHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAltaVuelosLayout.createSequentialGroup()
                                .addComponent(label27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(campoHoraLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAltaVuelosLayout.createSequentialGroup()
                                .addComponent(label28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(campoDiasOpera, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAltaVuelosLayout.createSequentialGroup()
                            .addComponent(btnDarAltaVuelos)
                            .addGap(108, 108, 108)))
                    .addComponent(txtAltaAerolineas3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAltaVuelosLayout.setVerticalGroup(
            panelAltaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAltaVuelosLayout.createSequentialGroup()
                .addContainerGap(110, Short.MAX_VALUE)
                .addComponent(txtAltaAerolineas3)
                .addGap(29, 29, 29)
                .addGroup(panelAltaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoCodVuelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelAltaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoIataOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(panelAltaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoIataDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(panelAltaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoNumPlazas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelAltaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(panelAltaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAltaVuelosLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(label27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(campoHoraLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(panelAltaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoDiasOpera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(btnDarAltaVuelos)
                .addContainerGap(130, Short.MAX_VALUE))
        );

        jPanel1.add(panelAltaVuelos, "altaVuelos");

        txtAltaAerolineas4.setText("BAJA DE VUELOS");

        label29.setText("Código");

        label30.setText("Iata origen");

        campoIataOrigenBaja.setEditable(false);

        label31.setText("Iata destino");

        campoIataDestBaja.setEditable(false);

        label32.setText("Plazas");

        campoPlazasBaja.setEditable(false);

        label33.setText("Hora de salida");

        campoHoraSalidaBaja.setEditable(false);

        label34.setText("Hora llegada");

        campoHoraLlegadaBaja.setEditable(false);

        label35.setText("Dias que opera");

        campoDiasOperaBaja.setEditable(false);

        btnDarBajaVuelos.setText("Dar de Baja");
        btnDarBajaVuelos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDarBajaVuelosActionPerformed(evt);
            }
        });

        comboBoxCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxCodigoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBajaVuelosLayout = new javax.swing.GroupLayout(panelBajaVuelos);
        panelBajaVuelos.setLayout(panelBajaVuelosLayout);
        panelBajaVuelosLayout.setHorizontalGroup(
            panelBajaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBajaVuelosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBajaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBajaVuelosLayout.createSequentialGroup()
                        .addComponent(label31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(campoIataDestBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBajaVuelosLayout.createSequentialGroup()
                        .addComponent(label32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(campoPlazasBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBajaVuelosLayout.createSequentialGroup()
                        .addComponent(label33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(campoHoraSalidaBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBajaVuelosLayout.createSequentialGroup()
                        .addComponent(label34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(campoHoraLlegadaBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBajaVuelosLayout.createSequentialGroup()
                        .addComponent(label35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(campoDiasOperaBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBajaVuelosLayout.createSequentialGroup()
                        .addGroup(panelBajaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(panelBajaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(campoIataOrigenBaja, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                            .addComponent(comboBoxCodigo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txtAltaAerolineas4)
                    .addComponent(btnDarBajaVuelos))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBajaVuelosLayout.setVerticalGroup(
            panelBajaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBajaVuelosLayout.createSequentialGroup()
                .addContainerGap(98, Short.MAX_VALUE)
                .addComponent(txtAltaAerolineas4)
                .addGap(67, 67, 67)
                .addGroup(panelBajaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBajaVuelosLayout.createSequentialGroup()
                        .addComponent(label29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addGroup(panelBajaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoIataOrigenBaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(panelBajaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoIataDestBaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(panelBajaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoPlazasBaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(panelBajaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoHoraSalidaBaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(panelBajaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoHoraLlegadaBaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(panelBajaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoDiasOperaBaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(comboBoxCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(btnDarBajaVuelos)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        jPanel1.add(panelBajaVuelos, "bajaVuelos");

        tablaVuelos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaConsultaVuelos.setViewportView(tablaVuelos);

        javax.swing.GroupLayout panelConsultaVuelosLayout = new javax.swing.GroupLayout(panelConsultaVuelos);
        panelConsultaVuelos.setLayout(panelConsultaVuelosLayout);
        panelConsultaVuelosLayout.setHorizontalGroup(
            panelConsultaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaVuelosLayout.createSequentialGroup()
                .addContainerGap(141, Short.MAX_VALUE)
                .addComponent(tablaConsultaVuelos, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(169, Short.MAX_VALUE))
        );
        panelConsultaVuelosLayout.setVerticalGroup(
            panelConsultaVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaVuelosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tablaConsultaVuelos, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        jPanel1.add(panelConsultaVuelos, "consultaVuelos");

        txtAltaAerolineas5.setText("MODIFICAR VUELOS");

        label36.setText("Código");

        label37.setText("Iata origen");

        label38.setText("Iata destino");

        label39.setText("Número de plazas");

        label40.setText("Hora de Salida");

        label41.setText("Hora de llegada");

        label42.setText("Días que opera");

        btnDarModVuelo.setText("Modificar");
        btnDarModVuelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDarModVueloActionPerformed(evt);
            }
        });

        comboBoxCodigoMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxCodigoModActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelModVuelosLayout = new javax.swing.GroupLayout(panelModVuelos);
        panelModVuelos.setLayout(panelModVuelosLayout);
        panelModVuelosLayout.setHorizontalGroup(
            panelModVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelModVuelosLayout.createSequentialGroup()
                .addContainerGap(123, Short.MAX_VALUE)
                .addGroup(panelModVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelModVuelosLayout.createSequentialGroup()
                        .addComponent(label38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(campoIataDestinoMod, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelModVuelosLayout.createSequentialGroup()
                        .addComponent(label39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(campoPlazasMod, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelModVuelosLayout.createSequentialGroup()
                        .addComponent(label40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(campoHoraSalidaMod, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelModVuelosLayout.createSequentialGroup()
                        .addComponent(label41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(campoHoraLlegadaMod, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelModVuelosLayout.createSequentialGroup()
                        .addComponent(label42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(campoDiasOperaMod, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelModVuelosLayout.createSequentialGroup()
                        .addComponent(label37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addGroup(panelModVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboBoxCodigoMod, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(campoIataOrigenMod, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE))))
                .addContainerGap(113, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelModVuelosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtAltaAerolineas5)
                .addGap(335, 335, 335))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelModVuelosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDarModVuelo)
                .addGap(358, 358, 358))
        );
        panelModVuelosLayout.setVerticalGroup(
            panelModVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelModVuelosLayout.createSequentialGroup()
                .addContainerGap(107, Short.MAX_VALUE)
                .addComponent(txtAltaAerolineas5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(panelModVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxCodigoMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(panelModVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoIataOrigenMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelModVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoIataDestinoMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelModVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoPlazasMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelModVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoHoraSalidaMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelModVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoHoraLlegadaMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelModVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoDiasOperaMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(btnDarModVuelo)
                .addContainerGap(115, Short.MAX_VALUE))
        );

        jPanel1.add(panelModVuelos, "modVuelos");

        txtTituloInformacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTituloInformacion.setText("INFORMACION DE VUELOS");

        btnSalidasInf.setText("Salidas");
        btnSalidasInf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalidasInfActionPerformed(evt);
            }
        });

        btnLlegadasInf.setText("Llegadas");
        btnLlegadasInf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLlegadasInfActionPerformed(evt);
            }
        });

        btnAerolineasInf.setText("Por Aerolineas");
        btnAerolineasInf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAerolineasInfActionPerformed(evt);
            }
        });

        btnRecaudacionInf.setText("Recaudación");
        btnRecaudacionInf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecaudacionInfActionPerformed(evt);
            }
        });

        btnDestinoInf.setText("Según destino");
        btnDestinoInf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDestinoInfActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelInformacionVuelosLayout = new javax.swing.GroupLayout(panelInformacionVuelos);
        panelInformacionVuelos.setLayout(panelInformacionVuelosLayout);
        panelInformacionVuelosLayout.setHorizontalGroup(
            panelInformacionVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInformacionVuelosLayout.createSequentialGroup()
                .addContainerGap(320, Short.MAX_VALUE)
                .addGroup(panelInformacionVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTituloInformacion)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInformacionVuelosLayout.createSequentialGroup()
                        .addGroup(panelInformacionVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSalidasInf, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLlegadasInf, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAerolineasInf, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRecaudacionInf, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDestinoInf, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addContainerGap(291, Short.MAX_VALUE))
        );
        panelInformacionVuelosLayout.setVerticalGroup(
            panelInformacionVuelosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacionVuelosLayout.createSequentialGroup()
                .addContainerGap(165, Short.MAX_VALUE)
                .addComponent(txtTituloInformacion)
                .addGap(43, 43, 43)
                .addComponent(btnSalidasInf)
                .addGap(17, 17, 17)
                .addComponent(btnLlegadasInf)
                .addGap(17, 17, 17)
                .addComponent(btnAerolineasInf)
                .addGap(17, 17, 17)
                .addComponent(btnRecaudacionInf)
                .addGap(17, 17, 17)
                .addComponent(btnDestinoInf)
                .addContainerGap(184, Short.MAX_VALUE))
        );

        jPanel1.add(panelInformacionVuelos, "informacionVuelos");

        tablaSalidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaSalidas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaSalidasMouseClicked(evt);
            }
        });
        tablaSalidas.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablaSalidasPropertyChange(evt);
            }
        });
        tablaSalidasVuelos.setViewportView(tablaSalidas);

        calendarioSalidas.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                calendarioSalidasPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout panelSalidasLayout = new javax.swing.GroupLayout(panelSalidas);
        panelSalidas.setLayout(panelSalidasLayout);
        panelSalidasLayout.setHorizontalGroup(
            panelSalidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSalidasLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(calendarioSalidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addComponent(componenteTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(151, 151, 151))
            .addGroup(panelSalidasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tablaSalidasVuelos)
                .addContainerGap())
        );
        panelSalidasLayout.setVerticalGroup(
            panelSalidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSalidasLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(tablaSalidasVuelos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelSalidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calendarioSalidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(componenteTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(panelSalidas, "panelSalidas");

        tablaLlegadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaLlegadas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaLlegadasMouseClicked(evt);
            }
        });
        tablaLlegadas.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablaLlegadasPropertyChange(evt);
            }
        });
        tablaLlegadasVuelos.setViewportView(tablaLlegadas);

        calendarioLlegadas.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                calendarioLlegadasPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout panelLegadasLayout = new javax.swing.GroupLayout(panelLegadas);
        panelLegadas.setLayout(panelLegadasLayout);
        panelLegadasLayout.setHorizontalGroup(
            panelLegadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLegadasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLegadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tablaLlegadasVuelos)
                    .addGroup(panelLegadasLayout.createSequentialGroup()
                        .addGap(0, 101, Short.MAX_VALUE)
                        .addComponent(calendarioLlegadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(112, 112, 112)
                        .addComponent(componenteTemp2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 156, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelLegadasLayout.setVerticalGroup(
            panelLegadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLegadasLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(tablaLlegadasVuelos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelLegadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(calendarioLlegadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(componenteTemp2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(panelLegadas, "panelLlegadas");

        tablaVuelosPorAerolinea.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaVuelosPorAerolinea.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablaVuelosPorAerolineaPropertyChange(evt);
            }
        });
        tablaVuelosAerolinea.setViewportView(tablaVuelosPorAerolinea);

        calendarioVuelosAerolinea.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                calendarioVuelosAerolineaPropertyChange(evt);
            }
        });

        comboBoxAerolineas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboBoxAerolineas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxAerolineasActionPerformed(evt);
            }
        });

        jLabel2.setText("Seleccione Aerolinea");

        javax.swing.GroupLayout panelVuelosAerolineaLayout = new javax.swing.GroupLayout(panelVuelosAerolinea);
        panelVuelosAerolinea.setLayout(panelVuelosAerolineaLayout);
        panelVuelosAerolineaLayout.setHorizontalGroup(
            panelVuelosAerolineaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVuelosAerolineaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVuelosAerolineaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVuelosAerolineaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(calendarioVuelosAerolinea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addGroup(panelVuelosAerolineaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboBoxAerolineas, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelVuelosAerolineaLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(tablaVuelosAerolinea, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelVuelosAerolineaLayout.setVerticalGroup(
            panelVuelosAerolineaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVuelosAerolineaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tablaVuelosAerolinea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelVuelosAerolineaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calendarioVuelosAerolinea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelVuelosAerolineaLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comboBoxAerolineas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(panelVuelosAerolinea, "panelVuelosAerolinea");

        tablaVuelosPorDestino.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaVuelosPorDestino.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablaVuelosPorDestinoPropertyChange(evt);
            }
        });
        tablaVuelosDestino.setViewportView(tablaVuelosPorDestino);

        calendarioVuelosDestino.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                calendarioVuelosDestinoPropertyChange(evt);
            }
        });

        comboBoxDestino.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        comboBoxDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxDestinoActionPerformed(evt);
            }
        });

        jLabel3.setText("Seleccione Destino");

        jLabel6.setText("Se muestran vuelos de los 7 días");

        jLabel7.setText("siguientes a la fecha seleccionada.");

        javax.swing.GroupLayout panelVuelosDestinoLayout = new javax.swing.GroupLayout(panelVuelosDestino);
        panelVuelosDestino.setLayout(panelVuelosDestinoLayout);
        panelVuelosDestinoLayout.setHorizontalGroup(
            panelVuelosDestinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVuelosDestinoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(calendarioVuelosDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(panelVuelosDestinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVuelosDestinoLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(comboBoxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelVuelosDestinoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tablaVuelosDestino, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelVuelosDestinoLayout.setVerticalGroup(
            panelVuelosDestinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVuelosDestinoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(tablaVuelosDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelVuelosDestinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calendarioVuelosDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelVuelosDestinoLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(comboBoxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(panelVuelosDestino, "panelVuelosDestino");

        tablaRecaudacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaRecaudacion.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablaRecaudacionPropertyChange(evt);
            }
        });
        tablaDeRecaudacion.setViewportView(tablaRecaudacion);

        calendarioRecaudacion.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                calendarioRecaudacionPropertyChange(evt);
            }
        });

        jLabelRecaudacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelRecaudacion.setText("Introduzca una fecha");

        jLabel4.setText("Se toma como plazas ocupadas el 80%");

        jLabel5.setText("de las plazas totales");

        javax.swing.GroupLayout panelRecaudacionLayout = new javax.swing.GroupLayout(panelRecaudacion);
        panelRecaudacion.setLayout(panelRecaudacionLayout);
        panelRecaudacionLayout.setHorizontalGroup(
            panelRecaudacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRecaudacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRecaudacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tablaDeRecaudacion)
                    .addGroup(panelRecaudacionLayout.createSequentialGroup()
                        .addGap(0, 98, Short.MAX_VALUE)
                        .addComponent(calendarioRecaudacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addGroup(panelRecaudacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRecaudacionLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4)
                            .addComponent(jLabelRecaudacion, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 170, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelRecaudacionLayout.setVerticalGroup(
            panelRecaudacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRecaudacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tablaDeRecaudacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelRecaudacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calendarioRecaudacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelRecaudacionLayout.createSequentialGroup()
                        .addComponent(jLabelRecaudacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(panelRecaudacion, "panelRecaudacion");

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        btnVolverPrincipal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnVolverPrincipal.setText("Volver");
        btnVolverPrincipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
        btnVolverPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverPrincipalActionPerformed(evt);
            }
        });
        getContentPane().add(btnVolverPrincipal, java.awt.BorderLayout.PAGE_START);

        btnSalirPrincipal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSalirPrincipal.setText("Salir");
        btnSalirPrincipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        btnSalirPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPrincipalActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalirPrincipal, java.awt.BorderLayout.PAGE_END);

        btnAyuda.setText("Ayuda");

        jMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem.setText("Ayuda Principal");
        jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActionPerformed(evt);
            }
        });
        btnAyuda.add(jMenuItem);

        jMenuBar1.add(btnAyuda);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGestionAerolineasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionAerolineasActionPerformed
        Aeropuerto aeropuertoBase = (Aeropuerto) comboAeropuertoBase.getSelectedItem();
        if (aeropuertoBase == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un Aeropuerto Base", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            changePanel("gestionAerolineas");
        }
    }//GEN-LAST:event_btnGestionAerolineasActionPerformed

    private void btnGestionVuelosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionVuelosActionPerformed
        Aeropuerto aeropuertoBase = (Aeropuerto) comboAeropuertoBase.getSelectedItem();
        if (aeropuertoBase == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un Aeropuerto Base", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            changePanel("gestionVuelos");
        }
    }//GEN-LAST:event_btnGestionVuelosActionPerformed

    private void btnInformacionVuelosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformacionVuelosActionPerformed
        Aeropuerto aeropuertoBase = (Aeropuerto) comboAeropuertoBase.getSelectedItem();
        if (aeropuertoBase == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un Aeropuerto Base", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            changePanel("informacionVuelos");
        }
    }//GEN-LAST:event_btnInformacionVuelosActionPerformed

    private void btnConsultaAerolineasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaAerolineasActionPerformed
        AerolineasTableModel modeloTablaActual = getModeloTablaAerolineas();
        modeloTablaActual.fireTableDataChanged();
        changePanel("consultaAerolineas");
    }//GEN-LAST:event_btnConsultaAerolineasActionPerformed

    private void btnAltaAerolineasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaAerolineasActionPerformed
        changePanel("altaAerolineas");
    }//GEN-LAST:event_btnAltaAerolineasActionPerformed

    private void btnDarBajaAerolineasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarBajaAerolineasActionPerformed
        if (comboBoxPrefijo.getSelectedItem() != null) {
            Aerolinea aerolineaPrefijo = (Aerolinea) comboBoxPrefijo.getSelectedItem();
            String prefijoAerolinea = aerolineaPrefijo.getPrefijo();
            GestionAerolineas.eliminarAerolinea(prefijoAerolinea);
            JOptionPane.showMessageDialog(this, "Aerolínea con Prefijo " + prefijoAerolinea + " dada de Baja");
            getModeloComboBoxPrefijo();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un Prefijo");
        }
    }//GEN-LAST:event_btnDarBajaAerolineasActionPerformed

    private void btnBajaAerolineasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaAerolineasActionPerformed
        getModeloComboBoxPrefijo();
        changePanel("bajaAerolineas");
    }//GEN-LAST:event_btnBajaAerolineasActionPerformed

    private void btnDarModAerolineasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarModAerolineasActionPerformed
        if (comboBoxPrefijoMod.getSelectedItem() != null) {
            Aerolinea aerolineaPrefijo = (Aerolinea) comboBoxPrefijoMod.getSelectedItem();
            String prefijoAerolinea = aerolineaPrefijo.getPrefijo();
            GestionAerolineas.modificarAerolinea(
                    prefijoAerolinea,
                    campoCodigo2.getText(),
                    campoNombre2.getText(),
                    campoDireccion2.getText(),
                    campoMunicipio2.getText(),
                    campoTfnoPasajero2.getText(),
                    campoTfnoAeropuerto2.getText());
            JOptionPane.showMessageDialog(this, "Aerolínea " + prefijoAerolinea + " modificada");
            getModeloComboBoxPrefijoMod();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un Prefijo");
        };
    }//GEN-LAST:event_btnDarModAerolineasActionPerformed

    private void btnModificacionAerolineasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificacionAerolineasActionPerformed
        getModeloComboBoxPrefijoMod();
        changePanel("modAerolineas");
    }//GEN-LAST:event_btnModificacionAerolineasActionPerformed

    private void btnDarAltaVuelosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarAltaVuelosActionPerformed
        boolean esValido = GestionVuelos.validarAltaVuelo(
                campoCodVuelo,
                campoIataOrigen,
                campoIataDestino,
                campoNumPlazas,
                campoHoraSalida,
                campoHoraLlegada,
                campoDiasOpera);
        if (esValido) {
            GestionVuelos.agregarVuelo(
                    campoCodVuelo,
                    campoIataOrigen,
                    campoIataDestino,
                    campoNumPlazas,
                    campoHoraSalida,
                    campoHoraLlegada,
                    campoDiasOpera);
            try {
                GestionVuelosDiarios.agregarVueloDiario(
                        campoCodVuelo,
                        campoDiasOpera,
                        campoHoraSalida,
                        campoHoraLlegada,
                        campoNumPlazas);
            } catch (ParseException ex) {
                Logger.getLogger(SkyManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(this, "Vuelo dado de Alta");
            campoCodVuelo.setText("");
            campoIataOrigen.setText("");
            campoIataDestino.setText("");
            campoNumPlazas.setText("");
            campoHoraSalida.setText("");
            campoHoraLlegada.setText("");
            campoDiasOpera.setText("");
        } else {
        }
    }//GEN-LAST:event_btnDarAltaVuelosActionPerformed

    private void btnDarBajaVuelosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarBajaVuelosActionPerformed
        if (comboBoxCodigo.getSelectedItem() != null) {
            Vuelos vueloCodigo = (Vuelos) comboBoxCodigo.getSelectedItem();
            String codigoVuelo = vueloCodigo.getCodVuelo();
            GestionVuelos.eliminarVuelo(codigoVuelo);
            JOptionPane.showMessageDialog(this, "Vuelo con código " + codigoVuelo + " dado de Baja");
            getModeloComboBoxCodigo();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un Código");
        }
    }//GEN-LAST:event_btnDarBajaVuelosActionPerformed

    private void btnDarModVueloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarModVueloActionPerformed
        if (comboBoxCodigoMod.getSelectedItem() != null) {
            Vuelos vueloCodigo = (Vuelos) comboBoxCodigoMod.getSelectedItem();
            String codigoVuelo = vueloCodigo.getCodVuelo();
            GestionVuelos.modificarVuelo(codigoVuelo,
                    campoIataOrigenMod.getText(),
                    campoIataDestinoMod.getText(),
                    campoPlazasMod.getText(),
                    campoHoraSalidaMod.getText(),
                    campoHoraLlegadaMod.getText(),
                    campoDiasOperaMod.getText());
            JOptionPane.showMessageDialog(this, "Vuelo con código " + codigoVuelo + " dado de Baja");
            getModeloComboBoxCodigoMod();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un Código");
        }
    }//GEN-LAST:event_btnDarModVueloActionPerformed

    private void btnAltaVuelosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaVuelosActionPerformed
        changePanel("altaVuelos");
    }//GEN-LAST:event_btnAltaVuelosActionPerformed

    private void btnBajaVuelosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaVuelosActionPerformed
        getModeloComboBoxCodigo();
        changePanel("bajaVuelos");
    }//GEN-LAST:event_btnBajaVuelosActionPerformed

    private void btnConsultaVuelosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaVuelosActionPerformed
        VuelosTableModel modeloTablaVuelos = getModeloTablaVuelos();
        modeloTablaVuelos.fireTableDataChanged();
        changePanel("consultaVuelos");
    }//GEN-LAST:event_btnConsultaVuelosActionPerformed

    private void btnModificacionVuelosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificacionVuelosActionPerformed
        getModeloComboBoxCodigoMod();
        changePanel("modVuelos");
    }//GEN-LAST:event_btnModificacionVuelosActionPerformed

    private void campoNumPlazasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNumPlazasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNumPlazasActionPerformed

    private void btnVolverPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverPrincipalActionPerformed
        if (!history.isEmpty()) {
            history.pop(); // Remueve el panel actual
            if (!history.isEmpty()) {
                String previousPanel = history.peek();
                cardLayout.show(jPanel1, previousPanel);
            } else {
                cardLayout.show(jPanel1, "menuPrincipal");
            }
        }
    }//GEN-LAST:event_btnVolverPrincipalActionPerformed

    private void btnSalirPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPrincipalActionPerformed

        int respuesta = JOptionPane.showConfirmDialog(null,
                "¿Guardar las modificaciones?", "Salir",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            GuardaDatos.guardarAerolineas(".\\src\\main\\resources\\csv\\aerolineas.csv");
            GuardaDatos.guardarVuelos(".\\src\\main\\resources\\csv\\vuelos.csv");
            GuardaDatos.guardarVuelosDiarios(".\\src\\main\\resources\\csv\\vuelosDiarios.csv");
            System.exit(0);
            System.out.println("Modificaciones guardadas");
        } else {
            System.exit(0);
            System.out.println("Salida sin guardar");
        }
    }//GEN-LAST:event_btnSalirPrincipalActionPerformed

    private void comboAeropuertoBaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAeropuertoBaseActionPerformed
        Aeropuerto aeropuertoBase = (Aeropuerto) comboAeropuertoBase.getSelectedItem();
        if (aeropuertoBase != null) {
            System.out.println("Aeropuerto seleccionado: " + aeropuertoBase.getNombre());
            SalidasTableModel modeloTablaSalidas = (SalidasTableModel) tablaSalidas.getModel();
            modeloTablaSalidas.filtrarPorAeropuerto(aeropuertoBase);
            LlegadasTableModel modeloTablaLlegadas = (LlegadasTableModel) tablaLlegadas.getModel();
            modeloTablaLlegadas.filtrarPorAeropuerto(aeropuertoBase);
            PorAerolineaTableModel modeloTablaPorAerolinea = (PorAerolineaTableModel) tablaVuelosPorAerolinea.getModel();
            modeloTablaPorAerolinea.filtrarPorAeropuerto(aeropuertoBase);
        }
    }//GEN-LAST:event_comboAeropuertoBaseActionPerformed

    private void btnLlegadasInfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLlegadasInfActionPerformed
        Aeropuerto aeropuertoBase = (Aeropuerto) comboAeropuertoBase.getSelectedItem();
        LlegadasTableModel modeloTablaLlegadas = (LlegadasTableModel) tablaLlegadas.getModel();
        modeloTablaLlegadas.filtrarPorAeropuerto(aeropuertoBase);
        componenteTemp2.getLabelAeropuertoSalida().setText("Llegada: ");
        componenteTemp2.getLabelAeropuertoLlegada().setText("Salida: ");
        componenteTemp2.getLabelSalidaMax().setText("Temperaturas Max y  Min");
        componenteTemp2.getLabelLlegadaMax().setText("Temperaturas Max y  Min");
        changePanel("panelLlegadas");
    }//GEN-LAST:event_btnLlegadasInfActionPerformed

    private void btnSalidasInfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalidasInfActionPerformed
        Aeropuerto aeropuertoBase = (Aeropuerto) comboAeropuertoBase.getSelectedItem();
        SalidasTableModel modeloTablaSalidas = (SalidasTableModel) tablaSalidas.getModel();
        modeloTablaSalidas.filtrarPorAeropuerto(aeropuertoBase);
        componenteTemp.getLabelAeropuertoSalida().setText("Salida: ");
        componenteTemp.getLabelAeropuertoLlegada().setText("Llegada: ");
        componenteTemp.getLabelSalidaMax().setText("Temperaturas Max y  Min");
        componenteTemp.getLabelLlegadaMax().setText("Temperaturas Max y  Min");
        changePanel("panelSalidas");
    }//GEN-LAST:event_btnSalidasInfActionPerformed

    private void tablaSalidasPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablaSalidasPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaSalidasPropertyChange

    private void calendarioSalidasPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_calendarioSalidasPropertyChange
        if ("calendar".equals(evt.getPropertyName())) {
            Date fecha = calendarioSalidas.getDate();
            SalidasTableModel modeloTabla = (SalidasTableModel) tablaSalidas.getModel();
            modeloTabla.filtrarPorFecha(fecha);
        }
    }//GEN-LAST:event_calendarioSalidasPropertyChange

    private void tablaLlegadasPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablaLlegadasPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaLlegadasPropertyChange

    private void calendarioLlegadasPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_calendarioLlegadasPropertyChange
        if ("calendar".equals(evt.getPropertyName())) {
            Date fecha = calendarioLlegadas.getDate();
            LlegadasTableModel modeloTabla = (LlegadasTableModel) tablaLlegadas.getModel();
            modeloTabla.filtrarPorFecha(fecha);
        }
    }//GEN-LAST:event_calendarioLlegadasPropertyChange

    private void tablaVuelosPorAerolineaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablaVuelosPorAerolineaPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaVuelosPorAerolineaPropertyChange

    private void calendarioVuelosAerolineaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_calendarioVuelosAerolineaPropertyChange
        if ("calendar".equals(evt.getPropertyName())) {
            Date fecha = calendarioVuelosAerolinea.getDate();
            PorAerolineaTableModel modeloTabla = (PorAerolineaTableModel) tablaVuelosPorAerolinea.getModel();
            modeloTabla.filtrarPorFecha(fecha);
        }
    }//GEN-LAST:event_calendarioVuelosAerolineaPropertyChange

    private void btnAerolineasInfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAerolineasInfActionPerformed
        Aeropuerto aeropuertoBase = (Aeropuerto) comboAeropuertoBase.getSelectedItem();
        PorAerolineaTableModel modeloTablaPorAerolinea = (PorAerolineaTableModel) tablaVuelosPorAerolinea.getModel();
        modeloTablaPorAerolinea.filtrarPorAeropuerto(aeropuertoBase);
        modeloTablaPorAerolinea.filtrarPorFecha(new Date());
        modeloTablaPorAerolinea.inicializarListaFiltradaPorAerolinea();
        getModeloComboBoxAerolineas();
        changePanel("panelVuelosAerolinea");

    }//GEN-LAST:event_btnAerolineasInfActionPerformed

    private void comboBoxAerolineasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxAerolineasActionPerformed
        Aerolinea aerolineaBase = (Aerolinea) comboBoxAerolineas.getSelectedItem();
        PorAerolineaTableModel modeloTablaPorAerolinea = (PorAerolineaTableModel) tablaVuelosPorAerolinea.getModel();

        if (aerolineaBase != null) {
            modeloTablaPorAerolinea.filtrarPorNombreAerolinea(aerolineaBase.getNombre());
        } else {
            // Si aerolineaBase es null, no se aplica el filtro de aerolínea.
            modeloTablaPorAerolinea.filtrarPorNombreAerolinea("");
        }
    }//GEN-LAST:event_comboBoxAerolineasActionPerformed

    private void tablaVuelosPorDestinoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablaVuelosPorDestinoPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaVuelosPorDestinoPropertyChange

    private void calendarioVuelosDestinoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_calendarioVuelosDestinoPropertyChange
        if ("calendar".equals(evt.getPropertyName())) {
            Date fecha = calendarioVuelosDestino.getDate();
            PorDestinoTableModel modeloTabla = (PorDestinoTableModel) tablaVuelosPorDestino.getModel();
            modeloTabla.filtrarPorFecha(fecha);
        }
    }//GEN-LAST:event_calendarioVuelosDestinoPropertyChange

    private void comboBoxDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxDestinoActionPerformed
        Aeropuerto aeropuertoBase = (Aeropuerto) comboBoxDestino.getSelectedItem();
        PorDestinoTableModel modeloTablaPorDestino = (PorDestinoTableModel) tablaVuelosPorDestino.getModel();
        if (aeropuertoBase != null) {
            modeloTablaPorDestino.filtrarPorDestino(aeropuertoBase.getNombre());
        } else {
            // Si aerolineaBase es null, no se aplica el filtro de aerolínea.
            modeloTablaPorDestino.filtrarPorDestino("");
        }
    }//GEN-LAST:event_comboBoxDestinoActionPerformed

    private void btnDestinoInfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDestinoInfActionPerformed
        Aeropuerto aeropuertoBase = (Aeropuerto) comboBoxDestino.getSelectedItem();
        PorDestinoTableModel modeloTablaPorDestino = (PorDestinoTableModel) tablaVuelosPorDestino.getModel();
        modeloTablaPorDestino.filtrarPorAeropuerto(aeropuertoBase);
        modeloTablaPorDestino.filtrarPorFecha(new Date());
        modeloTablaPorDestino.inicializarListaFiltradaPorDestino();
        getModeloComboBoxDestino();
        changePanel("panelVuelosDestino");
    }//GEN-LAST:event_btnDestinoInfActionPerformed

    private void tablaRecaudacionPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablaRecaudacionPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaRecaudacionPropertyChange

    private void calendarioRecaudacionPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_calendarioRecaudacionPropertyChange
        if ("calendar".equals(evt.getPropertyName())) {
            Date fecha = calendarioRecaudacion.getDate();
            RecaudacionTableModel modeloTabla = (RecaudacionTableModel) tablaRecaudacion.getModel();
            float nuevaRecaudacion = modeloTabla.filtrarPorFecha(fecha);
            jLabelRecaudacion.setText("Recaudación Total: " + String.format("%.2f", nuevaRecaudacion) + " €");
        }
    }//GEN-LAST:event_calendarioRecaudacionPropertyChange

    private void btnRecaudacionInfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecaudacionInfActionPerformed
        Aeropuerto aeropuertoBase = (Aeropuerto) comboAeropuertoBase.getSelectedItem();
        RecaudacionTableModel modeloTablaRecaudacion = (RecaudacionTableModel) tablaRecaudacion.getModel();
        modeloTablaRecaudacion.filtrarPorAeropuerto(aeropuertoBase);
        modeloTablaRecaudacion.inicializarListaFiltradaRecaudacion();
        changePanel("panelRecaudacion");
    }//GEN-LAST:event_btnRecaudacionInfActionPerformed

    private void btnDarAltaAerolineasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarAltaAerolineasActionPerformed
        boolean esValido = GestionAerolineas.validarAltaAerolinea(
                campoPrefijo,
                campoCodigo,
                campoNombre,
                campoDireccion,
                campoMunicipio,
                campoTfnoPasajero,
                campoTfnoAeropuerto);
        if (esValido) {
            GestionAerolineas.agregarAerolinea(
                    campoPrefijo,
                    campoCodigo,
                    campoNombre,
                    campoDireccion,
                    campoMunicipio,
                    campoTfnoPasajero,
                    campoTfnoAeropuerto);
            JOptionPane.showMessageDialog(this, "Aerolínea dada de Alta");
            campoPrefijo.setText("");
            campoCodigo.setText("");
            campoNombre.setText("");
            campoDireccion.setText("");
            campoMunicipio.setText("");
            campoTfnoPasajero.setText("");
            campoTfnoAeropuerto.setText("");
        } else {
        }
    }//GEN-LAST:event_btnDarAltaAerolineasActionPerformed

    private void comboBoxPrefijoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxPrefijoActionPerformed
        Aerolinea aerolineaPrefijo = (Aerolinea) comboBoxPrefijo.getSelectedItem();
        if (comboBoxPrefijo.getSelectedItem() != null) {
            campoCodigo1.setText(aerolineaPrefijo.getCodigo());
            campoNombre1.setText(aerolineaPrefijo.getNombre());
            campoDireccion1.setText(aerolineaPrefijo.getDireccion());
            campoMunicipio1.setText(aerolineaPrefijo.getMunicipio());
            campoTfnoPasajero1.setText(aerolineaPrefijo.getTfnoInfoPasajero());
            campoTfnoAeropuerto1.setText(aerolineaPrefijo.getTfnoInfoAeropuerto());
        }
    }//GEN-LAST:event_comboBoxPrefijoActionPerformed

    private void comboBoxPrefijoModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxPrefijoModActionPerformed
        Aerolinea aerolineaPrefijo = (Aerolinea) comboBoxPrefijoMod.getSelectedItem();
        if (comboBoxPrefijoMod.getSelectedItem() != null) {
            campoCodigo2.setText(aerolineaPrefijo.getCodigo());
            campoNombre2.setText(aerolineaPrefijo.getNombre());
            campoDireccion2.setText(aerolineaPrefijo.getDireccion());
            campoMunicipio2.setText(aerolineaPrefijo.getMunicipio());
            campoTfnoPasajero2.setText(aerolineaPrefijo.getTfnoInfoPasajero());
            campoTfnoAeropuerto2.setText(aerolineaPrefijo.getTfnoInfoAeropuerto());
        }
    }//GEN-LAST:event_comboBoxPrefijoModActionPerformed

    private void comboBoxCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxCodigoActionPerformed
        Vuelos vuelocodigo = (Vuelos) comboBoxCodigo.getSelectedItem();
        if (comboBoxCodigo.getSelectedItem() != null) {
            campoIataOrigenBaja.setText(vuelocodigo.getCodIataOrigen());
            campoIataDestBaja.setText(vuelocodigo.getCodIataDestino());
            campoPlazasBaja.setText(vuelocodigo.getNumPlazas());
            campoHoraSalidaBaja.setText(vuelocodigo.getHoraSalida());
            campoHoraLlegadaBaja.setText(vuelocodigo.getHoraLlegada());
            campoDiasOperaBaja.setText(vuelocodigo.getDiasOpera());
        }
    }//GEN-LAST:event_comboBoxCodigoActionPerformed

    private void comboBoxCodigoModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxCodigoModActionPerformed
        Vuelos vuelocodigo = (Vuelos) comboBoxCodigoMod.getSelectedItem();
        if (comboBoxCodigoMod.getSelectedItem() != null) {
            campoIataOrigenMod.setText(vuelocodigo.getCodIataOrigen());
            campoIataDestinoMod.setText(vuelocodigo.getCodIataDestino());
            campoPlazasMod.setText(vuelocodigo.getNumPlazas());
            campoHoraSalidaMod.setText(vuelocodigo.getHoraSalida());
            campoHoraLlegadaMod.setText(vuelocodigo.getHoraLlegada());
            campoDiasOperaMod.setText(vuelocodigo.getDiasOpera());
        }
    }//GEN-LAST:event_comboBoxCodigoModActionPerformed

    private void tablaSalidasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaSalidasMouseClicked
        int selectedRowIndex = tablaSalidas.getSelectedRow();
        if (selectedRowIndex != -1) {
            SalidasTableModel model = (SalidasTableModel) tablaSalidas.getModel();
            VueloDiario selectedVuelo = model.getVueloDiarioAt(selectedRowIndex);
            if (selectedVuelo != null) {
                System.out.println(selectedVuelo);
                System.out.println(selectedVuelo.getCodVuelo());
                String[] infoAeropuertos = BuscadorDeMunicipios.obtenerInformacionAeropuertos(listaVuelos, listaAeropuertos, selectedVuelo.getCodVuelo());
                String municipioOrigen = infoAeropuertos[0];
                String municipioDestino = infoAeropuertos[1];
                String nombreAeropuertoOrigen = infoAeropuertos[2];
                String nombreAeropuertoDestino = infoAeropuertos[3];
                municipioOrigen = String.format("%05d", Integer.parseInt(municipioOrigen));
                municipioDestino = String.format("%05d", Integer.parseInt(municipioDestino));
                componenteTemp.getLabelAeropuertoSalida().setText("Salida: " + nombreAeropuertoOrigen);
                componenteTemp.getLabelAeropuertoLlegada().setText("Llegada: " + nombreAeropuertoDestino);
                try {
                    Temperaturas t = ServiceREST.serviceSearch(municipioOrigen);
                    componenteTemp.getLabelSalidaMax().setText("Máx: " + t.getTempMax() + "°C  " + "Min: " + t.getTempMin() + "°C");
                } catch (UnirestException ex) {
                    Logger.getLogger(SkyManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    Temperaturas t = ServiceREST.serviceSearch(municipioDestino);
                    componenteTemp.getLabelLlegadaMax().setText("Máx: " + t.getTempMax() + "°C  " + "Min: " + t.getTempMin() + "°C");
                } catch (UnirestException ex) {
                    Logger.getLogger(SkyManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_tablaSalidasMouseClicked

    private void tablaLlegadasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaLlegadasMouseClicked
        int selectedRowIndex = tablaLlegadas.getSelectedRow();
        if (selectedRowIndex != -1) {
            LlegadasTableModel model = (LlegadasTableModel) tablaLlegadas.getModel();
            VueloDiario selectedVuelo = model.getVueloDiarioAt(selectedRowIndex);
            if (selectedVuelo != null) {
                System.out.println(selectedVuelo);
                System.out.println(selectedVuelo.getCodVuelo());
                String[] infoAeropuertos = BuscadorDeMunicipios.obtenerInformacionAeropuertos(listaVuelos, listaAeropuertos, selectedVuelo.getCodVuelo());
                String municipioOrigen = infoAeropuertos[0];
                String municipioDestino = infoAeropuertos[1];
                String nombreAeropuertoOrigen = infoAeropuertos[2];
                String nombreAeropuertoDestino = infoAeropuertos[3];
                municipioOrigen = String.format("%05d", Integer.parseInt(municipioOrigen));
                municipioDestino = String.format("%05d", Integer.parseInt(municipioDestino));
                componenteTemp2.getLabelAeropuertoSalida().setText("Llegada: " + nombreAeropuertoDestino);
                componenteTemp2.getLabelAeropuertoLlegada().setText("Salida: " + nombreAeropuertoOrigen);
                try {
                    Temperaturas t = ServiceREST.serviceSearch(municipioDestino);
                    componenteTemp2.getLabelSalidaMax().setText("Máx: " + t.getTempMax() + "°C  " + "Min: " + t.getTempMin() + "°C");
                } catch (UnirestException ex) {
                    Logger.getLogger(SkyManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    Temperaturas t = ServiceREST.serviceSearch(municipioOrigen);
                    componenteTemp2.getLabelLlegadaMax().setText("Máx: " + t.getTempMax() + "°C  " + "Min: " + t.getTempMin() + "°C");
                } catch (UnirestException ex) {
                    Logger.getLogger(SkyManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_tablaLlegadasMouseClicked

    private void jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemActionPerformed
        Platform.runLater(() -> {
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            webEngine.load("https://qxh68568.gitbook.io/untitled/");
            jfxPanel.setScene(new Scene(webView));
            frame.setVisible(true);
        });
    }//GEN-LAST:event_jMenuItemActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new SkyManager().setVisible(true);
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAerolineasInf;
    private javax.swing.JButton btnAltaAerolineas;
    private javax.swing.JButton btnAltaVuelos;
    private javax.swing.JMenu btnAyuda;
    private javax.swing.JButton btnBajaAerolineas;
    private javax.swing.JButton btnBajaVuelos;
    private javax.swing.JButton btnConsultaAerolineas;
    private javax.swing.JButton btnConsultaVuelos;
    private javax.swing.JButton btnDarAltaAerolineas;
    private javax.swing.JButton btnDarAltaVuelos;
    private javax.swing.JButton btnDarBajaAerolineas;
    private javax.swing.JButton btnDarBajaVuelos;
    private javax.swing.JButton btnDarModAerolineas;
    private javax.swing.JButton btnDarModVuelo;
    private javax.swing.JButton btnDestinoInf;
    private javax.swing.JButton btnGestionAerolineas;
    private javax.swing.JButton btnGestionVuelos;
    private javax.swing.JButton btnInformacionVuelos;
    private javax.swing.JButton btnLlegadasInf;
    private javax.swing.JButton btnModificacionAerolineas;
    private javax.swing.JButton btnModificacionVuelos;
    private javax.swing.JButton btnRecaudacionInf;
    private javax.swing.JButton btnSalidasInf;
    private javax.swing.JButton btnSalirPrincipal;
    private javax.swing.JButton btnVolverPrincipal;
    private com.toedter.calendar.JCalendar calendarioLlegadas;
    private com.toedter.calendar.JCalendar calendarioRecaudacion;
    private com.toedter.calendar.JCalendar calendarioSalidas;
    private com.toedter.calendar.JCalendar calendarioVuelosAerolinea;
    private com.toedter.calendar.JCalendar calendarioVuelosDestino;
    public java.awt.TextField campoCodVuelo;
    private java.awt.TextField campoCodigo;
    private java.awt.TextField campoCodigo1;
    private java.awt.TextField campoCodigo2;
    private java.awt.TextField campoDiasOpera;
    private java.awt.TextField campoDiasOperaBaja;
    private java.awt.TextField campoDiasOperaMod;
    private java.awt.TextField campoDireccion;
    private java.awt.TextField campoDireccion1;
    private java.awt.TextField campoDireccion2;
    private javax.swing.JFormattedTextField campoHoraLlegada;
    private java.awt.TextField campoHoraLlegadaBaja;
    private java.awt.TextField campoHoraLlegadaMod;
    private javax.swing.JFormattedTextField campoHoraSalida;
    private java.awt.TextField campoHoraSalidaBaja;
    private java.awt.TextField campoHoraSalidaMod;
    private java.awt.TextField campoIataDestBaja;
    private java.awt.TextField campoIataDestino;
    private java.awt.TextField campoIataDestinoMod;
    private java.awt.TextField campoIataOrigen;
    private java.awt.TextField campoIataOrigenBaja;
    private java.awt.TextField campoIataOrigenMod;
    private java.awt.TextField campoMunicipio;
    private java.awt.TextField campoMunicipio1;
    private java.awt.TextField campoMunicipio2;
    private java.awt.TextField campoNombre;
    private java.awt.TextField campoNombre1;
    private java.awt.TextField campoNombre2;
    private java.awt.TextField campoNumPlazas;
    private java.awt.TextField campoPlazasBaja;
    private java.awt.TextField campoPlazasMod;
    public java.awt.TextField campoPrefijo;
    private java.awt.TextField campoTfnoAeropuerto;
    private java.awt.TextField campoTfnoAeropuerto1;
    private java.awt.TextField campoTfnoAeropuerto2;
    private java.awt.TextField campoTfnoPasajero;
    private java.awt.TextField campoTfnoPasajero1;
    private java.awt.TextField campoTfnoPasajero2;
    private javax.swing.JComboBox<Aeropuerto> comboAeropuertoBase;
    private javax.swing.JComboBox<Aerolinea> comboBoxAerolineas;
    private javax.swing.JComboBox<Vuelos> comboBoxCodigo;
    private javax.swing.JComboBox<Vuelos> comboBoxCodigoMod;
    private javax.swing.JComboBox<Aeropuerto> comboBoxDestino;
    private javax.swing.JComboBox<Aerolinea> comboBoxPrefijo;
    private javax.swing.JComboBox<Aerolinea> comboBoxPrefijoMod;
    private componente.ComponenteTemp componenteTemp;
    private componente.ComponenteTemp componenteTemp2;
    private javax.swing.JTextField filtroAeropuertoBase;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabelRecaudacion;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem;
    private javax.swing.JPanel jPanel1;
    private java.awt.Label label1;
    private java.awt.Label label10;
    private java.awt.Label label11;
    private java.awt.Label label12;
    private java.awt.Label label13;
    private java.awt.Label label14;
    private java.awt.Label label15;
    private java.awt.Label label16;
    private java.awt.Label label17;
    private java.awt.Label label18;
    private java.awt.Label label19;
    private java.awt.Label label2;
    private java.awt.Label label20;
    private java.awt.Label label21;
    private java.awt.Label label22;
    private java.awt.Label label23;
    private java.awt.Label label24;
    private java.awt.Label label25;
    private java.awt.Label label26;
    private java.awt.Label label27;
    private java.awt.Label label28;
    private java.awt.Label label29;
    private java.awt.Label label3;
    private java.awt.Label label30;
    private java.awt.Label label31;
    private java.awt.Label label32;
    private java.awt.Label label33;
    private java.awt.Label label34;
    private java.awt.Label label35;
    private java.awt.Label label36;
    private java.awt.Label label37;
    private java.awt.Label label38;
    private java.awt.Label label39;
    private java.awt.Label label4;
    private java.awt.Label label40;
    private java.awt.Label label41;
    private java.awt.Label label42;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private java.awt.Label label8;
    private java.awt.Label label9;
    private javax.swing.JPanel panelAltaAerolineas;
    private javax.swing.JPanel panelAltaVuelos;
    private javax.swing.JPanel panelBajaAerolineas;
    private javax.swing.JPanel panelBajaVuelos;
    private javax.swing.JPanel panelConsultaAerolineas;
    private javax.swing.JPanel panelConsultaVuelos;
    private javax.swing.JPanel panelGestionAerolineas;
    private javax.swing.JPanel panelGestionVuelos;
    private javax.swing.JPanel panelInformacionVuelos;
    private javax.swing.JPanel panelLegadas;
    private javax.swing.JPanel panelMenuPrincipal;
    private javax.swing.JPanel panelModAerolineas;
    private javax.swing.JPanel panelModVuelos;
    private javax.swing.JPanel panelRecaudacion;
    private javax.swing.JPanel panelSalidas;
    private javax.swing.JPanel panelVuelosAerolinea;
    private javax.swing.JPanel panelVuelosDestino;
    private javax.swing.JTable tablaAerolineas;
    private javax.swing.JScrollPane tablaConsultaAerolineas;
    private javax.swing.JScrollPane tablaConsultaVuelos;
    private javax.swing.JScrollPane tablaDeRecaudacion;
    private javax.swing.JTable tablaLlegadas;
    private javax.swing.JScrollPane tablaLlegadasVuelos;
    private javax.swing.JTable tablaRecaudacion;
    private javax.swing.JTable tablaSalidas;
    private javax.swing.JScrollPane tablaSalidasVuelos;
    private javax.swing.JTable tablaVuelos;
    private javax.swing.JScrollPane tablaVuelosAerolinea;
    private javax.swing.JScrollPane tablaVuelosDestino;
    private javax.swing.JTable tablaVuelosPorAerolinea;
    private javax.swing.JTable tablaVuelosPorDestino;
    private javax.swing.JLabel txtAltaAerolineas;
    private javax.swing.JLabel txtAltaAerolineas1;
    private javax.swing.JLabel txtAltaAerolineas2;
    private javax.swing.JLabel txtAltaAerolineas3;
    private javax.swing.JLabel txtAltaAerolineas4;
    private javax.swing.JLabel txtAltaAerolineas5;
    private javax.swing.JLabel txtTituloAerolineas;
    private javax.swing.JLabel txtTituloInformacion;
    private javax.swing.JLabel txtTituloVuelos;
    // End of variables declaration//GEN-END:variables

}
