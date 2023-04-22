package co.jp.groves.domain.service.notice;

import co.jp.groves.domain.model.UserNotice;
import co.jp.groves.domain.repository.notice.NoticeRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NoticeService {

    public enum NoticeId {
        Purchased(0);
        private int id;

        private NoticeId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public enum Status {
        Unnotified(0),
        Notified(1);
        private int status;

        private Status(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }

    private final NoticeRepository noticeRepository;

    NoticeService(final NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Transactional(readOnly = true)
    public List<UserNotice> findByAccountId(int accountId) {
        return noticeRepository.findByAccountId(accountId);
    }

    @Transactional(readOnly = false)
    public UserNotice register(UserNotice userNotice) {
        return noticeRepository.create(userNotice);
    }

    @Transactional(readOnly = false)
    public List<UserNotice> updateBatch(List<UserNotice> userNotices) {
        return noticeRepository.updateBatch(userNotices);
    }
}
