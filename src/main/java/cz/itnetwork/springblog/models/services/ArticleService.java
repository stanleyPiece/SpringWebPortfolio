package cz.itnetwork.springblog.models.services;

import cz.itnetwork.springblog.models.dto.ArticleDTO;

import java.util.List;

//toto rozhraní obsahuje všechnu logiku a manipulaci s databází
public interface ArticleService {

    void createArticle(ArticleDTO article);

    List<ArticleDTO> getAllArticles();

    ArticleDTO getArticleById(long articleId);

    void editArticle(ArticleDTO article);

    void removeArticle(long articleId);
}
