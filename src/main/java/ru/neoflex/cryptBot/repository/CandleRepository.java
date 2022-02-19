package ru.neoflex.cryptBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.neoflex.cryptBot.entity.Candle;

import java.util.Optional;

public interface CandleRepository extends JpaRepository<Candle, Long> {
    Optional<Candle> findByFigi(String id);
}
