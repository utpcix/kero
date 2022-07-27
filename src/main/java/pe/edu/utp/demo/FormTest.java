package pe.edu.utp.demo;

//import pe.edu.utp.utils.SwingUTP;
import pe.edu.utp.utils.SwingUTP;

import javax.swing.*;

public class FormTest {

    // 1. Declarar controles
    JLabel lblNombre, lblEdad, lblEmail, lblDetalle;
    static JTextField txtNombre, txtEdad, txtEmail;
    JTextArea txtDetalle;
    JButton btnAceptar, btnCancelar;

    public void show(){
        // 2. Crear controles
        lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField(20);
        lblEdad = new JLabel("Edad:");
        txtEdad = new JTextField(20);
        lblEmail = new JLabel("Email:");
        txtEmail = new JTextField(20);
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");
        txtNombre = new JTextField(20);
        lblDetalle = new JLabel("Detalle del usuario:");
        txtDetalle = new JTextArea(2,30);
        // 3. Agregar controles al formulario
        SwingUTP.addControlH(lblNombre,txtNombre,lblDetalle);
        SwingUTP.addControlH(lblEdad,txtEdad);
        SwingUTP.addControlH(lblEmail,txtEmail);
        SwingUTP.addControlH(btnAceptar,btnCancelar);
        SwingUTP.addControlVE(txtDetalle, 2, 2, 1);
        // 4. Agregar eventos

        SwingUTP.addClickEvent(btnAceptar, this::openFormAlumno);
        SwingUTP.addClickEvent(btnCancelar, this::mostrarVentana);

        // 5. Lanzar ventana
        SwingUTP.runWindow("Hola SwingUTP",400, 200);
    }

    private void mostrarVentana(Object o) {
        System.out.println("SwingUTP.currentWindowID = " + SwingUTP.getCurrentWindowID());
    }

    private void openFormAlumno(Object o) {
        FormAlumno frmAlumno = new FormAlumno();
        frmAlumno.show();
        //SwingUTP.closeWindow();
    }

    public static void main(String[] args) {
        FormTest window = new FormTest();
        window.show();
    }
}