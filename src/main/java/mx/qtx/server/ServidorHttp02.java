package mx.qtx.server;
import java.net.*;
import java.io.*;

public class ServidorHttp02 {
	private static int PUERTO_TCP = 8080;
	private static String[] mensajes= {
			"<h1>Bienvenido a mi servidor HTTP</h1>",
			"<h2>Esta es una respuesta de prueba</h2>",
			"<h3>Segunda respuesta de prueba</h3>",
			"<h4>Tercera respuesta de prueba</h4>",
			};
    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(PUERTO_TCP);
        System.out.println("Servidor HTTP escuchando en el puerto "
        		+ PUERTO_TCP
        		+ "...");

        while (true) {
            Socket cliente = null;
            try {
                cliente = servidor.accept();
                System.out.println("\nCliente conectado: " + cliente.getInetAddress().getHostAddress());

                BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                String linea = entrada.readLine();
                while (!linea.isEmpty()) {
                    System.out.println(linea);
                    linea = entrada.readLine();
                }
                int iMensaje = (int)(Math.random() * 1000) % mensajes.length;
                System.out.println(iMensaje);

                PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
                String respuesta = "HTTP/1.1 200 OK"
                		  + "\r\n"
                		  + "Content-Type: text/html"
                		  + "\r\n\r\n";
                respuesta += "<html><head><title>Mi servidor HTTP</title></head>";
                respuesta += "<body><h1>"
                		+ mensajes[iMensaje]
                		+ "</body></html>";
                salida.println(respuesta);
                salida.flush();
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            } finally {
                if (cliente != null) {
                    cliente.close();
                }
            }
        }
    }
}
