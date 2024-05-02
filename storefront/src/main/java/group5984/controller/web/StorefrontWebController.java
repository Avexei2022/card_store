package group5984.controller.web;

import group5984.model.basket.Basket;
import group5984.model.clients.Cards;
import group5984.service.api.ContentApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 * Веб контроллер пользовательского сайта магазина
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/storefront")
@Log
public class StorefrontWebController {
    private final ContentApiService serviceApi;


    /**
     * Перенаправление к методу вызова основной веб-страницы списка товаров
     * @return строка вызова
     */
    @GetMapping("/")
    public String redirectToFirstPage() {
        return "redirect:/storefront/cards/page/1";
    }

    /**
     * Основной метод подготовки веб-страницы
     * @param page номер страницы в списке товаров, выставленных на продажу
     * @param model Модель веб-страницы
     * @return готовая страница purchase.html
     *  с загруженной в нее информацией:
     *      - количество товара, выставленного на продажу;
     *      - количество страниц для загрузки товара;
     *      - номер текущей страницы;
     *      - номер предыдущей страницы;
     *      - номер следующей страницы
     *      - список товара;
     */
    @GetMapping("/cards/page/{page}")
    public String getAllCardsStorage(@PathVariable("page") String page, Model model) {
        Cards cards = serviceApi.getAllFromSale("/cards/page/" + page);
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
     * @param id id товара.
     * @param page номер текущей страницы для последующего возврата к ней
     * @return возврат к методу подготовки основной веб-страницы магазина со списком товара
      */
    @GetMapping("/basket/add_to_basket/{id}/{page}")
    public String addToBasketById(@PathVariable("id") Integer id, @PathVariable("page") String page) {
        serviceApi.addToBasketById(id);
        return "redirect:/storefront/cards/page/" + page;
    }

    /**
     * Основной метод подготовки веб-страницы
     * @param page номер страницы в списке товаров, выставленных на продажу
     * @param model Модель веб-страницы
     * @return готовая страница purchase.html
     *  с загруженной в нее информацией:
     *      - количество товара, выставленного на продажу;
     *      - количество страниц для загрузки товара;
     *      - номер текущей страницы;
     *      - номер предыдущей страницы;
     *      - номер следующей страницы;
     *      - список товара;
     *      - общая сумма товаров в корзине.
     */
    @GetMapping("/basket/page/{page}")
    public String getAllFromBasket(@PathVariable("page") String page, Model model) {
        Basket basket = serviceApi.getAllFromBasket("/basket/page/" + page);
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
     * Добавить товар в корзину.
     * @param id id товара.
     * @param page номер текущей страницы для последующего возврата к ней
     * @return возврат к методу подготовки основной веб-страницы магазина со списком товара
     */
    @GetMapping("/basket/delete_from_basket/{id}/{page}")
    public String deleteFromBasketById(@PathVariable("id") Integer id, @PathVariable("page") String page) {
        serviceApi.deleteFromBasketById(id);
        return "redirect:/storefront/basket/page/" + page;
    }

    //TODO Поработать над исключениями
    /**
     * Оплатить товар из корзины
     * @return переход к веб-странице покупок
     */
    @GetMapping("/basket/pay")
    public String basketPay() {
        log.info("TEST 1");
        try {
            serviceApi.basketPay();
        } catch (Exception ignore) {
        }
        return "redirect:/storefront/cards/page/1";
    }

}
