package com.cupones.cupones.controller;

import com.cupones.cupones.model.Cupones;
import com.cupones.cupones.service.CuponServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cupones")
public class CuponController {

    @Autowired
    private CuponServices cuponServices;

    @GetMapping
    public List<Cupones> listarCupones() {
        return cuponServices.listarCupones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cupones> buscarPorId(@PathVariable Long id) {
        Cupones cupones = cuponServices.buscarPorId(id);

        if (cupones == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cupones);
    }

    @PostMapping
    public Cupones crearCupon(@RequestBody Cupones cupon) {
        return cuponServices.crearCupon(cupon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cupones> actualizarCupon(@PathVariable Long id, @RequestBody Cupones cupon) {
        Cupones cuponActualizado = cuponServices.actualizarCupon(id, cupon);

        if (cuponActualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cuponActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCupon(@PathVariable Long id) {
        boolean eliminado = cuponServices.eliminarCupon(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Cupón eliminado correctamente");
    }

    @PostMapping("/aplicar")
    public Map<String, Object> aplicarCupon(
            @RequestParam String codigo,
            @RequestParam Double totalCarrito
    ) {
        return cuponServices.aplicarCupon(codigo, totalCarrito);
    }
}