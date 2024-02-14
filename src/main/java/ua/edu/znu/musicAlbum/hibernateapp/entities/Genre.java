package ua.edu.znu.musicAlbum.hibernateapp.entities;

import lombok.*;
import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id", nullable = false)
    private Long id;


    @Column(name = "genre_name")
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "genre", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Song> songs = new LinkedHashSet<>();

}

