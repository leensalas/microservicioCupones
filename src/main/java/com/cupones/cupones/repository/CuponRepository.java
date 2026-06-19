package com.cupones.cupones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cupones.cupones.model.Cupones;

@Repository
public interface CuponRepository extends JpaRepository<Cupones, Long> {

    Optional<Cupones> findByCodigo(String codigo);
}