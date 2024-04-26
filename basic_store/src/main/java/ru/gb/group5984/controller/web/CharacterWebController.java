package ru.gb.group5984.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.group5984.configaration.BasicConfig;
import ru.gb.group5984.model.characters.CharacterInfo;
import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.characters.Characters;
import ru.gb.group5984.service.api.CharacterApiService;

import java.util.List;


/**
 * Веб контроллер героев
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
@Log
public class CharacterWebController {
    private final CharacterApiService serviceApi;
    private final BasicConfig basicConfig;

    /**
     * Метод принудительного перенаправления к методу подготовки страниц
     * @return ссылка на первую страницу героев
     */
    @GetMapping("/")
    public String redirectToFirstPage() {
        return "redirect:/characters/page/1";
    }

    /**
     * Основной метод подготовки вебстраницы
     * @param page номер страницы в списке героев с рессурса Rick and Morty
     * @param model Модель веб страницы
     * @return готовая страница index.html
     * Шаги:
     * - подготовка ссылки на соответствующую страницу героев в соответствии с документацией Rick and Morty
     * - Получение с рессурса Rick and Morty объединенной информации о странице со списком героев
     * - Изъятие информационной части
     * - Изъятие списка героев
     * - получение списка героев из базы данных (корзины)
     * - подгрузка в модель:
     *      - информация о странице;
     *      - список героев;
     *      - номер текущей страниы;
     *      - количество карточек героев в корзине;
     *      - список карточек героев из корзины
     */
    @GetMapping("/characters/page/{page}")
    public String getCharacters(@PathVariable("page") String page, Model model) {
        String url = basicConfig.getCHARACTER_API() + "/?page=" + page;
        Characters allCharacters = serviceApi.getAllCharacters(url);
        CharacterInfo characterInfo = getCharacterInfo(allCharacters);
        List<CharacterResult> characterResultList = allCharacters.getResults();
        List<CharacterResult> characterResultListFromBasket = serviceApi.getAllFromBasket();
        model.addAttribute("character_info", characterInfo);
        model.addAttribute("characters_list", characterResultList);
        model.addAttribute("current_page", page);
        model.addAttribute("basket_size", characterResultListFromBasket.size());
        model.addAttribute("basket_cards", characterResultListFromBasket);
        return "index";
    }

    /**
     * Метод добавления карточки героя в корзину
     * @param id номер героя
     * @param page номер текущей страницы для возврата к ней
     * @return возврат ссылки на соответствующую страницу
     * Шаги:
     *      - подготовка ссылки на страницу героя по его id в соответствии с документацией Rick and Morty
     *      - передача сервису подготовленной ссылки для загрузки карточки героя и сохранения её в базе данных
     *      - возврат к странице, с которой карточка героя была добавлена в корзину
     */
    @GetMapping("/characters/add_to_basket/{id}/{page}")
    public String addToBasket(@PathVariable("id") Integer id, @PathVariable("page") String page) {
        String url = basicConfig.getCHARACTER_API() + "/" + id;
        serviceApi.saveOneCharacterById(url);
        return "redirect:/characters/page/" + page;
    }

    /**
     * Метод удаления карточки героя из базы данных
     * @param id id героя
     * @param page номер страницы на которой пользователь производил действия удаления
     * @return возврат к этой же странице
     * Шаги:
     *      - передача сервису id героя для удаления его карточки из базы данных
     *      - возврат к страние
     */
    @GetMapping("/characters/delete_from_basket/{id}/{page}")
    public String deleteFromBasket(@PathVariable("id") Integer id, @PathVariable("page") String page) {
        serviceApi.deleteById(id);
        return "redirect:/characters/page/" + page;
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

}
