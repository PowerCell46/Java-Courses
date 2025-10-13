package com.springweb.repositories;

import com.springweb.models.entities.Comment;
import com.springweb.models.projections.CommentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(path = "comments", excerptProjection = CommentProjection.class)
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    CommentProjection findCommentById(@Param("id") Integer id);
//    GET ONE Request: http://localhost:8080/comments/search/findCommentById?id=1


    @Query("SELECT c FROM Comment c ORDER BY c.id DESC")
    List<Comment> findAllComments();
//    GET ALL Request: http://localhost:8080/comments/search/findAllComments


//    POST Request:  http://localhost:8080/comments
//    {
//        "comment": "Hello There",
//        "user": "/users/11"
//    }


//    PATCH: http://localhost:8080/comments/search/findCommentById?id=7
//    {
//        "comment": "Kafka e Ebavka",
//        "user": "/users/19"
//    }

//    DELETE: http://localhost:8080/comments/7
}
