package com.Book_social_Network.book;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {


    public static Specification<Book> withOwnerId(Integer ownerId){

    return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"),ownerId);
    }
}
