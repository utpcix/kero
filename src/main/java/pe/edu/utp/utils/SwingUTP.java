package pe.edu.utp.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.function.Consumer;

public class SwingUTP {

    private static class SwingWindowUTP{
        enum LayoutType { NoLayoutSelected, FreeDraw, GridBag, Border  };

        public SwingWindowUTP(String windowsName) {
            this.windowsName = windowsName;
        }

        private String windowsName;
        private boolean DEBUG = false;
        private HashMap<Component, Object> lista = new HashMap<>();
        private JFrame frame = new JFrame();
        private LayoutType tipo = LayoutType.NoLayoutSelected;
        private JPanel panelMain = new JPanel(new GridBagLayout());
        private GridBagConstraints d = new GridBagConstraints();
        private int verticalY = 0;    // y
        private int horizontalX = 0;  // x
        private int maxRow = 0;
        private int maxCol = 0;
        private int ptop = 3;
        private int pleft= 3;
        private int pbottom = 3;
        private int pright = 3;

        public JFrame getFrame(){
            return frame;
        }

        public void enableDebug(){
            DEBUG = true;
        }

        public void disableDebug(){
            DEBUG = false;
        }

        public void setPadding(int top, int left, int bottom, int right){
            ptop = top;
            pleft = left;
            pbottom = bottom;
            pright = right;
        }

        public void addControl(Component control, Object constrain){
            lista.put(control, constrain);
            tipo = LayoutType.Border;
        }

        public void addControlH(Component... control){
            for (Component c : control) {
                addControl(horizontalX, verticalY, c);
                verticalY++;
            }
            horizontalX++;
            verticalY=0;
            maxCol = (maxCol >= control.length) ? maxCol: control.length;
            maxRow++;
        }

        public void addControlV(Component... control){
            for (Component c : control) {
                addControl(horizontalX, verticalY, c);
                horizontalX++;
            }
            maxCol+=control.length;
        }

        public void addControl(int row, int col, Component control){
            d.gridy=row;d.gridx=col;
            d.gridwidth=1;d.gridheight=1;
            d.fill = GridBagConstraints.BOTH;
            d.insets = new Insets(ptop, pleft, pbottom, pright);
            panelMain.add(control, d);
            tipo = LayoutType.GridBag;
        }

        public void addControlVE(Component control){
            addControlVE(control,maxRow, maxCol+1,0);
        }

        public void addControlVE(Component control, int heigth){
            addControlVE(control,heigth, maxCol+1,0);
        }

        public void addControlVE(Component control, int heigth, int col){
            addControlVE(control,heigth, col,0);
        }

        public void addControlVE(Component control, int heigth, int col, int row){
            d.gridy=row;d.gridx=col;
            d.gridwidth=1;
            d.gridheight = heigth;
            d.fill = GridBagConstraints.BOTH;
            panelMain.add(control, d);
            tipo = LayoutType.GridBag;
        }

        public void addControl(int x, int y, int width, int height, Component control){
            control.setBounds(x,y,width,height);
            frame.add(control);
            tipo = LayoutType.FreeDraw;
        }

        public void addClickEvent(AbstractButton control, Consumer action){
            control.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action.accept(e);
                }
            });
        }

        public void addMenuBar(JMenuBar menu) {
            frame.setJMenuBar(menu);
            frame.setFocusable(true);
        }

        public void close(){
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }

        public void addMenuAction(AbstractButton menuItem, Consumer action){
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action.accept(e);
                }
            });
        }

        public void addKeyTypedEvent(JTextField control, Consumer action){
            control.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    action.accept(e);
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        }

        public void addKeyPressedEvent(JTextField control, Consumer action){
            control.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    action.accept(e);
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        }

        public void addReleasedEvent(JTextField control, Consumer action){
            control.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {
                    action.accept(e);
                }
            });
        }

        public void makeWindow(String title, int width, int height, boolean packed){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (UnsupportedLookAndFeelException e) {
                        e.printStackTrace();
                    }
                    frame.setTitle(title);
                    frame.setSize(width, height);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.addWindowListener(new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {

                        }

                        @Override
                        public void windowClosing(WindowEvent e) {

                        }

                        @Override
                        public void windowClosed(WindowEvent e) {
                            SwingUTP.number_of_windows--;
                            SwingUTP.hwindows.remove(windowsName);
                            if (DEBUG) System.out.println("frame in "+windowsName+" exec windowClosed");
                        }

                        @Override
                        public void windowIconified(WindowEvent e) {

                        }

                        @Override
                        public void windowDeiconified(WindowEvent e) {

                        }

                        @Override
                        public void windowActivated(WindowEvent e) {

                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {

                        }
                    });

                    switch (tipo) {
                        case FreeDraw -> {
                            frame.setLayout(null);
                        }
                        case Border -> {
                            frame.setLayout(new BorderLayout());
                            for (HashMap.Entry<Component, Object> item: lista.entrySet()) {
                                frame.add(item.getKey(), item.getValue());
                            }
                        }
                        case GridBag -> {
                            d.insets = new Insets(10, 0, 0, 0);
                            d.anchor = GridBagConstraints.LINE_START;
                            frame.add(panelMain);
                        }
                        default -> {
                            frame.setLayout(null);
                        }
                    }

                    if (packed) frame.pack();
                    frame.setVisible(true);
                }
            });

        }

        public void runWindow(String title, int width, int height){
            makeWindow(title, width, height, false);
        }

        public void runWindow(String title, int width, int height, boolean packed){
            makeWindow(title, width, height, packed);
        }
    }

//    static HashMap<String, SwingWindowUTP> hwindows = new HashMap<>(){{
//        put("default", new SwingWindowUTP("default"));
//    }};
    static HashMap<String, SwingWindowUTP> hwindows = new HashMap<>();
    static int number_of_windows = 0;
    static String currentWindowID = "default";
    static boolean debug = false;

    public static void setCurrentWindow(String windowsID){
        currentWindowID = windowsID;
    }

    public static String getCurrentWindowID() {
        return currentWindowID;
    }

    /*
    * CRITICO: No llamar a getWindow desde un método auxiliar
    *          La generación de frames es decidida por el nivel de stack
    *          Se tomara el indice 3 del stacktrace y se debe procurar cumplirlo
    * */
    private static SwingWindowUTP getWindow(){
        // 1. Verificar si la caller class no existe para crearla
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        String callerFile = ste[3].getFileName();
        if (debug) System.out.println("callerFile = " + callerFile);
        if ( hwindows.containsKey(callerFile) ) {
            return hwindows.get(callerFile);
        }else{
            // Creamos una nueva ventana y la asociamos con su caller File
            SwingWindowUTP newWindow = new SwingWindowUTP(callerFile);
            hwindows.put(callerFile, newWindow);
            number_of_windows++;
            return newWindow;
        }
    }


    public static void runWindow(String title, int width, int height){
        SwingWindowUTP cw = getWindow();
        cw.makeWindow(title, width, height, false);
    }

    public static void runWindow(String title, int width, int height, boolean packed){
        SwingWindowUTP cw = getWindow();
        cw.makeWindow(title, width, height, packed);
    }

    public static void setPadding(int top, int left, int bottom, int right){
        SwingWindowUTP cw = getWindow();
        cw.setPadding(top, left, bottom, right);
    }

    public static void addControl(Component control, Object constrain){
        SwingWindowUTP cw = getWindow();
        cw.addControl(control,constrain);
    }

    public static void addControlH(Component... control){
        SwingWindowUTP cw = getWindow();
        cw.addControlH(control);
    }

    public static void addControlV(Component... control){
        SwingWindowUTP cw = getWindow();
        cw.addControlV(control);
    }

    public static void addControl(int row, int col, Component control){
        SwingWindowUTP cw = getWindow();
        cw.addControl(row,col,control);
    }

    public static void addControlVE(Component control){
        SwingWindowUTP cw = getWindow();
        cw.addControlVE(control);
    }

    public static void addControlVE(Component control, int heigth){
        SwingWindowUTP cw = getWindow();
        cw.addControlVE(control, heigth);
    }

    public static void addControlVE(Component control, int heigth, int col){
        SwingWindowUTP cw = getWindow();
        cw.addControlVE(control, heigth, col);
    }

    public static void addControlVE(Component control, int heigth, int col, int row){
        SwingWindowUTP cw = getWindow();
        cw.addControlVE(control, heigth, col, row);
    }

    public static void addControl(int x, int y, int width, int height, Component control){
        SwingWindowUTP cw = getWindow();
        cw.addControl(x,y,width,height,control);
    }

    public static void addClickEvent(AbstractButton control, Consumer action){
        SwingWindowUTP cw = getWindow();
        cw.addClickEvent(control, action);
    }

    public static void addKeyTypedEvent(JTextField control, Consumer action){
        SwingWindowUTP cw = getWindow();
        cw.addKeyTypedEvent(control, action);
    }

    public static void addKeyPressedEvent(JTextField control, Consumer action){
        SwingWindowUTP cw = getWindow();
        cw.addKeyPressedEvent(control, action);
    }

    public static void addReleasedEvent(JTextField control, Consumer action){
        SwingWindowUTP cw = getWindow();
        cw.addReleasedEvent(control, action);
    }

    public static void addMenuBar(JMenuBar menu){
        SwingWindowUTP cw = getWindow();
        cw.addMenuBar(menu);
    }

    public static void addMenuAction(AbstractButton menuItem, Consumer action){
        SwingWindowUTP cw = getWindow();
        cw.addMenuAction(menuItem, action);
    }

    public static JFrame getFrame(){
        SwingWindowUTP cw = getWindow();
        return cw.getFrame();
    }

    public static void closeWindow(){
        SwingWindowUTP cw = getWindow();
        cw.close();
    }

    public static void enableDebug(){
        debug = true;
        SwingWindowUTP cw = getWindow();
        cw.enableDebug();
    }

    public static void disableDebug(){
        debug = false;
        SwingWindowUTP cw = getWindow();
        cw.disableDebug();
    }

}
