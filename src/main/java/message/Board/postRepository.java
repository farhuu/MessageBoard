package message.Board;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface postRepository extends JpaRepository<Messageboard, Integer> {
}