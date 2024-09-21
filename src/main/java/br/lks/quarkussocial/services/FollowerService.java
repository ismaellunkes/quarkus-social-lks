package br.lks.quarkussocial.services;

import br.lks.quarkussocial.domain.*;
import br.lks.quarkussocial.rest.dto.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import jakarta.transaction.*;
import jakarta.validation.*;

import java.time.*;
import java.util.*;

@ApplicationScoped
public class FollowerService {

    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;

    private final Validator validator;

    @Inject
    public FollowerService(UserRepository userRepository, FollowerRepository followerRepository, Validator validator) {
        this.userRepository = userRepository;
        this.followerRepository = followerRepository;
        this.validator = validator;
    }

    @Transactional
    public void create(Long idUser, CreateFollowerRequest createFollowerRequest) throws ConstraintViolationException {

        Set<ConstraintViolation<CreateFollowerRequest>> violations = validator.validate(createFollowerRequest);
        if(!violations.isEmpty()){
            throw new ErrorsValidatorException(violations);
        }

        if (idUser == null) {
            throw new ErrorsValidatorException("id user is null");
        }

        if (createFollowerRequest.getFollowerId().equals(idUser)) {
            return;
        }

        if (isUserFollowered(idUser, createFollowerRequest.getFollowerId())) {
            return;
        }

        User user = userRepository.findById(idUser);
        if (user == null) {
            throw new ErrorsValidatorException("Usuário não encontrado");
        }

        User userFollower = userRepository.findById(createFollowerRequest.getFollowerId());
        if (userFollower == null) {
            throw new ErrorsValidatorException("Usuário não encontrado");
        }

        Follower follower = new Follower();
        follower.setUser(user);
        follower.setFollower(userFollower);
        follower.setDataHoraInicio(LocalDateTime.now());
        followerRepository.persist(follower);
    }

    private boolean isUserFollowered(Long idUser, Long idFollower) {
        Optional<Follower> userFollowered =  followerRepository.findFollowerByUserId(idUser, idFollower);
        return userFollowered.isPresent();
    }

    public List<FollowerRequest> listFollowersByUser(Long idUser) {
        if (idUser == null) {
            throw new ErrorsValidatorException("id do usuário não informado");
        }

        List<Follower> followers = followerRepository.findFollowersByUserId(idUser);
        List<FollowerRequest> followerRequests = new ArrayList<>();
        followers.stream().forEach(q -> {
            followerRequests.add(new FollowerRequest(q.getId(), q.getFollower().getId(), q.getFollower().getUsername()));
        });
        return followerRequests;
    }

}
