package org.teta.service;

import org.teta.event.ListsNotificationDto;
import org.teta.event.UpdateListsEvent;
import org.teta.model.Lists;

public interface ListsHandlerService {

    void handleUpdateList(UpdateListsEvent listsEvent);

    Lists getOrCreateList(ListsNotificationDto lists);
}
