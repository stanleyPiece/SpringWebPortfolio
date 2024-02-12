package cz.itnetwork.springblog.controllers;

import cz.itnetwork.springblog.models.dto.ArticleDTO;
import cz.itnetwork.springblog.models.dto.mappers.ArticleMapper;
import cz.itnetwork.springblog.models.exceptions.ArticleNotFoundException;
import cz.itnetwork.springblog.models.services.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    //injektáž služby, která využívá repozitář, který pracuje s databází
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

    //akce pro vykreslení stránky se články
    @GetMapping
    public String renderIndex(Model model) {
        List<ArticleDTO> articles = articleService.getAllArticles();
        model.addAttribute("articles", articles);

        return "pages/articles/articleIndex";
    }

    //akce pro vykreslení stránky pro vytvoření článku
    @Secured("ROLE_ADMIN") //přiřazení oprávnění pro zobrazení formuláře pro vytvoření článku k roli administrátora
    @GetMapping("createArticle")
    public String renderCreateForm(@ModelAttribute ArticleDTO articleDTO) {
        return "pages/articles/createArticle";
    }

    //akce pro odeslání vyplněného formuláře nového článku
    @PostMapping("createArticle")
    @Secured("ROLE_ADMIN") //přiřazení oprávnění pro provedení akce vytvoření článku k roli administrátora
    public String createArticle(
            @Valid @ModelAttribute ArticleDTO articleDTO, //@Valid - chceme atributy této přepravky validovat
            BindingResult result, //objekt, který obsahuje informace o formulářových chybách, lekce 6 JAVA PRO SpringBoot Blog
            RedirectAttributes redirectAttributes // rozhraní pro flash zprávy
    ) {
        if (result.hasErrors()) //metoda, kterou se ptáme, zda uživatel vyplnil alespoň jedno pole chybně - pokud ano, formulář vykreslí znovu s chybovými hláškami
            return renderCreateForm(articleDTO);

        // System.out.println(articleDTO.getTitle() + " – " + articleDTO.getDescription()); // <-- pro výpis do konzole
        articleService.createArticle(articleDTO); // <-- přidání článku do databáze
        redirectAttributes.addFlashAttribute("success", "Článek byl vytvořen."); //flash zpráva k informování uživatele o úspěšném vytvoření článku

        return "redirect:/articles";
    }

    //akce pro vykreslení hledaného článku
    @GetMapping("{articleId}") //v cestě je proměnná, za kterou se dosadí ID článku, např. /articles/123 vrátí článek s ID 123
    public String renderArticleDetail(@PathVariable long articleId, //s pomocí @PathVariable dosadíme výše zmíněné ID článku
                                      Model model) {
        ArticleDTO fetchedArticle = articleService.getArticleById(articleId);
        model.addAttribute("article", fetchedArticle);

        return "pages/articles/articleDetail";
    }

    //akce pro vykreslení článku za účelem editace (úprav)
    @GetMapping("editArticle/{articleId}") //v cestě je proměnná, za kterou se dosadí ID článku, např. /articles/123 upraví článek s ID 123
    @Secured("ROLE_ADMIN") //přiřazení oprávnění pro zobrazení formuláře pro úpravu článku k roli administrátora
    public String renderEditForm(@PathVariable Long articleId, //s pomocí @PathVariable dosadíme výše zmíněné ID článku
                                 ArticleDTO article) {
        ArticleDTO fetchedArticle = articleService.getArticleById(articleId);
        articleMapper.updateArticleDTO(fetchedArticle, article);

        return "pages/articles/editArticle";
    }

    //akce pro odeslání nových (upravených) hodnot upravovaného článku
    @PostMapping("editArticle/{articleId}")
    @Secured("ROLE_ADMIN") //přiřazení oprávnění pro zobrazení provedení akce úpravy článku k roli administrátora
    public String editArticle(@PathVariable long articleId,
                              @Valid ArticleDTO article, //kontrola, zda je upravený formulář validní (platný)
                              BindingResult result, //objekt, který obsahuje informace o formulářových chybách, lekce 6 JAVA PRO SpringBoot Blog
                              RedirectAttributes redirectAttributes // rozhraní pro flash zprávy, lekce 16 JAVA PRO SpringBoot blog
    ) {
        if (result.hasErrors())
            return renderEditForm(articleId, article);

        article.setArticleId(articleId);
        articleService.editArticle(article);
        redirectAttributes.addFlashAttribute("info", "Článek byl upraven."); //flash zpráva k informování uživatele o úspěšné úpravě článku

        return "redirect:/articles";
    }

    @GetMapping("deleteArticle/{articleId}")
    @Secured("ROLE_ADMIN") //přiřazení oprávnění pro provedení akce odstranění článku k roli administrátora
    public String deleteArticle(@PathVariable long articleId,
                                RedirectAttributes redirectAttributes) { // rozhraní pro flash zprávy, lekce 16 JAVA PRO SpringBoot blog
        articleService.removeArticle(articleId);
        redirectAttributes.addFlashAttribute("success", "Článek byl smazán."); //flash zpráva k informování uživatele o úspěšném odstranění článku

        return "redirect:/articles";
    }

    @ExceptionHandler ({ArticleNotFoundException.class})
    public String handleArticleNotFoundException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Článek nebyl nalezen.");
        return "redirect:/articles";
    }
}
