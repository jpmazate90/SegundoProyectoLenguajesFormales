package AnalisisSintactico;

import AnalizadorLexico.Token;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author jpmazate
 */
public class AnalisisSintactico {

    // variables privadas que serviran para el analizador sintactico
    private ArrayList<Token> tokens;
    private int indiceEntrada;
    private Pila pila;
    private Constantes constantes = new Constantes();
    private ArrayList<AnalisisSintactico> analisis = new ArrayList<AnalisisSintactico>();
    private File archivoSalida;
    private DefaultListModel listaErrores;
    private HacerErrores errores = new HacerErrores();
    private ArrayList<String> cosasEscribir = new ArrayList<>();
    private ArrayList<Object> objetosRepetir = new ArrayList<>();
// constructor de la clase que servira para inicializar el analisis

    public AnalisisSintactico(ArrayList<Token> tokens, int indiceEntrada, Pila pila, File archivoSalida, DefaultListModel listaErrores) {
        this.listaErrores = listaErrores;
        this.tokens = new ArrayList<>(tokens);
        this.archivoSalida = archivoSalida;
        if (pila == null) {// si la pila es nula del parametr inicializa la pila nueva
            this.pila = new Pila();
        } else {
            this.pila = new Pila();// si no le asigna los valores que ya tiene
            this.pila.setPila(pila.getPila());
        }
    }

    public void inicioAnalisis() {
        q0();// el analisis empezara en q0
    }

    public void q0() {// si el indice de entrada no es el mismo que el de los tokens
        if (indiceEntrada != tokens.size()) {//entra
            if (this.pila.empty()) {//  y pone la estructura global
                this.pila.push(constantes.getESTRUCTURA_GLOBAL());
                q1();// se mueve a q1
            } else {// dice que no se puede hacer nada
                System.out.println("no se puede hacer nada");
            }
        } else {// si el indice de entrada es igual al de los tokens termino de leer 
            System.out.println("Ya se termino de leer");
            errores.mostrarErrores(listaErrores);// si ya termino de leer entonces entra
            errores.escribirTexto(listaErrores, cosasEscribir, archivoSalida);// y escribe si hay errores
            JOptionPane.showMessageDialog(null, "Ha finalizado el analisis sintactico");// y si no hay escribre el texto
        }

    }

    public void q1() {// en q1 lo que hace es ver las derivaciones de estructura global
        if (tokens.get(indiceEntrada) != null) {// si hay donde ir entra
            String objeto = this.pila.peek().toString();
            String tokenActual = tokens.get(indiceEntrada).getToken();
            if (objeto.equals(constantes.getESTRUCTURA_GLOBAL())) {
                estadosEstructuraGlobal();// y manda a estructura global
            }
        }
    }

    public void estadosEstructuraGlobal() {
        ElementosAuxiliares elementos;
        AnalisisSintactico analisis1;// aqui empezara a evluar los posibles caminos
        pila.quitarPoner(constantes.getESTRUCTURA1());// empezara con estructura 1
        elementos = estadoEstructura1(this.pila, this.indiceEntrada, true, false);
        if (elementos != null) {// si cumplio entra
            System.out.println("cumplio el etado 1");
            pila.setPila(elementos.getPila().getPila());// asigna los nuevos valores 
            this.indiceEntrada = elementos.getIndiceActual();
            q0();// y empieza de nuevo el analisis
        } else {// si no cumplio entra a estructrua 3 y evalua
            pila.quitarPoner(constantes.getESTRUCTURA3());
            elementos = estadoEstructura3(this.pila, this.indiceEntrada);
            if (elementos != null) {// si cumplio entra
                System.out.println("Cumplio el estado 3");
                pila.setPila(elementos.getPila().getPila());
                this.indiceEntrada = elementos.getIndiceActual();
                q0();// asigna los vlaores nuevos y empieza en q0 otra vez
            } else {// si no cumplio pone estructura 2
                pila.quitarPoner(constantes.getESTRUCTURA2());
                elementos = estadoEstructura2(this.pila, this.indiceEntrada);
                if (elementos != null) {// si cumplio entra
                    System.out.println("cumplio el estado 2");
                    pila.setPila(elementos.getPila().getPila());
                    this.indiceEntrada = elementos.getIndiceActual();
                    q0();// y reinicia el analisis a q0
                } else {
                    pila.quitarPoner(constantes.getESTRUCTURA4());
                    elementos = estadoEstructura4(this.pila, this.indiceEntrada);
                    if (elementos != null) {// si no cumplio entra a estructura 4
                        System.out.println("cumplio la estructura 4");
                        pila.setPila(elementos.getPila().getPila());// y asigna valores
                        this.indiceEntrada = elementos.getIndiceActual();
                        System.out.println(indiceEntrada + ": es el indice");
                        q0();// reinicia el analisis
                    } else {// si no cumplio entra a la 5 
//                        pila.quitarPoner(constantes.getESTRUCTURA5());
//                        elementos = estadoEstructura5(this.pila, this.indiceEntrada);
//                        if (elementos != null) {// si cumplio la 5
//                            System.out.println("cumplio la estructura 5");
//                            pila.setPila(elementos.getPila().getPila());
//                            this.indiceEntrada = elementos.getIndiceActual();
//                            q0();// evaluara el 5
//                        } else { 
                        errores.reportarError(listaErrores, tokens.get(indiceEntrada));
                        indiceEntrada++;// si entro aqui significa que no pudo hacer nada con el token actual
                        pila.clear();// entonces quita el error avanza y limpia la pila
                        q0();
//                        }
                    }
                }
            }
        }

    }
// en estructura 1 

    public ElementosAuxiliares estadoEstructura1(Pila pila, int indiceActual, boolean hayQueEscribir, boolean esGuardar) {
        ElementosAuxiliares elementos;
        Pila pilaNueva = new Pila();// crea una nueva pila
        int indiceNuevo = indiceActual;// crea un nuevo indice
        pilaNueva.setPila(pila.getPila());// le asigna los valores de la pila
        pilaNueva.pop();// quita la estructura 1 y mete la derivacion de estructura 1
        pilaNueva.push(constantes.getTOKEN_FIN());
        pilaNueva.push(constantes.getTOKEN());
        pilaNueva.push(constantes.getTOKEN_ESCRIBIR());
        String objeto = pilaNueva.peek().toString();
        try {// evalua los tokens de escribir
            if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                indiceNuevo++;// si hace match entra y avanza
                pilaNueva.pop();// quita el token de la pila
                objeto = pilaNueva.peek().toString();// y el siguiente es token
                if (objeto.equals(constantes.getTOKEN())) {// entonces hay que derivar token
                    elementos = derivacionToken(pilaNueva, indiceNuevo);// y si regresan sin ser null significa que si se derivo bien
                    if (elementos != null) {// entra
                        pilaNueva.setPila(elementos.getPila().getPila());
                        indiceNuevo = elementos.getIndiceActual();// asigna los nuevos valores
                        objeto = pilaNueva.peek().toString();
                        if (objeto.equals(constantes.getTOKEN_FIN()) && tokens.get(indiceNuevo).getToken().equals(objeto)) {
                            pilaNueva.pop();// si hace match ahora el fin de pila y tokens entra
                            indiceNuevo++;// y crea los elementos auxiliares
                            elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                            if (esGuardar == false) {// si el parametro de guardar es false
                                hacerEscritura(hayQueEscribir, indiceNuevo);// significa que hay que ejecutarlo
                            } else {// si no hay que guardarlo
                                guardarObjetosRepetir(indiceNuevo);
                            }// si no retorna los elementos
                            return elementos;
                        } else {
                            return null;
                        }

                    } else {
                        return null;
                    }
                }
            }// excepciones
        } catch (IndexOutOfBoundsException e) {
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }
// estructura 3

    public ElementosAuxiliares estadoEstructura3(Pila pila, int indiceActual) {
        try {
            ElementosAuxiliares elementos;
            Pila pilaNueva = new Pila();
            int indiceNuevo = indiceActual;
            pilaNueva.setPila(pila.getPila());
            pilaNueva.pop();// quita la estructura 3 de la pila y pone la derivacion
            pilaNueva.push(constantes.getVER_ESTRUCTURAS());
            pilaNueva.push(constantes.getTOKEN_ENTONCES());
            pilaNueva.push(constantes.getBOOLEAN());
            pilaNueva.push(constantes.getTOKEN_SI());
            String objeto = pilaNueva.peek().toString();
            if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                indiceNuevo++;// si hace match el si entra
                pilaNueva.pop();// lo quita
                objeto = pilaNueva.peek().toString();
                if (objeto.equals(constantes.getBOOLEAN())) {// hay que hacer la derivacion de boolean ahora
                    elementos = derivacionBoolean(pilaNueva, indiceNuevo);
                    if (elementos != null) {// si se derivo entra
                        pilaNueva.setPila(elementos.getPila().getPila());
                        indiceNuevo = elementos.getIndiceActual();
                        boolean queEs = verificarTokenBooleano(indiceNuevo);// y mira que tipo de booleano es si verdadero o falso
                        objeto = pilaNueva.peek().toString();// si hace match
                        if (objeto.equals(constantes.getTOKEN_ENTONCES()) && tokens.get(indiceNuevo).getToken().equals(objeto)) {
                            pilaNueva.pop();// quita el entonces si hace match
                            indiceNuevo++;
                            objeto = pilaNueva.peek().toString();
                            if (objeto.equals(constantes.getVER_ESTRUCTURAS())) {// y ahora toca derivar el no terminal
                                elementos = derivacionVerEstructuras(pilaNueva, indiceNuevo, queEs, false);
                                if (elementos != null) {// si cumplio algo entra
                                    pilaNueva.setPila(elementos.getPila().getPila());
                                    indiceNuevo = elementos.getIndiceActual();
                                    return elementos;// y regresa los valores
                                } else {
                                    return null;
                                }
                            }
                        } else {
                            return null;
                        }

                    } else {
                        return null;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;

    }
// estructura 2

    public ElementosAuxiliares estadoEstructura2(Pila pila, int indiceActual) {
        try {
            objetosRepetir.clear();
            ElementosAuxiliares elementos;
            Pila pilaNueva = new Pila();
            int indiceNuevo = indiceActual;
            pilaNueva.setPila(pila.getPila());
            pilaNueva.pop();// quita y pone la derivacion de estructura 2
            pilaNueva.push(constantes.getVER_ESTRUCTURAS());
            pilaNueva.push(constantes.getTOKEN_INICIAR());
            pilaNueva.push(constantes.getTOKEN2());
            pilaNueva.push(constantes.getTOKEN_REPETIR());
            String objeto = pilaNueva.peek().toString();
            if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                indiceNuevo++;// si hace match repetir entra
                pilaNueva.pop();
                objeto = pilaNueva.peek().toString();
                if (objeto.equals(constantes.getTOKEN2())) {// ahora toc ver la derivacion de token 2
                    elementos = derivacionToken2(pilaNueva, indiceNuevo);
                    if (elementos != null) {// si cumplio entra y asigna nuevos valores
                        pilaNueva.setPila(elementos.getPila().getPila());
                        indiceNuevo = elementos.getIndiceActual();
                        int vecesARepetir = verVecesRepetir(indiceNuevo);// mira cuantas veces hay que repetirlo
                        objeto = pilaNueva.peek().toString();
                        if (vecesARepetir >= 0) {// SI el nmero es un entero positivo entra
                            if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                                indiceNuevo++;// si 
                                pilaNueva.pop();// si hace match el siguiente entra
                                objeto = pilaNueva.peek().toString();
                                if (objeto.equals(constantes.getVER_ESTRUCTURAS())) {
                                    elementos = derivacionVerEstructuras(pilaNueva, indiceNuevo, true, true);
                                    if (elementos != null) {// deriva estructuras ysi cumple entra
                                        pilaNueva.setPila(elementos.getPila().getPila());
                                        indiceNuevo = elementos.getIndiceActual();
                                        escribirRepetir(vecesARepetir);// entonces hay que escribir lo que almaceno
                                        return elementos;
                                    } else {
                                        return null;
                                    }
                                }
                            } else {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }
// estructura 4

    public ElementosAuxiliares estadoEstructura4(Pila pila, int indiceActual) {
        try {
            ElementosAuxiliares elementos;
            Pila pilaNueva = new Pila();
            String objeto;
            int indiceNuevo = indiceActual;
            pilaNueva.setPila(pila.getPila());
            pilaNueva.pop();// quita estructura 4 y asigna los valores de la derivacion a la pila
            pilaNueva.push(constantes.getTOKEN_FIN());
            pilaNueva.push(constantes.getESTRUCTURA5());
            pilaNueva.push(constantes.getSIGNO_IGUAL());
            pilaNueva.push(constantes.getTOKEN_IDENTIFICADOR());
            objeto = pilaNueva.peek().toString();
            if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                indiceNuevo++;// si hace match un identificador entra
                pilaNueva.pop();
                objeto = pilaNueva.peek().toString();
                if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                    indiceNuevo++;// ahora hay que derivar el signo igual
                    pilaNueva.pop();// estructura 5
                    elementos = estadoEstructura5(pilaNueva, indiceNuevo);
                    if (elementos != null) {// si la derivacoin no es nulla entra
                        pilaNueva.setPila(elementos.getPila().getPila());
                        indiceNuevo = elementos.getIndiceActual();
                        objeto = pilaNueva.peek().toString();// asigna valores
                        if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                            pilaNueva.pop();// si el final es un token fin entra
                            indiceNuevo++;// y retorna el valor
                            elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                            asignarValores(indiceNuevo);
                            return elementos;
                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;

    }
// la derivacion de token 2

    public ElementosAuxiliares derivacionToken2(Pila pila, int indiceActual) {
        ElementosAuxiliares elementos;
        Pila pilaNueva = new Pila();
        int indiceNuevo = indiceActual;
        pilaNueva.setPila(pila.getPila());
        pilaNueva.quitarPoner(constantes.getTOKEN_NUMERO());//quita y prueba si es un numero
        String objeto = tokens.get(indiceNuevo).getToken();
        if (objeto.equals(pilaNueva.peek().toString())) {
            int valorLexema = Integer.parseInt(tokens.get(indiceNuevo).getLexema());
            if (valorLexema >= 0) {// si cumple entra y que sea positivo
                System.out.println("El elemento es mayor o igual a 0");
                pilaNueva.pop();
                indiceNuevo++;
                elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                return elementos;
            } else {// si es menor a 0 no hace nada
                System.out.println("El elemento es menor a 0");
                return null;
            }
        } else {
            pilaNueva.quitarPoner(constantes.getTOKEN_IDENTIFICADOR());
            if (objeto.equals(pilaNueva.peek().toString())) {
                pilaNueva.pop();//  si es un identificador entra
                indiceNuevo++;
                elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                return elementos;
            }
        }

        return null;
    }
// mira cuantas veces hay que repetir el valor

    public int verVecesRepetir(int indice) {
        try {
            int veces = -1;// no es lecal sale
            if(tokens.get(indice-1).getToken().equals(constantes.getTOKEN_NUMERO())){
                String lexema = tokens.get(indice - 1).getLexema();
                veces = Integer.parseInt(lexema);
                return veces;
            }else if(tokens.get(indice-1).getToken().equals(constantes.getTOKEN_IDENTIFICADOR())){
                veces = tokens.get(indice - 1).getValor();
                return veces;
            }
            
            
        } catch (NumberFormatException e) {
            return -1;
        }
        return -1;
    }
// para derivar un booleana

    public boolean verificarTokenBooleano(int indice) {
        String token = tokens.get(indice - 1).getToken();
        if (token.equals(constantes.getTOKEN_VERDADERO())) {// prueba con un verdadero
            return true;// si no con un falso
        } else if (token.equals(constantes.getTOKEN_FALSO())) {
            return false;
        }
        return false;
    }
// hace escritura

    public void hacerEscritura(boolean hayQueEscribir, int indice) {
        if (hayQueEscribir == true) {// si el parametro es verdadero significa que si se puede escribir
            String token = tokens.get(indice - 2).getToken();
            String lexema = tokens.get(indice - 2).getLexema();
            String cadenaEscribir;
            Token tokenAEscribir = tokens.get(indice - 2);// prubea si es u nidentificaodr
            if (token.equals(constantes.getTOKEN_NUMERO())) {// y escribe el lexema
                System.out.println("Hay que escribir unicamente el lexema ");
                cadenaEscribir = errores.arreglarNumero(tokenAEscribir);
                cosasEscribir.add(cadenaEscribir);
            } else if (token.equals(constantes.getTOKEN_LITERAL())) {// si no prueba con un literal
                System.out.println("Hay que escribir el literal sin comillas");
                cadenaEscribir = errores.arreglarLiteral(tokenAEscribir);
                cosasEscribir.add(cadenaEscribir);
            } else {// por ultimo prueba con un identificador
                System.out.println("Hay que escribir el valor del identificador");
                cadenaEscribir = errores.arreglarIdentificador(tokenAEscribir);
                cosasEscribir.add(cadenaEscribir);
            }// y almacena en un arra list
        } else {// s es falso no hace nada
            System.out.println("No hay que escribir nada");
        }
    }
// derivar un token

    public ElementosAuxiliares derivacionToken(Pila pila, int indiceActual) {
        ElementosAuxiliares elementos;
        Pila pilaNueva = new Pila();
        int indiceNuevo = indiceActual;
        pilaNueva.setPila(pila.getPila());// prueba primeiro si es un numero
        pilaNueva.quitarPoner(constantes.getTOKEN_NUMERO());
        String objeto = tokens.get(indiceNuevo).getToken();
        if (objeto.equals(pilaNueva.peek().toString())) {
            pilaNueva.pop();
            indiceNuevo++;// si hace match quita de la pila y añade nuevos valores
            elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
            return elementos;
        } else {// si no prubea con un identificador
            pilaNueva.quitarPoner(constantes.getTOKEN_IDENTIFICADOR());
            if (objeto.equals(pilaNueva.peek().toString())) {
                pilaNueva.pop();
                indiceNuevo++;
                elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                return elementos;// y retorna el valor
            } else {// si no con un literal
                pilaNueva.quitarPoner(constantes.getTOKEN_LITERAL());
                if (objeto.equals(pilaNueva.peek().toString())) {
                    pilaNueva.pop();// y retorna el valor
                    indiceNuevo++;
                    elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                    return elementos;
                }
            }
        }

        return null;
    }
// derivacio boolean

    public ElementosAuxiliares derivacionBoolean(Pila pila, int indiceActual) {
        ElementosAuxiliares elementos;
        Pila pilaNueva = new Pila();
        int indiceNuevo = indiceActual;
        pilaNueva.setPila(pila.getPila());// mira si se puede derivar en un verdadero
        pilaNueva.quitarPoner(constantes.getTOKEN_VERDADERO());
        String objeto = tokens.get(indiceNuevo).getToken();
        if (objeto.equals(pilaNueva.peek().toString())) {
            pilaNueva.pop();
            indiceNuevo++;// si hace match lo retorna
            elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
            return elementos;
        } else {// si es un falso hace match y retorna
            pilaNueva.quitarPoner(constantes.getTOKEN_FALSO());
            if (objeto.equals(pilaNueva.peek().toString())) {
                pilaNueva.pop();
                indiceNuevo++;
                elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                return elementos;
            }
        }
// si no es ninguno retorna null
        return null;
    }
// para derivar estructuras 1

    public ElementosAuxiliares derivacionVerEstructuras(Pila pila, int indiceActual, boolean hacerEscritura, boolean esGuardar) {
        ElementosAuxiliares elementos;
        Pila pilaNueva = new Pila();
        int indiceNuevo = indiceActual;
        pilaNueva.setPila(pila.getPila());// primero mira si no hay nada entonces mete un token fin a la pila
        pilaNueva.quitarPoner(constantes.getTOKEN_FIN());// si hace match entra
        String objeto = tokens.get(indiceNuevo).getToken();
        if (objeto.equals(pilaNueva.peek().toString())) {
            pilaNueva.pop();// y reronta el valor
            indiceNuevo++;
            elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
            return elementos;
        } else {// si no mete a la pila estructura 1 y otra vez el no terminal ver estructuras
            pilaNueva.quitarPoner(constantes.getVER_ESTRUCTURAS());
            pilaNueva.push(constantes.getESTRUCTURA1());// hace la derivacion de estructura 1
            elementos = estadoEstructura1(pilaNueva, indiceNuevo, hacerEscritura, esGuardar);
            if (elementos != null) {// y si cumple
                pilaNueva.setPila(elementos.getPila().getPila());
                indiceNuevo = elementos.getIndiceActual();// se llama recursivamente con la nueva pila
                return derivacionVerEstructuras(pilaNueva, indiceNuevo, hacerEscritura, esGuardar);
            } else {// si no cumple retorna null
                return null;
            }
        }
    }
// para guardar objetos a del repetir

    public void guardarObjetosRepetir(int indice) {
        String valorAEscribir;// agarra el valor
        Token tokenEvaluar = tokens.get(indice - 2);// seria de agregar lo correspondiente al token que es y no guardar el token completo
        if (tokenEvaluar.getToken().equals(constantes.getTOKEN_NUMERO())) {
            valorAEscribir = errores.arreglarNumero(tokenEvaluar);
            objetosRepetir.add(valorAEscribir);// si es un numero guardada su lexema
        } else if (tokenEvaluar.getToken().equals(constantes.getTOKEN_LITERAL())) {
            valorAEscribir = errores.arreglarLiteral(tokenEvaluar);// aqui igual sin comillas
            objetosRepetir.add(valorAEscribir);
        } else {// aqui guardara el identificador
            valorAEscribir = errores.arreglarIdentificador(tokenEvaluar);
            objetosRepetir.add(valorAEscribir);
        }
    }//y añadira a un arreglo 

    public void escribirRepetir(int veces) {
        System.out.println("hay que escribir " + veces);
        for (int i = 0; i < veces; i++) {
            for (int j = 0; j < objetosRepetir.size(); j++) {
                cosasEscribir.add(objetosRepetir.get(j).toString());
// aqui al finalizar el repetir añadira a los objetos a escribir
            }

        }

    }
// derivacion de estructura 5

    public ElementosAuxiliares estadoEstructura5(Pila pila, int indiceActual) {
        try {
            boolean falloCamino = false;
            String objeto;
            ElementosAuxiliares elementos;
            Pila pilaNueva = new Pila();
            int indiceNuevo = indiceActual;
            pilaNueva.setPila(pila.getPila());// primero prueba a derivar t+s
            pilaNueva.quitarPoner(constantes.getS());
            pilaNueva.push(constantes.getSIGNO_MAS());
            pilaNueva.push(constantes.getT());
            elementos = derivacionDeT(pilaNueva, indiceNuevo);// hace la derivacion de t
            if (elementos != null) {// si cumple va por buen camino
                pilaNueva.setPila(elementos.getPila().getPila());
                indiceNuevo = elementos.getIndiceActual();
                objeto = pilaNueva.peek().toString();
                try {// entonces ahora mira si es un signo mas el que viene
                    if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                        pilaNueva.pop();
                        indiceNuevo++;// si cumple sigue por buen camino y deriva la s
                        elementos = derivacionDeS(pilaNueva, indiceNuevo);
                        if (elementos != null) {// si cumple la derivacion de s significa que fue el camino correcto
                            pilaNueva.setPila(elementos.getPila().getPila());
                            indiceNuevo = elementos.getIndiceActual();// devuelve el camino
                            elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                            if (pilaNueva.getPila().size() == 1 && tokens.get(indiceNuevo).getToken().equals(constantes.getTOKEN_FIN())) {
                                return elementos;
                            } else {// si no funciona fallo el camino
                                falloCamino = true;
                            }
                        } else {
                            falloCamino = true;
                        }
                    } else {
                        falloCamino = true;
                    }
                } catch (IndexOutOfBoundsException e) {
                    falloCamino = true;
                }
            } else {
                falloCamino = true;
            }// ahora reinicia todo y prueba con otro camino
            if (falloCamino == true) {
                indiceNuevo = indiceActual;
                pilaNueva.setPila(pila.getPila());
                falloCamino = false;
                pilaNueva.pop();// ahora prueba con f*t
                pilaNueva.push(constantes.getT());
                pilaNueva.push(constantes.getSIGNO_MULTIPLICACION());
                pilaNueva.push(constantes.getF());
                elementos = derivacionDeF(pilaNueva, indiceNuevo);// hace derivacion de f
                if (elementos != null) {
                    pilaNueva.setPila(elementos.getPila().getPila());
                    indiceNuevo = elementos.getIndiceActual();
                    objeto = pilaNueva.peek().toString();
                    try {// si cumple mira si hay un signo de *
                        if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                            pilaNueva.pop();
                            indiceNuevo++;// si cumple ahora deriva t
                            elementos = derivacionDeT(pilaNueva, indiceNuevo);
                            if (elementos != null) {
                                pilaNueva.setPila(elementos.getPila().getPila());
                                indiceNuevo = elementos.getIndiceActual();
                                elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                                if (pilaNueva.getPila().size() == 1 && tokens.get(indiceNuevo).getToken().equals(constantes.getTOKEN_FIN())) {
                                    return elementos;// si todo fue bien ese era el camino y manda que si cumplio
                                } else {
                                    falloCamino = true;
                                }
                            } else {
                                falloCamino = true;
                            }
                        } else {
                            falloCamino = true;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        falloCamino = true;
                    }

                } else {// si no fallo el camino y prueba con otro
                    falloCamino = true;
                }
                if (falloCamino == true) {
                    indiceNuevo = indiceActual;
                    pilaNueva.setPila(pila.getPila());
                    falloCamino = false;// ahora prueba con (s)
                    pilaNueva.quitarPoner(constantes.getPARENTESIS_CERRADO());
                    pilaNueva.push(constantes.getS());
                    pilaNueva.push(constantes.getPARENTESIS_ABIERTO());
                    objeto = pilaNueva.peek().toString();
                    try {// si cumple el parentesis entra
                        if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                            pilaNueva.pop();
                            indiceNuevo++;// y ahora deriva s
                            elementos = derivacionDeS(pilaNueva, indiceNuevo);
                            if (elementos != null) {
                                pilaNueva.setPila(elementos.getPila().getPila());
                                indiceNuevo = elementos.getIndiceActual();
                                objeto = pilaNueva.peek().toString();// si cumple entra y mira el parentesis cerrado
                                if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                                    pilaNueva.pop();
                                    indiceNuevo++;// ahora devuelve el valor si cumple
                                    elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                                    if (pilaNueva.getPila().size() == 1 && tokens.get(indiceNuevo).getToken().equals(constantes.getTOKEN_FIN())) {
                                        return elementos;
                                    } else {
                                        falloCamino = true;
                                    }
                                } else {
                                    falloCamino = true;
                                }
                            } else {
                                falloCamino = true;
                            }
                        } else {
                            falloCamino = true;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        falloCamino = true;
                    }
// si los caminos siguen fallando entra aqui
                    if (falloCamino == true) {
                        indiceNuevo = indiceActual;
                        pilaNueva.setPila(pila.getPila());
                        falloCamino = false;// mira si la derivacion es solo un numero
                        pilaNueva.quitarPoner(constantes.getTOKEN_NUMERO());
                        objeto = pilaNueva.peek().toString();
                        try {//si es asi devuelve que encontro el numero
                            if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                                pilaNueva.pop();
                                indiceNuevo++;
                                elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                                if (pilaNueva.getPila().size() == 1 && tokens.get(indiceNuevo).getToken().equals(constantes.getTOKEN_FIN())) {
                                    return elementos;
                                } else {
                                    falloCamino = true;
                                }
                            } else {// si no mira si es un identificador 
                                pilaNueva.quitarPoner(constantes.getTOKEN_IDENTIFICADOR());
                                objeto = pilaNueva.peek().toString();
                                if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                                    pilaNueva.pop();
                                    indiceNuevo++;
                                    elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                                    if (pilaNueva.getPila().size() == 1 && tokens.get(indiceNuevo).getToken().equals(constantes.getTOKEN_FIN())) {
                                        return elementos;
                                    } else {
                                        falloCamino = true;
                                    }
                                }
                            }// si al final no encuentra nada retornara el null
                        } catch (IndexOutOfBoundsException e) {
                            falloCamino = true;
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public ElementosAuxiliares derivacionDeT(Pila pila, int indiceActual) {
        try {
            boolean falloCamino = false;
            String objeto;
            ElementosAuxiliares elementos;
            Pila pilaNueva = new Pila();
            int indiceNuevo = indiceActual;
            pilaNueva.setPila(pila.getPila());
            pilaNueva.pop();// aqui empezara a derivar segun sus reglas con f*t
            pilaNueva.push(constantes.getT());
            pilaNueva.push(constantes.getSIGNO_MULTIPLICACION());
            pilaNueva.push(constantes.getF());
            elementos = derivacionDeF(pilaNueva, indiceNuevo);// mirara la derivacion de f
            if (elementos != null) {
                pilaNueva.setPila(elementos.getPila().getPila());
                indiceNuevo = elementos.getIndiceActual();
                objeto = pilaNueva.peek().toString();// si cumple entra
                try {// y mira el signo de *
                    if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                        pilaNueva.pop();
                        indiceNuevo++;
                        elementos = derivacionDeT(pilaNueva, indiceNuevo);
                        if (elementos != null) {// si cumple mira la derivacion de t recursivamente
                            pilaNueva.setPila(elementos.getPila().getPila());
                            indiceNuevo = elementos.getIndiceActual();// si cumplio el camino lo retorna
                            elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                            return elementos;
                        } else {
                            falloCamino = true;
                        }
                    } else {
                        falloCamino = true;
                    }
                } catch (IndexOutOfBoundsException e) {
                    falloCamino = true;
                }
            } else {
                falloCamino = true;
            }// si fallo el camion prueba con otro
            if (falloCamino == true) {
                indiceNuevo = indiceActual;
                pilaNueva.setPila(pila.getPila());
                falloCamino = false;
                pilaNueva.quitarPoner(constantes.getPARENTESIS_CERRADO());
                pilaNueva.push(constantes.getS());
                pilaNueva.push(constantes.getPARENTESIS_ABIERTO());
                objeto = pilaNueva.peek().toString();
                try {// mira si hace match (
                    if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                        pilaNueva.pop();
                        indiceNuevo++;
                        elementos = derivacionDeS(pilaNueva, indiceNuevo);
                        if (elementos != null) {// deriva s y sicumple entra
                            pilaNueva.setPila(elementos.getPila().getPila());
                            indiceNuevo = elementos.getIndiceActual();
                            objeto = pilaNueva.peek().toString();
                            if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                                pilaNueva.pop();
                                indiceNuevo++;// si hace match )  entinces devuelve el vvalor
                                elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                                return elementos;
                            } else {
                                falloCamino = true;
                            }
                        } else {
                            falloCamino = true;
                        }
                    } else {
                        falloCamino = true;
                    }
                } catch (IndexOutOfBoundsException e) {
                    falloCamino = true;
                }
// si el camino fallo prueba aqui
                if (falloCamino == true) {
                    indiceNuevo = indiceActual;
                    pilaNueva.setPila(pila.getPila());
                    falloCamino = false;// mira si es un nuemo
                    pilaNueva.quitarPoner(constantes.getTOKEN_NUMERO());
                    objeto = pilaNueva.peek().toString();
                    try {
                        if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                            pilaNueva.pop();
                            indiceNuevo++;// si es asi lo manda
                            elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                            return elementos;
                        } else {// prueba con identificador y lo manda
                            pilaNueva.quitarPoner(constantes.getTOKEN_IDENTIFICADOR());
                            objeto = pilaNueva.peek().toString();
                            if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                                pilaNueva.pop();
                                indiceNuevo++;
                                elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                                return elementos;
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        falloCamino = true;
                    }

                }
            }// si llega aqui no cumplio ningun camino y manda null
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    // ahora con f

    public ElementosAuxiliares derivacionDeF(Pila pila, int indiceActual) {
        try {
            boolean falloCamino = false;
            ElementosAuxiliares elementos;
            Pila pilaNueva = new Pila();
            int indiceNuevo = indiceActual;
            pilaNueva.setPila(pila.getPila());//prueba con los parentesis
            pilaNueva.quitarPoner(constantes.getPARENTESIS_CERRADO());
            pilaNueva.push(constantes.getS());
            pilaNueva.push(constantes.getPARENTESIS_ABIERTO());
            String objeto = pilaNueva.peek().toString();
            try {// si hace match ( enntra
                if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                    pilaNueva.pop();
                    indiceNuevo++;// hace derivacion de s
                    elementos = derivacionDeS(pilaNueva, indiceNuevo);
                    if (elementos != null) {// si cumplio entra
                        pilaNueva.setPila(elementos.getPila().getPila());
                        indiceNuevo = elementos.getIndiceActual();
                        objeto = pilaNueva.peek().toString();
                        if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                            pilaNueva.pop();// y mira si hace match )y retorna
                            indiceNuevo++;
                            elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                            return elementos;
                        } else {
                            falloCamino = true;
                        }
                    } else {
                        falloCamino = true;
                    }
                } else {
                    falloCamino = true;
                }
            } catch (IndexOutOfBoundsException e) {
                falloCamino = true;
            }// si el camino fallo prueba aqui
            if (falloCamino == true) {
                indiceNuevo = indiceActual;
                pilaNueva.setPila(pila.getPila());
                falloCamino = false;
                pilaNueva.quitarPoner(constantes.getTOKEN_NUMERO());
                objeto = pilaNueva.peek().toString();
                try {// mira si es un numero y lo devuelve si cumple
                    if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                        pilaNueva.pop();
                        indiceNuevo++;
                        elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                        return elementos;
                    } else {// si no prueba un identificador y si cumple lo devuelve
                        pilaNueva.quitarPoner(constantes.getTOKEN_IDENTIFICADOR());
                        objeto = pilaNueva.peek().toString();
                        if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                            pilaNueva.pop();
                            indiceNuevo++;
                            elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                            return elementos;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    falloCamino = true;
                }

            }// si llega aqui es que no es ningun camino
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
//derivacon de s

    public ElementosAuxiliares derivacionDeS(Pila pila, int indiceActual) {
        try {
            boolean falloCamino = false;
            String objeto;
            ElementosAuxiliares elementos;
            Pila pilaNueva = new Pila();
            int indiceNuevo = indiceActual;
            pilaNueva.setPila(pila.getPila());// probara primero con derivacion de t+s
            pilaNueva.quitarPoner(constantes.getS());
            pilaNueva.push(constantes.getSIGNO_MAS());
            pilaNueva.push(constantes.getT());
            elementos = derivacionDeT(pilaNueva, indiceNuevo);
            if (elementos != null) {// si la derivacion de t cumplio entra
                pilaNueva.setPila(elementos.getPila().getPila());
                indiceNuevo = elementos.getIndiceActual();
                objeto = pilaNueva.peek().toString();
                try {// ahora mirara el signo +
                    if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                        pilaNueva.pop();
                        indiceNuevo++;// si hace match hace la derivaicon de s y mira si cumple
                        elementos = derivacionDeS(pilaNueva, indiceNuevo);
                        if (elementos != null) {
                            pilaNueva.setPila(elementos.getPila().getPila());
                            indiceNuevo = elementos.getIndiceActual();// retorna que encontro camino
                            elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                            return elementos;
                        } else {
                            falloCamino = true;
                        }
                    } else {
                        falloCamino = true;
                    }
                } catch (IndexOutOfBoundsException e) {
                    falloCamino = true;
                }
            } else {
                falloCamino = true;
            }
// si fallo el camino entra aqui
            if (falloCamino == true) {
                indiceNuevo = indiceActual;
                pilaNueva.setPila(pila.getPila());
                falloCamino = false;
                pilaNueva.pop();// prueba con f*t
                pilaNueva.push(constantes.getT());
                pilaNueva.push(constantes.getSIGNO_MULTIPLICACION());
                pilaNueva.push(constantes.getF());
                elementos = derivacionDeF(pilaNueva, indiceNuevo);
                if (elementos != null) {// si derivaicon de f cumplio entra
                    pilaNueva.setPila(elementos.getPila().getPila());
                    indiceNuevo = elementos.getIndiceActual();
                    objeto = pilaNueva.peek().toString();
                    try {
                        if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                            pilaNueva.pop();// mira si hace match * 
                            indiceNuevo++;
                            elementos = derivacionDeT(pilaNueva, indiceNuevo);
                            if (elementos != null) {// y mira la derivacoin de t y si cumple entra
                                pilaNueva.setPila(elementos.getPila().getPila());
                                indiceNuevo = elementos.getIndiceActual();
                                elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                                return elementos;// retorna el valor
                            } else {
                                falloCamino = true;
                            }
                        } else {
                            falloCamino = true;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        falloCamino = true;
                    }
                } else {
                    falloCamino = true;
                }// si e lcamino fallo entra
                if (falloCamino == true) {
                    indiceNuevo = indiceActual;
                    pilaNueva.setPila(pila.getPila());
                    falloCamino = false;
                    pilaNueva.quitarPoner(constantes.getPARENTESIS_CERRADO());
                    pilaNueva.push(constantes.getS());// prueba con parentesis cerrado y abierto
                    pilaNueva.push(constantes.getPARENTESIS_ABIERTO());
                    objeto = pilaNueva.peek().toString();
                    try {
                        if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                            pilaNueva.pop();
                            indiceNuevo++;// mira si s cumple
                            elementos = derivacionDeS(pilaNueva, indiceNuevo);
                            if (elementos != null) {
                                pilaNueva.setPila(elementos.getPila().getPila());
                                indiceNuevo = elementos.getIndiceActual();
                                objeto = pilaNueva.peek().toString();// hace derivacion y match
                                if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                                    pilaNueva.pop();
                                    indiceNuevo++;// si cumplio lo retorna
                                    elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                                    return elementos;
                                } else {
                                    falloCamino = true;
                                }
                            } else {
                                falloCamino = true;
                            }
                        } else {
                            falloCamino = true;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        falloCamino = true;
                    }// si el camino fallo entra
                    if (falloCamino == true) {
                        indiceNuevo = indiceActual;
                        pilaNueva.setPila(pila.getPila());
                        falloCamino = false;// prueba si es un numero
                        pilaNueva.quitarPoner(constantes.getTOKEN_NUMERO());
                        objeto = pilaNueva.peek().toString();
                        try {
                            if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                                pilaNueva.pop();
                                indiceNuevo++;// si cumple lo devuelve
                                elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                                return elementos;
                            } else {
                                pilaNueva.quitarPoner(constantes.getTOKEN_IDENTIFICADOR());
                                objeto = pilaNueva.peek().toString();
                                if (tokens.get(indiceNuevo).getToken().equals(objeto)) {
                                    pilaNueva.pop();// prueba con un identificaodr y si cumple lo devuelve
                                    indiceNuevo++;
                                    elementos = new ElementosAuxiliares(pilaNueva, indiceNuevo);
                                    return elementos;
                                }
                            }
                        } catch (IndexOutOfBoundsException e) {
                            falloCamino = true;
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
// asigna los valroes

    public void asignarValores(int indiceQueCumplio) {
        try {
            int indiceTokenFin = indiceQueCumplio - 1;
            int indiceTokenIgual = -1;
            String objeto;// si es una expresion aisgnara el valor
            int indiceIdentificadorAsignarle = -1;
            for (int i = indiceTokenFin; i > -1; i--) {
                objeto = tokens.get(i).getToken();
                if (objeto.equals(constantes.getSIGNO_IGUAL())) {
                    indiceTokenIgual = i;
                    indiceIdentificadorAsignarle = i - 1;
                    break;// con esto mirara desde donde hasta donde esta la expresion el signo igual y el identificador
                }
            }
            String expresion = "";
            String valorToken = null;// si encontro algo valido entrara
            if (indiceTokenIgual >= 0 && indiceIdentificadorAsignarle >= 0) {
                for (int i = indiceTokenIgual + 1; i < indiceTokenFin; i++) {
                    valorToken = tokens.get(i).getToken();
                    if (valorToken.equals(constantes.getTOKEN_IDENTIFICADOR())) {
                        expresion += tokens.get(i).getValor();
                    } else if (valorToken.equals(constantes.getTOKEN_NUMERO())) {
                        expresion += tokens.get(i).getLexema();
                    } else {
                        expresion += tokens.get(i).getLexema();
                    }// ira añadiendo valores a la expresion
                }
                
// mostrara en pantalla donde estan ubicados todos los elementos de expresion
                System.out.println("La expresion es: " + expresion);
                System.out.println("Se le asignara al identificador: " + tokens.get(indiceIdentificadorAsignarle).getLexema());
                System.out.println("El signo igual esta en: " + indiceTokenIgual);
                try {
                    ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");
                    Object result = engine.eval(expresion);
                    int valor = (int)result;
                    cambiarValorIdentificador(valor, tokens.get(indiceIdentificadorAsignarle).getLexema());
                    System.out.println("el valor es: "+ valor);
                    System.out.println(expresion + " = " + result);
                } catch (ScriptException e) {
                    // Something went wrong
                    e.printStackTrace();
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
// aqui cambiaria el valor de todos los identificadores que tuvieran el mismo lexema 

    public void cambiarValorIdentificador(int valor, String lexemaIdentificador) {
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).getToken().equals(constantes.getTOKEN_IDENTIFICADOR())) {
                if (tokens.get(i).getLexema().equals(lexemaIdentificador)) {
                    tokens.get(i).setValor(valor);
                    System.out.println("posicion: " + i);
                    System.out.println(tokens.get(i).getLexema() + " su nuevo valor es: " + tokens.get(i).getValor());
                }
            }
        }
    }

    

}
