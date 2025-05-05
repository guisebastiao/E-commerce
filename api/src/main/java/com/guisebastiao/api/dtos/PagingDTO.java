package com.guisebastiao.api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingDTO {
    private long totalItems;
    private int totalPages;
    private int currentPage;
    private int itemsPerPage;
}
