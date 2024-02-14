package ua.edu.znu.musicAlbum.hibernateapp.entities;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "album_artist_group")
public class AlbumArtistGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "group_id")
    private Group group;
}