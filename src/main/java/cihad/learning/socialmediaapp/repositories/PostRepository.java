package cihad.learning.socialmediaapp.repositories;

import cihad.learning.socialmediaapp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    //bu metot imzasinda; findBy bir kalip olarak kullanilir, ardindan yazilan parametreyi
    // JPARepository kendisi algilayarak buna gore arama yapar. metot olusturmak gerekmez.
    List<Post> findByUserId(Long userId);
}
