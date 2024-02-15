package ua.edu.znu.musicAlbum.hibernateapp.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id", nullable = false)
    private Long id;

    @Column(name = "song_name")
    private String songName;

    @Column(name = "duration_minutes")
    private Long durationMinutes;

    @Column(name = "duration_seconds")
    private Long durationSeconds;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "songs", cascade = CascadeType.PERSIST)
    private Set<Album> albums = new LinkedHashSet<>();
}
