package factory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class SelectionGenerator extends Thread{

    int n = 1;
    int freq;
    Socket client;
    private final Object lock = new Object();

    Boolean running = false;

    public SelectionGenerator(Socket client) {
        this.client = client;
    }


    public void run() {

        float period = (float)1/freq*1000;

        while(running){
            try {
                Unit task =new Unit(freq,n);
                if(client.isClosed()){
                    return;
                }
                BufferedWriter out =
                        new BufferedWriter(
                                new OutputStreamWriter(this.client.getOutputStream())
                        );
                out.write(String.valueOf(task.getValue()));
                out.flush();
                n++;
                System.out.println(task.getValue());
                TimeUnit.MILLISECONDS.sleep((long)period);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setN(int n) {
        this.n = n;
    }
    public void setRunning(Boolean running) {
        this.running = running;
    }
    public void setFreq(int freq) {
        this.freq = freq;
    }
}