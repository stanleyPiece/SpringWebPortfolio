package cz.itnetwork.springblog.database.entities;

import jakarta.persistence.*;

    /* další anotace
       @Table - definuje se nad třídou, uvedeme název tabulky v databázi (když má být jiný než název třídy)
       @Enumerated (nepoužito) - definuje se u atributu, k ukládání výčtových datových typů, v databázi pak mohou nabývat jen daných hodnot
       @Lob (nepoužito) - definuje se u atributu, vhodné pro ukládání delšího článku nebo souboru v binární podobě
     */

    @Table(name = "clanky")
    @Entity //anotace, která sděluje, že se jedná o třídu, která reprezentuje záznam (řádek) v tabulce databáze (entita)
    public class ArticleEntity {

        @Id //označuje, že se jedná o unikátní identifikační číslo (primární klíč tabulky SQL)
        @GeneratedValue(strategy = GenerationType.IDENTITY) //způsob generování nových ID, hodnota IDENTITY - databáze bude využívat zabudovanou číselnou řadu (každé ID bude o jedno vyšší)
        private Long articleId; // velké L pro long, na začátku, když posíláme data, může ID být NULL

        @Column(nullable = false) //@Column - atribut představuje jeden sloupec v tabulce, parametr nullable - zda může mít sloupec hodnotu NULL, parametr type - datový typ sloupce (INT, VARCHAR, DATE atd.)
        private String title;

        @Column(nullable = false)
        private String description;

        @Column(nullable = false, columnDefinition = "TEXT")
        private String content;

        public long getArticleId() {
            return articleId;
        }

        public void setArticleId(long articleId) {
            this.articleId = articleId;
        }

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

        public void setContent(String content) {
            this.content = content;
        }
    }

