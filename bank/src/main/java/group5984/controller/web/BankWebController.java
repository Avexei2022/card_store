package group5984.controller.web;

import group5984.model.clients.Client;
import group5984.model.clients.ClientsList;
import group5984.model.clients.ClientsListInfo;
import group5984.model.messeges.Message;
import group5984.model.visitors.CharacterInfo;
import group5984.model.visitors.CharacterResult;
import group5984.model.visitors.Characters;
import group5984.service.api.CharactersApiService;
import group5984.service.api.BankApiService;
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
 * Веб контроллер банка.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/bank")
@Log
public class BankWebController {
    private final CharactersApiService charactersApiService;
    private final BankApiService bankApiService;

    /**
     * Перенаправление к методу подготовки веб-страницы посетителей банка.
     * @return ссылка на первую страницу посетителей банка.
     */
    @GetMapping("")
    public String redirectToFirstPage() {
        return "redirect:/bank/visitors/page/1";
    }

    /**
     * Подготовка веб-страницы поетителей банка.
     * @param page номер страницы в списке посетителей банка.
     * В тестовом варианте - загрузга персонажей с рессурса Rick and Morty.
     * @param model Модель веб-страницы.
     * @return готовая страница посетителей банка visitors.html
     * или переход к странице сообщений message.html.
     */
    @GetMapping("/visitors/page/{page}")
    public String getVisitors(@PathVariable("page") String page, Model model) {
        try {
            Characters characters = charactersApiService.getPageCharacters(page);
            CharacterInfo characterInfo = characters.getInfo();
            List<CharacterResult> visitorsList = characters.getResults();
            model.addAttribute("characters_size", characterInfo.getCount())
                    .addAttribute("characters_pages", characterInfo.getPages())
                    .addAttribute("prev_page", characterInfo.getPrev())
                    .addAttribute("next_page", characterInfo.getNext())
                    .addAttribute("current_page", page)
                    .addAttribute("visitors_list", visitorsList);
            return "visitors";
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
            return "message";
        }

    }

    /**
     * Добавить посетителя банка в базу данных кандидатов на открытие счета.
     * @param id уникальный номер посетителя банка.
     * @param page номер текущей страницы списка посетителей для последующего возврата к ней.
     * @return возврат ссылки на сохраненную страницу
     * или переход к странице сообщений message.html.
     */
    @GetMapping("/visitors/add_to_bank/{id}/{page}")
    public String addToBank(@PathVariable("id") Integer id, @PathVariable("page") String page, Model model) {
        Message message = charactersApiService.saveOneCharacterById(id);
        if (message.getMessage().equals("OK")) {
            return "redirect:/bank/visitors/page/" + page;
        } else {
            model.addAttribute("message", message);
            return "message";
        }
    }

    /**
     * Метод удаления кандидата на открытие счета из базы данных.
     * @param id уникальный номер кандидата.
     * @param page номер страницы на которой пользователь производил действия удаления.
     * @return возврат к странице кандидатов
     * или переход к станице сообщений message.html.
     */
    @GetMapping("/candidates/delete_from_bank/{id}/{page}")
    public String deleteCandidateFromBank(@PathVariable("id") Integer id
            , @PathVariable("page") String page
            , Model model) {
        Message message = bankApiService.deleteVisitorById(id);
        if (message.getMessage().equals("OK")) {
            return "redirect:/bank/candidates/page/" + page;
        } else {
            model.addAttribute("message", message);
            return "message";
        }
    }

    /**
     * Получить список кандидатов на открытие счета.
     * @param page страница из списка кандидатов.
     * @param model модель веб-страницы.
     * @return веб-страница со списком кандидатов
     * или переход к станице сообщений message.html.
     */
    @GetMapping("/candidates/page/{page}")
    public String getPageCandidates(@PathVariable("page") String page, Model model) {
        try {
            Characters characters = bankApiService.getPageCandidates(page);
            CharacterInfo characterInfo = characters.getInfo();
            List<CharacterResult> candidatesList = characters.getResults();
            model.addAttribute("characters_size", characterInfo.getCount())
                    .addAttribute("characters_pages", characterInfo.getPages())
                    .addAttribute("prev_page", characterInfo.getPrev())
                    .addAttribute("next_page", characterInfo.getNext())
                    .addAttribute("current_page", page)
                    .addAttribute("candidates_list", candidatesList);
            return "candidates";
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
            return "message";
        }
    }

    /**
     * Добавить кандидата в базу данных клиентов банка - открыть счет.
     * @param id уникальный номер кандидата
     * @param page номер текущей страницы для возврата к ней.
     * @return возврат ссылки на соответствующую страницу кандидатов банка
     * или переход к станице сообщений message.html.
     */
    @GetMapping("/candidates/add_to_client/{id}/{page}")
    public String addCandidateToClient(@PathVariable("id") Integer id
            , @PathVariable("page") String page, Model model) {
        Message message = bankApiService.saveOneClientById(id);
        if (message.getMessage().equals("OK")) {
            return "redirect:/bank/candidates/page/" + page;
        } else {
            model.addAttribute("message", message);
            return "message";
        }
    }

    /**
     * Удалить клиента из базы данных банка - закрыть счет.
     * @param id уникальный номер клиента.
     * @param page номер страницы на которой пользователь производил действия удаления.
     * @return возврат к странице клиентов банка
     * или переход к станице сообщений message.html.
     */
    @GetMapping("/clients/delete_client/{id}/{page}")
    public String deleteClient(@PathVariable("id") Integer id
            , @PathVariable("page") String page
            , Model model) {
        Message message = bankApiService.deleteClientById(id);
        if (message.getMessage().equals("OK")) {
            return "redirect:/bank/clients/page/" + page;
        } else {
            model.addAttribute("message", message);
            return "message";
        }
    }

    /**
     * Получить список клиентов банка.
     * @param page номер запрашиваемой пользователем страницы из списка клиентов.
     * @param model модель веб-страницы.
     * @return готовая выб-страница клиентов банка clients.html
     * или переход к станице сообщений message.html.
     */
    @GetMapping("/clients/page/{page}")
    public String getAllCardsInSale(@PathVariable("page") Integer page, Model model) {

        try {
            ClientsList clientsList = bankApiService.getPageBankClients(page);
            ClientsListInfo clientsListInfo = clientsList.getInfo();
            List<Client> clientList = clientsList.getClientList();

            model.addAttribute("clients_list_size", clientsListInfo.getCount())
                    .addAttribute("amount_pages", clientsListInfo.getPages())
                    .addAttribute("prev_page", clientsListInfo.getPrev())
                    .addAttribute("next_page", clientsListInfo.getNext())
                    .addAttribute("current_page", page)
                    .addAttribute("clients_list", clientList);
            return "clients";
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
            return "message";
        }
    }

    /**
     * Обновление данных о клиенте банка.
     * @param client клиент банка.
     * @return возврат к первой странице клиентов банка
     * или переход к станице сообщений message.html.
     */
    @PostMapping("/clients/update")
    public String updateClient(Client client, Model model) {
        Message message = bankApiService.saveClient(client);
        if (message.getMessage().equals("OK")) {
            return "redirect:/bank/clients/page/1";
        } else {
            model.addAttribute("message", message);
            return "message";
        }
    }

}
