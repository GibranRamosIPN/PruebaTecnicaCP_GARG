package com.cacopaycard.PruebaTecnicaGARG.Repository;

import com.cacopaycard.PruebaTecnicaGARG.Entity.Solicitud;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gibran
 */
@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long>{
 
    public Optional<Solicitud> findByCuenta(Long cuenta);
    public void deleteByCuenta(Long cuenta);
    
}
