package com.example.reportes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;
    @NotBlank(message = "Name is required.")
    private String name;
    @NotBlank(message = "User name is required.")
    private String userName;
    @NotBlank(message = "Email is required.")
    private String email;
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "companyId", referencedColumnName = "companyId")
    //private Company company;
}
