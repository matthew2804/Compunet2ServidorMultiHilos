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
            SolicitudHttp solicitud = new SolicitudHttp(socketdeConexion);
            Thread hilo = new Thread(solicitud);
            hilo.start();


            
        }

        


     }
}

final class SolicitudHttp implements Runnable {
    final static String CRLF = "/r/n";
    Socket socket; 

    public SolicitudHttp(Socket socket) throws Exception{


    }

    public void Run(){
        try{

            proceSolicitudI();
        }catch(Exception e){
            System.out.println(e);

        }


    }

    public void proceSolicitudI(){

        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String linea = br.readLine();
        System.out.println("");
        System.out.println(linea);
        String lineaHeader = null;
        while (lineaHeader = br.readLine().length()!= 0) {
            System.out.println(lineaHeader);
            
        }
        os.close();
        br.close();
        socket.close();


    }

    
}
