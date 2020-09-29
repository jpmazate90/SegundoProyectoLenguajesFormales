
package AnalizadorLexico;

import Logica.CargaArchivo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalisisLexico {
    
    public AnalisisLexico(){
        
    }
    // clase que servira para añadir coasas del lexico
    public Lexer crearObjetoLexer(File archivo){
        Lexer lexer;
        try {// creara un lexer
            Reader reader = new BufferedReader(new FileReader(archivo));
            lexer = new Lexer(reader);
                    return lexer;
        } catch (FileNotFoundException ex) {
           ex.printStackTrace();
        }
        return null;
    }
    // hara el analisis lexico
    public void hacerAnalisisLexico(Lexer lexer){
        try{
            lexer.yylex();
        }catch(IOException f){
            f.printStackTrace();
        }
    }
    
    // obtendra los tokens
    public ArrayList<Token> obtenerTokens(Lexer lexer){
        ArrayList<Token> tokens=null;
        try {
                tokens = lexer.getTokens();
                return tokens;
            }catch(Exception e){
                return null;
            }
            
        }// obtendra tokens de error
    public ArrayList<Token> obtenerTokensError(Lexer lexer){
        ArrayList<Token> tokens=null;
        try {
                tokens = lexer.getTokensError();
                return tokens;
            }catch(Exception e){
                return null;
            }
           
    }
}
