package pe.edu.utp.demo;

import pe.edu.utp.utils.SwingUTP;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class FormAlumno {

    JLabel lblNombre = new JLabel("Nombre:");
    JTextField txtNombre = new JTextField(20);
    JLabel lblNota = new JLabel("Nota:");
    JTextField txtNota = new JTextField(20);
    JButton btnRegistrar = new JButton("Registrar");

    // Controles para el menu
    JMenuBar menu = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenuItem fileAbout = new JMenuItem("Acerca De", KeyEvent.VK_A);
    JMenuItem fileExit = new JMenuItem("Salir", KeyEvent.VK_S);
    JMenu edit = new JMenu("Edit");
    JMenuItem cortar = new JMenuItem("Cortar", KeyEvent.VK_C);
    JSeparator sep = new JSeparator();

    public JMenuBar buildMenu(){
        // Distribucion del menu File
        file.setMnemonic(KeyEvent.VK_F);
        KeyStroke ctrlX = KeyStroke.getKeyStroke("control X");
        fileExit.setAccelerator(ctrlX);
        file.add(fileAbout);
        file.add(sep);
        file.add(fileExit);

        edit.add(cortar);

        menu.add(file);
        menu.add(edit);
        return menu;
    }

    public void show(){
        SwingUTP.addControlH(lblNombre, txtNombre);
        SwingUTP.addControlH(lblNota, txtNota);
        SwingUTP.addControlH(btnRegistrar);
        SwingUTP.addMenuBar(buildMenu());
        SwingUTP.addClickEvent(btnRegistrar, this::agregarAlumno);
        SwingUTP.addMenuAction(fileExit, this::cerrarVentana);
        SwingUTP.runWindow("Registrar Alumno", 400, 300);
    }

    private void cerrarVentana(Object o) {
        SwingUTP.closeWindow();
    }

    private void agregarAlumno(Object o) {
        FormTest.txtNombre.setText(txtNombre.getText());
    }

    public static void main(String[] args) {
        FormAlumno window = new FormAlumno();
        window.show();
    }

}
