package pe.edu.utp.demo;

import pe.edu.utp.utils.SwingUTP;

import javax.swing.*;

public class FormHello {

    JButton btnHello = new JButton("Hola Mundo");

    public void show(){
        SwingUTP.addControlH(btnHello);
        SwingUTP.addClickEvent(btnHello, this::HolaMundo);
        SwingUTP.runWindow("Hola", 300,200);
    }

    private void HolaMundo(Object o) {
        System.out.println("btnHello.getUIClassID() = " + btnHello.getUIClassID());
    }

    public static void main(String[] args) {
        FormHello fh = new FormHello();
        fh.show();
    }
}
