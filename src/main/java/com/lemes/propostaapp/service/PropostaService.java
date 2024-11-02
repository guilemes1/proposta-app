package com.lemes.propostaapp.service;

import com.lemes.propostaapp.dto.PropostaRequestDto;
import com.lemes.propostaapp.dto.PropostaResponseDto;
import com.lemes.propostaapp.entity.Proposta;
import com.lemes.propostaapp.mapper.PropostaMapper;
import com.lemes.propostaapp.repository.PropostaRepository;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;

    private final NotificacaoRabbitService notificacaoService;

    private final String exchange;

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

        int prioridade = proposta.getUsuario().getRenda() > 10000 ? 10 : 5;
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setPriority(prioridade);
            return message;
        };

        PropostaResponseDto response = PropostaMapper.INSTANCE.convertEntityToDto(proposta);
        notificarRabbitMq(proposta, messagePostProcessor);

        return response;
    }

    public void notificarRabbitMq(Proposta proposta, MessagePostProcessor messagePostProcessor) {
        try {
            notificacaoService.notificar(proposta, exchange, messagePostProcessor);
        } catch (RuntimeException exception) {
            proposta.setIntegrada(false);
            propostaRepository.save(proposta);
        }

    }

    public List<PropostaResponseDto> obterProposta() {
        return PropostaMapper.INSTANCE.convertListEntityToListDto(propostaRepository.findAll());
    }
}

