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
    final static String CRLF = "\r\n";
    Socket socket; 

    public SolicitudHttp(Socket socket) throws Exception{
        this.socket = socket;


    }

    public void run(){
        try{

            proceSolicitudI();
        }catch(Exception e){
            System.out.println(e);

        }


    }

   public void proceSolicitudI() throws Exception {
    DataOutputStream os = new DataOutputStream(socket.getOutputStream());
    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    String linea = br.readLine();
    System.out.println();
    System.out.println(linea);
    if (linea == null || linea.isEmpty()) {
        os.writeBytes("HTTP/1.1 400 Bad Request\r\n");
        os.writeBytes("Content-Type: text/plain\r\n");
        os.writeBytes("\r\n");
        os.writeBytes("Solicitud inválida.");
        os.close();
        br.close();
        socket.close();
        return;
    }
    StringTokenizer partesLinea = new StringTokenizer(linea);
    partesLinea.nextToken();
    String nombreArchivo = partesLinea.nextToken();
    if (nombreArchivo.equals("/")) {
        nombreArchivo = "/index.html";
    }
    nombreArchivo = "." + nombreArchivo;

    FileInputStream fis = null;
    boolean existeArchivo = true;
    try {
            fis = new FileInputStream(nombreArchivo);
    } catch (FileNotFoundException e) {
            existeArchivo = false;
    }



    String lineaHeader;
    while ((lineaHeader = br.readLine()) != null && lineaHeader.length() != 0) {
        System.out.println(lineaHeader);
    }

    String lineaDeEstado = null;
    String lineaDeTipoContenido = null;
    String cuerpoMensaje = null;
    if (existeArchivo) {
            lineaDeEstado = "HTTP/1.1 200 OK";
            lineaDeTipoContenido = "Content-type: " + contentType(nombreArchivo) + CRLF;
                    
    } else {
            lineaDeEstado = "HTTP/1.1 404 Not Found";
            lineaDeTipoContenido = "Content-type: text/plain";
            cuerpoMensaje = "<HTML>" + 
                    "<HEAD><TITLE>404 Not Found</TITLE></HEAD>" +
                    "<BODY><b>404</b> Not Found</BODY></HTML>";
    }

    os.writeBytes(lineaDeEstado + CRLF);
    os.writeBytes(lineaDeTipoContenido);
    os.writeBytes(CRLF);

    if (existeArchivo) {
        enviarBytes(fis, os);
        fis.close();
    } else {
            os.writeBytes(cuerpoMensaje);
    }
    
    
    os.close();
    br.close();
    socket.close();
}

private static void enviarBytes(FileInputStream fis, OutputStream os) throws Exception
{
   // Construye un buffer de 1KB para guardar los bytes cuando van hacia el socket.
   byte[] buffer = new byte[1024];
   int bytes = 0;

   // Copia el archivo solicitado hacia el output stream del socket.
   while((bytes = fis.read(buffer)) != -1 ) {
      os.write(buffer, 0, bytes);
   }
}

private static String contentType(String nombreArchivo)
{
        if(nombreArchivo.endsWith(".htm") || nombreArchivo.endsWith(".html")) {
                return "text/html";
        }
        if (nombreArchivo.endsWith(".jpg")) {
            return "image/jpeg";

            
        }

        if (nombreArchivo.endsWith(".gif")) {
            return "image/gif";
            
        }

        if (nombreArchivo.endsWith(".png")) {
            return "image/png";
            
        }

        return "application /octet-stream";

    }


    
}
