package com.comic.backend.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.*;
import java.util.*;

public class DataTablePaginationRequest<T> {
    private int draw;
    private int start;
    private int length;
    private String searchValue;
    private Map<Integer, DataTableColumn> columns = new HashMap<Integer, DataTableColumn>();

    private int orderColumnIndex;
    private String orderDirection;
    private PageRequest pageRequest;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public Map<Integer, DataTableColumn> getColumns() {
        return columns;
    }

    public void setColumns(Map<Integer, DataTableColumn> columns) {
        this.columns = columns;
    }

    public PageRequest getPageRequest() {
        PageRequest pageRequest = null;
        try {
                pageRequest = new PageRequest(start / length, length);
        } catch (Exception e) {
            System.out.println("Co loi xay ra: " + e.getMessage());
        }
        return pageRequest;
    }

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }

    public int getOrderColumnIndex() {
        return orderColumnIndex;
    }

    public void setOrderColumnIndex(int orderColumnIndex) {
        this.orderColumnIndex = orderColumnIndex;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public Specifications getSpecifications() {
        Specifications specifications = null;
        Specifications specificationsAll = null;
        boolean filterAll = this.getSearchValue() != null && !this.getSearchValue().trim().isEmpty();
        for (int idx : this.getColumns().keySet()) {
            DataTableColumn dtc = this.getColumns().get(idx);
            if (dtc.getSearchValue() != null && !dtc.getSearchValue().trim().isEmpty()) {
                Specification specification = filterByFieldName(dtc.getData(), dtc.getSearchValue());
                Specifications filterSpec = Specifications.where(specification);
                if (specifications == null) {
                    specifications = filterSpec;
                } else {
                    specifications = specifications.and(filterSpec);
                }
            }

            Specification specificationAll = filterByFieldName(dtc.getData(), this.getSearchValue());
            Specifications filterSpecAll = Specifications.where(specificationAll);
            if (specificationsAll == null) {
                specificationsAll = filterSpecAll;
            } else {
                specificationsAll = specificationsAll.or(filterSpecAll);
            }

        }
        if (specifications == null) return specificationsAll;
        if (specificationsAll == null) return specifications;
        return specifications.and(specificationsAll);
    }

    public Specification<T> filterByFieldName(final String fieldName, final String filterValue) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                try {
                    if (null != filterValue && !filterValue.isEmpty()) {
                        final Predicate likePredicate = cq.where(cb.like(root.<String>get(fieldName).as(String.class), "%" + filterValue + "%")).getRestriction();
                        return likePredicate;
                    } else {
                        return null;
                    }
                } catch (Exception ex) {
                    System.out.println("co loi xay ra: " + ex.getMessage());
                    return null;
                }
            }
        };
    }

    public Specification<T> filterEqualByFieldName(final String fieldName, final String filterValue) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                try {
                    if (null != filterValue && !filterValue.isEmpty()) {
                        final Predicate likePredicate = cq.where(cb.equal(root.<String>get(fieldName).as(String.class), filterValue)).getRestriction();
                        return likePredicate;
                    } else {
                        return null;
                    }
                } catch (Exception ex) {
                    System.out.println("co loi xay ra: " + ex.getMessage());
                    return null;
                }
            }
        };
    }

}
