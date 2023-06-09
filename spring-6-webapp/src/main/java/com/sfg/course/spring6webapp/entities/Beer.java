package com.sfg.course.spring6webapp.entities;

import com.sfg.course.spring6webapp.model.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Beer {

    public Beer(UUID id, Integer version, String beerName, BeerStyle beerStyle, String upc, Integer quantityOnHand, BigDecimal price, LocalDateTime createdDate, LocalDateTime updateDate, Set<BeerOrderLine> beerOrderLines, Set<Category> categories) {
        this.id = id;
        this.version = version;
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.upc = upc;
        this.quantityOnHand = quantityOnHand;
        this.price = price;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.beerOrderLines = beerOrderLines;
        this.categories=categories;
    }



    @Id
    @GeneratedValue(generator ="UUID")
    @GenericGenerator(name="UUID",strategy="org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)//used for debugging,not for prod code
    @Column(length = 36,columnDefinition = "varchar(36)",updatable=false,nullable=false)//36 to varchar added for mysql configuration
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

    @CreationTimestamp//hibernate specific annotation not spring data jpa
    private LocalDateTime createdDate;

    @UpdateTimestamp//hibernate specific annotation not spring data jpa
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "beer")
    private Set<BeerOrderLine> beerOrderLines;
    @Builder.Default
    @ManyToMany
    @JoinTable(name = "beer_category",
            joinColumns = @JoinColumn(name = "beer_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories=new HashSet<>();
    public void addCategoryToCategories(Category category) {
        this.categories.add(category);
        category.getBeers().add(this);

    }

    public void  removeCategory(Category category){
        this.categories.remove(category);
    }
}
