package com.yellow.test.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "images")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GenericGenerator(
            name = "imagesSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "images_seq")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imagesSequenceGenerator")
    private long id;

    @Column(name = "uuid", nullable = false, unique = true, length = 100)
    private String uuid;

    @Column(name = "filename", length = 100)
    private String filename;

    @Column(name = "content_type", length = 50)
    private String contentType;

    @Column(name = "data")
    private byte[] data;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_images_users"))
    private User user;

}
