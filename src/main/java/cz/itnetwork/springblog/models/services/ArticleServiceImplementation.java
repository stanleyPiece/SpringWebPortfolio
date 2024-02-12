package cz.itnetwork.springblog.models.services;

import cz.itnetwork.springblog.database.entities.ArticleEntity;
import cz.itnetwork.springblog.database.repositories.ArticleRepository;
import cz.itnetwork.springblog.models.dto.ArticleDTO;
import cz.itnetwork.springblog.models.dto.mappers.ArticleMapper;
import cz.itnetwork.springblog.models.exceptions.ArticleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ArticleServiceImplementation implements ArticleService {


    @Autowired //anotace sloužící k propojení služeb
    private ArticleRepository articleRepository;

    //mapovač atributů pro atributy DTO na atributy Entity
    @Autowired //anotace sloužící k propojení služeb
    private ArticleMapper articleMapper;

    //metoda pro vytvoření článku v tabulce databáze SQL
    @Override
    public void createArticle(ArticleDTO articleDTO) {

        ArticleEntity newArticle = articleMapper.toEntity(articleDTO);

        /* způsob namapování atributů bez využití mapovače

        ArticleEntity newArticle = new ArticlesEntity();

        newArticle.setTitle(articleDTO.getTitle()); //nastavíme titulek
        newArticle.setContent(articleDTO.getContent()); //nastavíme obsah
        newArticle.setDescription(articleDTO.getDescription()); // nastavíme popis

        */

        articleRepository.save(newArticle); //uložíme do databáze/tabulky
    }

    //metoda pro získání všech článků z tabulky databáze SQL
    @Override
    public List<ArticleDTO> getAllArticles() {
        return StreamSupport.stream(articleRepository.findAll().spliterator(), false)
                .map(articleEntity -> articleMapper.toDTO(articleEntity))
                .toList();
    }

    @Override
    public ArticleDTO getArticleById(long articleId) {
        ArticleEntity fetchedArticle = getArticleOrThrow(articleId);
        /*
        // téměř totožný kód jako níže v metodě edit, vytvoříme pomocnou metodu, abychom nemuseli opakovat jeho psaní
        ArticleEntity fetchedArticle = articleRepository.findById(articleId)
                                                          .orElseThrow(); //vyvolání výjimky, pokud objekt neexistuje
        */
        return articleMapper.toDTO(fetchedArticle);
    }

    // metoda pro úpravu článku
    @Override
    public void editArticle(ArticleDTO article) {
        ArticleEntity fetchedArticle = getArticleOrThrow(article.getArticleId());
        /*
        // téměř totožný kód jako výše v metodě getById, vytvoříme pomocnou metodu, abychom nemuseli opakovat jeho psaní
        ArticleEntity fetchedArticle = articleRepository.findById(article.getArticleId())
                                                         .orElseThrow();
        */
        articleMapper.updateArticleEntity(article, fetchedArticle);
        articleRepository.save(fetchedArticle);
    }

    //pomocná metoda pro získání článku
    private ArticleEntity getArticleOrThrow(long articleId) {

        return articleRepository
                .findById(articleId)
                .orElseThrow(ArticleNotFoundException::new);

    }

    //metoda pro odstranění článku
    @Override
    public void removeArticle(long articleId) {
        ArticleEntity fetchedEntity = getArticleOrThrow(articleId);
        articleRepository.delete(fetchedEntity);
    }
}
