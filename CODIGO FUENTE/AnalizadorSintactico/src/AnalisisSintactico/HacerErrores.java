
package AnalisisSintactico;

import AnalizadorLexico.Token;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class HacerErrores {
    
    // serviara para reportar un error
    public  void reportarError(DefaultListModel modelo, Token token){
        String error = "Error Sintactico con el token: "+token.getToken()+" ,lexema: "+token.getLexema()+" , Fila: "+token.getFila()+" ,Columna: "+token.getColumna();
        modelo.addElement(error);// lo añadira a la lista
    }
    // mostrara los errores nada mas en consola
    public void mostrarErrores(DefaultListModel modelo){
        for (int i = 0; i < modelo.getSize(); i++) {
            System.out.println(modelo.getElementAt(i));
            
        }
    }
    // ira añadiendo valores a escribir
    public void agregarElementoEscribir(ArrayList<String> arreglo, String valor){
        arreglo.add(valor);
    }
    // escribira los elemento sde la estructura escribir
    public void escribirTexto(DefaultListModel modelo, ArrayList<String> arreglo,File archivo){
        if(modelo.getSize()<=0){
            System.out.println("Se puede escribir");
            try{// si no hubieron errores escribe
            File archivoReportes = archivo;
            FileWriter escritura = new FileWriter(archivoReportes, false);
            BufferedWriter escritor = new BufferedWriter(escritura);
                for (int i = 0; i < arreglo.size(); i++) {
                    escritor.write(arreglo.get(i)+"\n");
                }// agarra los valores para escribir
                
               escritor.flush();
               escritor.close();
               JOptionPane.showMessageDialog(null,"Sin errores, se puede escribir en el archivo de salida");
            }catch(IOException e){
                System.out.println("Hubo un problema");
            }
        }else{// si hubieron errores no se puede escribir
            JOptionPane.showMessageDialog(null,"Debido a que hay errores sintacticos no se puede escribir en el archivo de salida");
        }
    }
    // arregla si es un numero lo que hay que escribir
    public String arreglarNumero(Token token){
        String valor = token.getLexema();
        return valor;
    }// si es un identificador lo arregla para escribirle
    public String arreglarIdentificador(Token token){
        String valor = Integer.toString(token.getValor());
//        String valor = Integer.toString(token.getValor());
        return valor;
    }// si es un literal le quita las comillas para mandarlo
    public String arreglarLiteral(Token token){
        String valor = token.getLexema();
        String auxiliar = quitarComillasALaLinea(valor);
        return auxiliar;
    }
    
    public String quitarComillasALaLinea(String lineaTexto){
        int posicion1, posicion2;
        String texto;// quita las comillas
        // busca donde esta el ultimo parentesis
        posicion1 = lineaTexto.indexOf("\"");
        posicion2 = lineaTexto.lastIndexOf("\"");
        //guarda la posicion
        texto=lineaTexto.substring(posicion1+1, posicion2);
        // genera el arreglo 
        System.out.println(texto);
        return texto;
        
    }
}
