package org.jung.crypto.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
public class Hold extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hold_id")
    private Long id;
    private String symbol;
    private Double amount;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void increaseAmount(Double amount) {
        this.amount += amount;
    }

    public void decreaseAmount(Double amount) {
        this.amount -= amount;
    }
}
