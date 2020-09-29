
package Logica;

import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollPane;

/**
 *
 * @author jpmazate
 */
public class MyAdjustmentListener implements AdjustmentListener {
    // dos scroll pane como atributos
    private JScrollPane panel1;
    private JScrollPane panel2;
    
    public MyAdjustmentListener(JScrollPane panel1, JScrollPane panel2){
        this.panel1=panel1;// constructor de la clase
        this.panel2=panel2;
    }
    
    @Override// este es el metodo que iindica que hacer cuando se desarrolla el evento del scroll
  public void adjustmentValueChanged(AdjustmentEvent evt) {
    Adjustable source = evt.getAdjustable();
    if (evt.getValueIsAdjusting()) {
      return;
    }// agarra la orientacion
    int orient = source.getOrientation();
    if (orient == Adjustable.HORIZONTAL) {//les da el valor al scroll pane 2 el del scroll pane 1
      panel2.getVerticalScrollBar().setValue(panel1.getVerticalScrollBar().getValue());
      panel1.getVerticalScrollBar().setValue(panel2.getVerticalScrollBar().getValue());
    } else {// con esto hace que puedan bajar al mismo tiempo
      panel2.getVerticalScrollBar().setValue(panel1.getVerticalScrollBar().getValue());
      panel1.getVerticalScrollBar().setValue(panel2.getVerticalScrollBar().getValue());
    }// atributos si pasa algo con el adjustmente listener
    int type = evt.getAdjustmentType();
    switch (type) {
    case AdjustmentEvent.UNIT_INCREMENT:
      
    case AdjustmentEvent.UNIT_DECREMENT:
      
      break;
    case AdjustmentEvent.BLOCK_INCREMENT:
      
      break;
    case AdjustmentEvent.BLOCK_DECREMENT:
      
      break;
    case AdjustmentEvent.TRACK:
        
      break;
    }// agarra el valor
    int value = evt.getValue();
  }
}
