package com.cupones.cupones.DTO;

import java.time.LocalDate;

import com.cupones.cupones.model.Cupones;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuponesDTO {
    private Long idCupon;
    private String codigo;
    private Integer porcentajeDescuento;
    private LocalDate fechaExpiracion;
    private Boolean activo;

    public Cupones toModel() {
        return new Cupones(idCupon, codigo, porcentajeDescuento, fechaExpiracion, activo);
    }

    public static CuponesDTO fromModel(Cupones c) {
        if (c == null)
            return null;
        return new CuponesDTO(c.getIdCupon(), c.getCodigo(), c.getPorcentajeDescuento(), c.getFechaExpiracion(),
                c.getActivo());
    }
}
