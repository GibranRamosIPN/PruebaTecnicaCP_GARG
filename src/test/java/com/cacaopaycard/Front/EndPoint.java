package com.cacaopaycard.Front;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gibran
 */
public class EndPoint {
    
    static Scanner teclado = new Scanner(System.in);
    
    static class Solicitud
    {
        Long id_solicitud;
        Long cuenta;                
        String nombre;
        String ap_paterno;
        String ap_materno;
        String fecha_nacimiento;
        char genero;
        String curp;
        String telefono;
        String correo;
        String profesion;

        public Solicitud() {
        }
        
        public Solicitud(Long cuenta, String nombre, String ap_paterno, String ap_materno, String fecha_nacimiento, char genero, String curp, String telefono, String correo, String profesion) {
            this.cuenta = cuenta;
            this.nombre = nombre;
            this.ap_paterno = ap_paterno;
            this.ap_materno = ap_materno;
            this.fecha_nacimiento = fecha_nacimiento;
            this.genero = genero;
            this.curp = curp;
            this.telefono = telefono;
            this.correo = correo;
            this.profesion = profesion;
        }
    }
    
    private static Solicitud soli;
    
    public static String menu() {
        String opc;
        System.out.println("\n\t******** MENU ********");
        System.out.println("a. Agregar cuenta.");
        System.out.println("b. Consultar información de cuenta.");
        System.out.println("c. Eliminar una cuenta.");
        System.out.println("d. Actualizar información de cuenta.");
        System.out.println("e. Salir.\nOpcion: ");
        
        opc = teclado.nextLine();
        return opc;
    }
    
    public static void leerRespuesta(BufferedReader br) throws IOException {
        String respuesta;
        // Se recibe respuesta de tipo Solicitud
        while ((respuesta = br.readLine()) != null) {
            Gson j = new Gson();
            Solicitud s = (Solicitud) j.fromJson(respuesta, Solicitud.class);
            soli = s;
            System.out.println("Cuenta: " + s.cuenta);
            System.out.println("Nombre: " + s.nombre);
            System.out.println("Apellido Paterno: " + s.ap_paterno);
            System.out.println("Apellido Materno: " + s.ap_materno);
            System.out.println("Fecha de Nacimiento: " + s.fecha_nacimiento);
            System.out.println("Genero: " + s.genero);
            System.out.println("CURP: " + s.curp);
            System.out.println("Telefono: " + s.telefono);
            System.out.println("Correo: " + s.correo);
            System.out.println("Profesión: " + s.profesion);
            System.out.println("***************************************************\n");
        }
    }
    
    // MÉTODOS QUE HACEN PETICIONES    
    public static void enviaPost(String ruta, String solicitud) throws MalformedURLException, IOException {
        URL url = new URL("http://localhost:8080/api/" + ruta);
        
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        
        // Se utiliza el método HTTP POST
        conexion.setRequestMethod("POST");
        
        // La petición estará codificada con contenido JSON
        conexion.setRequestProperty("Content-Type", "application/json; utf-8");
        conexion.setRequestProperty("Accept", "application/json");
        
        conexion.setDoOutput(true);
        
        // Request-Body
        try (OutputStream os = conexion.getOutputStream()) {
            byte[] input = solicitud.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
                        
        // Verficando código de respuesta HTTP
        if (conexion.getResponseCode() != HttpURLConnection.HTTP_BAD_REQUEST) {
            System.out.println("\n -- ResponseCodeHTTP:" + conexion.getResponseCode() + " --\n\nRegistro creado:");
        } else {
            throw new RuntimeException("Codigo de error HTTP: " + conexion.getResponseCode());
        }
        
        BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
        leerRespuesta(br);
        
        soli = null;
        conexion.disconnect();
    }
    
    public static Boolean getData(Long cuenta) throws MalformedURLException, IOException {
        URL url = new URL("http://localhost:8080/api/" + cuenta);
        
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        
        try {
            // Se utiliza el método HTTP GET
            conexion.setRequestMethod("GET");
        } catch (ProtocolException ex) {
            Logger.getLogger(EndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // La información se recibe en formato JSON
        conexion.setRequestProperty("Accept", "application/json");
        
        conexion.setDoOutput(true);
        
        // Verificando código de respuesta HTTP
        if (conexion.getResponseCode() != HttpURLConnection.HTTP_OK) {
            System.out.println("Codigo de error HTTP: " + conexion.getResponseCode() +
                    " - Número de cuenta NO encontrado");
            return false;
        } else {
            System.out.println("\n -- ResponseCodeHTTP:" + conexion.getResponseCode() + " --\n\nRegistro obtenido:");
            BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
            leerRespuesta(br);
            conexion.disconnect();
            return true;            
        }        
    }
    
    public static void enviaDelete(Long cuenta) throws MalformedURLException, IOException {
        URL url = new URL("http://localhost:8080/api/eliminar/" + cuenta);
        
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        
        try {
            // Se utiliza el método HTTP DELETE
            conexion.setRequestMethod("DELETE");
        } catch (ProtocolException ex) {
            Logger.getLogger(EndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // La información se recibe en formato JSON
        conexion.setRequestProperty("Accept", "application/json");
        
        conexion.setDoOutput(true);
        
        // Verificando código de respuesta HTTP
        if (conexion.getResponseCode() != HttpURLConnection.HTTP_BAD_REQUEST) {
            System.out.println("\n -- ResponseCodeHTTP:" + conexion.getResponseCode() + " --\n\nRegistro eliminado.");
        } else {
            System.out.println("Codigo de error HTTP: " + conexion.getResponseCode() + " - NO se pudo eliminar el número de cuenta");
        }
        
        conexion.disconnect();
    }
    
    public static void enviaUpdate(Long cuenta) throws MalformedURLException, IOException {        
        Solicitud s = new Solicitud();
        // Primero se busca el registro
        if(getData(cuenta)) {       // Existe
            URL url = new URL("http://localhost:8080/api/actualizar");
        
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

            try {
                // Se utiliza el método HTTP PUT
                conexion.setRequestMethod("PUT");
            } catch (ProtocolException ex) {
                Logger.getLogger(EndPoint.class.getName()).log(Level.SEVERE, null, ex);
            }
            // La información está en formato JSON
            conexion.setRequestProperty("Content-Type", "application/json; utf-8");
            conexion.setRequestProperty("Accept", "application/json");

            conexion.setDoOutput(true);
            
            // Se copia la info recuperada al objeto
            s.id_solicitud = soli.id_solicitud;
            s.nombre = soli.nombre;
            s.ap_paterno = soli.ap_paterno;
            s.ap_materno = soli.ap_materno;
            s.fecha_nacimiento = soli.fecha_nacimiento;
            s.genero = soli.genero;
            s.curp = soli.curp;
            s.telefono = soli.telefono;
            s.correo = soli.correo;
            s.profesion = soli.profesion;
            // Se solicita la nueva info
            System.out.println("Escribe el nuevo número de cuenta: ");
            s.cuenta = Long.parseLong(teclado.nextLine());
            System.out.println("¿Modificar telefono, correo y profesion? S | N ");
            String opc = teclado.nextLine();
            if (opc.equals("S")) {
                System.out.println("Escribe el telefono: ");
                s.telefono = teclado.nextLine();
                System.out.println("Escribe el correo: ");
                s.correo = teclado.nextLine();
                System.out.println("Escribe la profesión: ");
                s.profesion = teclado.nextLine();
            }
            
            Gson j = new Gson();
            String jsonSolicitud = j.toJson(s);
            //System.out.println("El json queda: \n" + jsonSolicitud);
            // Request-Body
            try (OutputStream os = conexion.getOutputStream()) {
                byte[] input = jsonSolicitud.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            // Verificando código de respuesta HTTP
            if (conexion.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Codigo de error HTTP: " + conexion.getResponseCode() +
                        " - Es posible que el número de cuenta ingresado ya exista con otra información");
            } else {
                System.out.println("\n -- ResponseCodeHTTP:" + conexion.getResponseCode() + " --\n\nRegistro actualizado.");
            }
            conexion.disconnect();
        } else {    // NO existe
            System.out.println("El número de cuenta NO existe");
        }
        
        
    }
    
    // MÉTODOS PARA OBTENER LA INFORMACION DE USUARIO
    public static void agregarCuenta() {
        Long cuenta = null;
        String nombre = null, apPat = null, apMat = null, fechaNac = null,
                curp = null, telefono = null, correo = null, profesion = null;
        char genero;
        
        System.out.println("\n\t ****** Agregar número de cuenta ******");
        System.out.println("Escribe el número de cuenta: ");
        cuenta = Long.parseLong(teclado.nextLine());
        System.out.println("Escribe el nombre: ");
        nombre = teclado.nextLine();
        System.out.println("Escribe el apellido paterno: ");
        apPat = teclado.nextLine();
        System.out.println("Escribe el apellido materno: ");
        apMat = teclado.nextLine();
        System.out.println("Escribe la fecha de nacimiento (yyyy-MM-dd): ");
        fechaNac = teclado.nextLine();
        System.out.println("Escribe el genero (M | F): ");
        genero = teclado.nextLine().charAt(0);
        System.out.println("Escribe el CURP: ");
        curp = teclado.nextLine();
        System.out.println("¿Ingresar datos opcionales? S | N ");
        String opc = teclado.nextLine();
        if (opc.equals("S")) {
            System.out.println("Escribe el telefono: ");
            telefono = teclado.nextLine();
            System.out.println("Escribe el correo: ");
            correo = teclado.nextLine();
            System.out.println("Escribe la profesión: ");
            profesion = teclado.nextLine();
        }
                
        Solicitud s = new Solicitud(cuenta, nombre, apPat, apMat, fechaNac, genero, curp, telefono, correo, profesion);
        Gson j = new Gson();
        String jsonSolicitud = j.toJson(s);
        
        //System.out.println("El json queda: \n" + jsonSolicitud);
        try {            
            enviaPost("crear", jsonSolicitud);
        } catch (IOException ex) {
            Logger.getLogger(EndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void consultarCuenta() {
        Long cuenta = null;
        System.out.println("\n\t ****** Consultar info. número de cuenta ******");
        System.out.println("Escribe el número de cuenta: ");
        cuenta = Long.parseLong(teclado.nextLine());
        
        try {
            getData(cuenta);
        } catch (IOException ex) {
            Logger.getLogger(EndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void eliminarCuenta() {
        Long cuenta = null;
        System.out.println("\n\t ****** Eliminar número de cuenta ******");
        System.out.println("Escribe el número de cuenta: ");
        cuenta = Long.parseLong(teclado.nextLine());
        
        try {
            enviaDelete(cuenta);
        } catch (IOException ex) {
            Logger.getLogger(EndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void actualizarCuenta() {
        Long cuenta = null;
        System.out.println("\n\t ****** Actualizar info. número de cuenta ******");
        System.out.println("Escribe el número de cuenta: ");
        cuenta = Long.parseLong(teclado.nextLine());
        
        try {
            enviaUpdate(cuenta);
        } catch (IOException ex) {
            Logger.getLogger(EndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        String opc = menu();
        while (!opc.equals("e")) {
            switch (opc) {
                case "a":
                    agregarCuenta();
                break;
                
                case "b":
                    consultarCuenta();
                break;
                
                case "c":
                    eliminarCuenta();
                break;
                
                case "d":
                    actualizarCuenta();
                break;
                
                default:
                    System.out.println("ERROR. Opción no válida!\nIntenta de nuevo...");
                break;
            }
            opc = menu();
        }
    }
    
}
