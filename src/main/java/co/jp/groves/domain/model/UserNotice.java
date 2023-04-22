package co.jp.groves.domain.model;

import java.io.Serializable;

public record UserNotice(Integer userNoticeId, Integer accountId, Integer noticeId, Integer goodsId, Integer status)
        implements Serializable {}
