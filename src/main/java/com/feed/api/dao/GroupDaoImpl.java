/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.dao;

import com.feed.api.domain.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ruben
 */
public class GroupDaoImpl implements GroupDao {

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(GroupDaoImpl.class);
    public static Integer groupId = 1;
    private static Map<Integer, Group> db = new HashMap<Integer, Group>();

    public Group create(Group model) {

        model.setId(groupId);
        db.put(model.getId(), model);
        groupId += 1;

        return model;
    }

    public List<Group> find() {

        List<Group> result = new ArrayList<Group>();

        for (Map.Entry<Integer, Group> entry : db.entrySet()) {
            result.add(entry.getValue());
        }

        return result;
    }

    public Group find(Integer id) {

        return db.get(id);
    }

    public Group update(Group item) {

        return db.put(item.getId(), item);
    }

    public void delete(Integer id) {
        db.remove(id);
    }

    public void deleteAll() {
        db.clear();
    }

    public int count() {
        return db.size();
    }
}
