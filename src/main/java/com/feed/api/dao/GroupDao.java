/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.dao;

import com.feed.api.domain.Group;
import java.util.List;

/**
 *
 * @author ruben
 */
public interface GroupDao {

    //create
    public Group create(Group item);

    //retrieve
    public List<Group> find();

    public Group find(Integer id);

    //update
    public Group update(Group item);

    //delete
    public void delete(Integer id);

    public void deleteAll();

    public int count();
}
