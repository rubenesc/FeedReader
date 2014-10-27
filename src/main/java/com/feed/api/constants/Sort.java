/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.api.constants;

/**
 *
 * @author ruben
 */
public enum Sort {
    
    asc, desc, round;
    
    public static Sort getEnum(String value) {

        for (Sort sort : Sort.values()) {
            if (sort.name().equals(value)) {
                return sort;
            }
        }

        return null;
    }    
    
}
