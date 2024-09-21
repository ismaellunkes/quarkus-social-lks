package br.lks.quarkussocial.services;

import br.lks.quarkussocial.domain.*;
import br.lks.quarkussocial.rest.dto.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import jakarta.transaction.*;
import jakarta.validation.*;

import java.util.*;

@ApplicationScoped
public class PostService {

    private final FollowerRepository followerRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final Validator validator;

    @Inject
    public PostService(FollowerRepository followerRepository, PostRepository postRepository, UserRepository userRepository, Validator validator) {
        this.followerRepository = followerRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @Transactional
    public PostRequest create(CreatePostRequest postRequest) throws ConstraintViolationException {

        Set<ConstraintViolation<CreatePostRequest>> violations = validator.validate(postRequest);
        if(!violations.isEmpty()){
            throw new ErrorsValidatorException(violations);
        }

        User user = userRepository.findById(postRequest.getUserId());
        if (user == null) {
            throw new ErrorsValidatorException("Usuário não encontrado");
        }

        Post post = new Post();
        post.setUser(user);
        post.setContent(postRequest.getContent());
        postRepository.persist(post);

        return new PostRequest(post.getId(), post.getUser().getId(), post.getUser().getUsername(), post.getContent());
    }

    public List<PostRequest> listPostsByUser(Long idUser) {
        if (idUser == null) {
            throw new ErrorsValidatorException("id do usuário não informado");
        }

        List<Post> posts = postRepository.findPostByUserId(idUser);
        List<PostRequest> postRequests = new ArrayList<>();
        posts.stream().forEach(q -> {
            postRequests.add(new PostRequest(q.getId(), q.getUser().getId(), q.getUser().getUsername(), q.getContent()));
        });
        return postRequests;
    }

    public List<PostTimelineRequest> listPostsTimeLineByIdUser(Long idUser) {
        if (idUser == null) {
            throw new ErrorsValidatorException("id do usuário não informado");
        }

        List<Follower> usersFollowered = followerRepository.findFollowersByUserId(idUser);
        List<PostTimelineRequest> postsTimeLine = new ArrayList<>();

        usersFollowered.forEach(f -> {
            List<Post> postsFollowered = postRepository.findPostByUserId(f.getUser().getId());
            postsFollowered.forEach(p -> {
                PostTimelineRequest pr = new PostTimelineRequest(p.getUser().getUsername(), p.getContent());
                postsTimeLine.add(pr);
            });
        });

        return postsTimeLine;
    }

    @Transactional
    public void delete (Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new ErrorsValidatorException("User not found.");
        }
        userRepository.delete(user);
    }

    public PostRequest findById(Long id) {
        Optional<Post> post = postRepository.findByIdOptional(id);
        return post.map(value -> new PostRequest(value.getId(), value.getUser().getId(), value.getUser().getUsername(), value.getContent())).orElse(null);
    }

}
