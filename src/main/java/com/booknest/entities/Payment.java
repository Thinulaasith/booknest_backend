package com.booknest.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Double amount;

    private String paymentMethod;

    private String paymentStatus;

    private String transactionId;

    private LocalDateTime paymentDate;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @PrePersist
    public void prePersist() {
        this.paymentDate = LocalDateTime.now();
    }
}