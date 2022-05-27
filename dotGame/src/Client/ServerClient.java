package Client;

import java.net.*;
import java.io.*;
import Common.*;

public class ServerClient implements Runnable{
    ServerSocket server;
    Socket client;
    ObjectInputStream input;
    Dot dot;

    public ServerClient(Dot d){
        dot = d;
        try {
            server = new ServerSocket(4445);
        } catch (Exception e) {
            //TODO: handle exception
        }
        
    }

    public void run(){
        try {
            while(true){
                client = server.accept();
                input = new ObjectInputStream(client.getInputStream());
                Dot dotTemp = (Dot)input.readObject();
                dot.currentPosition[0] = dotTemp.currentPosition[0];
                dot.currentPosition[1] = dotTemp.currentPosition[1];
                dot.lastPosition[0] = dotTemp.lastPosition[0];
                dot.lastPosition[1] = dotTemp.lastPosition[1];
                input.close();
                client.close();
            }
        } catch (Exception e) {
            //TODO: handle exception
        }

    }
    
}
