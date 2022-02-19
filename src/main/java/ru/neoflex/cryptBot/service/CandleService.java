package ru.neoflex.cryptBot.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import ru.neoflex.cryptBot.entity.Candle;
import ru.neoflex.cryptBot.repository.CandleRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CandleService {
    private final CandleRepository candleRepository;

    @Transactional
    public Candle findById (String id) throws ServiceException {
        return candleRepository.findByFigi(id).orElseThrow(() -> new ServiceException("No such crypt with the specified ID"));
    }
}
