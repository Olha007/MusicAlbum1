package ua.edu.znu.musicAlbum.hibernateapp.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id", nullable = false)
    private Long id;


    @Column(name = "first_name")
    private String firstName;


    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<AlbumArtistGroup> albumArtistGroups = new LinkedHashSet<>();

}