package com.service.wallet_service.domain.entities;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("user_document")
public class User {

    @PersistenceCreator
    public User(String _id, String name, String surname, String cpf, String email, String phone, List<Wallet> wallets) {
        this._id = _id;
        this.name = name;
        this.surname = surname;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.wallets = wallets;
    }

    private User(Builder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.cpf = builder.cpf;
        this.email = builder.email;
        this.phone = builder.phone;
        this.wallets = new ArrayList<>();
    }

    @Id
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    private String _id;

    private String name;
    private String surname;
    private String cpf;
    private String email;
    private String phone;
    private List<Wallet> wallets;

    public static class Builder {
        private String name;
        private String surname;
        private String cpf;
        private String email;
        private String phone;

        public User build() {
            return new User(this);
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder setCpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }
    }
}
