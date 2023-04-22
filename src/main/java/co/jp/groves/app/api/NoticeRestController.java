package co.jp.groves.app.api;

import co.jp.groves.domain.model.UserNotice;
import co.jp.groves.domain.service.notice.NoticeService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notice")
class NoticeRestController {
    private final NoticeService noticeService;

    NoticeRestController(final NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping(value = "/{accountId}")
    List<UserNotice> getNotices(@PathVariable Integer accountId) {
        return noticeService.findByAccountId(accountId);
    }

    @PutMapping
    List<UserNotice> putNotices(@RequestBody List<UserNotice> userNotices) {
        return noticeService.updateBatch(userNotices);
    }
}
