package PaperWeight.Biscuit_corpo.controller;

import PaperWeight.Biscuit_corpo.entity.ClosedPos;
import PaperWeight.Biscuit_corpo.entity.Pos;
import PaperWeight.Biscuit_corpo.entity.User;
import PaperWeight.Biscuit_corpo.repository.ClosedPosRepository;
import PaperWeight.Biscuit_corpo.repository.PosRepository;
import PaperWeight.Biscuit_corpo.repository.UserRepository;
import PaperWeight.Biscuit_corpo.service.TokenDataService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class TokenTracker {
    @Autowired
    UserRepository uRepo;
    @Autowired
    PosRepository repo;
    @Autowired
    ClosedPosRepository Crepo;
    @Scheduled(initialDelay = 10000,fixedRate = 30000)
    public void checkPos () {

        for(int i = 1; i != 4; i++) {
            List<Pos> positions = repo.findByType(i);
            for(Pos position : positions) {
                Optional<User> tempUser = uRepo.findById(position.getUser());
                User cleint = tempUser.get();
                double price = TokenDataService.getCryptoData(i);
                if(position.getShortLong()) {//long
                    if(position.getStopLoss() > price || position.getTakeProfit() < price) {
                        ClosedPos temp = new ClosedPos(true, position.getOpen(),
                                position.getOpenPos(), price,
                                position.getUsdt(), position.getUser(), i,
                                position.getLev(), position.getStopLoss(),
                                position.getTakeProfit());
                        cleint.setUsdt(cleint.getUsdt() + position.calUsdt() );
                        uRepo.save(cleint);
                        Crepo.save(temp);
                        repo.delete(position);
                    }
                } else { //short
                    if(position.getStopLoss() < price || position.getTakeProfit() > price) {
                        ClosedPos temp = new ClosedPos(false, position.getOpen(),
                                position.getOpenPos(), price,
                                position.getUsdt(), position.getUser(), i,
                                position.getLev(), position.getStopLoss(),
                                position.getTakeProfit());
                        cleint.setUsdt(cleint.getUsdt() + position.calUsdt() );
                        uRepo.save(cleint);
                        Crepo.save(temp);
                        repo.delete(position);
                    }
                }
            }
        }


    }
}
