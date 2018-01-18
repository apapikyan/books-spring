package books.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "book")
public @Data class Book {

    private static final long serialVersionUID = 4910225916550731446L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "author", length = 50)
    private String author;

    @Column(name = "price")
    private Integer price;


}
