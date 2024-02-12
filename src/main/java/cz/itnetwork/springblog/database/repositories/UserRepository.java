package cz.itnetwork.springblog.database.repositories;

import cz.itnetwork.springblog.database.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

/* rozhraní CrudRepository, ze kterého toto rozhraní dědí, obsahuje například tyto důležité metody:

save() – Slouží k uložení (vytvoření i	aktualizaci) jedné entity (záznamu) do databáze.

findById() – Slouží k vyhledání jedné konkrétní entity podle unikátního identifikačního znaku (v našem
	případě primárního klíče). Metoda vrací datový typ	Optional pro případy, kdyby v databázi takový záznam nebyl a neměla co vrátit.

findAll() – Vrátí všechny uložené záznamy z databáze.

count() – Vrátí počet všech záznamů	uložených v databázi. Obdoba SELECT COUNT(*) FROM ….

deleteById() a	delete() – Slouží k mazání záznamů z databáze. Metoda deleteById() dostává v parametru unikátní
	identifikační znak (primární klíč) záznamu, který chceme smazat. Metoda	delete() dostává v parametru entitu (objekt).
 */

    Optional<UserEntity> findByEmail (String email); /* Spring boot sám vygeneruje databázový dotaz základě názvu metody
                                                     / díky tomuto přístupu můžete vytvořit mnoho složitějších dotazů SQL bez nutnosti je ručně psát
                                                     / nevýhodou tohoto přístupu je, že je náchylný na případné přejmenování atributů. SQL příkaz se generuje podle názvu
                                                     / metody v repositáři – když tedy změníme název sloupce/atributu v entitě
                                                     / a zapomeneme upravit i název metody v repositáři, aplikace nám přestane fungovat.
                                                     */
}