package br.lks.quarkussocial.domain;

import io.quarkus.hibernate.orm.panache.*;
import io.quarkus.panache.common.*;
import jakarta.enterprise.context.*;

import java.util.*;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {

    public List<Follower> findUsersByFollowerId(Long followerId) {
        return find("follower.id = ?1", followerId).list();
    }

    public List<Follower> findFollowersByUserId(Long userId) {
        return find("user.id = ?1", userId).list();
    }

    public Optional<Follower> findFollowerByUserId(Long userId, Long followerId) {
        return find("user.id = ?1 and follower.id = ?2", userId, followerId).firstResultOptional();
    }
}
