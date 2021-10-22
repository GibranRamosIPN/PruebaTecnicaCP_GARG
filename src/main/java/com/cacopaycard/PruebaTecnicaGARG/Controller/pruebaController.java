package com.cacopaycard.PruebaTecnicaGARG.Controller;


import com.cacopaycard.PruebaTecnicaGARG.Entity.Solicitud;
import com.cacopaycard.PruebaTecnicaGARG.Service.SolicitudService;
import java.net.URI;import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Gibran
 */
@RestController
@RequestMapping("/api")
public class pruebaController {
    
    @Autowired
    private SolicitudService solicitudService;
    
    @PostMapping("/crear")
    public ResponseEntity<Solicitud> crearSolicitud(@RequestBody Solicitud s) {
        if(!existe(s.getCuenta())) {
            solicitudService.crearSolicitud(s);
        } else {
            System.out.println("Cuenta: " + s.getCuenta() + " ya existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }       
        try {
            return ResponseEntity.created(new URI("/api/"+s.getCuenta())).body(s);
	} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }        
    }
    
    @GetMapping(value = "{cuenta}")
    public ResponseEntity<Optional<Solicitud>> getSolicitud(@PathVariable ("cuenta") Long cuenta) {
        Optional<Solicitud> temp = solicitudService.solicitudByCuenta(cuenta);
        if (temp.isPresent()) {             
           return ResponseEntity.ok(temp);
        } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
    }
    
    @DeleteMapping("/eliminar/{cuenta}")
    private ResponseEntity<Solicitud> eliminarSolicitud (@PathVariable ("cuenta") Long cuenta){        
        if (existe(cuenta)) {
            solicitudService.deleteSolicitudByCuenta(cuenta);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }        
    }
    
    @PutMapping("/actualizar")
    private ResponseEntity<Solicitud> actualizarSolicitud (@RequestBody Solicitud s) {
        if (!existe(s.getCuenta())) {
            System.out.println("ID Solicitud a actualizar: " + s.getId_solicitud());
            return ResponseEntity.ok(solicitudService.actualizarSolicitud(s));
        } else {
            System.out.println("El número de cuenta ya existe con otra información."); 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }        
    }
    
    private boolean existe(Long cuenta) {
        Optional<Solicitud> temp = solicitudService.solicitudByCuenta(cuenta);        
        if (temp.isPresent()) {
            return true;
        } else {
            return false;
        }
    }
    
}
