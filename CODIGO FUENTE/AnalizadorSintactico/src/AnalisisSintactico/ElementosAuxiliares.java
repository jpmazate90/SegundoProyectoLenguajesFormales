
package AnalisisSintactico;

/**
 *
 * @author jpmazate
 */
public class ElementosAuxiliares {
    // poyo que serviara para la logica
    private Pila pila;
    private int indiceActual;
//constructor de la calse
    public ElementosAuxiliares(Pila pila, int indiceActual) {
        this.pila = pila;
        this.indiceActual = indiceActual;
    }
// getters
    public Pila getPila() {
        return pila;
    }

    public void setPila(Pila pila) {
        this.pila = pila;
    }

    public int getIndiceActual() {
        return indiceActual;
    }// getters

    public void setIndiceActual(int indiceActual) {
        this.indiceActual = indiceActual;
    }
    
    
}
