package com.blog.blog_server.service;

import com.blog.blog_server.model.ArticleModel;
import com.blog.blog_server.model.LikesModel;
import com.blog.blog_server.repository.ArticleRepo;
import com.blog.blog_server.repository.LikeRepo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Pageable;

@Service
public class ArticleService {
    private final ArticleRepo articleRepo;
    private final LikeRepo likeRepo;

    public ArticleService(ArticleRepo articleRepo, LikeRepo likeRepo) {
        this.articleRepo = articleRepo;
        this.likeRepo = likeRepo;
    }

    public void postArticle(ArticleModel articleModel) {
        try {
            articleRepo.save(articleModel);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public ArticleModel getArticle(String id, String userid) {
        try {
            Optional<ArticleModel> article = articleRepo.findById(id);
            Optional<LikesModel> userLike = likeRepo.findByUserID(userid);
            if(!article.isPresent()){
                throw new NoSuchElementException();
            }
    
            userLike.ifPresent(like -> setIsLikedForArticle(id, like, article));
    
            return article.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error getting article: " + e.getMessage());
        }
    }
    

    public List<ArticleModel> getLatest7Articles(String userid) {
        Pageable pageable = PageRequest.of(0, 7, Sort.by(Sort.Direction.DESC, "createdAt"));
        Optional<LikesModel> userLike = likeRepo.findByUserID(userid);
 
        List<ArticleModel> articles = articleRepo.findAllByOrderByCreatedAtDesc(pageable);
  
        userLike.ifPresentOrElse(
                like -> articles.forEach(article -> setIsLikedForArticle(article.getId(), like, Optional.of(article))),
                () -> {}  
        );
    
        return articles;
    }
    

    public void likeArticle(String id, String action, String userId) {
        try {
            Optional<ArticleModel> articleOpt = articleRepo.findById(id);
            Optional<LikesModel> userLikeOpt = likeRepo.findByUserID(userId);
    
            articleOpt.ifPresentOrElse(article -> {
                if ("like".equalsIgnoreCase(action)) {
                    if (handleLikeAction(userLikeOpt, id, userId)) {
                        article.setLikes(article.getLikes() + 1); 
                    }
                } else if ("unlike".equalsIgnoreCase(action)) {
                    if (handleUnlikeAction(userLikeOpt, id)) {
                        if (article.getLikes() > 0) {
                            article.setLikes(article.getLikes() - 1); 
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Invalid action: " + action);
                }
                articleRepo.save(article);
            }, () -> {
                throw new NoSuchElementException("Article not found with ID: " + id);
            });
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Error processing like action: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }
    

    private boolean handleLikeAction(Optional<LikesModel> userLikeOpt, String postId, String userId) {
        return userLikeOpt.map(like -> {
            if (!like.checkIfUserLikedPost(postId)) {
                like.addLikedPost(postId);
                likeRepo.save(like);
                return true; 
            }
            return false; 
        }).orElseGet(() -> {
            LikesModel newLike = new LikesModel();
            newLike.setUserID(userId);
            newLike.addLikedPost(postId);
            likeRepo.save(newLike);
            return true; 
        });
    }
    

    private boolean handleUnlikeAction(Optional<LikesModel> userLikeOpt, String postId) {
        return userLikeOpt.map(unlike -> {
            if (unlike.checkIfUserLikedPost(postId)) {
                unlike.removeLikedPost(postId);
                likeRepo.save(unlike);
                return true; 
            }
            return false; 
        }).orElse(false); 
    }
    
    private void setIsLikedForArticle(String postId, LikesModel userLike, Optional<ArticleModel> article) {
        boolean isLiked = userLike.checkIfUserLikedPost(postId);
        article.ifPresent(articleModel -> articleModel.setLiked(isLiked));
    }
    
}
