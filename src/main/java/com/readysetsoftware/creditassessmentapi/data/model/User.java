package com.readysetsoftware.creditassessmentapi.data.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="vUser")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "iUserID")
    private Integer id;
    @Column(name = "cNTUserID")
    private String username;
    @Column(name = "cPassword")
    private String password;
    @Column(name = "cRole")
    private String role;

}
