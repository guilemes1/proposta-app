package com.pieropan.propostaapp.service;

import com.pieropan.propostaapp.dto.PropostaRequestDto;
import com.pieropan.propostaapp.dto.PropostaResponseDto;
import com.pieropan.propostaapp.entity.Proposta;
import com.pieropan.propostaapp.mapper.PropostaMapper;
import com.pieropan.propostaapp.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    private PropostaRepository propostaRepository;

    private NotificacaoRabbitService notificacaoService;

    private String exchange;

    public PropostaService(PropostaRepository propostaRepository,
                           NotificacaoRabbitService notificacaoService,
                           @Value("${rabbitmq.propostapendente.exchange}") String exchange) {
        this.propostaRepository = propostaRepository;
        this.notificacaoService = notificacaoService;
        this.exchange = exchange;
    }

    public PropostaResponseDto criar(PropostaRequestDto requestDto) {
        Proposta proposta = PropostaMapper.INSTANCE.converteDtoToProposta(requestDto);
        propostaRepository.save(proposta);
        PropostaResponseDto response = PropostaMapper.INSTANCE.convertEntityToDto(proposta);
        notificarRabbitMq(proposta);

        return response;
    }

    public void notificarRabbitMq(Proposta proposta) {
        try {
            notificacaoService.notificar(proposta, exchange);
        } catch (RuntimeException exception) {
            proposta.setIntegrada(false);
            propostaRepository.save(proposta);
        }

    }

    public List<PropostaResponseDto> obterProposta() {
        return PropostaMapper.INSTANCE.convertListEntityToListDto(propostaRepository.findAll());
    }
}

