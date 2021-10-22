package com.cacopaycard.PruebaTecnicaGARG.Service;

import com.cacopaycard.PruebaTecnicaGARG.Entity.Solicitud;
import com.cacopaycard.PruebaTecnicaGARG.Repository.SolicitudRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gibran
 */
@Service
@Transactional
public class SolicitudService {
    
    @Autowired
    private SolicitudRepository solicitudRepository;
    
    public Solicitud crearSolicitud(Solicitud s) {
        return solicitudRepository.save(s);
    }
    
    public Solicitud actualizarSolicitud(Solicitud s) {
        return solicitudRepository.save(s);
    }
    
    public Optional<Solicitud> solicitudByCuenta(Long cuenta) {
        return solicitudRepository.findByCuenta(cuenta);
    }
    
    public void deleteSolicitudByCuenta(Long cuenta) {
        solicitudRepository.deleteByCuenta(cuenta);
    }
    
}
