package ru.neoflex.cryptBot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "candle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Candle {
    @Id
    @Column(name = "figi", length = 100)
    private String figi;

    @Column(name = "interval", length = 100)
    private String interval;

    @Column(name = "low", length = 100)
    private String low;

    @Column(name = "high", length = 100)
    private String high;

    @Column(name = "open", length = 100)
    private String open;

    @Column(name = "close", length = 100)
    private String close;

    @Column(name = "open_time", length = 100)
    private Long openTime;
}
