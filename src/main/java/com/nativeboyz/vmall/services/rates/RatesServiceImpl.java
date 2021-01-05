package com.nativeboyz.vmall.services.rates;

import com.nativeboyz.vmall.models.criteria.RateCriteria;
import com.nativeboyz.vmall.models.entities.RateEntity;
import com.nativeboyz.vmall.models.entities.identities.CustomerProductIdentity;
import com.nativeboyz.vmall.repositories.customers.CustomersRepository;
import com.nativeboyz.vmall.repositories.products.ProductsRepository;
import com.nativeboyz.vmall.repositories.rates.RatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatesServiceImpl implements RatesService {

    private final RatesRepository ratesRepository;
    private final CustomersRepository customersRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public RatesServiceImpl(RatesRepository ratesRepository, CustomersRepository customersRepository, ProductsRepository productsRepository) {
        this.ratesRepository = ratesRepository;
        this.customersRepository = customersRepository;
        this.productsRepository = productsRepository;
    }

    @Override
    public Optional<RateEntity> saveRate(RateCriteria criteria) {

        boolean exists = customersRepository.existsById(criteria.getCustomerId()) && productsRepository.existsById(criteria.getProductId());

        RateEntity rate = new RateEntity(
                new CustomerProductIdentity(criteria.getCustomerId(), criteria.getProductId()),
                criteria.getRate(),
                criteria.getComment()
        );

        return exists ? Optional.of(ratesRepository.save(rate)) : Optional.empty();
    }

}
