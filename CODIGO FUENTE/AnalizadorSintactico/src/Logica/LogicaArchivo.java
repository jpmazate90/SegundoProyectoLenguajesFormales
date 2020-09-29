package Logica;

import AnalizadorLexico.Token;
import GUI.MenuPrincipal;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class LogicaArchivo {
// metodo de guardar archivo

    public File guardarArchivo(File archivo, JTextArea area) {
        try {// si el archivo existe 
            if (archivo.exists()) {
                // guarda datos de la lista
                try (FileWriter out = new FileWriter(archivo, false); BufferedWriter archivoSalida = new BufferedWriter(out);) {
                    archivoSalida.write(area.getText());
                    archivoSalida.newLine();// agarra la linea y la mete al archivo que se selecciono
                    archivoSalida.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Se guardo correctamente");
                return archivo;
            } else {// si no existe el archivo dice que no se puede
                JOptionPane.showMessageDialog(null, "No funciona");
                return null;
            }
        } catch (Exception e) {
            return guardarComoArchivo(area);
        }
    }

    // archivo guardar como 
    public File guardarComoArchivo(JTextArea area) {
        JFileChooser guardarComo = new JFileChooser();
        guardarComo.setApproveButtonText("Guardar");
        int opcion = guardarComo.showSaveDialog(null);// agarra los valores del text area
        if (opcion == JFileChooser.APPROVE_OPTION) {
        File archivo = new File(guardarComo.getSelectedFile() + ".txt");// lo guarda como punto txt
        try (FileWriter out = new FileWriter(archivo, false); BufferedWriter archivoSalida = new BufferedWriter(out);) {
            archivoSalida.write(area.getText());// agarra el texto y lo escribe dentro del archivo nuevo
            archivoSalida.newLine();
            archivoSalida.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Se guardo correctamente");
        return archivo;

        }else if (opcion == JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null, "No se ha cargado ningun archivo");
            return null;
        }
        return null;
    }
// crea un nuevo archivo

    public void nuevoArchivo(File archivoViejo, JTextArea area, MenuPrincipal menu) {
        boolean noSeModifico = verificarArchivo(area, archivoViejo);
        boolean bandera = true;
        if (noSeModifico == false) {// pregunta si desea guardar los cambios 
            int opcion = JOptionPane.showConfirmDialog(null, "¿Desea guardar los cambios?");

            switch (opcion) {
                case 0:// si dice que si se guarda correctamente
                    guardarArchivo(archivoViejo, area);
                    break;
                case 1:// si no dice que no 
                    break;
                case 2:// si lo cancela no hace nada
                    bandera = false;
                    break;
            }
        }// si la bandera es verdadera abre un nuevo menu principal
        if (bandera == true) {
            menu.setVisible(false);
            MenuPrincipal nuevoMenu = new MenuPrincipal();
            nuevoMenu.setVisible(true);
        } else {// se cancela la operacion
            JOptionPane.showMessageDialog(null, "Se cancelo la creacion de un nuevo documento");
        }

    }
// verifica si hay cambios en el archivo

    public boolean verificarArchivo(JTextArea area, File archivo) {
        String[] lineaTexto = area.getText().split("\n");
        try {// separa el texto por lineas
            FileReader archivo1 = new FileReader(archivo);
            BufferedReader archivo2 = new BufferedReader(archivo1);
            String auxiliar = archivo2.readLine();// lee una linea del archivo de texto
            boolean bandera = true;
            for (int i = 0; i < lineaTexto.length; i++) {
                String[] auxiliarEspacio = auxiliar.split(" ");// separa por espacios el vector
                String[] palabraTexto = lineaTexto[i].split(" ");
                // recorre una palabra
                for (int j = 0; j < auxiliarEspacio.length; j++) {// crea un vector de palabras
                    char[] vectorAuxiliar = auxiliarEspacio[j].toCharArray();
                    char[] vectorPalabra = palabraTexto[j].toCharArray();
                    for (int k = 0; k < vectorAuxiliar.length; k++) {// compara caracter a caracter
                        if (vectorAuxiliar[k] != vectorPalabra[k] || palabraTexto[j].length() != auxiliarEspacio[j].length()) {
                            bandera = false;
                            break;// cambia la bandera y corta
                        }// si la bandera ya es falsa corta
                        if (bandera == false) {
                            break;
                        }
                    }
                }// lee una linea
                auxiliar = archivo2.readLine();
            }// regresa la bandera
            return bandera;
        } catch (Exception e) {
            return false;
        }
    }

    public void agregarErroresTabla(DefaultListModel modeloLista, ArrayList<Token> tokensError) {
        for (int i = 0; i < tokensError.size(); i++) {
            // AGREGA el erro que exista a la lista en una linea 
            String error = " Error lexico en la fila: " + tokensError.get(i).getFila() + " ,Columna: " + tokensError.get(i).getColumna() + " ,Lexema: " + tokensError.get(i).getLexema();
            modeloLista.addElement(error);// indica el error en la posicion que es y lo agrega a la lista
        }

    }
    
    // hace el recuento de lexemas
    public void recuentoLexemas(ArrayList<Token> lexemas, DefaultTableModel tablaLexemas) {
        ArrayList<Token> modelo = new ArrayList<Token>();
        for (int i = 0; i < lexemas.size(); i++) {// agarra los lexemas que ya existen
            contarLexemas(lexemas.get(i).getLexema(), lexemas.get(i).getToken(), modelo);
        }// los añade a la tabla que se le mando 
        for (int i = 0; i < modelo.size(); i++) {
            String hola = modelo.get(i).getToken();
            String datos[] = new String[3];// los añade a la tabla que se le mando 
            datos[0] = modelo.get(i).getLexema();
            datos[1] = modelo.get(i).getToken();// los añade a la tabla que se le mando 
            datos[2] = Integer.toString(modelo.get(i).getVeces());
            tablaLexemas.addRow(datos);// los añade a la tabla que se le mando 
        }

    }
// cuenta los lexemas
    public void contarLexemas(String cadena, String tokenNombre, ArrayList<Token> lexema) {
        boolean banderaEsPalabraNueva = true;
        boolean banderaSiCumpleLexema = true;
        String nombreToken = tokenNombre;
        Token token;// crea un objeto modelo token
        for (int i = 0; i < lexema.size(); i++) {// agarra el lexema en la posicion 1
            if (cadena.length() == lexema.get(i).getLexema().length()) {
                for (int j = 0; j < lexema.get(i).getLexema().length(); j++) {
                    banderaSiCumpleLexema = true;//verifica si el lexema se repite o no
                    if (cadena.charAt(j) != lexema.get(i).getLexema().charAt(j)) {
                        banderaSiCumpleLexema = false;
                        break;
                    }
                }// si el lexema se repite solo agrega una unidad a las veces repetida
                if (banderaSiCumpleLexema == true) {
                    lexema.get(i).setVeces(lexema.get(i).getVeces() + 1);
                    banderaEsPalabraNueva = false;
                }
            }
        }// si no existe se crea un elemento del vector para luego ya empezar a sumarle.
        if (banderaEsPalabraNueva == true) {
            token = new Token(cadena, nombreToken);
            lexema.add(token);
        }

    }
// el recento de tokens no es mas que agarrar cada posicion del arreglo de modelos tokens
    public void recuentoToken(ArrayList<Token> modelo, DefaultTableModel tablaTokens) {
        for (int i = 0; i < modelo.size(); i++) {
            String datos[] = new String[4];// CON CADA posicion los va agregando a una tabla
            datos[0] = modelo.get(i).getToken();
            datos[1] = modelo.get(i).getLexema();// CON CADA posicion los va agregando a una tabla
            datos[2] = Integer.toString(modelo.get(i).getFila());
            datos[3] = Integer.toString(modelo.get(i).getColumna());// CON CADA posicion los va agregando a una tabla
            tablaTokens.addRow(datos);
        }// CON CADA posicion los va agregando a una tabla
    }

}
