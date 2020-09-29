
package analizadorsintactico;

import AnalisisSintactico.AnalisisSintactico;
import AnalisisSintactico.Constantes;
import AnalisisSintactico.Pila;
import AnalizadorLexico.AnalisisLexico;
import AnalizadorLexico.Lexer;
import AnalizadorLexico.Token;
import GUI.MenuPrincipal;
import Logica.CargaArchivo;
import Logica.LogicaArchivo;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class AnalizadorSintactico {

    public static void main(String[] args) {

        
        
        
        
        
        MenuPrincipal menu = new MenuPrincipal();
        menu.setExtendedState(Frame.MAXIMIZED_BOTH);
        menu.setVisible(true);
        
    }
    
}
// ESTRUCTURA REPETIR

//nuevoToken = new Token(c.getTOKEN_REPETIR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_NUMERO(),"2", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_INICIAR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_ESCRIBIR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_NUMERO(),"1234", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_ESCRIBIR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_LITERAL(),"hola amigos", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);
// ESTRUCTURA BOOLEANA
 //                nuevoToken = new Token(c.getTOKEN_SI(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FALSO(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_ENTONCES(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_ESCRIBIR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_NUMERO(),"200", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_ESCRIBIR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_LITERAL(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);

//ESCRIBIRRRRRRRRRRRRRRRRRRRRRRR

//                 nuevoToken = new Token(c.getTOKEN_ESCRIBIR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_LITERAL(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);


// ASIGNACIOOOOOOOOOOOOOOON

//
//                nuevoToken = new Token(c.getPARENTESIS_ABIERTO(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_IDENTIFICADOR(),"2", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getSIGNO_MAS(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_NUMERO(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getPARENTESIS_CERRADO(),"1234", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getSIGNO_MULTIPLICACION(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_NUMERO(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_NUMERO(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                

//EXPRESIOOOOOOOOOOOOOOON

//nuevoToken = new Token(c.getTOKEN_IDENTIFICADOR(),"IDEN", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getSIGNO_IGUAL(),"=", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getPARENTESIS_ABIERTO(),"(", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_NUMERO(),"3", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getPARENTESIS_CERRADO(),")", 0, 0);
//                tokens.add(nuevoToken);                
//                nuevoToken = new Token(c.getSIGNO_MULTIPLICACION(),"*", 0, 0);
//                tokens.add(nuevoToken);
//                 nuevoToken = new Token(c.getPARENTESIS_ABIERTO(),"(", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_NUMERO(),"4", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getSIGNO_MULTIPLICACION(),"*", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_NUMERO(),"4", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getPARENTESIS_CERRADO(),")", 0, 0);
//                tokens.add(nuevoToken); 
//                nuevoToken = new Token(c.getTOKEN_FIN(),"FIN", 0, 0);
//                tokens.add(nuevoToken);
//                ArrayList<Token> tokens = new ArrayList<>();
//                Constantes c = new Constantes();
//                Token nuevoToken;
//                nuevoToken = new Token(c.getTOKEN_REPETIR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_NUMERO(),"1", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_INICIAR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_ESCRIBIR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_NUMERO(),"1234", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_ESCRIBIR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_LITERAL(),"\"hola amigos\"", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                
//                nuevoToken = new Token(c.getTOKEN_REPETIR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                
//                nuevoToken = new Token(c.getTOKEN_NUMERO(),"3", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_INICIAR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                
//                nuevoToken = new Token(c.getTOKEN_ESCRIBIR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_NUMERO(),"200", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_ESCRIBIR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_LITERAL(),"\"hola tocayos\"", 0, 0);
//                tokens.add(nuevoToken);
//                
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                
//                nuevoToken = new Token(c.getTOKEN_ESCRIBIR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_LITERAL(),"\"babaa\"", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                
//                nuevoToken = new Token(c.getTOKEN_SI(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_VERDADERO(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_ENTONCES(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_ESCRIBIR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_NUMERO(),"200", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_ESCRIBIR(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_LITERAL(),"\"escribiendo en un condicional\"", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                nuevoToken = new Token(c.getTOKEN_FIN(),"a", 0, 0);
//                tokens.add(nuevoToken);
//                 
//                
               
                
                
//                File archivo = new File("archivoPrueba");
//                DefaultListModel modelo = new DefaultListModel();
//                AnalisisSintactico analisis =  new AnalisisSintactico(tokens, 0, null,archivo,modelo);
//                analisis.inicioAnalisis();
        
        