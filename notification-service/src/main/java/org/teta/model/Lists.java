package org.teta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "lists")
public class Lists {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "list_name", nullable = false)
    private String listName;
}
