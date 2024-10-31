package jla44.example.Trading.Platform.repository;

import jla44.example.Trading.Platform.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchListRepository extends JpaRepository<WatchList, Long> {

    WatchList findByUserId(Long userId);
}
