package GUI;

import AnalisisSintactico.AnalisisSintactico;
import AnalizadorLexico.AnalisisLexico;
import AnalizadorLexico.Lexer;
import AnalizadorLexico.Token;
import Logica.CargaArchivo;
import Logica.LogicaArchivo;
import Logica.MyAdjustmentListener;
import java.awt.Event;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.UndoManager;

public class MenuPrincipal extends javax.swing.JFrame implements UndoableEditListener {
// atributos de la clase
    private String archivoALeer;
    private String nombreArchivoALeer;
    private LogicaArchivo logica = new LogicaArchivo();
    private File archivo;
    private UndoManager undo;
    private HashMap<Integer, String> miHash = new HashMap<Integer, String>();
    private DefaultListModel modeloLista;
    private DefaultTableModel modeloLexemas;
    private DefaultTableModel modeloTokens;
    private ArrayList<Token> tokensValidos;
    private ArrayList<Token> tokensError;
    
// como iniciara el menu principal
    public MenuPrincipal() {
        initComponents();
        asignarValoresModelos();

        undo = new UndoManager();
        asignar();

    }
// asignara valores a las columnas
    public void asignarValoresModelos() {
        modeloLista = new DefaultListModel();
        listaErrores.setModel(modeloLista);
        modeloLexemas = new DefaultTableModel();
        modeloTokens = new DefaultTableModel();
        modeloLexemas.addColumn("Lexema");
        modeloLexemas.addColumn("Token");
        modeloLexemas.addColumn("Veces Repetido");
        modeloTokens.addColumn("Token");
        modeloTokens.addColumn("Lexema");
        modeloTokens.addColumn("Fila");
        modeloTokens.addColumn("Columna");
        JTable tablaLexemas = new JTable(modeloLexemas);
        JTable tablaTokens = new JTable(modeloTokens);
        ocultarBotones();
        
    }
// oculta los botones
    public void ocultarBotones(){
        botonLexemas.setVisible(false);
        botonTokens.setVisible(false);
    }
    public void mostrarBotones(){// muestra los botones
        botonLexemas.setVisible(true);
        botonTokens.setVisible(true);
    }
    public void asignar() {// asigna los valores de las funcionalidades del ide
        textArea.setAutoscrolls(false);
        numeracion.setAutoscrolls(false);
        textArea.getDocument().addUndoableEditListener(undo);

        AdjustmentListener listener = new MyAdjustmentListener(scrollPane1, scrollPane2);
        scrollPane1.getHorizontalScrollBar().addAdjustmentListener(listener);
        scrollPane1.getVerticalScrollBar().addAdjustmentListener(listener);
        scrollPane2.getHorizontalScrollBar().addAdjustmentListener(listener);
        scrollPane2.getVerticalScrollBar().addAdjustmentListener(listener);
        ActionMap acciones = textArea.getActionMap();
        Action accionCopiar = acciones.get(DefaultEditorKit.copyAction);
        Action accionPegar = acciones.get(DefaultEditorKit.pasteAction);
        Action accionCortar = acciones.get(DefaultEditorKit.cutAction);
// servira para hacer el copiar cortar y pegar
        accionCopiar.putValue(Action.NAME, "Copiar");
        accionCopiar.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getAWTKeyStroke('C', Event.CTRL_MASK));

        accionCortar.putValue(Action.NAME, "Cortar");
        accionCortar.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getAWTKeyStroke('X', Event.CTRL_MASK));

        accionPegar.putValue(Action.NAME, "Pegar");
        accionPegar.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getAWTKeyStroke('V', Event.CTRL_MASK));

        menuEditar.add(accionCortar);
        menuEditar.add(accionCopiar);
        menuEditar.add(accionPegar);

    }   // llmara al metodo guarrdar
    public void guardar() {
        this.archivo = logica.guardarArchivo(this.archivo, textArea);
        if(archivo!=null){
            labelArchivo.setText(archivo.getName());
        }
    }
// para el guardar como
    public void guardarComo() {
        this.archivo = logica.guardarComoArchivo(textArea);
        labelArchivo.setText(archivo.getName());
    }
// para nuevo archivo
    public void nuevo() {
        logica.nuevoArchivo(archivo, textArea, this);

    }// borrara los datos de las tablas

    public void borrarDatos(){
        vaciarModelos(modeloTokens);
        vaciarModelos(modeloLexemas);
        modeloLista.clear();
    }
    // vaciara un modelo
    public void vaciarModelos(DefaultTableModel modelo) {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            if (modelo.getRowCount() > 0) {
                vaciarModelos(modelo);
            }
        }
    }
    // agregara errores
    public void agregarErrores(){
        LogicaArchivo logica = new LogicaArchivo();
        logica.agregarErroresTabla(modeloLista, tokensError);
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaErrores = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelPosicion = new javax.swing.JLabel();
        labelArchivo = new javax.swing.JLabel();
        scrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        scrollPane2 = new javax.swing.JScrollPane();
        numeracion = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        botonLexemas = new javax.swing.JButton();
        botonTokens = new javax.swing.JButton();
        textoPosicion = new javax.swing.JLabel();
        textoPosicion1 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        menuEditar = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Posicion:");
        desktopPane.add(jLabel1);
        jLabel1.setBounds(620, 90, 90, 17);
        desktopPane.add(jLabel2);
        jLabel2.setBounds(770, 30, 230, 0);

        jScrollPane1.setViewportView(listaErrores);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addContainerGap())
        );

        desktopPane.add(jPanel2);
        jPanel2.setBounds(590, 150, 460, 360);

        jLabel3.setText("ERRORES");
        desktopPane.add(jLabel3);
        jLabel3.setBounds(910, 130, 80, 17);
        desktopPane.add(jLabel4);
        jLabel4.setBounds(680, 30, 370, 0);
        desktopPane.add(jLabel5);
        jLabel5.setBounds(690, 30, 310, 0);

        jLabel6.setText("Archivo En Uso:");
        desktopPane.add(jLabel6);
        jLabel6.setBounds(620, 130, 110, 17);
        desktopPane.add(labelPosicion);
        labelPosicion.setBounds(720, 30, 220, 0);

        labelArchivo.setText("Ninguno");
        desktopPane.add(labelArchivo);
        labelArchivo.setBounds(750, 130, 100, 17);

        textArea.setColumns(20);
        textArea.setRows(5);
        textArea.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                textAreaCaretUpdate(evt);
            }
        });
        textArea.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                textAreaCaretPositionChanged(evt);
            }
        });
        scrollPane1.setViewportView(textArea);

        desktopPane.add(scrollPane1);
        scrollPane1.setBounds(80, 50, 420, 460);

        numeracion.setColumns(20);
        numeracion.setRows(5);
        numeracion.setEnabled(false);
        scrollPane2.setViewportView(numeracion);

        desktopPane.add(scrollPane2);
        scrollPane2.setBounds(10, 50, 50, 470);

        jButton1.setText("Validar Texto");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        desktopPane.add(jButton1);
        jButton1.setBounds(10, 10, 170, 29);

        botonLexemas.setText("MostrarLexemas");
        botonLexemas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLexemasActionPerformed(evt);
            }
        });
        desktopPane.add(botonLexemas);
        botonLexemas.setBounds(200, 10, 190, 29);

        botonTokens.setText("Mostrar Tokens");
        botonTokens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTokensActionPerformed(evt);
            }
        });
        desktopPane.add(botonTokens);
        botonTokens.setBounds(410, 10, 170, 29);
        desktopPane.add(textoPosicion);
        textoPosicion.setBounds(740, 90, 290, 0);

        textoPosicion1.setText("Aqui va la posicion");
        desktopPane.add(textoPosicion1);
        textoPosicion1.setBounds(750, 90, 250, 17);

        fileMenu.setMnemonic('f');
        fileMenu.setText("Documento");

        jMenuItem1.setText("Abrir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem1);

        jMenuItem4.setText("Guardar");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem4);

        jMenuItem5.setText("Guardar Como");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem5);

        jMenuItem6.setText("Nuevo");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem6);

        menuBar.add(fileMenu);

        menuEditar.setMnemonic('e');
        menuEditar.setText("Editar");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Deshacer");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menuEditar.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Rehacer");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        menuEditar.add(jMenuItem3);

        menuBar.add(menuEditar);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Acerca De");

        contentMenuItem.setMnemonic('c');
        contentMenuItem.setText("Acerca De");
        contentMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contentMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(contentMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1059, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        //crea un file chooser para escoger el archivo a evaluar
        JFileChooser buscadorArchivos = new JFileChooser();
        int opcion = buscadorArchivos.showOpenDialog(this);
        // si se acepta el archivo entra
        if (opcion == JFileChooser.APPROVE_OPTION) {
            String archivo = buscadorArchivos.getSelectedFile().getAbsolutePath();
            String archivo1 = buscadorArchivos.getSelectedFile().toString();
            // si se encuentra el archivo pide el tiempo en milisegundos
            System.out.println("Se ha encontrado el archivo: " + archivo1);
            try {

                this.archivoALeer = archivo;
                this.nombreArchivoALeer = archivo1;
                this.archivo = new File(archivoALeer);
                if (this.archivo.exists()) {
                    System.out.println("entro");
                    CargaArchivo carga = new CargaArchivo(this.archivo);
                    carga.leerArchivo(textArea);
                    carga.numerar(textArea, numeracion);
                    labelArchivo.setText(this.archivo.getName());
                }

            } catch (Exception e) {
                System.out.println("Hubo algun error");

            }
        } else if (opcion == JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null, "No se ha cargado ningun archivo");
        }
        try {
            // crea el archivo que se selecciono

        } catch (Exception e) {
            System.out.println("No se selecciono ningun archivo");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            undo.undo();
        } catch (Exception e) {

        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
            undo.redo();
        } catch (Exception e) {

        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        guardar();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        guardarComo();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void contentMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contentMenuItemActionPerformed
        JOptionPane.showMessageDialog(null, "Esta aplicacion fue creada bajo circunstancias muy extremas. Se creo en una laptop Dell Inspiron n9010\n"
                + "Ademas que se necesito de cuatro semanas para terminarlo. El dessarrollador es Juan Pablo Orizabal Gil, carné 201730318. Estudiante de\n "
                + "Ingenieria en Ciencias Y Sistemas del Centro Universitario de Occidente, que es una extencion de la Universidad de San Carlos de Guatemala \n"
                + "en el interior del pais. Curso el cuarto semestre de dicha ingenieria. 16-11-2018", "Datos del desarrollador", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_contentMenuItemActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        nuevo();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        borrarDatos();
        CargaArchivo carga = new CargaArchivo(this.archivo);
        LogicaArchivo logica = new LogicaArchivo();
        carga.numerar(textArea, numeracion);
        AnalisisLexico analisis = new AnalisisLexico();
        guardar();
        if(archivo!=null){// hara el analisis lexico
        Lexer lexer = analisis.crearObjetoLexer(this.archivo);
        if(lexer!=null){
            analisis.hacerAnalisisLexico(lexer);
            tokensValidos = analisis.obtenerTokens(lexer);
            tokensError = analisis.obtenerTokensError(lexer);
            if(tokensError.isEmpty()){
                logica.recuentoLexemas(tokensValidos, modeloLexemas);
                logica.recuentoToken(tokensValidos, modeloTokens);
            }else{
                agregarErrores();
            }
        }else{
            JOptionPane.showMessageDialog(null,"Hubo un problema con el analisis lexico");
        }// si no hay errores ira al sintactico
        int valor = modeloLista.getSize();
        if(valor==0){
            mostrarBotones();
            JOptionPane.showMessageDialog(null,"Sin errores en el analisis lexico, procedera el analisis sintactico");
            JOptionPane.showMessageDialog(null,"Por favor crea un archivo nuevo para la salida del analisis sintactico");
            File archivoSintactico = carga.seleccionarRuta();
            if(archivo!=null){
                AnalisisSintactico analisisSintactico =  new AnalisisSintactico(tokensValidos, 0, null, archivoSintactico,this.modeloLista);
                analisisSintactico.inicioAnalisis();
            }else{
                JOptionPane.showMessageDialog(null,"No se puede hacer nada no se selecciono archivo");
            }
        }else{
            ocultarBotones();
        }
        }else{
            JOptionPane.showMessageDialog(null,"No se hizo nada con el archivo");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void botonLexemasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLexemasActionPerformed
        TablasVisuales tabla = new TablasVisuales(this, false, modeloLexemas,"RECUENTO DE LEXEMAS");
        tabla.setVisible(true);
    }//GEN-LAST:event_botonLexemasActionPerformed

    private void botonTokensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTokensActionPerformed
        TablasVisuales tabla = new TablasVisuales(this,false, modeloTokens,"RECUENTO DE TOKENS");
        tabla.setVisible(true);
    }//GEN-LAST:event_botonTokensActionPerformed

    private void textAreaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_textAreaCaretUpdate
        int pos = evt.getDot();
        try {
           int fila= textArea.getLineOfOffset(pos) + 1;
           int columna = pos - textArea.getLineStartOffset(fila-1);
           textoPosicion1.setText("Línea: " + fila + " Columna: " + columna );
           textoPosicion1.setVisible(true);
       }

       catch( BadLocationException exc ){
           exc.printStackTrace();
       }
    }//GEN-LAST:event_textAreaCaretUpdate

    private void textAreaCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_textAreaCaretPositionChanged
        
    }//GEN-LAST:event_textAreaCaretPositionChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonLexemas;
    private javax.swing.JButton botonTokens;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelArchivo;
    private javax.swing.JLabel labelPosicion;
    private javax.swing.JList<String> listaErrores;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEditar;
    private javax.swing.JTextArea numeracion;
    private javax.swing.JScrollPane scrollPane1;
    private javax.swing.JScrollPane scrollPane2;
    private javax.swing.JTextArea textArea;
    private javax.swing.JLabel textoPosicion;
    private javax.swing.JLabel textoPosicion1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
