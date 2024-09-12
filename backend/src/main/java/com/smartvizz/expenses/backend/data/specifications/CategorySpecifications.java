
package com.smartvizz.expenses.backend.data.specifications;

import com.smartvizz.expenses.backend.data.entities.CategoryEntity;
import com.smartvizz.expenses.backend.data.entities.ExpenseEntity;
import com.smartvizz.expenses.backend.data.entities.UserEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CategorySpecifications {

    private CategorySpecifications() {
    }

    public static Specification<CategoryEntity> searchCategory(String searchBy) {
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
                    builder.like(builder.lower(root.get("name")), "%" + searchBy.toLowerCase() + "%")
            );
            predicateList.add(
                    builder.like(builder.lower(root.get("description")), "%" + searchBy.toLowerCase() + "%")
            );
            predicateList.add(
                    builder.like(builder.toString(root.get("createdAt")), "%" + searchBy + "%")
            );
            predicateList.add(
                    builder.like(builder.toString(root.get("updatedAt")), "%" + searchBy + "%")
            );
            Join<CategoryEntity, ExpenseEntity> expenseJoin = root.join("expenses");
            predicateList.add(
                    builder.like(builder.lower(expenseJoin.get("title")), "%" + searchBy.toLowerCase() + "%")
            );
            predicateList.add(
                    builder.like(builder.lower(expenseJoin.get("description")), "%" + searchBy.toLowerCase() + "%")
            );

            return builder.or(predicateList.toArray(new Predicate[]{}));
        };
    }

    public static Specification<CategoryEntity> byUser(UserEntity userEntity) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), userEntity);
    }
}
