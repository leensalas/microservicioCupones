package com.cupones.cupones.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cupones.cupones.model.Cupones;
import com.cupones.cupones.repository.CuponRepository;

@Service
public class CuponServices {

    @Autowired
    private CuponRepository cuponRepository;

    public List<Cupones> listarCupones() {
        return cuponRepository.findAll();
    }

    public Cupones buscarPorId(Long idCupon) {
        return cuponRepository.findById(idCupon).orElse(null);
    }

    public Cupones crearCupon(Cupones cupon) {
        return cuponRepository.save(cupon);
    }

    public Cupones actualizarCupon(Long idCupon, Cupones cuponActualizado) {
        Cupones cuponExistente = buscarPorId(idCupon);

        if (cuponExistente == null) {
            return null;
        }

        cuponExistente.setCodigo(cuponActualizado.getCodigo());
        cuponExistente.setPorcentajeDescuento(cuponActualizado.getPorcentajeDescuento());
        cuponExistente.setFechaExpiracion(cuponActualizado.getFechaExpiracion());
        cuponExistente.setActivo(cuponActualizado.getActivo());

        return cuponRepository.save(cuponExistente);
    }

    public boolean eliminarCupon(Long idCupon) {
        if (!cuponRepository.existsById(idCupon)) {
            return false;
        }

        cuponRepository.deleteById(idCupon);
        return true;
    }

    public Map<String, Object> aplicarCupon(String codigo, Double totalCarrito) {
        Cupones cupon = cuponRepository.findByCodigo(codigo).orElse(null);

        Map<String, Object> respuesta = new HashMap<>();

        if (cupon == null) {
            respuesta.put("mensaje", "Cupón no encontrado");
            return respuesta;
        }

        if (!cupon.getActivo()) {
            respuesta.put("mensaje", "Cupón inactivo");
            return respuesta;
        }

        if (cupon.getFechaExpiracion().isBefore(LocalDate.now())) {
            respuesta.put("mensaje", "Cupón vencido");
            return respuesta;
        }

        double descuentoAplicado = totalCarrito * cupon.getPorcentajeDescuento() / 100;
        double totalFinal = totalCarrito - descuentoAplicado;

        respuesta.put("codigo", cupon.getCodigo());
        respuesta.put("porcentajeDescuento", cupon.getPorcentajeDescuento());
        respuesta.put("totalCarrito", totalCarrito);
        respuesta.put("descuentoAplicado", descuentoAplicado);
        respuesta.put("totalFinal", totalFinal);

        return respuesta;
    }
}