package group5984.controller.rest;

import group5984.aspect.TrackUserAction;
import group5984.model.transactions.Transaction;
import group5984.service.db.BankDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * REST Контроллер банка
 * Проверка свагером http://localhost:8081/swagger-ui/index.html
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank/rest")
@Log
public class BankRestController {
    private final BankDbService service;

    /**
     * Проведение транзакции.
     * @param transaction транзакция с данными.
     * @return ответ с подтверждением.
     */
    @TrackUserAction
    @PostMapping()
    public ResponseEntity<Void> transaction(@RequestBody Transaction transaction){
        service.transaction(transaction);
        return ResponseEntity.ok().body(null);
    }

    /**
     * Откат произведенной транзакции.
     * @param transaction транзакция с данными.
     * @return ответ с подтверждением.
     */
    @PostMapping("/rollback")
    public ResponseEntity<Void> rollbackTransaction(@RequestBody Transaction transaction){
        service.rollbackTransaction(transaction);
        return ResponseEntity.ok().body(null);
    }
}
