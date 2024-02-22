package vista;

import controlador.AntenaController;
import javax.swing.JOptionPane;
import controlador.Grafos.Antena.UtilidadesAntenaVista;
import controlador.Grafos.Dijkstra;
import controlador.Util.Utilidades;
import controlador.grafos.DibujarGrafo;
import controlador.listas.LinkedList;
import controlador.listas.exceptions.VacioException;
import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import vista.modeloTabla.ModeloTablaAdyacencia;
/**
 *
 * @author Asus
 */
public class FrmMapa extends javax.swing.JFrame {
    AntenaController ac = new AntenaController();
    ModeloTablaAdyacencia mta = new ModeloTablaAdyacencia();
    /**
     * Creates new form FrmMapa
     */
    public FrmMapa() {
        initComponents();
        limpiar();
    }

    private void cargarTabla(){
        try {
            mta.setGrafo(ac.getGrafoAntena());
            mta.fireTableDataChanged();
            tbltabla.setModel(mta);
            tbltabla.updateUI();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo cargar escuelas","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiar(){
        try {
            UtilidadesAntenaVista.cargarComboAntena(cbxorigen);
            UtilidadesAntenaVista.cargarComboAntena(cbxdestino);
            //edao.getGrafoEscuela();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo cargar las adyacencias","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void guardarGrafo(){
        int i = JOptionPane.showConfirmDialog(null, "¿Está de acuerdo en guardar el grafo?","Confirmación",JOptionPane.OK_CANCEL_OPTION);
        if(i == JOptionPane.OK_OPTION){
            try {
//                ac.cargarGrafo();//cojo el grafo del archivo
//                    GrafoEtiquetadoNoDirigido<Antena> grafoCargado = ac.getGrafoAntena();
//                    grafoCargado.etiquetarVertice((grafoCargado.nro_vertices() + 1), ac.getAntena());
//                    ac.guardarGrafo();
                ac.guardarGrafo();
                JOptionPane.showMessageDialog(null, "Grafo guardado","OK",JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, "No se pudo cargar el grafo","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cargarGrafo(){
        int i = JOptionPane.showConfirmDialog(null, "¿Está de acuerdo en cargar el grafo?","Confirmación",JOptionPane.OK_CANCEL_OPTION);
        if(i == JOptionPane.OK_OPTION){
            try {
                ac.cargarGrafo();
                limpiar();
                //Mensaje grafo cargado
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, "No se pudo cargar el grafo","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void adyacencia(){
        Integer pos0 = cbxorigen.getSelectedIndex();
        Integer posD = cbxdestino.getSelectedIndex();
        if(pos0 != posD){
            try {
                Double peso = UtilidadesAntenaVista.distanciaAntena(UtilidadesAntenaVista.getComboAntena(cbxorigen), UtilidadesAntenaVista.getComboAntena(cbxdestino));
                System.out.println("Insertando arista en pos: "+pos0+" y "+posD);
                ac.getGrafoAntena().insertarAristaE(ac.getAntenas().get(pos0), ac.getAntenas().get(posD),peso);
                JOptionPane.showMessageDialog(null, "Adyacencia agregada","OK",JOptionPane.INFORMATION_MESSAGE);
                limpiar();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else
            JOptionPane.showMessageDialog(null, "No se pudo agregar la misma adyacencia","Error",JOptionPane.ERROR_MESSAGE);
    }
    
    private void camino() throws Exception{
        if(ac.getGrafoAntena()!= null){
            Integer pos0 = cbxorigen.getSelectedIndex()+1;
            Integer posD = cbxdestino.getSelectedIndex()+1;
            HashMap<String, LinkedList> mapa = ac.getGrafoAntena().camino(pos0, posD);
            if(mapa.isEmpty())
                JOptionPane.showMessageDialog(null, "No existe camino","Error",JOptionPane.ERROR_MESSAGE);
            else{
                JOptionPane.showMessageDialog(null, "Camino encontrado","OK",JOptionPane.INFORMATION_MESSAGE);
                LinkedList<Integer> caminos = mapa.get("camino");
                for (int i = 0; i < caminos.getSize(); i++) {
                    Integer v = caminos.get(i);
                    System.out.println(ac.getGrafoAntena().obtenerEtiqueta(v));
                }
                //System.out.println(mapa.get("camino").print());
            }
        }else
            JOptionPane.showMessageDialog(null, "No se pudo cargar el grafo de escuela","Error",JOptionPane.ERROR_MESSAGE);
    }
    
    private void caminoDijkstra() throws Exception{
        if(ac.getGrafoAntena()!= null){
            Integer pos0 = cbxorigen.getSelectedIndex()+1;
            Integer posD = cbxdestino.getSelectedIndex()+1;
            //Se empieza el conteo del tiempo de ejecucion
            Long vinicial = System.nanoTime();
            HashMap<Integer, Integer> rutaAnterior = ac.getGrafoAntena().caminosDijsktra(pos0);
            if(rutaAnterior.isEmpty())
                JOptionPane.showMessageDialog(null, "No existe camino","Error",JOptionPane.ERROR_MESSAGE);
            else{
                LinkedList<Integer> caminos = mostrarCamino(rutaAnterior, posD);
                String ruta = "La ruta óptima de "+pos0+" a "+posD+" es: \n";
                for (int i = 0; i < caminos.getSize(); i++) {
                    Integer v = caminos.get(i);
                    ruta += ac.getGrafoAntena().obtenerEtiqueta(v)+" -> ";
                    System.out.println(ac.getGrafoAntena().obtenerEtiqueta(v));
                }
                Long vfinal = System.nanoTime();//Finaliza el conteo
                Long duracion = vfinal - vinicial;
                System.out.println("Tiempo empleado en Dijkstra: "+duracion/1e6 + " ms");
                JOptionPane.showMessageDialog(null, ruta,"Camino encontrado",JOptionPane.INFORMATION_MESSAGE);
            }
        }else
            JOptionPane.showMessageDialog(null, "Grafo Nulo","Error",JOptionPane.ERROR_MESSAGE);
    }
    
    private void caminoFloyd() throws Exception{
        if(ac.getGrafoAntena()!= null){
            Integer pos0 = cbxorigen.getSelectedIndex()+1;
            Integer posD = cbxdestino.getSelectedIndex()+1;
            //Se empieza el conteo del tiempo de ejecucion
            Long vinicial = System.nanoTime();
            HashMap<Integer, Integer> rutaAnterior = ac.getGrafoAntena().caminosDijsktra(pos0);
            if(rutaAnterior.isEmpty())
                JOptionPane.showMessageDialog(null, "No existe camino","Error",JOptionPane.ERROR_MESSAGE);
            else{
                LinkedList<Integer> caminos = mostrarCamino(rutaAnterior, posD);
                String ruta = "La ruta óptima de "+pos0+" a "+posD+" es: \n";
                for (int i = 0; i < caminos.getSize(); i++) {
                    Integer v = caminos.get(i);
                    ruta += ac.getGrafoAntena().obtenerEtiqueta(v)+" -> ";
                    System.out.println(ac.getGrafoAntena().obtenerEtiqueta(v));
                }
                Long vfinal = System.nanoTime();//Finaliza el conteo
                Long duracion = vfinal - vinicial;
                System.out.println(ruta);
                System.out.println("Tiempo empleado en Floyd: "+duracion/1e6 + " ms");
            }
        }else
            JOptionPane.showMessageDialog(null, "Grafo Nulo","Error",JOptionPane.ERROR_MESSAGE);
    }
    
    private LinkedList<Integer> mostrarCamino(HashMap<Integer, Integer> predecesores, Integer D) throws VacioException {
        LinkedList<Integer> listaRutas = new LinkedList<>();
        Integer actual = D;
        while (actual != null) {
            listaRutas.add(actual, 0);
            actual = predecesores.get(actual);
        }
        return listaRutas;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelShadow1 = new org.edisoncor.gui.panel.PanelShadow();
        labelHeader1 = new org.edisoncor.gui.label.LabelHeader();
        btnVermapa = new org.edisoncor.gui.button.ButtonAeroRound();
        btnVergrafo = new org.edisoncor.gui.button.ButtonAeroRound();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbxorigen = new org.edisoncor.gui.comboBox.ComboBoxRect();
        jLabel2 = new javax.swing.JLabel();
        cbxdestino = new org.edisoncor.gui.comboBox.ComboBoxRect();
        btnadyacencia = new javax.swing.JButton();
        btnadyacencia1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltabla = new javax.swing.JTable();
        btnguardar = new javax.swing.JButton();
        btncamino = new javax.swing.JButton();
        btncargar = new javax.swing.JButton();
        btndijstra = new javax.swing.JButton();
        btndijstra1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelHeader1.setText("Ubicaciones de Antenas");

        btnVermapa.setText("Ver mapa");
        btnVermapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVermapaActionPerformed(evt);
            }
        });

        btnVergrafo.setText("Ver grafo");
        btnVergrafo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVergrafoActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Construir grafo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tw Cen MT", 1, 14))); // NOI18N

        jLabel1.setText("Origen:");

        jLabel2.setText("Destino:");

        btnadyacencia.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btnadyacencia.setText("Adyacencia");
        btnadyacencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnadyacenciaActionPerformed(evt);
            }
        });

        btnadyacencia1.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btnadyacencia1.setText("Aleatoria");
        btnadyacencia1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnadyacencia1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbxorigen, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbxdestino, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnadyacencia1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnadyacencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxorigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnadyacencia))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxdestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnadyacencia1))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Matriz de Adyacencias", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tw Cen MT", 1, 14))); // NOI18N

        tbltabla.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbltabla);

        btnguardar.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btncamino.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btncamino.setText("CAMINO");
        btncamino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncaminoActionPerformed(evt);
            }
        });

        btncargar.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btncargar.setText("CARGAR");
        btncargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncargarActionPerformed(evt);
            }
        });

        btndijstra.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btndijstra.setText("Buscar con DIJKSTRA");
        btndijstra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndijstraActionPerformed(evt);
            }
        });

        btndijstra1.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btndijstra1.setText("Buscar con FLOYD");
        btndijstra1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndijstra1ActionPerformed(evt);
            }
        });

        jButton1.setText("Anch");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Prof");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btndijstra, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnguardar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btncamino)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton2)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btncargar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btndijstra1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnguardar)
                    .addComponent(btncamino)
                    .addComponent(btncargar)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btndijstra, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btndijstra1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelShadow1Layout = new javax.swing.GroupLayout(panelShadow1);
        panelShadow1.setLayout(panelShadow1Layout);
        panelShadow1Layout.setHorizontalGroup(
            panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShadow1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelShadow1Layout.createSequentialGroup()
                        .addComponent(btnVermapa, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVergrafo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelHeader1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelShadow1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelShadow1Layout.setVerticalGroup(
            panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShadow1Layout.createSequentialGroup()
                .addComponent(labelHeader1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVermapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVergrafo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelShadow1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelShadow1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnadyacenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnadyacenciaActionPerformed
        adyacencia();
    }//GEN-LAST:event_btnadyacenciaActionPerformed

    private void btnVermapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVermapaActionPerformed
        try {
            String dir = Utilidades.getDirProject();
            UtilidadesAntenaVista.crearMapaAntena(ac.getGrafoAntena());
            Utilidades.abrirNavegadorPredWindows(dir+File.separatorChar+"mapas"+File.separatorChar+"index.html");
        } catch (Exception e) {
            System.out.println(e);
        } 
    }//GEN-LAST:event_btnVermapaActionPerformed

    private void btnVergrafoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVergrafoActionPerformed
        try {
            String dir = Utilidades.getDirProject();
            DibujarGrafo dg = new DibujarGrafo();
            dg.crearArchivo(ac.getGrafoAntena());
            Utilidades.abrirNavegadorPredWindows(dir+File.separatorChar+"d3"+File.separatorChar+"grafo.html");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnVergrafoActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        guardarGrafo();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btncaminoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncaminoActionPerformed
        try {
            camino();
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btncaminoActionPerformed

    private void btncargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncargarActionPerformed
        cargarGrafo();
    }//GEN-LAST:event_btncargarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            if(ac.getGrafoAntena().conectadoAnchuraGNoDirigido())
                System.out.println("Todos conectados");
            else
                System.out.println("No están conectados");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            if(ac.getGrafoAntena().conectadoProfundidadGNoDirigido())
                System.out.println("Todos conectados");
            else
                System.out.println("No están conectados");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btndijstraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndijstraActionPerformed
        try {
            caminoDijkstra();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btndijstraActionPerformed

    private void btndijstra1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndijstra1ActionPerformed
        try {
            Double dist[][] = ac.getGrafoAntena().caminosFloyd(ac.getGrafoAntena());
            caminoFloyd();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btndijstra1ActionPerformed

    private void btnadyacencia1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnadyacencia1ActionPerformed
        try {
            for (int j = 1; j <= 2; j++) {
                for (int i = 0; i < ac.getAntenas().getSize(); i++) {
                    Integer posO = i;
                    Integer posD = (new Random().nextInt(ac.getAntenas().getSize()));
                    posD = (posO == posD) ? (posD+1) : posD;
                    posD = (posD > ac.getAntenas().getSize()) ? posD-1 : posD;
                    if (posO != posD) {
                        Double peso = new Random().nextDouble();
                        ac.getGrafoAntena().insertarAristaE(ac.getAntenas().get(posO), ac.getAntenas().get(posD), peso);
                        //JOptionPane.showMessageDialog(null, "Adyacencia Agregada", "POSI", JOptionPane.OK_OPTION);
                        limpiar();
                    }
                }  
            }
        } catch (Exception ex) {
            System.out.println("ERROR Aleatorio" + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnadyacencia1ActionPerformed

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
            java.util.logging.Logger.getLogger(FrmMapa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMapa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMapa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMapa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMapa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonAeroRound btnVergrafo;
    private org.edisoncor.gui.button.ButtonAeroRound btnVermapa;
    private javax.swing.JButton btnadyacencia;
    private javax.swing.JButton btnadyacencia1;
    private javax.swing.JButton btncamino;
    private javax.swing.JButton btncargar;
    private javax.swing.JButton btndijstra;
    private javax.swing.JButton btndijstra1;
    private javax.swing.JButton btnguardar;
    private org.edisoncor.gui.comboBox.ComboBoxRect cbxdestino;
    private org.edisoncor.gui.comboBox.ComboBoxRect cbxorigen;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private org.edisoncor.gui.label.LabelHeader labelHeader1;
    private org.edisoncor.gui.panel.PanelShadow panelShadow1;
    private javax.swing.JTable tbltabla;
    // End of variables declaration//GEN-END:variables
}
