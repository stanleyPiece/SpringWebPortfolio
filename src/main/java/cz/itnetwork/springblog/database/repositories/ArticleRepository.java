package cz.itnetwork.springblog.database.repositories;


import cz.itnetwork.springblog.database.entities.ArticleEntity;
import org.springframework.data.repository.CrudRepository;


public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> { //první parametr určuje, s jakou entitou (třídou) bude rozhraní pracovat (dotazovací operace SQL)
                                                                                   //druhý parametr určuje typ unikátního identifikačního znaku (v tomto případě primárního klíče - celé číslo)

/* rozhraní CrudRepository, ze kterého toto rozhraní dědí, obsahuje například tyto důležité metody:

save() – Slouží k uložení (vytvoření i	aktualizaci) jedné entity (záznamu) do databáze.

findById() – Slouží k vyhledání jedné konkrétní entity podle unikátního identifikačního znaku (v našem
	případě primárního klíče). Metoda vrací datový typ	Optional pro případy, kdyby v databázi takový záznam nebyl a neměla co vrátit.

findAll() – Vrátí všechny uložené záznamy z databáze.

count() – Vrátí počet všech záznamů	uložených v databázi. Obdoba SELECT COUNT(*) FROM ….

deleteById() a	delete() – Slouží k mazání záznamů z databáze. Metoda deleteById() dostává v parametru unikátní
	identifikační znak (primární klíč) záznamu, který chceme smazat. Metoda	delete() dostává v parametru entitu (objekt).
 */

}
