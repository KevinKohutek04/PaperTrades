package PaperWeight.Biscuit_corpo.controller;

import PaperWeight.Biscuit_corpo.APIReturn;
import PaperWeight.Biscuit_corpo.entity.ClosedPos;
import PaperWeight.Biscuit_corpo.entity.Pos;
import PaperWeight.Biscuit_corpo.entity.Song;
import PaperWeight.Biscuit_corpo.entity.User;
import PaperWeight.Biscuit_corpo.repository.ClosedPosRepository;
import PaperWeight.Biscuit_corpo.repository.PosRepository;
import PaperWeight.Biscuit_corpo.repository.RoleRepository;
import PaperWeight.Biscuit_corpo.repository.UserRepository;
import PaperWeight.Biscuit_corpo.response.JwtResponse;
import PaperWeight.Biscuit_corpo.service.TokenDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coin/")
public class CoinController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PosRepository posRepository;
    @Autowired
    ClosedPosRepository cposRepository;
    @CrossOrigin(origins = "http://localhost:8888")
    @GetMapping("/info")
    public ResponseEntity<?> money (@RequestParam String username) {
        Optional<User> tempUser = userRepository.findByUsername(username);
        User cleint = tempUser.get();
        return ResponseEntity.ok().body(cleint.getUsdt());
    }
    @CrossOrigin(origins = "http://localhost:8888")
    @GetMapping("/leaders")
    public ResponseEntity<?> leaders () {
        List<User> users = userRepository.findAll();
        users.sort((user1, user2) -> Double.compare(user2.getUsdt(), user1.getUsdt()));
        List<Map<String, Object>> top10Users = users.stream()
                .limit(10)
                .map(user -> {
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("username", user.getUsername());
                    userMap.put("usdt", user.getUsdt());
                    return userMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(top10Users);

    }
    @CrossOrigin(origins = "http://localhost:8888")
    @GetMapping("/type")
    public double typeo (@RequestParam int type) {
        return TokenDataService.getCryptoData(type);
    }
    @CrossOrigin(origins = "http://localhost:8888")
    @GetMapping("/viewOpenPos")
    public ResponseEntity<?> veiwOpenPos (@RequestParam String username) {
        Optional<User> user = userRepository.findByUsername(username);
        List<Pos> pos = posRepository.findByUser(user.get().getId());

        return ResponseEntity.ok().body(Arrays.asList(pos));
    }
    @CrossOrigin(origins = "http://localhost:8888")
    @GetMapping("/viewPos")
    public ResponseEntity<?> veiwPos (@RequestParam String username) {
        Optional<User> user = userRepository.findByUsername(username);
        List<ClosedPos> Cpos = cposRepository.findByUser(user.get().getId());

        return ResponseEntity.ok().body(Arrays.asList(Cpos));
    }
    @CrossOrigin(origins = "http://localhost:8888")
    @PostMapping("/openPos")
    public ResponseEntity<APIReturn> openPos (@RequestParam double usdt,
                                                  @RequestParam String user, @RequestParam int type,
                                                  @RequestParam double stopLoss, @RequestParam double takeProfit,
                                                  @RequestParam boolean shortLong, @RequestParam int lev) {
        try {
            Optional<User> tempUser = userRepository.findByUsername(user);
            //User cleint = userRepository.findByUsername(user).orElseThrow(() -> new UsernameNotFoundException("User couldnt be found using this username"));
           User cleint = tempUser.get();
           double openPos = TokenDataService.getCryptoData(type);
            if(cleint.getUsdt() < usdt) {
                return ResponseEntity.status(401).body(new APIReturn("556", "ERROR", "BAD MONEY"));
            }
            cleint.setUsdt(cleint.getUsdt() - usdt);
            Pos pos = new Pos(usdt, openPos, cleint.getId(), type, stopLoss, takeProfit, shortLong, lev);
            userRepository.save(cleint);
            posRepository.save(pos);
            return ResponseEntity.status(201).body(new APIReturn("0","SAVED","SAVED"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new APIReturn("556", "ERROR", "REQUEST FAILED"));
        }
    }
    @CrossOrigin(origins = "http://localhost:8888")
    @PostMapping("/closePos")
    public ResponseEntity<APIReturn> closePos (@RequestParam String username, @RequestParam int id) {
        try {
            Optional<Pos> temp = posRepository.findById(id);
            Optional<User> temp2 = userRepository.findByUsername(username);
            Pos pos = temp.get();
            User user = temp2.get();
            user.setUsdt(user.getUsdt() + pos.calUsdt() + pos.getUsdt());
            ClosedPos cPos = new ClosedPos(pos.getShortLong(),pos.getOpen(),pos.getOpenPos(), TokenDataService.getCryptoData(pos.getType()),
                    pos.getUsdt(), pos.getUser(), pos.getType(), pos.getLev(), pos.getStopLoss(), pos.getTakeProfit());
            cposRepository.save(cPos);
            userRepository.save(user);
            posRepository.delete(pos);
            return ResponseEntity.status(201).body(new APIReturn("0","SAVED","SAVED"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new APIReturn("556", "ERROR", "REQUEST FAILED"));
        }
    }
}