package vista.Listas;
import controlador.Listas.AutoControllerListas;
import controlador.Listas.VendedorControllerListas;
import controlador.Listas.VentaControllerListas;
import controlador.TDALista.LinkedList;
import controlador.listas.MarcaControllerListas;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.AgenteVendedor;
import modelo.Auto;
import modelo.Marca;
import modelo.Venta;
import vista.Listas.Tablas.ModeloVentaLista;
import vista.listas.util.UtilVistaLista;

/**
 *
 * @author Asus
 */
public class FrmVenta extends javax.swing.JFrame {
    VendedorControllerListas vc = new VendedorControllerListas();
    AutoControllerListas ac = new AutoControllerListas();
    VentaControllerListas ventac = new VentaControllerListas();
    MarcaControllerListas mc = new MarcaControllerListas();
    ModeloVentaLista modvental = new ModeloVentaLista();
    Date fecha = new Date();
    
    /**
     * Creates new form FrmVendedor
     */
    public FrmVenta() {
        initComponents();
        limpiar();
        txtfecha.setText(String.format("%tD", fecha));
        txtvalor.setVisible(false);
        dtcfecha.setVisible(false);
        jLabel10.setVisible(false);
        cbxMayorMenor.setVisible(false);
    }

    private void limpiar(){
        cargarTabla();
        txtdescripcion.setText("");
        txtcodigo.setText(ventac.generatedCode());
        txtfecha.setText(String.format("%tD", fecha));
        cbxvendedor.setSelectedIndex(-1);
        cbxauto.setSelectedIndex(-1);
        ventac.setVenta(null);
        ventac.setVentas(new LinkedList<>());
        tbldatos.clearSelection();
        ventac.setIndex(-1);
        try {
            UtilVistaLista.cargarVendedor(cbxvendedor);
            UtilVistaLista.cargarAuto(cbxauto);
        } catch (Exception e) {
            System.out.println("Error al cargar frmventa");
        }
    }
    
    private void cargarTabla(){
        cbxvalor.removeAllItems();
        for (Marca marca: mc.getMarcas().toArray()) {
            cbxvalor.addItem(marca.getNombre());
        }
        modvental.setVentas(ventac.getVentas());
        tbldatos.setModel(modvental);
        tbldatos.updateUI();
    }
    
    private void obtenerVenta(){
        ventac.getVenta().setCodigoVenta(txtcodigo.getText());
        ventac.getVenta().setDescripcion(txtdescripcion.getText());
        if(ventac.getVenta().getId_venta() == null)//Si es una nueva venta, le envio el auto del cbx, sino, no le cambio, ya que ese dato no puede cambiarse
            ventac.getVenta().setId_auto(((Auto)UtilVistaLista.getCombo(cbxauto)).getId());
        //ventac.getVenta().setId_auto(((Auto)cbxauto.getSelectedItem()).getId());
        ventac.getVenta().setId_vendedor(((AgenteVendedor)UtilVistaLista.getCombo(cbxvendedor)).getId());
        //ventac.getVenta().setId_vendedor(((AgenteVendedor)cbxauto.getSelectedItem()).getId());
        ventac.getVenta().setFecha(new Date());
    }
    
    private boolean validar(){
        return !txtdescripcion.getText().trim().isEmpty() &&//
                (cbxvendedor.getSelectedIndex() >= 0) &&
                (cbxauto.getSelectedIndex() >= 0);
    }  
    
    private void cargarVista() {
        ventac.setIndex(tbldatos.getSelectedRow());
        if (ventac.getIndex() < 0) {
            JOptionPane.showMessageDialog(null,
                    "Seleccione una fila", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                ventac.setVenta(modvental.getVentas().get(ventac.getIndex()));
                txtcodigo.setText(ventac.getVenta().getCodigoVenta());
                txtdescripcion.setText(ventac.getVenta().getDescripcion());
                txtfecha.setText(new SimpleDateFormat().format(ventac.getVenta().getFecha()));
                cbxvendedor.setSelectedIndex(ventac.getVenta().getId_vendedor()-1);
                //cbxauto.setSelectedIndex(ventac.getVenta().getId_auto()-1);
                txtplaca.setText(ac.getAutos().get(ventac.getVenta().getId_auto()-1).toString());
               // vc.setVendedor(mvl.getVendedores().get(vc.getIndex()));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                        e.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void guardar() {
        if (validar()) {
            try {
                obtenerVenta();
                if (ventac.getVenta().getId_venta()== null) {
                    if (ventac.save()) {
                        limpiar();
                        JOptionPane.showMessageDialog(null, 
                                "Se ha guardado correctamente", "Operación exitosa", 
                                JOptionPane.INFORMATION_MESSAGE);   
                        ventac.setVenta(null); 
                    } else {
                    JOptionPane.showMessageDialog(null, 
                            "No se ha podido guardar", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                } 
            } else {
                    if (ventac.update(ventac.getIndex())) {
                        limpiar();
                        JOptionPane.showMessageDialog(null, 
                                "Se ha editado correctamente", "Operación exitosa", 
                                JOptionPane.INFORMATION_MESSAGE);   
                        ventac.setVenta(null);  
                    } else {
                    JOptionPane.showMessageDialog(null, 
                            "No se ha podido editar", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }  
               }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                        e.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);                   
            } 
        }else {
                JOptionPane.showMessageDialog(null, 
                        "¡Llene todos los campos!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panelPrincipal = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        btnguardar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        txtcodigo = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        cbxvendedor = new javax.swing.JComboBox<>();
        cbxauto = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtdescripcion = new javax.swing.JTextArea();
        jLabel37 = new javax.swing.JLabel();
        txtfecha = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        btnnuevovendedor = new javax.swing.JButton();
        btnnuevovendedor1 = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        txtplaca = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbxcriterio = new javax.swing.JComboBox<>();
        cbxvalor = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtvalor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnordenar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldatos = new javax.swing.JTable();
        btnseleccionar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbxorden = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        btnbuscar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cbxmetodoorden = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cbxmetodobuscar = new javax.swing.JComboBox<>();
        cbxcriterio1 = new javax.swing.JComboBox<>();
        dtcfecha = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        cbxMayorMenor = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Ventas");

        panelPrincipal.setBackground(new java.awt.Color(255, 178, 132));

        jPanel6.setBackground(new java.awt.Color(245, 206, 199));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Complete los datos de la venta:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tw Cen MT", 1, 14))); // NOI18N

        jLabel33.setBackground(new java.awt.Color(204, 204, 255));
        jLabel33.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel33.setText("Agente Vendedor:");

        btnguardar.setBackground(new java.awt.Color(204, 204, 255));
        btnguardar.setFont(new java.awt.Font("SimSun-ExtB", 1, 18)); // NOI18N
        btnguardar.setText("Guardar");
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btncancelar.setBackground(new java.awt.Color(204, 204, 255));
        btncancelar.setFont(new java.awt.Font("SimSun-ExtB", 1, 18)); // NOI18N
        btncancelar.setText("Cancelar");
        btncancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarActionPerformed(evt);
            }
        });

        jLabel34.setBackground(new java.awt.Color(204, 204, 255));
        jLabel34.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel34.setText("Código de Venta:");

        txtcodigo.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtcodigo.setEnabled(false);

        jLabel35.setBackground(new java.awt.Color(204, 204, 255));
        jLabel35.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel35.setText("Autos Disponibles:");

        cbxvendedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxauto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel36.setBackground(new java.awt.Color(204, 204, 255));
        jLabel36.setText("Observación:");

        txtdescripcion.setColumns(20);
        txtdescripcion.setRows(5);
        jScrollPane2.setViewportView(txtdescripcion);

        jLabel37.setBackground(new java.awt.Color(204, 204, 255));
        jLabel37.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel37.setText("Fecha:");

        txtfecha.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtfecha.setEnabled(false);

        jLabel38.setBackground(new java.awt.Color(204, 204, 255));
        jLabel38.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel38.setText("(Placa):");

        btnnuevovendedor.setBackground(new java.awt.Color(204, 204, 255));
        btnnuevovendedor.setFont(new java.awt.Font("SimSun-ExtB", 1, 18)); // NOI18N
        btnnuevovendedor.setText("Crear nuevo vendedor");
        btnnuevovendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevovendedorActionPerformed(evt);
            }
        });

        btnnuevovendedor1.setBackground(new java.awt.Color(204, 204, 255));
        btnnuevovendedor1.setFont(new java.awt.Font("SimSun-ExtB", 1, 18)); // NOI18N
        btnnuevovendedor1.setText("Crear nuevo auto");
        btnnuevovendedor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevovendedor1ActionPerformed(evt);
            }
        });

        jLabel39.setBackground(new java.awt.Color(204, 204, 255));
        jLabel39.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel39.setText("o:");

        jLabel40.setBackground(new java.awt.Color(204, 204, 255));
        jLabel40.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel40.setText("o:");

        txtplaca.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        txtplaca.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbxvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnnuevovendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxauto, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtplaca, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(197, 197, 197))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addGap(18, 18, 18)
                                .addComponent(btnnuevovendedor1))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(178, 178, 178)))
                .addGap(70, 70, 70))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jLabel33)
                    .addComponent(cbxvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxauto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnnuevovendedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnnuevovendedor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel40)
                            .addComponent(jLabel39)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(txtplaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(79, 79, 79)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel36))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btncancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 201, 139));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registros existentes:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tw Cen MT", 1, 14))); // NOI18N
        jPanel1.setLayout(null);

        jLabel1.setText("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 40, 770, 20);

        cbxcriterio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Marca", "Agente Vendedor", "Placa", "Precio", "Fecha" }));
        cbxcriterio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxcriterioItemStateChanged(evt);
            }
        });
        jPanel1.add(cbxcriterio);
        cbxcriterio.setBounds(180, 20, 70, 22);

        cbxvalor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxvalor);
        cbxvalor.setBounds(300, 60, 120, 22);

        jLabel2.setText("Método:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(420, 60, 50, 20);
        jPanel1.add(txtvalor);
        txtvalor.setBounds(300, 60, 120, 30);

        jLabel3.setText("Valor:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(260, 60, 37, 20);

        btnordenar.setBackground(new java.awt.Color(204, 204, 255));
        btnordenar.setFont(new java.awt.Font("SimSun-ExtB", 1, 18)); // NOI18N
        btnordenar.setText("Ordenar");
        btnordenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnordenarActionPerformed(evt);
            }
        });
        jPanel1.add(btnordenar);
        btnordenar.setBounds(630, 20, 110, 26);

        tbldatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbldatos);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 120, 650, 210);

        btnseleccionar.setBackground(new java.awt.Color(204, 204, 255));
        btnseleccionar.setFont(new java.awt.Font("SimSun-ExtB", 1, 18)); // NOI18N
        btnseleccionar.setText("Seleccionar");
        btnseleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnseleccionarActionPerformed(evt);
            }
        });
        jPanel1.add(btnseleccionar);
        btnseleccionar.setBounds(660, 100, 150, 36);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Buscar:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(10, 60, 50, 17);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Ordenar:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(10, 20, 60, 17);

        jLabel6.setText("Atributo a buscar:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(70, 60, 110, 20);

        cbxorden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ASCENDENTE", "DESCENDENTE" }));
        jPanel1.add(cbxorden);
        cbxorden.setBounds(300, 20, 110, 22);

        jLabel7.setText("Atributo a ordenar:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(70, 20, 110, 20);

        btnbuscar.setBackground(new java.awt.Color(204, 204, 255));
        btnbuscar.setFont(new java.awt.Font("SimSun-ExtB", 1, 18)); // NOI18N
        btnbuscar.setText("Buscar");
        btnbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarActionPerformed(evt);
            }
        });
        jPanel1.add(btnbuscar);
        btnbuscar.setBounds(630, 60, 110, 30);

        jLabel8.setText("Orden:");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(260, 20, 40, 20);

        cbxmetodoorden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MergeSort", "QuickSort" }));
        jPanel1.add(cbxmetodoorden);
        cbxmetodoorden.setBounds(470, 20, 100, 22);

        jLabel9.setText("Método:");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(420, 20, 50, 20);

        cbxmetodobuscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Binaria", "Lineal Binaria" }));
        jPanel1.add(cbxmetodobuscar);
        cbxmetodobuscar.setBounds(470, 60, 100, 22);

        cbxcriterio1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Marca", "Agente Vendedor", "Placa", "Precio", "Fecha" }));
        cbxcriterio1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxcriterio1ItemStateChanged(evt);
            }
        });
        jPanel1.add(cbxcriterio1);
        cbxcriterio1.setBounds(180, 60, 70, 22);
        jPanel1.add(dtcfecha);
        dtcfecha.setBounds(300, 60, 120, 22);

        jButton1.setText("ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(690, 170, 72, 23);

        jLabel10.setText("Buscar:");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(240, 90, 50, 20);

        cbxMayorMenor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Valor exacto", "Valores Menores", "Valores Mayores" }));
        jPanel1.add(cbxMayorMenor);
        cbxMayorMenor.setBounds(300, 90, 120, 22);

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 767, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 45, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnseleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnseleccionarActionPerformed
        cargarVista();
    }//GEN-LAST:event_btnseleccionarActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        limpiar();
    }//GEN-LAST:event_btncancelarActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btnnuevovendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevovendedorActionPerformed
        new FrmVendedor().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnnuevovendedorActionPerformed

    private void btnnuevovendedor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevovendedor1ActionPerformed
        new FrmAuto().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnnuevovendedor1ActionPerformed

    private void btnordenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnordenarActionPerformed
        Venta array[] = ventac.getVentas().toArray();
        String field = cbxcriterio.getSelectedItem().toString();
        Integer orden = cbxorden.getSelectedIndex();
        
        try {
            if(cbxmetodoorden.getSelectedIndex() == 0){
                Long vinicial = System.nanoTime();
                modvental.setVentas(ventac.mergeSortVenta(array, 0, (array.length-1), orden, field));
                Long vfinal = System.nanoTime();
                Long duracion = vfinal - vinicial;
                System.out.println("Tiempo empleado en Merge Sort: "+duracion/1e6 + " ms");
            }else{
                Long vinicial = System.nanoTime();
                modvental.setVentas(ventac.quickSortVenta(array, 0, (array.length-1), orden, field));
                Long vfinal = System.nanoTime();
                Long duracion = vfinal - vinicial;
                System.out.println("Tiempo empleado en Quick Sort: "+duracion/1e6 + " ms");
            }
            tbldatos.setModel(modvental);
            tbldatos.updateUI();
        } catch (Exception e) {
            System.out.println("error al ordenar: "+e.getMessage());
        }
    }//GEN-LAST:event_btnordenarActionPerformed

    private void cbxcriterioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxcriterioItemStateChanged
        
    }//GEN-LAST:event_cbxcriterioItemStateChanged

    private void cbxcriterio1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxcriterio1ItemStateChanged
        if(evt.getItem().toString().equalsIgnoreCase("MARCA")){
            txtvalor.setVisible(false);
            cbxvalor.setVisible(true);
            dtcfecha.setVisible(false);
            cbxMayorMenor.setVisible(false);
        }else if(evt.getItem().toString().equalsIgnoreCase("FECHA")){
            txtvalor.setVisible(false);
            cbxvalor.setVisible(false);
            dtcfecha.setVisible(true);
            cbxMayorMenor.setVisible(false);
        }else{
            txtvalor.setVisible(true);
            cbxvalor.setVisible(false);
            dtcfecha.setVisible(false);
            cbxMayorMenor.setVisible(false);
        }
        if(evt.getItem().toString().equalsIgnoreCase("PRECIO" ) || evt.getItem().toString().equalsIgnoreCase("FECHA" )){
            jLabel10.setVisible(true);
            cbxMayorMenor.setVisible(true);
        }
    }//GEN-LAST:event_cbxcriterio1ItemStateChanged

    private void btnbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarActionPerformed
        String field = cbxcriterio1.getSelectedItem().toString();
        Object valor = null;
        Integer orden;
        
        if(cbxcriterio1.getSelectedIndex() == 0)
            valor = cbxvalor.getSelectedItem().toString();
        else if(cbxcriterio1.getSelectedIndex() == 4){
            SimpleDateFormat formatofe = new SimpleDateFormat("yy-MM-dd");
            valor = formatofe.format(dtcfecha.getDate());
        }else
            valor = txtvalor.getText();
        
        if(cbxcriterio1.getSelectedIndex() > 2)
            orden = cbxMayorMenor.getSelectedIndex();
        else
            orden = 0;
        try {
            LinkedList<Venta> busqueda = new LinkedList<>();
            if(cbxmetodobuscar.getSelectedIndex() == 0)
                busqueda = ventac.busquedaBin(field, valor);
            else
                busqueda = ventac.busqLineBin(field, valor, orden);
            if(!busqueda.isEmpty()){
                modvental.setVentas(busqueda);
                tbldatos.setModel(modvental);
                tbldatos.updateUI();
            }else
                JOptionPane.showMessageDialog(null, "No se ha encontrado lo solicitado", "Error en la búsqueda", JOptionPane.ERROR_MESSAGE);
        }catch (Exception e) {
            System.out.println("Error en busqueda : "+e.getMessage());
        }
    }//GEN-LAST:event_btnbuscarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String field = cbxcriterio1.getSelectedItem().toString();
        Object valor = null;
        Integer orden = cbxMayorMenor.getSelectedIndex();
        if(cbxcriterio1.getSelectedIndex() == 0)
            valor = cbxvalor.getSelectedItem();
        else if(cbxcriterio1.getSelectedIndex() == 4){
            SimpleDateFormat formatofe = new SimpleDateFormat("yy-MM-dd");
            valor = formatofe.format(dtcfecha.getDate());
        }else
            valor = txtvalor.getText();
        try {
            LinkedList<Venta> busqueda = ventac.busqLineBin(field, valor,orden);
            if(!busqueda.isEmpty()){
                modvental.setVentas(busqueda);
                tbldatos.setModel(modvental);
                tbldatos.updateUI();
            }else
                JOptionPane.showMessageDialog(null, "No se ha encontrado lo solicitado", "Error en la búsqueda", JOptionPane.ERROR_MESSAGE);
        }catch (Exception e) {
            System.out.println("Error en busqueda binario: "+e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmVenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscar;
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevovendedor;
    private javax.swing.JButton btnnuevovendedor1;
    private javax.swing.JButton btnordenar;
    private javax.swing.JButton btnseleccionar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxMayorMenor;
    private javax.swing.JComboBox<String> cbxauto;
    private javax.swing.JComboBox<String> cbxcriterio;
    private javax.swing.JComboBox<String> cbxcriterio1;
    private javax.swing.JComboBox<String> cbxmetodobuscar;
    private javax.swing.JComboBox<String> cbxmetodoorden;
    private javax.swing.JComboBox<String> cbxorden;
    private javax.swing.JComboBox<String> cbxvalor;
    private javax.swing.JComboBox<String> cbxvendedor;
    private com.toedter.calendar.JDateChooser dtcfecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTable tbldatos;
    private javax.swing.JTextField txtcodigo;
    private javax.swing.JTextArea txtdescripcion;
    private javax.swing.JTextField txtfecha;
    private javax.swing.JTextField txtplaca;
    private javax.swing.JTextField txtvalor;
    // End of variables declaration//GEN-END:variables
}
