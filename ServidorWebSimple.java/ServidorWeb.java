import java.net.ServerSocket;
import java.net.Socket;
import java.io.* ;
import java.net.* ;
import java.util.* ;

public final class ServidorWeb {
     public static void main(String argv[]) throws Exception{

        // Establece el número de puerto.
        int puerto = 6789;
        ServerSocket socketdeEscucha = new ServerSocket(6789);
        
        System.out.println("Puerto iniciado en 6789");
        while (true) {
            Socket socketdeConexion = socketdeEscucha.accept();
           


            
        }

        SolicitudHttp solicitud = new SolicitudHttp();
        BufferedReader mensajeDesdeCliente = new BufferedReader();
        String lineaDeLaSolicitudHttp = mensajeDesdeCliente.readLine();


     }
}
