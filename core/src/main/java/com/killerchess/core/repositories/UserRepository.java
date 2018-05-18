package com.killerchess.core.repositories;

import com.killerchess.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//w przyszłości można dodawać więcej metod, wedle uznania (odpowiednie nazewnictwo załatwia nam implementację)
//drugą opcją jest przypisanie odpowiedniego "query" do metody
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByLogin(String login);
}
