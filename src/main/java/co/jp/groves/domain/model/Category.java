package co.jp.groves.domain.model;

import java.io.Serializable;

public record Category(Integer categoryId, String categoryName, Integer parentId) implements Serializable {}
