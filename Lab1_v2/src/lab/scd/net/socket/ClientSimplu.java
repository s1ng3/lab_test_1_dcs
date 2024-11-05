package lab.scd.net.socket;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ClientSimplu {

  public static void main(String[] args)throws Exception{
    Socket socket = null;
    Scanner scanner = new Scanner(System.in);
    try {
      InetAddress server_address = InetAddress.getByName("localhost");
      socket = new Socket(server_address, 1900);

      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

      System.out.println("Enter first number: ");
      String num1 = scanner.nextLine();
      System.out.println("Enter operation (+, -, *, /): ");
      String operation = scanner.nextLine();
      System.out.println("Enter second number: ");
      String num2 = scanner.nextLine();

      String message = num1 + " " + operation + " " + num2;
      out.println(message);
      out.flush();

      String response = in.readLine();
      System.out.println("Result from server: " + response);

      out.println("END");

    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (socket != null) socket.close();
      scanner.close();
    }
  }
}