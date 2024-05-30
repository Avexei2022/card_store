package group5984.controller.web;

import group5984.model.basket.Basket;
import group5984.model.cards.Cards;
import group5984.model.characters.CharacterInfo;
import group5984.model.characters.CharacterResult;
import group5984.model.characters.Characters;
import group5984.model.messeges.Message;
import group5984.service.api.CharacterApiService;
import group5984.service.api.ContentApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * Веб-контроллер пользовательского сайта магазина.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/storefront")
@Log
public class StorefrontWebController {

    /**
     * Сервис взаимодействия с API сервиса ресурсов магазина.
     */
    private final ContentApiService serviceApi;

    /**
     * Сервис взаимодействия с API Rick and Morty/
     */
    private final CharacterApiService characterApiService;

    /**
     * Перенаправление к методу вызова основной веб-страницы списка товаров в продаже.
     * @return строка вызова.
     */
    @GetMapping("")
    public String redirectToFirstPage() {
        return "redirect:/storefront/cards/page/1";
    }

    /**
     * Подготовка веб-страницы списка товаров в продаже с загруженной в нее информацией:
     * - количество товара, выставленного на продажу;
     * - количество страниц для загрузки товара;
     * - номер текущей страницы;
     * - номер предыдущей страницы;
     * - номер следующей страницы
     * - список товара;
     * @param page номер страницы в списке товаров, выставленных на продажу
     * @param model Модель веб-страницы
     * @return страница товаров в продаже purchase.html
     */
    @GetMapping("/cards/page/{page}")
    public String getPageCardsStorage(@PathVariable("page") String page, Model model) {
        Cards cards = serviceApi.getPageFromSale(page);
        model.addAttribute("sale_size", cards.getInfo().getCount())
                .addAttribute("amount_pages", cards.getInfo().getPages())
                .addAttribute("current_page", cards.getInfo().getCurrent())
                .addAttribute("prev_page", cards.getInfo().getPrev())
                .addAttribute("next_page", cards.getInfo().getNext())
                .addAttribute("sale_list", cards.getCardsStorageList());
        return "purchase";
    }

    /**
     * Добавить товар в корзину.
     * @param id уникальный номер товара.
     * @param userName имя/логин покупателя
     * @param page номер текущей страницы для последующего возврата к ней.
     * @param model Модель веб-страницы сообщений.
     * @return возврат к странице выбора товара при удачном сохранении товара в корзине,
     * или переход к странице сообщений.
     */
    @GetMapping("/basket/add_to_basket/{id}/{user_name}/{page}")
    public String addToBasketById(@PathVariable("id") Integer id
            , @PathVariable("user_name") String userName, @PathVariable("page") String page, Model model) {
        Message message = serviceApi.addToBasketById(id, userName);
        if (message.getMessage().equals("OK")) return "redirect:/storefront/cards/page/" + page;
        else {
            model.addAttribute("message", message.getMessage());
            return "message";
        }
    }

    /**
     * Подготовка веб-страницы корзины покупателя.
     * @param page номер страницы в списке товаров, выставленных на продажу.
     * @param userName имя/логин покупателя.
     * @param model Модель веб-страницы.
     * @return готовая страница basket.html с загруженной в нее информацией:
     *      - количество товара в корзине;
     *      - количество страниц для загрузки товара;
     *      - номер текущей страницы;
     *      - номер предыдущей страницы;
     *      - номер следующей страницы;
     *      - список товара в корзине;
     *      - общая сумма товаров в корзине.
     */
    @GetMapping("/basket/page/{page}/{user_name}")
    public String getPageFromBasket(@PathVariable("page") String page
            , @PathVariable("user_name") String userName, Model model) {
        Basket basket = serviceApi.getPageFromBasket(userName, page);
        model.addAttribute("sale_size", basket.getInfo().getCount())
                .addAttribute("amount_pages", basket.getInfo().getPages())
                .addAttribute("current_page", basket.getInfo().getCurrent())
                .addAttribute("prev_page", basket.getInfo().getPrev())
                .addAttribute("next_page", basket.getInfo().getNext())
                .addAttribute("sale_list", basket.getCardInBasketList())
                .addAttribute("total_price", basket.getInfo().getTotalPrice());
        return "basket";
    }

    /**
     * Удалить товар из корзины.
     * @param id id товара.
     * @param page номер текущей страницы для последующего возврата к ней.
     * @param userName имя пользователя.
     * @return возврат к корзине покупателя.
     */
    @GetMapping("/basket/delete_from_basket/{id}/{page}/{user_name}")
    public String deleteFromBasketById(@PathVariable("id") Integer id, @PathVariable("page") String page
            , @PathVariable("user_name") String userName) {
        serviceApi.deleteFromBasketById(id);
        return "redirect:/storefront/basket/page/" + page + "/" + userName;
    }

    /**
     * Оплатить товар из корзины.
     * @param userName имя/логин покупателя.
     * @param model модель страницы.
     * @return переход к веб-странице сообщений message.html
     */
    @GetMapping("/basket/pay/{user_name}")
    public String basketPay(@PathVariable("user_name") String userName, Model model) {
        Message message = serviceApi.basketPay(userName);
        model.addAttribute("message", message.getMessage());
        return "message";
    }

    /**
     * Подготовка веб-страницы потенциальных покупателей.
     * В модель страницы загружается следующая информация:
     *      - количество персонажей;
     *      - количество страниц в списке персонажей;
     *      - номера предыдущей, текущей и следующей страниц;
     *      - список персонажей.
     * @param page номер страницы в списке персонажей с рессурса Rick and Morty.
     * @param model Модель веб страницы.
     * @return готовая страница регистрации register.html.
     */
    @GetMapping("/characters/page/{page}")
    public String getCharacters(@PathVariable("page") String page, Model model) {
        Characters allCharacters = characterApiService.getPageCharacters(page);
        CharacterInfo characterInfo = getCharacterInfo(allCharacters);
        List<CharacterResult> characterResultList = allCharacters.getResults();
        model.addAttribute("characters_size", characterInfo.getCount())
                .addAttribute("characters_pages", characterInfo.getPages())
                .addAttribute("prev_page", characterInfo.getPrev())
                .addAttribute("next_page", characterInfo.getNext())
                .addAttribute("current_page", page)
                .addAttribute("characters_list", characterResultList);
        return "register";
    }

    /**
     * Зарегистрировать нового покупателя.
     * @param id уникальный номер покупателя.
     * @param model Модель веб страницы.
     * @return страница сообщений о результате регистрации.
     */
    @GetMapping("/characters/register/{id}")
    public String registerNewUser(@PathVariable("id") Integer id, Model model) {
        Message message = characterApiService.saveOneCharacterById(id);
        model.addAttribute("message", message.getMessage());
        return "message";
    }

    /**
     * Метод модификации информационной части о странице героев перед её добавлением в модель.
     * Пояснение: В информационной части прриходят ссылки на предыдущую и следующую странцы,
     * но для загрузки в модель ссылки не нужны, но нужны номера страниц,
     * поэтому ссылки меняются на номера страниц.
     * Если со ссылкой проблема, то она меняется на страницу 1.
     * @param allCharacters Информация полученная с рессурса Rick and Morty.
     * @return Модифицированная информационная часть о странице.
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
