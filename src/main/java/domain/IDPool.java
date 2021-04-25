package domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jakarta.ws.rs.core.Link;

/**
 * creates random id pool
 * meant to combine the power of List get( int i) and Set contains
 */
public class IDPool {
    private Set<Long> idPoolSet;
    private List<Long> idPoolList;
    public IDPool(final int idPoolSize){
        idPoolSet=new HashSet<>(idPoolSize);
        idPoolList=new LinkedList<>();
        
    }
    public boolean contains(Long id){
        return idPoolSet.contains(id);
    }
    public Long pop
    
}
