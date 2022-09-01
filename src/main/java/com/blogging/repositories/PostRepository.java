package com.blogging.repositories;

import com.blogging.entities.Category;
import com.blogging.entities.Post;
import com.blogging.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);
    /* We will use this method if hibernate version causes the problem
    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key") String title);
     */

}
