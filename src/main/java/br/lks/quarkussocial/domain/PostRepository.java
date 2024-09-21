package br.lks.quarkussocial.domain;

import io.quarkus.hibernate.orm.panache.*;
import jakarta.enterprise.context.*;

import java.util.*;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {

    public List<Post> findPostByUserId(Long userId) {
        return find("user.id = ?1", userId).list();
    }
}
