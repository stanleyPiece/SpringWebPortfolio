package cz.itnetwork.springblog.models.dto.mappers;

//rozhraní sloužící pro namapování (nakoppírování) hodnot z atributů instance jedné třídy do atributů druhé třídy

import cz.itnetwork.springblog.database.entities.ArticleEntity;
import cz.itnetwork.springblog.models.dto.ArticleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring") //@Mapper - "mapovač" atributů, parametr compnentModel - říká, že chceme vygenerovat implementaci pro framework SpringBoot


public interface ArticleMapper {

    ArticleEntity toEntity(ArticleDTO sourceArticleDTO); //metoda pro převedení atributů z formuláře webové aplikace na atributy sloupců záznamu (řádku) v tabulce databáze SQL

    ArticleDTO toDTO(ArticleEntity sourceArticle); //metoda pro převedení atributů ze záznamu (řádku) v tabulce databáze SQL na atributy ve formuláři webové aplikace

    void updateArticleDTO(ArticleDTO source, @MappingTarget ArticleDTO target); //metoda pro zkopírování dat z jedné přepravky do druhé, již EXISTUJÍCÍ přepravky
                                                                                //@MappingTarget - cíl, kam se kopíruje
    void updateArticleEntity(ArticleDTO source, @MappingTarget ArticleEntity target); //metoda pro zkopírování dat z přepravky do existující entity (třídy představující článek)
}
