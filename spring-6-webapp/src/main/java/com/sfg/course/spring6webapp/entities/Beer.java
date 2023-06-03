package com.sfg.course.spring6webapp.entities;

import com.sfg.course.spring6webapp.beer.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Beer {
    @Id
    @GeneratedValue(generator ="UUID")
    @GenericGenerator(name="UUID",strategy="org.hibernate.id.UUIDGenerator")
    @Column(length = 36,columnDefinition = "varchar",updatable=false,nullable=false)
    private UUID id;

    @Version
    private Integer version;

    @Size(max = 30)//bean validation.will get-jakarta.validation.ConstraintViolationException: Validation failed at the bean level
    @Column(length =30)//validation at the database level.will get-org.springframework.dao.DataIntegrityViolationException
    @NotNull
    @NotBlank
    private String beerName;

    @NotNull
    private BeerStyle beerStyle;

    @NotNull
    @NotBlank
    @Size(max=255)
    private String upc;
    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
