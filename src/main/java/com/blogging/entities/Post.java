package com.blogging.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="POSTS_TBL")
// @Table(name="posts",uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post {
    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="title", length =100, nullable= false)
    private String title;

    @Column(name="content", length =100, nullable= false)
    private String content;

    @Column(name="image")
    private String imageName;

    @Column(name="date")
    private Date addedDate;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
}
