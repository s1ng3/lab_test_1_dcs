package lab.scd.net.chat_app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class pc1 extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private DatagramSocket sendSocket;
    private DatagramSocket receiveSocket;
    private InetAddress address;
    private byte[] buf;

    public pc1() throws Exception {
        sendSocket = new DatagramSocket();
        receiveSocket = new DatagramSocket(4445);
        address = InetAddress.getByName("192.168.170.149"); // Address of pc1

        setTitle("Chat Application - PC1");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        inputField = new JTextField();
        add(inputField, BorderLayout.SOUTH);

        sendButton = new JButton("Send");
        add(sendButton, BorderLayout.EAST);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String message = inputField.getText();
                    send(message);
                    chatArea.append("Sent: " + message + "\n");
                    inputField.setText("");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String message = receive();
                        chatArea.append("Received: " + message + "\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void send(String msg) throws Exception {
        buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4449); // Sending to port 4445
        sendSocket.send(packet);
    }

    public String receive() throws Exception {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        receiveSocket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength());
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new pc1().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}