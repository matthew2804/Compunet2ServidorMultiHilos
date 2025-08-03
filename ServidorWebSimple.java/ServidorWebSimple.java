import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorWebSimple{
    public static void main(String argv[]) throws Exception{
        ServerSocket socketdeEscucha = new ServerSocket(6789);
        Socket socketdeConexion = socketdeEscucha.accept();
        BufferedReader mensajeDesdeCliente = new BufferedReader(new InputStreamReader(socketdeConexion.getInputStream()));
        String LineaDeLaSolicitudHttp = mensajeDesdeCliente.readLine();
        StringTokenizer lineaSeparada = new StringTokenizer(LineaDeLaSolicitudHttp);
        
        if (lineaSeparada.nextToken().equals("GET")){
            String nombreArchivo = lineaSeparada.nextToken();
           
            if (nombreArchivo.startsWith("/"));
                nombreArchivo = nombreArchivo.substring(1);

            File archivo = new File(nombreArchivo);
            FileInputStream archivoDeEntrada = new FileInputStream(nombreArchivo);
            int cantidadDeBytes = (int) archivo.length();
            byte[] archivoEnBytes = new byte[cantidadDeBytes];
            archivoDeEntrada.read(archivoEnBytes);

            DataOutputStream mensajeParaCliente =
                new DataOutputStream(socketdeConexion.getOutputStream());

            mensajeParaCliente.writeBytes("HTTP/1.0 200 Document Follows\r\n");
                if (nombreArchivo.endsWith(".jpg"))
                    mensajeParaCliente.writeBytes("Content-Type: image/jpeg\r\n");
                if (nombreArchivo.endsWith(".gif"))
                    mensajeParaCliente.writeBytes("Content-Type: image/gif\r\n");
            mensajeParaCliente.writeBytes("Content-Length: " + cantidadDeBytes + "\r\n");
            
            mensajeParaCliente.writeBytes("\r\n");
            mensajeParaCliente.write(archivoEnBytes, 0, cantidadDeBytes);
            socketdeConexion.close();
            socketdeEscucha.close();
            archivoDeEntrada.close();

        }else{
            System.out.println("Bad Request Message");
        }
    }

   

}
