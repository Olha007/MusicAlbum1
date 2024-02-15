package ua.edu.znu.musicAlbum.hibernateapp.entities;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "album")

@NamedQueries({
        @NamedQuery(name = "Album.findByNameOrderByReleaseYearAsc", query = "SELECT a FROM Album a WHERE a.albumName = :name ORDER BY a.releaseYear ASC"),
        @NamedQuery(name = "Album.updateReleaseYearByName",
                query = "UPDATE Album a SET a.releaseYear = :releaseYear WHERE a.albumName = :name"),
        @NamedQuery(name = "Album.deleteByName",
                query = "DELETE FROM Album a WHERE a.albumName = :name")
})

public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id", nullable = false)
    private Long id;

    @Column(name = "album_name")
    private String albumName;

    @Column(name = "release_year")
    private Long releaseYear;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "album_songs",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private Set<Song> songs = new LinkedHashSet<>();
}
