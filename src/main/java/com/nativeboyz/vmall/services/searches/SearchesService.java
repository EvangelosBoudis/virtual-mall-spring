package com.nativeboyz.vmall.services.searches;

import java.util.UUID;

public interface SearchesService {

    void saveSearch(UUID requesterId, String searchKey);

}
