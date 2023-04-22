package co.jp.groves.domain.service.prefecture;

import co.jp.groves.domain.model.Prefecture;
import co.jp.groves.domain.repository.prefecture.PrefectureRepository;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrefectureService {

    private final PrefectureRepository prefectureRepository;

    PrefectureService(final PrefectureRepository prefectureRepository) {
        this.prefectureRepository = prefectureRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable("prefectures")
    public List<Prefecture> findAll() {
        return prefectureRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Cacheable("prefectures")
    public Prefecture findById(int prefectureId) {
        return prefectureRepository.findById(prefectureId);
    }
}
