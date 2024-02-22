package vista;
import com.thoughtworks.xstream.converters.extended.FileConverter;
import controlador.AntenaController;
import controlador.Grafos.Antena.UtilidadesAntenaVista;
import controlador.Util.UtilesAntenaVista;
import controlador.Util.Utilidades;
import controlador.grafos.GrafoEtiquetadoNoDirigido;
import controlador.listas.LinkedList;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.Antena;
import modelo.TipoAntena;
import vista.modeloTabla.ModeloTablaAntena;

/**
 *
 * @author Asus
 */
public class FrmAntena extends javax.swing.JFrame {
    FondoPanelA fondo = new FondoPanelA();
    AntenaController ac = new AntenaController();
    ModeloTablaAntena mta = new ModeloTablaAntena();
    private File foto1;
    private File foto2;
    private File foto3;
    /**
     * Creates new form FrmAntena
     */
    public FrmAntena() {
        this.setContentPane(fondo);
        initComponents();
        cargarTipos();
        //dtcfecha.setDateFormatString("dd-MMMMMMMMMM-YYYY");
        //this.setExtendedState(MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        limpiar();
    }

    private boolean validar(){
        return !txtaltura.getText().trim().isEmpty() &&
                !txtcodigo.getText().trim().isEmpty() &&
                !txtfoto1.getText().trim().isEmpty() &&
                !txtlatitud.getText().trim().isEmpty() &&
                !txtlongitud.getText().trim().isEmpty() &&
                foto1 != null;
    }
    
    private void cargarTabla(){
        mta.setAntenas(ac.getAntenas());
        tblDatos.setModel(mta);
        tblDatos.updateUI();
    }
    
    private void limpiar(){
        cargarTabla();
        ac.setAntena(null);
        ac.setAntenas(new LinkedList<>());
        foto1 = null;
        foto2 = null;
        foto3 = null;
        txtfoto1.setText("");
        txtfoto2.setText("");
        txtfoto3.setText("");
        txtlongitud.setText("");
        txtlatitud.setText("");
        txtcodigo.setText(ac.generatedCode());
        txtaltura.setText("");
        dtcfecha.setDate(null);
    }
    
    private void cargarAntena(){
        Integer index = tblDatos.getSelectedRow();
        limpiar();
        if (index < 0) {
            JOptionPane.showMessageDialog(null,
                    "Seleccione una fila", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                ac.setAntena(mta.getAntenas().get(index));
                txtcodigo.setText(ac.getAntena().getCodigo());
                txtaltura.setText(ac.getAntena().getAltura().toString());
                txtfoto1.setText(ac.getAntena().getFoto1());
                foto1 = new File("multimedia\\"+txtfoto1.getText());
                txtfoto2.setText(ac.getAntena().getFoto2());
                if(!txtfoto2.getText().equals("Ninguna"))
                    foto2 = new File("multimedia\\"+txtfoto2.getText());
                txtfoto3.setText(ac.getAntena().getFoto3());
                if(!txtfoto3.getText().equals("Ninguna"))
                    foto3 = new File("multimedia\\"+txtfoto3.getText());
                txtlatitud.setText(ac.getAntena().getLatitud().toString());
                txtlongitud.setText(ac.getAntena().getLongitud().toString());
                cbxTipo.setSelectedItem(ac.getAntena().getTipo().toString());
                dtcfecha.setDate(ac.getAntena().getFechaInstalacion());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                        e.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void obtenerAntenaBas(){
            ac.getAntena().setCodigo(txtcodigo.getText());
            ac.getAntena().setAltura(Utilidades.redondear(Double.parseDouble(txtaltura.getText())));
            ac.getAntena().setLatitud(Double.parseDouble(txtlatitud.getText()));
            ac.getAntena().setLongitud(Double.parseDouble(txtlongitud.getText()));
            ac.getAntena().setFechaInstalacion(dtcfecha.getDate());
            ac.getAntena().setTipo(cbxTipo.getSelectedItem().toString());
    }
    
    private void actualizarFotos(){
        try {
            String uuidFo1 = ac.getAntena().getFoto1();
            String uuidFo2 = ac.getAntena().getFoto2();
            String uuidFo3 = ac.getAntena().getFoto3();
            if(!txtfoto1.getText().equals(ac.getAntena().getFoto1())){
                uuidFo1 = UUID.randomUUID().toString()+"."+Utilidades.extension(foto1.getName());
                Utilidades.copiarArchivo(foto1, new File("multimedia/"+uuidFo1));
            }
            if(!txtfoto2.getText().equals(ac.getAntena().getFoto2())){
                uuidFo2 = UUID.randomUUID().toString()+"."+Utilidades.extension(foto2.getName());
                Utilidades.copiarArchivo(foto2, new File("multimedia/"+uuidFo2));
            }
            if(!txtfoto3.getText().equals(ac.getAntena().getFoto3())){
                uuidFo3 = UUID.randomUUID().toString()+"."+Utilidades.extension(foto3.getName());
                Utilidades.copiarArchivo(foto3, new File("multimedia/"+uuidFo3));
            }
            ac.getAntena().setFoto1(uuidFo1);
            ac.getAntena().setFoto2(uuidFo2);
            ac.getAntena().setFoto3(uuidFo3);
        } catch (Exception e) {e.printStackTrace();}
    }
    
    private void registrarFotos(){
        try {
            String uuidF1 = UUID.randomUUID().toString()+"."+Utilidades.extension(foto1.getName());
            String uuidF2 = (foto2 != null) ? UUID.randomUUID().toString()+"."+Utilidades.extension(foto2.getName()) : "Ninguna";
            String uuidF3 = (foto3 != null) ? UUID.randomUUID().toString()+"."+Utilidades.extension(foto3.getName()) : "Ninguna";
            ac.getAntena().setFoto1(uuidF1);
            ac.getAntena().setFoto2(uuidF2);
            ac.getAntena().setFoto3(uuidF3);
            Utilidades.copiarArchivo(foto1, new File("multimedia/"+uuidF1+"."+Utilidades.extension(foto1.getName())));
            if(foto2 != null){Utilidades.copiarArchivo(foto2, new File("multimedia/"+uuidF2));}
            if(foto3 != null){Utilidades.copiarArchivo(foto3, new File("multimedia/"+uuidF3));}
        } catch (Exception e) {e.printStackTrace();}
    }
  
    private void guardar(){
        if(validar()){
            try{
            obtenerAntenaBas();
            if(ac.getAntena().getId() == null){//Es decir, crear una nueva antena:
                registrarFotos();
                if(ac.save()){
                    JOptionPane.showMessageDialog(null, "Antena registrada","Operacion exitosa",JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(null, 
                            "No se pudo registrar la antena", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }  
            } else {
                actualizarFotos();
                if(ac.update(tblDatos.getSelectedRow())){
                    JOptionPane.showMessageDialog(null, "Antena actualizada","Operacion exitosa",JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(null, 
                            "No se ha podido actualizar", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }  
            }        
            limpiar();
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error, no se puede convertir cadena a número","Error",JOptionPane.ERROR_MESSAGE);
        }
        }else{
            JOptionPane.showMessageDialog(null, "Algunos campos están vacíos","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarTipos(){
        cbxTipo.removeAllItems();
        for (TipoAntena tipo : TipoAntena.values()) {
            cbxTipo.addItem(tipo.toString());
        }
    }
    
    private void fileChooser(File foto, JTextField textF){
        JFileChooser filec = new JFileChooser("C:\\Users\\Asus\\Downloads");
        filec.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = 
                new FileNameExtensionFilter("Imagenes", "jpg","png","jpeg");
        filec.addChoosableFileFilter(filter);
        int i = filec.showOpenDialog(null);
        if(i == JFileChooser.APPROVE_OPTION){
            foto = filec.getSelectedFile();
            textF.setText(foto.getName());
        }else{
            System.out.println("Ningún archivo seleccionado");
        }
    }
    
    private File fileChooser2(){
        JFileChooser filec = new JFileChooser("C:\\Users\\Asus\\Downloads");
        filec.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = 
                new FileNameExtensionFilter("jpg,png,jpeg", "jpg","png","jpeg");
        filec.addChoosableFileFilter(filter);
        int i = filec.showOpenDialog(null);
        if(i == JFileChooser.APPROVE_OPTION){
            return filec.getSelectedFile();
        }else{
            System.out.println("Ningún archivo seleccionado");
            return null;
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel2 = new FondoPanelA();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnguardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtcodigo = new javax.swing.JTextField();
        txtaltura = new javax.swing.JTextField();
        txtlatitud = new javax.swing.JTextField();
        txtlongitud = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtfoto2 = new javax.swing.JTextField();
        txtfoto3 = new javax.swing.JTextField();
        btnfoto1 = new javax.swing.JButton();
        btnfoto2 = new javax.swing.JButton();
        btnfoto3 = new javax.swing.JButton();
        txtfoto1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbxTipo = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        dtcfecha = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        btnseleccionar = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Código:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Altura:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Latitud:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Longitud:");

        btnguardar.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Datos de Antenas:");

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Datos de Antenas:");

        txtcodigo.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Imagen 1:");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Imagen 2:");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Imagen 3:");

        txtfoto2.setEditable(false);
        txtfoto2.setBackground(new java.awt.Color(153, 204, 255));
        txtfoto2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtfoto2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtfoto2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtfoto2MouseClicked(evt);
            }
        });

        txtfoto3.setEditable(false);
        txtfoto3.setBackground(new java.awt.Color(153, 204, 255));
        txtfoto3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtfoto3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtfoto3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtfoto3MouseClicked(evt);
            }
        });
        txtfoto3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfoto3ActionPerformed(evt);
            }
        });

        btnfoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/buscar.png"))); // NOI18N
        btnfoto1.setContentAreaFilled(false);
        btnfoto1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnfoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfoto1ActionPerformed(evt);
            }
        });

        btnfoto2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/buscar.png"))); // NOI18N
        btnfoto2.setContentAreaFilled(false);
        btnfoto2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnfoto2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfoto2ActionPerformed(evt);
            }
        });

        btnfoto3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/buscar.png"))); // NOI18N
        btnfoto3.setContentAreaFilled(false);
        btnfoto3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnfoto3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfoto3ActionPerformed(evt);
            }
        });

        txtfoto1.setEditable(false);
        txtfoto1.setBackground(new java.awt.Color(153, 204, 255));
        txtfoto1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtfoto1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtfoto1.setDragEnabled(true);
        txtfoto1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtfoto1MouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Tipo:");

        cbxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Fecha de instalación:");

        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDatos);

        btnseleccionar.setFont(new java.awt.Font("Monospaced", 1, 10)); // NOI18N
        btnseleccionar.setText("SELECCIONAR");
        btnseleccionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnseleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnseleccionarActionPerformed(evt);
            }
        });

        btncancelar.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        btncancelar.setText("CANCELAR");
        btncancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btncancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(44, 44, 44)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtaltura, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                    .addComponent(txtlatitud)
                                    .addComponent(txtlongitud)
                                    .addComponent(txtcodigo))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(txtfoto2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnfoto2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(txtfoto3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnfoto3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel10))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtfoto1)
                                            .addComponent(cbxTipo, 0, 140, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnfoto1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel1)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(btnguardar)
                                        .addGap(18, 18, 18)
                                        .addComponent(btncancelar))
                                    .addComponent(dtcfecha, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel6)
                            .addComponent(btnseleccionar))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)
                                    .addComponent(cbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtaltura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtfoto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnfoto1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnfoto2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtlatitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(txtfoto2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnfoto3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtlongitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9)
                                .addComponent(txtfoto3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dtcfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnguardar)
                    .addComponent(btncancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnseleccionar)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnfoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfoto1ActionPerformed
        foto1 = fileChooser2();
        if(foto1 != null)
            txtfoto1.setText(foto1.getName());
    }//GEN-LAST:event_btnfoto1ActionPerformed

    private void btnfoto2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfoto2ActionPerformed
        foto2 = fileChooser2();
        if(foto2 != null)
            txtfoto2.setText(foto2.getName());
    }//GEN-LAST:event_btnfoto2ActionPerformed

    private void btnfoto3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfoto3ActionPerformed
       foto3 = fileChooser2();
       if(foto3 != null)
            txtfoto3.setText(foto3.getName());
    }//GEN-LAST:event_btnfoto3ActionPerformed

    private void txtfoto2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtfoto2MouseClicked
        if(foto2 != null)
            new FrmFoto(this, true,foto2).setVisible(true);
        else
            JOptionPane.showMessageDialog(null, "No ha seleccionado ningún archivo","Error",JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_txtfoto2MouseClicked

    private void txtfoto3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtfoto3MouseClicked
        if(foto3 != null)
            new FrmFoto(this, true,foto3).setVisible(true);
        else
            JOptionPane.showMessageDialog(null, "No ha seleccionado ningún archivo","Error",JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_txtfoto3MouseClicked

    private void txtfoto1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtfoto1MouseClicked
        if(foto1 != null)
            new FrmFoto(this, true,foto1).setVisible(true);
        else
            JOptionPane.showMessageDialog(null, "No ha seleccionado ningún archivo","Error",JOptionPane.WARNING_MESSAGE);
        System.out.println(foto1.getName());
    }//GEN-LAST:event_txtfoto1MouseClicked

    private void txtfoto3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfoto3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfoto3ActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btnseleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnseleccionarActionPerformed
        cargarAntena();
    }//GEN-LAST:event_btnseleccionarActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed
        limpiar();
    }//GEN-LAST:event_btncancelarActionPerformed

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
            java.util.logging.Logger.getLogger(FrmAntena.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAntena.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAntena.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAntena.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAntena().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btnfoto1;
    private javax.swing.JButton btnfoto2;
    private javax.swing.JButton btnfoto3;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnseleccionar;
    private javax.swing.JComboBox<String> cbxTipo;
    private com.toedter.calendar.JDateChooser dtcfecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtaltura;
    private javax.swing.JTextField txtcodigo;
    private javax.swing.JTextField txtfoto1;
    private javax.swing.JTextField txtfoto2;
    private javax.swing.JTextField txtfoto3;
    private javax.swing.JTextField txtlatitud;
    private javax.swing.JTextField txtlongitud;
    // End of variables declaration//GEN-END:variables

    class FondoPanelA extends JPanel{
        private Image imagen;

        @Override
        public void paint(Graphics g) {
            imagen = new ImageIcon(getClass().getResource("/multimedia/antena.jpeg")).getImage();
            //new ImageIcon("/multimedia/fondo.jpg").getImage();
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
        
    }
}
