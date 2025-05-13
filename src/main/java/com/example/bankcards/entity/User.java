package com.example.bankcards.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Schema(description = "Сущность пользователя")
@Entity @Table(name = "user")
public class User {
    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Имя пользователя", example = "Илья")
    @Column(name = "firstName", nullable = false)
    private String first_name;

    @Schema(description = "Фамилия пользователя", example = "Чикалев")
    @Column(name = "secondName", nullable = false)
    private String second_name;

    @Schema(description = "Отчетство пользователя", example = "Максимович")
    @Column(name = "middleName", nullable = false)
    private String middle_name;

    @Schema(description = "Карточки пользователя")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id")
    private Set<Card> cards = new HashSet<>();

    @Schema(description = "Роль пользователя", example = "user")
    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public long getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }
}
