package com.nativeboyz.vmall.services.searches;

import com.nativeboyz.vmall.models.entities.SearchEntity;
import com.nativeboyz.vmall.models.entities.identities.SearchIdentity;
import com.nativeboyz.vmall.repositories.searches.SearchesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SearchesServiceImpl implements SearchesService {

    private final SearchesRepository searchesRepository;

    @Autowired
    public SearchesServiceImpl(SearchesRepository searchesRepository) {
        this.searchesRepository = searchesRepository;
    }

    @Override
    public void saveSearch(UUID requesterId, String searchKey) {

        SearchEntity search = new SearchEntity(
                new SearchIdentity(requesterId, searchKey)
        );

        searchesRepository.save(search);
    }

}
