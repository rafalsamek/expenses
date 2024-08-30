package com.smartvizz.expenses.backend.data.specifications;

import com.smartvizz.expenses.backend.data.entities.CategoryEntity;
import com.smartvizz.expenses.backend.data.entities.ExpenseEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ExpenseSpecifications {

    private ExpenseSpecifications() {
    }

    public static Specification<ExpenseEntity> searchExpense(String searchBy) {
        return (root, query, builder) -> {
            if (searchBy == null || searchBy.isEmpty()) {
                // Return an empty predicate list if searchBy is null or empty
                return builder.conjunction();
            }
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(
                    builder.equal(builder.toString(root.get("id")), searchBy)
            );
            predicateList.add(
                    builder.like(builder.lower(root.get("title")), "%" + searchBy.toLowerCase() + "%")
            );
            predicateList.add(
                    builder.like(builder.lower(root.get("description")), "%" + searchBy.toLowerCase() + "%")
            );
            if (isConvertibleToFloat(searchBy)) {
                predicateList.add(
                        builder.equal(builder.toLong(root.get("amount")), Math.round(Float.parseFloat(searchBy) * 100))
                );
            }
            predicateList.add(
                    builder.equal(builder.lower(root.get("currency")), searchBy.toLowerCase())
            );
            predicateList.add(
                    builder.like(builder.toString(root.get("createdAt")), "%" + searchBy + "%")
            );
            predicateList.add(
                    builder.like(builder.toString(root.get("updatedAt")), "%" + searchBy + "%")
            );
            Join<?, ?> walletJoin = root.join("wallet");
            predicateList.add(
                    builder.like(builder.lower(walletJoin.get("name")), "%" + searchBy.toLowerCase() + "%")
            );
            predicateList.add(
                    builder.like(builder.lower(walletJoin.get("description")), "%" + searchBy.toLowerCase() + "%")
            );
            Join<ExpenseEntity, CategoryEntity> categoryJoin = root.join("categories");
            predicateList.add(
                    builder.like(builder.lower(categoryJoin.get("name")), "%" + searchBy.toLowerCase() + "%")
            );
            predicateList.add(
                    builder.like(builder.lower(categoryJoin.get("description")), "%" + searchBy.toLowerCase() + "%")
            );
            return builder.or(predicateList.toArray(new Predicate[0]));
        };
    }

    private static boolean isConvertibleToFloat(String searchBy) {
        try {
            Float.parseFloat(searchBy);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
