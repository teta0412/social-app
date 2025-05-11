package org.teta.service.impl;

import event.ListsNotificationDto;
import event.UpdateListsEvent;
import org.teta.model.Lists;
import org.teta.repository.ListsRepository;
import org.teta.service.ListsHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListsHandlerServiceImpl implements ListsHandlerService {

    private final ListsRepository listsRepository;

    @Override
    @Transactional
    public void handleUpdateList(UpdateListsEvent listsEvent) {
        listsRepository.findById(listsEvent.getId())
                .map(lists -> {
                    lists.setListName(lists.getListName());
                    return lists;
                });
    }

    @Override
    @Transactional
    public Lists getOrCreateList(ListsNotificationDto lists) {
        return listsRepository.findById(lists.getId())
                .orElseGet(() -> {
                    Lists newList = new Lists();
                    newList.setId(lists.getId());
                    newList.setListName(lists.getListName());
                    return listsRepository.save(newList);
                });
    }
}
