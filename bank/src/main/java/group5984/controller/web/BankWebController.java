package group5984.controller.web;

import group5984.configuration.BasicConfig;
import group5984.model.clients.Client;
import group5984.model.clients.ClientsList;
import group5984.model.visitors.CharacterInfo;
import group5984.model.visitors.CharacterResult;
import group5984.model.visitors.Characters;
import group5984.service.api.BankApiService;
import group5984.service.db.BankDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * Веб контроллер банка
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/bank")
@Log
public class BankWebController {
    private final BankApiService serviceApi;
    private final BankDbService serviceDb;
    private final BasicConfig basicConfig;

    /**
     * Перенаправление к методу подготовки веб-страницы посетителей банка
     * @return ссылка на первую страницу
     */
    @GetMapping("/")
    public String redirectToFirstPage() {
        return "redirect:/bank/visitors/page/1";
    }

    /**
     * Подготовка веб-страницы поетителей банка
     * @param page номер страницы в списке посетителей банка
     * В тестовом варианте - загрузга персонажей с рессурса Rick and Morty
     * @param model Модель веб-страницы
     * @return готовая страница bank.html
     */
    @GetMapping("/visitors/page/{page}")
    public String getVisitors(@PathVariable("page") String page, Model model) {
        String url = basicConfig.getCLIENT_API() + "/?page=" + page;
        Characters allCharacters = serviceApi.getAllCharacters(url);
        CharacterInfo characterInfo = getCharacterInfo(allCharacters);
        List<CharacterResult> visitorsList = allCharacters.getResults();
        List<CharacterResult> characterResultListFromBank = serviceDb.getAllBankCandidates();
        model.addAttribute("visitors_info", characterInfo)
                .addAttribute("visitors_list", visitorsList)
                .addAttribute("current_page", page)
                .addAttribute("candidates_list_size", characterResultListFromBank.size())
                .addAttribute("candidates_list", characterResultListFromBank);
        return "visitors";
    }

    /**
     * Добавить посетителя банка в базу данных кандидатов на открытие счета
     * @param id номер посетителя банка
     * @param page номер текущей страницы списка посетителей для последующего возврата к ней
     * @return возврат ссылки на сохраненную страницу
     */
    @GetMapping("/visitors/add_to_bank/{id}/{page}")
    public String addToBank(@PathVariable("id") Integer id, @PathVariable("page") String page) {
        String url = basicConfig.getCLIENT_API() + "/" + id;
        serviceApi.saveOneCharacterById(url);
        return "redirect:/bank/visitors/page/" + page;
    }

    /**
     * Метод удаления кандидата на открытие счета из базы данных
     * @param id id кандидата
     * @param page номер страницы на которой пользователь производил действия удаления
     * @return возврат к странице
     */
    @GetMapping("/candidates/delete_from_bank/{id}/{page}")
    public String deleteCandidateFromBank(@PathVariable("id") Integer id, @PathVariable("page") String page) {
        serviceDb.deleteClientById(id);
        return "redirect:/bank/candidates/page/" + page;
    }

    /**
     * Модификация информационной части о странице посетителей банка перед её добавлением в модель веб-стрвницы
     * @param allCharacters Страница со списком посетителей банка
     * В данном варианте - Информация полученная с рессурса Rick and Morty
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
     * Получить список кандидатов на открытие счета
     * @param page страница из списка кандидатов
     * @param model модель веб-страницы
     * @return веб-страница со списком кандидатов
     * Дополнительно отображается список клиентов банка
     */
    @GetMapping("/candidates/page/{page}")
    public String getAllCandidates(@PathVariable("page") String page, Model model) {
        //TODO оптимизировать - пока тестовый вариант
        List<CharacterResult> candidatesList = serviceDb.getAllBankCandidates();
        List<CharacterResult> clientsList = serviceDb.getAllClients();
        page = "1";
        model.addAttribute("candidates_list_size", candidatesList.size())
                .addAttribute("candidates_list", candidatesList)
                .addAttribute("current_page", page)
                .addAttribute("clients_list_size", clientsList.size())
                .addAttribute("client_list", clientsList);
        return "candidates";

    }

    /**
     * Добавить кандидата в базу данных клиентов банка - открыть счет
     * @param id номер кандидата
     * @param page номер текущей страницы для возврата к ней
     * @return возврат ссылки на соответствующую страницу
     */
    @GetMapping("/candidates/add_to_client/{id}/{page}")
    public String addCandidateToClient(@PathVariable("id") Integer id, @PathVariable("page") String page) {
        serviceDb.saveOneClientById(id);
        return "redirect:/bank/candidates/page/" + page;
    }

    /**
     * Удаление клиента из базы данных банка - закрыть счет
     * @param id id клиента
     * @param page номер страницы на которой пользователь производил действия удаления
     * @return возврат к этой же странице
     */
    @GetMapping("/clients/delete_client/{id}/{page}")
    public String deleteClient(@PathVariable("id") Integer id, @PathVariable("page") String page) {
        serviceDb.deleteClientById(id);
        return "redirect:/bank/clients/page/" + page;
    }

    /**
     * Получить список клиентов банка
     * @param page номер запрашиваемой пользователем страницы из списка клиентов
     * @param model модель веб-страницы
     * @return готовая выб-страница clients.html
     */
    @GetMapping("/clients/page/{page}")
    public String getAllCardsInSale(@PathVariable("page") Integer page, Model model) {
        ClientsList clientsList = serviceDb.getAllBankClients(page);
        model.addAttribute("clients_list_size", clientsList.getInfo().getCount())
                .addAttribute("amount_pages", clientsList.getInfo().getPages())
                .addAttribute("current_page", clientsList.getInfo().getCurrent())
                .addAttribute("prev_page", clientsList.getInfo().getPrev())
                .addAttribute("next_page", clientsList.getInfo().getNext())
                .addAttribute("clients_list", clientsList.getClientList());
        return "clients";

    }

    /**
     * Обновление данных о клиенте банка
     * @param client клиент банка
     * @return возврат к первой странице клиентов
     */
    @PostMapping("/clients/update")
    public String updateClient(Client client) {
        serviceDb.saveClient(client);
        return "redirect:/bank/clients/page/1";
    }

}
