package ru.kirillvenediktov.springbootuniversity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kirillvenediktov.springbootuniversity.models.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
