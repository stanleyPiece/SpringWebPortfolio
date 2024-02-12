package cz.itnetwork.springblog.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ArticleDTO {

    @NotBlank(message = "Vyplňte titulek") //@NotBlank - zda hodnota není prázdná a zda neobsahuje pouze bílé znaky = mezery
    @Size(max = 100, message = "Titulek je příliš dlouhý") //@Size - maximální počet znaků v textovém řetězci,
                                                           // parametr max - kolik znaků, parametr message - zpráva, která se zobrazí v případě chyby
    private String title;

    @NotBlank(message = "Vyplňte popisek")
    private String description;

    @NotBlank(message = "Vyplňte obsah")
    private String content;

    private long articleId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String concent) {
        this.content = concent;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }
}
