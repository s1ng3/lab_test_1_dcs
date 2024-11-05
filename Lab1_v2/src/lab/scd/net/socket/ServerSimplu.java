package lab.scd.net.socket;
import java.io.*;
import java.net.*;

public class ServerSimplu {
  public static void main(String[] args) throws IOException {
    ServerSocket ss = null;
    Socket s = null;

    try {
      ss = new ServerSocket(1900);
      System.out.println("Serverul asteapta conexiuni...");
      s = ss.accept();

      BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
      PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);

      InetSocketAddress remoteadr = (InetSocketAddress) s.getRemoteSocketAddress();
      String remotehost = remoteadr.getHostName();
      int remoteport = remoteadr.getPort();

      System.out.println("Client nou conectat: " + remotehost + ":" + remoteport);

      String line = in.readLine();
      while (!line.equals("END")) {
        System.out.println("Server a receptionat: " + line);
        String[] parts = line.split(" ");
        double num1 = Double.parseDouble(parts[0]);
        String operation = parts[1];
        double num2 = Double.parseDouble(parts[2]);
        double result = 0;

        switch (operation) {
          case "+":
            result = num1 + num2;
            break;
          case "-":
            result = num1 - num2;
            break;
          case "*":
            result = num1 * num2;
            break;
          case "/":
            if (num2 != 0) {
              result = num1 / num2;
            } else {
              out.println("Error: Division by zero");
              continue;
            }
            break;
          default:
            out.println("Error: Invalid operation");
            continue;
        }

        out.println("Result: " + result);
        out.flush();
        line = in.readLine();
      }

      System.out.println("Aplicatie server gata.");

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (ss != null) ss.close();
      if (s != null) s.close();
    }
  }
}