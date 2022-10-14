package com.store.Application.Controller;

import com.store.Application.common.data.Item;
import com.store.Application.common.data.ItemId;
import com.store.Application.common.data.UserId;
import com.store.Application.service.ItemServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ItemController extends BaseConfigController {

    @Autowired
    private ItemServices itemServices;

    @RequestMapping(value = "/item/{userId}/{itemId}", method = RequestMethod.GET)
    @ResponseBody
    public Item getItemById(@PathVariable("userId") String userId,
                            @PathVariable("itemId") String itemId) {
        UserId userId1 = new UserId(UUID.fromString(userId));
        ItemId itemId1 = new ItemId(UUID.fromString(itemId));
        log.info("The item with Id {} will be retrieved", itemId1);
        return itemServices.findItemById(userId1.getId(), itemId1.getId());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/item", method = RequestMethod.POST)
    @ResponseBody
    public void saveItem(@RequestBody Item item) {

        try {
            log.info("The {} inside process of saving with price {} ",item.getName(),item.getPrice());
            itemServices.saveItem(getCurrentUser(), item);
        } catch (Exception e) {
            log.info("keep tracing {}", e.getMessage());
        }
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void removeItem(@PathVariable("itemId") String str) {
        UserId userId = getCurrentUser();
        ItemId itemId = new ItemId(UUID.fromString(str));
        log.info("The item with id {} will be in process of Deletion",itemId);
        itemServices.removeByUserIdAndItemId(userId.getId(), itemId.getId());
    }


}
