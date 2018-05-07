package com.killerchess.core.repositories;

import com.killerchess.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//w przyszłości można dodawać więcej metod, wedle uznania (odpowiednie nazewnictwo załatwia nam implementację)
//drugą opcją jest przypisanie odpowiedniego "query" do metody
@Repository
public interface UserRepository extends JpaRepository<User, String> {

//    przykłąd przypisania query do metody
//    oczywiście u nas nie ma atrybutu "age", ale to tylko przykład
//    @Query("select u from User u where u.age = ?1")
//    List<User> findUsersByAge(int age);

    User save(User newUser);

    User findOne(String id);

    List<User> findAll();

    long count();

    void delete(User userToDelete);

    boolean exists(String primaryKey);
}
