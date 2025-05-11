package org.teta.service;

import event.ListsNotificationDto;
import event.UpdateListsEvent;
import org.teta.model.Lists;

public interface ListsHandlerService {

    void handleUpdateList(UpdateListsEvent listsEvent);

    Lists getOrCreateList(ListsNotificationDto lists);
}
