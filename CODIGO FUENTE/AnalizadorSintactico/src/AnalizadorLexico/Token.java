
package AnalizadorLexico;

public class Token {
    // poyo d etokens
    private String token;
    private String lexema;
    private int fila;
    private int columna;
    private int valor;
    private int veces;
// constructor
    public Token(String token, String lexema, int fila, int columna) {
        this.token = token;
        this.lexema = lexema;
        this.fila = fila;
        this.columna = columna;
    }
    // sobrecarga del constructor
    public Token(String lexema, String token){
        this.lexema=lexema;
        this.token=token;
        this.veces=1;
    }// getters y settesrs

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getVeces() {
        return veces;
    }

    public void setVeces(int veces) {
        this.veces = veces;
    }
    
    
    
}
