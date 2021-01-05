package com.nativeboyz.vmall.services.favorites;

import com.nativeboyz.vmall.models.criteria.FavoriteCriteria;
import com.nativeboyz.vmall.models.entities.FavoriteEntity;
import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import com.nativeboyz.vmall.repositories.customers.CustomersRepository;
import com.nativeboyz.vmall.repositories.favorites.FavoritesRepository;
import com.nativeboyz.vmall.repositories.products.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoritesServiceImpl implements FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final CustomersRepository customersRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public FavoritesServiceImpl(
            FavoritesRepository favoritesRepository,
            CustomersRepository customersRepository,
            ProductsRepository productsRepository
    ) {
        this.favoritesRepository = favoritesRepository;
        this.customersRepository = customersRepository;
        this.productsRepository = productsRepository;
    }

    @Override
    public Optional<FavoriteEntity> saveFavorite(FavoriteCriteria criteria) {

        boolean exists = customersRepository.existsById(criteria.getCustomerId()) && productsRepository.existsById(criteria.getProductId());

        FavoriteEntity favorite = new FavoriteEntity(
                new CustomerProductIdentity(criteria.getCustomerId(), criteria.getProductId()),
                criteria.getStatus()
        );

        return exists ? Optional.of(favoritesRepository.save(favorite)) : Optional.empty();
    }

}
