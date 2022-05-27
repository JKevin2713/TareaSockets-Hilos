package Server;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.net.*;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JFrame;

import Common.Casilla;
import Common.Constantes;
import Common.Dot;
import Common.Mapa;
import Common.Target;

public class GUI implements ActionListener, Constantes{

    JFrame ventana;
    JButton next;
    Mapa mapa;
    Dot dot;

    Socket client;
    ObjectOutputStream output;
    
    public GUI(){

        ventana = new JFrame();
        mapa = new Mapa(this);


        ventana.add(mapa.panelTablero);

        next = new JButton("continuar");
        next.addActionListener(this);
        next.setActionCommand("next");

        ventana.add(next, BorderLayout.SOUTH);

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);

        dot = new Dot();

        Server server = new Server(dot);
        Thread hilo = new Thread(server);
        hilo.start();

        moveDot();
        run();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public void moveDot(){
        mapa.tablero[dot.lastPosition[X]][dot.lastPosition[Y]].clearDot();
        mapa.tablero[dot.currentPosition[X]][dot.currentPosition[Y]].setAsDot();
    }

    public void run(){
        while (true){
            dot.move();
            moveDot();

            try {
                client = new Socket("127.0.0.1", 4445);
                output = new ObjectOutputStream(client.getOutputStream());
                System.out.println("Dont x: "+ dot.currentPosition[X]+" Dot y: "+dot.currentPosition[Y]);
                output.writeObject(dot);
                output.flush();
                output.close();
                client.close();
            } catch (Exception ex) {
                //TODO: handle exception
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
    }

}