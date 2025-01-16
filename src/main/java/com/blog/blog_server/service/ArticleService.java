package com.blog.blog_server.service;

import com.blog.blog_server.model.ArticleModel;
import com.blog.blog_server.repository.ArticleRepo;
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
    public ArticleService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    public void postArticle(ArticleModel articleModel) {
        try {
            articleRepo.save(articleModel);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public ArticleModel getArticle(String id) {
        try{
            System.out.println(id);
            Optional<ArticleModel> article = articleRepo.findById(id);
            return article.orElse(null);
        }catch(Exception e){
            throw new RuntimeException("Error getting article: " + e.getMessage());
        }
    }

    public List<ArticleModel> getLatest7Articles() {
        Pageable pageable = PageRequest.of(0, 7, Sort.by(Sort.Direction.DESC, "createdAt"));
        return articleRepo.findAllByOrderByCreatedAtDesc(pageable);
    }

    public void likeArticle(String id, String action) {
        try {
            Optional<ArticleModel> article = articleRepo.findById(id);
            article.ifPresent(articleModel -> {
                if (action.equals("like")) {
                    articleModel.setLikes(articleModel.getLikes() + 1);
                } else if (action.equals("unlike")) {
                    if (articleModel.getLikes() > 0) {
                        articleModel.setLikes(articleModel.getLikes() - 1);
                    }
                }
                articleRepo.save(articleModel);
            });
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Article not found with ID: " + id, e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating article: " + e.getMessage(), e);
        }
    }

}
