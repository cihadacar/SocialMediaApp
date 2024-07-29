package cihad.learning.socialmediaapp.repositories;

import cihad.learning.socialmediaapp.entities.Post;
import org.hibernate.annotations.ParamDef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    //bu metot imzasinda; findBy bir kalip olarak kullanilir, ardindan yazilan parametreyi
    // JPARepository kendisi algilayarak buna gore arama yapar. metot olusturmak gerekmez.
    List<Post> findByUserId(Long userId);
    @Query(value = "select id from post where user_id = :userId order by create_date desc limit 5 ", nativeQuery = true)
    List<Long> findTopByUserId(@Param("userId") Long userId);
}
