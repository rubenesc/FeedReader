/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.services;

import com.feed.api.dao.GroupDao;
import com.feed.api.domain.Group;
import java.util.List;
import javax.ws.rs.NotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ruben
 */
@Service
public class GroupService {

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(GroupService.class);
    @Autowired
    GroupDao groupDao;

    public Group create(Group model) throws Exception {

        return groupDao.create(model);
    }

    public List<Group> find() throws Exception {

        return groupDao.find();
    }

    public Group find(Integer id) throws Exception {

        Group model = groupDao.find(id);

        validateFound(model, id);

        return model;
    }

    public Group update(Group item) throws Exception {

        Group dbItem = groupDao.find(item.getId());

        validateFound(dbItem, item.getId());

        updateAttributes(item, dbItem);

        dbItem = groupDao.update(dbItem);

        return dbItem;

    }

    public void delete(Integer id) {

        //delete from DB
        groupDao.delete(id);
    }

    public void deleteAll() {

        groupDao.deleteAll();
    }

    public int size() {

        return groupDao.count();
    }

    private void validateFound(Group entity, Integer id) throws NotFoundException {

        if (entity == null) {
            throw new NotFoundException("The resource with id " + id + " was not found.");
        }
    }

    private void updateAttributes(Group source, Group dest) {
        dest.setName(source.getName());
        dest.setUrls(source.getUrls());
    }
    
    //spring DI
    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }
    
}
