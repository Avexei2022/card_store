package ru.gb.group5984.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.group5984.aspect.TrackUserAction;
import ru.gb.group5984.model.characters.CharacterInfo;
import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.characters.Characters;
import ru.gb.group5984.model.messeges.Message;
import ru.gb.group5984.model.storage.Cards;
import ru.gb.group5984.service.api.CharacterApiService;
import ru.gb.group5984.service.api.ServerApiService;

import java.util.List;


/**
 * Веб контроллер склада магазина
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/storage")
@Log
public class StorageWebController {
    private final CharacterApiService characterApiService;
    private final ServerApiService serverApiService;

    /**
     * Переадресация к странице 1 списка товаров поставщика
     * @return адрес
     */
    @GetMapping("")
    public String redirectToFirstPage() {
        return "redirect:/storage/characters/page/1";
    }

    /**
     * Подготовка веб-страницы для закупки товаров на склад магазина
     * @param page номер страницы в списке товаров с рессурса поставщика - Rick and Morty
     * @param model Модель веб страницы
     * @return готовая страница purchase.html
     * В модель страницы загружается следующая информация:
     *      - информация о странице;
     *      - список товаров поставщика;
     *      - номер текущей страницы из списка товаров;
     *      - количество товара, закупленного на склад;
     *      - список товаров на складе.
     */
    @GetMapping("/characters/page/{page}")
    public String getCharacters(@PathVariable("page") String page, Model model) {
        Characters allCharacters = characterApiService.getAllCharacters(page);
        CharacterInfo characterInfo = getCharacterInfo(allCharacters);
        List<CharacterResult> characterResultList = allCharacters.getResults();
        model.addAttribute("characters_size", characterInfo.getCount())
                .addAttribute("characters_pages", characterInfo.getPages())
                .addAttribute("prev_page", characterInfo.getPrev())
                .addAttribute("next_page", characterInfo.getNext())
                .addAttribute("current_page", page)
                .addAttribute("characters_list", characterResultList);
        return "purchase";
    }

    /**
     * Добавить единицу товара на склад - закупить у поставщика.
     * @param id номер товара
     * @param page номер текущей страницы для возврата к ней
     * @return возврат ссылки на соответствующую страницу
      */
    @GetMapping("/characters/add_to_storage/{id}/{page}")
    public String addToStorage(@PathVariable("id") Integer id, @PathVariable("page") String page) {
        characterApiService.saveOneCharacterById(id);
        return "redirect:/storage/characters/page/" + page;
    }

    /**
     * Удалить единицу товара со склада.
     * @param id id товара
     * @param page номер страницы на которой пользователь производил действия удаления
     * @return возврат к этой же странице
     */
    @TrackUserAction
    @GetMapping("/characters/delete_from_storage/{id}/{page}")
    public String deleteFromStorage(@PathVariable("id") Integer id, @PathVariable("page") String page, Model model) {
        Message message = serverApiService.deleteFromStorageById(id);
        log.info(message.getMessage());
        if (message.getMessage().equals("none")) return "redirect:/storage/storage/page/" + page;
        else {
            model.addAttribute("message", message.getMessage());
            return "message";
        }
    }

    /**
     * Метод модификации информационной части о странице героев перед её добавлением в модель
     * @param allCharacters Информация полученная с рессурса Rick and Morty
     * @return Модифицированная информационная часть о странице
     * Пояснение: В информационной части прриходят ссылки на предыдущую и следующую странцы,
     * но для загрузки в модель ссылки не нужны, но нужны номера страниц. Поэтому ссылки меняются на номера страниц.
     * Если со ссылкой проблема, то она меняется на страницу 1.
     */
    private CharacterInfo getCharacterInfo(Characters allCharacters) {
        CharacterInfo characterInfo = allCharacters.getInfo();
        if (characterInfo.getPrev() == null
                || characterInfo.getPrev().isEmpty()
                || !characterInfo.getPrev().contains("https://rickandmortyapi.com/api/character/?page=")) {
            characterInfo.setPrev("1");
        } else {
            characterInfo.setPrev(characterInfo
                    .getPrev()
                    .replace("https://rickandmortyapi.com/api/character/?page=", ""));
        }
        if (characterInfo.getNext() == null
                || characterInfo.getNext().isEmpty()
                || !characterInfo.getNext().contains("https://rickandmortyapi.com/api/character/?page=")) {
            characterInfo.setNext("1");
        } else {
            characterInfo.setNext(characterInfo
                    .getNext()
                    .replace("https://rickandmortyapi.com/api/character/?page=", ""));
        }
        return characterInfo;
    }

    /**
     * Получить страницу из списка товаров, хранящихся на складе.
     * @param page номер страницы
     * @param model модель веб-страницы
     * @return веб-страница storage.html
     */
    @GetMapping("/storage/page/{page}")
    public String getAllCardsInStorage(@PathVariable("page") String page, Model model) {
        //TODO оптимизировать - пока тестовый вариант
        Characters characters = serverApiService.getPageFromStorage(page);
        CharacterInfo characterInfo = characters.getInfo();
        List<CharacterResult> characterResultList = characters.getResults();
        model.addAttribute("storage_size", characterInfo.getCount())
                .addAttribute("storage_pages", characterInfo.getPages())
                .addAttribute("prev_page", characterInfo.getPrev())
                .addAttribute("next_page", characterInfo.getNext())
                .addAttribute("current_page", page)
                .addAttribute("characters_list", characterResultList);
        return "storage";

    }

    /**
     * Переместить единицу товара со склада на полку продаж
     * @param id номер товара
     * @param page номер текущей страницы для возврата к ней
     * @return возврат ссылки на соответствующую страницу
     */
    @GetMapping("/storage/add_to_sale/{id}/{page}")
    public String addToSale(@PathVariable("id") Integer id, @PathVariable("page") String page) {
        serverApiService.saveOneCardToSaleById(id);
        return "redirect:/storage/storage/page/" + page;
    }

    /**
     * Удалить товар из списка продаж - убрать с витрины.
     * @param id номер товара
     * @param page номер страницы на которой пользователь производил действия удаления
     * @return возврат к этой же странице
     */
    @GetMapping("/storage/delete_from_sale/{id}/{page}")
    public String deleteFromSale(@PathVariable("id") Integer id, @PathVariable("page") String page) {
        serverApiService.deleteCardFromSaleById(id);
        return "redirect:/storage/sale/page/" + page;
    }

    /**
     * Получить страницу из списка товаров, выставленных на продажу.
     * @param page номер страницы
     * @param model заготовка веб-страницы
     * @return веб-страница sale.html
     */
    @GetMapping("/sale/page/{page}")
    public String getAllCardsInSale(@PathVariable("page") Integer page, Model model) {
        Cards cards = serverApiService.getPageCardsStorageFromSale(page);
        model.addAttribute("sale_size", cards.getInfo().getCount())
                .addAttribute("amount_pages", cards.getInfo().getPages())
                .addAttribute("current_page", cards.getInfo().getCurrent())
                .addAttribute("prev_page", cards.getInfo().getPrev())
                .addAttribute("next_page", cards.getInfo().getNext())
                .addAttribute("sale_list", cards.getCardsStorageList());
        return "sale";

    }


}
