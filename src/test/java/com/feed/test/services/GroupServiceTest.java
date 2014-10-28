/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.test.services;

import com.feed.api.domain.Group;
import com.feed.api.services.GroupService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author ruben
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-applicationContext.xml"})
public class GroupServiceTest {

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(GroupServiceTest.class);
    @Autowired
    GroupService groupService;

    @Before
    public void setUp() throws Exception {

        groupService.deleteAll();
    }

    @Test
    public void tests() throws Exception {

        try {
            this.testFindNone();

            this.testCreate();

            this.testFindAll();
            this.testNotFound();

            this.testUpdate();

            this.testDelete();
            this.testInvalidDelete();

            this.testFindAll();


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

    }

    private void testFindNone() throws Exception {

        List<Group> find = groupService.find();
        Assert.assertTrue(find.size() == 0);
    }

    private void testCreate() throws Exception {

        List<String> urls = new ArrayList<String>();
        urls.add("http://tuneage.com/rss");
        urls.add("http://metallica.tumblr.com/rss");
        urls.add("http://blog.bandpage.com/feed/");

        Group entity = new Group("Group 1", urls);
        Group created = groupService.create(entity);

        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertEquals(entity.getName(), created.getName());
        Assert.assertArrayEquals(urls.toArray(), created.getUrls().toArray());

        //find the created item
        Group found = groupService.find(created.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(created.getId(), found.getId());
        Assert.assertEquals(created.getName(), found.getName());
        Assert.assertArrayEquals(urls.toArray(), found.getUrls().toArray());
    }

    private void testFindAll() throws Exception {

        List<Group> found = groupService.find();
        Assert.assertTrue(found.size() > 0);

        for (Group group : found) {
            logger.info(group.toString());
        }

    }

    private void testNotFound() throws Exception {

        Exception exNotFound = null;
        try {
            Group found = groupService.find(-1);
        } catch (NotFoundException ex) {
            exNotFound = ex;
        }

        Assert.assertNotNull(exNotFound);

    }

    private void testUpdate() throws Exception {

        List<String> urls = new ArrayList<String>();
        urls.add("http://tuneage.com/rss");
//        urls.add("http://metallica.tumblr.com/rss");
        urls.add("http://blog.bandpage.com/feed/");

        Group entity = new Group("Group 2", urls);
        Group created = groupService.create(entity);

        //udate item
        created.setName(created.getName() + " - updated");
        created.getUrls().add("http://metallica.tumblr.com/rss");
        Group updated = groupService.update(created);

        Assert.assertNotNull(updated);
        Assert.assertEquals(created.getId(), updated.getId());
        Assert.assertEquals(created.getName(), updated.getName());
        Assert.assertArrayEquals(urls.toArray(), updated.getUrls().toArray());

    }

    private void testDelete() throws Exception {

        List<String> urls = new ArrayList<String>();
        urls.add("http://tuneage.com/rss");
        urls.add("http://metallica.tumblr.com/rss");
        urls.add("http://blog.bandpage.com/feed/");

        Group entity = new Group("Group 3", urls);
        Group created = groupService.create(entity);

        //find it
        Group found = groupService.find(created.getId());
        Assert.assertNotNull(found);
        Assert.assertNotNull(created.getId());

        //delete 
        groupService.delete(created.getId());

        //dont find it
        NotFoundException exNotFound = null;
        try {
            groupService.find(created.getId());
        } catch (NotFoundException ex) {
            exNotFound = ex;
        }

        Assert.assertNotNull(exNotFound);
        
    }

    private void testInvalidDelete() {
        groupService.delete(-1);
    }
}
