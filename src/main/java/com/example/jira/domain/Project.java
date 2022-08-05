package com.example.jira.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString//(exclude = {"boards"})
//@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
   // private  Long projectId;
    @Column(length = 11, nullable = false)
    private String id;
    private String name ;

    @OneToMany( mappedBy = "project")
    //@JsonSerialize(using = BoardSerializer.class)
    //  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
   // @JsonIdentityReference(alwaysAsId = true)
    @JsonIgnore
    private Set<Board> boards =  new HashSet<>();

    @ManyToOne
    @JoinColumn
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Category projectCategory;
}
