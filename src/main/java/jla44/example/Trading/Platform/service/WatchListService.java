package jla44.example.Trading.Platform.service;

import jla44.example.Trading.Platform.model.Coin;
import jla44.example.Trading.Platform.model.User;
import jla44.example.Trading.Platform.model.WatchList;
import org.springframework.boot.autoconfigure.ssl.SslProperties;

public interface WatchListService {

    WatchList findUserWatchList(Long userId) throws Exception;
    WatchList createWatchList(User user);
    WatchList findById(Long id) throws Exception;

    Coin addItemToWatchList(Coin coin, User user) throws Exception;
}
