package com.smartvizz.expenses.backend.data.specifications;

import com.smartvizz.expenses.backend.data.entities.UserEntity;
import com.smartvizz.expenses.backend.data.entities.WalletEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class WalletSpecifications {

    private WalletSpecifications() {
    }

    public static Specification<WalletEntity> searchWallet(String searchBy) {
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
                    builder.equal(builder.lower(root.get("currency")), searchBy.toLowerCase())
            );
            predicateList.add(
                    builder.like(builder.toString(root.get("createdAt")), "%" + searchBy + "%")
            );
            predicateList.add(
                    builder.like(builder.toString(root.get("updatedAt")), "%" + searchBy + "%")
            );

            return builder.or(predicateList.toArray(new Predicate[]{}));
        };
    }

    public static Specification<WalletEntity> byUser(UserEntity userEntity) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), userEntity);
    }
}
