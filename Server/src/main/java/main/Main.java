package main;

import factory.SelectionGenerator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Main {
    public static int PORT;
    public static void main(String[] args) {

        try {
            File portFile = new File("src/port.txt");
            Scanner sc = new Scanner(portFile);
            PORT = sc.nextInt();
            sc.close();

            ServerSocket server = new ServerSocket(PORT);

            System.out.println("Server has started on port: " + PORT);

            while(true){
                Socket client = server.accept();
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8)
                        );
                String s;
                SelectionGenerator generator = new SelectionGenerator(client);
                int freq = 0;
                while ((s = in.readLine()) != null) {
                    if(s.equals("freq")){
                        freq = Integer.parseInt(in.readLine());
                        System.out.println("Got freq of: "+ freq);
                    }
                    if(s.equals("start")){
                        System.out.println("Command to start Generator");

                        generator.setRunning(true);
                        generator.setFreq(freq);

                        if(generator.getState() == Thread.State.NEW){
                            generator.start();
                        }else {
                            generator.resume(); //Using deprecated methods because its safe from deadlock - only 1 thread
                        }

                    }
                    if(s.equals("stop")){
                        System.out.println("Generator has stopped");
                        generator.setRunning(false);
                        generator.suspend();
                    }
                    if(s.equals("clear")){
                        System.out.println("Clearing");
                       generator.setN(1);
                    }
                }

            }


        } catch (FileNotFoundException e) {
            System.err.println("File with port is not Found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}