package controlador;

import java.io.*;
import java.awt.event.*;
import javax.swing.*;

public class SelectorDeArchivo extends JPanel
                              implements ActionListener {
    private static final String newline = "\n";
    private JFileChooser fc;

    public File seleccionar() {
        String ruta="";
        File file = null;
            fc = new JFileChooser();
        
        int returnVal = fc.showDialog(SelectorDeArchivo.this,"Abrir");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
        }
        fc.setSelectedFile(null);
        return file;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
