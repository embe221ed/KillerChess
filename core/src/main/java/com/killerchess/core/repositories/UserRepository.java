package com.killerchess.core.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import java.util.List;

//w przyszłości można dodawać więcej metod, wedle uznania (odpowiednie nazewnictwo załatwia nam implementację)
//drugą opcją jest przypisanie odpowiedniego "query" do metody
public interface UserRepository<User, String>
        extends Repository<User, String> {

//    przykłąd przypisania query do metody
//    oczywiście u nas nie ma atrybutu "age", ale to tylko przykład
//    @Query("select u from User u where u.age = ?1")
//    List<User> findUsersByAge(int age);

    User save(User newUser);

    User findOne(String id);

    Iterable<User> findAll();

    Long count();

    void delete(User userToDelete);

    boolean exists(String primaryKey);
}
