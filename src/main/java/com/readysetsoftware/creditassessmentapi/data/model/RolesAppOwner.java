package com.readysetsoftware.creditassessmentapi.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "vAppRoleOwners")
public class RolesAppOwner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "cRoleName")
    private String roleName;

    @Column(name = "cRoleOwner")
    private String roleOwner;

    @ManyToOne
    @JoinColumn(name="iAppID")
    @JsonIgnoreProperties("rolesAppOwners")
    private Application application;
}
