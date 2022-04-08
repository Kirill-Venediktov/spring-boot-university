package ru.kirillvenediktov.springbootuniversity.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kirillvenediktov.springbootuniversity.models.Group;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class GroupsDAO {

    @PersistenceContext
    EntityManager em;

    public List<Group> getAllGroups() {
        return em.createQuery("select g from Group g order by g.id", Group.class).getResultList();
    }

    public Group getGroup(Long groupId) {
        return em.find(Group.class, groupId);
    }

    public void deleteGroup(Group group) {
        em.remove(group);
    }

    public void createGroup(Group group) {
        em.persist(group);
    }

    public Group updateGroup(Group group) {
        return em.merge(group);
    }

}
