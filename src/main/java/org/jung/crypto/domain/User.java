package org.jung.crypto.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;
    private String username;
    private String password;
    private String role;
    private Double balance;

    public void decreaseBalance(Double balance) {
        this.balance = this.balance - balance;
    }
    public void increaseBalance(Double balance) {
        this.balance = this.balance + balance;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
