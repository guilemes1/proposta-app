package com.pieropan.propostaapp.repository;

import com.pieropan.propostaapp.entity.Proposta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PropostaRepository extends CrudRepository<Proposta, Long> {

    List<Proposta> findAllByIntegradaIsFalse();
}
