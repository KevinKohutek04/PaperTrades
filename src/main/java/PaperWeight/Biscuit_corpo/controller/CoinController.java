package PaperWeight.Biscuit_corpo.controller;

import PaperWeight.Biscuit_corpo.APIReturn;
import PaperWeight.Biscuit_corpo.entity.Pos;
import PaperWeight.Biscuit_corpo.entity.Song;
import PaperWeight.Biscuit_corpo.entity.User;
import PaperWeight.Biscuit_corpo.repository.PosRepository;
import PaperWeight.Biscuit_corpo.repository.RoleRepository;
import PaperWeight.Biscuit_corpo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coin/")
public class CoinController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PosRepository posRepository;
    @GetMapping("/myPos")
    public List<Pos> getPos (long user) {//u need user id
        return posRepository.findByUser(user);
    }
    @PutMapping("/buyCoin")
    public ResponseEntity<APIReturn> buyCoin (@RequestParam double cValue, @RequestParam String username, @RequestParam double usdt, @RequestParam int type) {
        try {
            if(usdt<1)
                return ResponseEntity.status(400).body(new APIReturn("400","ERROR","BAD REQUEST, USDT TO LOW"));
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User couldnt be found using this username"));
             long id = user.getId();
            LocalDate currentDate = LocalDate.now();
            String fDate = currentDate.format(DateTimeFormatter.ofPattern("MMddyyyy"));
            Pos pos = new Pos(usdt, cValue, Integer.parseInt(fDate) , id, type); //double usdt, double openPos, double open, long user, int type  System
            posRepository.save(pos);
            //no money
            if(user.getUsdt() < usdt)
                return ResponseEntity.status(401).body(new APIReturn("400","ERROR","NO MONEY"));
            user.setUsdt(user.getUsdt() - usdt);
            userRepository.save(user);
            return ResponseEntity.status(201).body(new APIReturn("0","SAVED","BOUGHT: " + usdt / cValue + " WORTH. ID: " + pos.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new APIReturn("556","ERROR","REQUEST FAILED"));
        }
    }
    /*
            try {
                posRepository.save(pos);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
    @PutMapping("/sellCoin")
    public ResponseEntity<APIReturn> sellCoin (@RequestParam double cValue, @RequestParam double usdt, @RequestParam int pId) {
        try {
            if(usdt<1)
                return ResponseEntity.status(400).body(new APIReturn("400","ERROR","BAD REQUEST, USDT TO LOW"));
            LocalDate currentDate = LocalDate.now();
            String fDate = currentDate.format(DateTimeFormatter.ofPattern("MMddyyyy"));

            Optional<Pos> array = posRepository.findById(pId);
            Pos pos = array.get();
            Optional<User> userA = userRepository.findById(pos.getUser());
            User user = userA.get();
            pos.setClose(Integer.parseInt(fDate));
            pos.setClosePos(cValue);
            posRepository.save(pos);
            //important Math.floor(user.getUsdt() + (pos.getUsdt() / pos.getOpenPos() * cValue) * 100) / 100
            user.setUsdt(Math.floor(user.getUsdt() + (pos.getUsdt() / pos.getOpenPos() * cValue) * 100) / 100 );
            userRepository.save(user);
            return ResponseEntity.status(201).body(new APIReturn("0","SAVED","SOLD: " + usdt / cValue + " WORTH"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new APIReturn("556","ERROR","REQUEST FAILED"));
        }
    }
}
/*
* @PutMapping("/buyCoin")
    public ResponseEntity<APIReturn> buyCoin (@RequestParam double cValue, @RequestParam String username, @RequestParam double usdt, @RequestParam int type) {
        try {
            if(usdt<1)
                return ResponseEntity.status(400).body(new APIReturn("400","ERROR","BAD REQUEST USDT TO LOW"));
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User couldnt be found using this username"));
            double amount = usdt / cValue;
            switch (type) {//1btc 2eth 3sol
                case 1:
                    user.setBtc(user.getBtc() + amount);
                    break;
                case 2:
                    user.setEth(user.getEth() + amount);
                    break;
                case 3:
                    user.setSol(user.getSol() + amount);
                    break;
            }
            user.setUsdt(user.getUsdt() - usdt);
            userRepository.save(user);
            return ResponseEntity.status(201).body(new APIReturn("0","SAVED","BOUGHT: " + amount + " WORTH"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new APIReturn("556","ERROR","REQUEST FAILED"));
        }
    }
    @PutMapping("/sellCoin")
    public ResponseEntity<APIReturn> sellCoin (@RequestParam double cValue, @RequestParam String username, @RequestParam double cAmount, @RequestParam int type) {
        try {
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User couldnt be found using this username"));
            switch (type) {//1btc 2eth 3sol
                case 1:
                    user.setBtc(user.getBtc() - cAmount);
                    break;
                case 2:
                    user.setEth(user.getEth() - cAmount);
                    break;
                case 3:
                    user.setSol(user.getSol() - cAmount);
                    break;
            }
            user.setUsdt(user.getUsdt() + cValue * cAmount);
            userRepository.save(user);
            return ResponseEntity.status(201).body(new APIReturn("0","SAVED","SOLD: " + cAmount + " WORTH"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new APIReturn("556","ERROR","REQUEST FAILED"));
        }
    }
    @PutMapping("/giveCoin")
    public ResponseEntity<APIReturn> give (@RequestParam String gUsername, @RequestParam String rUsername, @RequestParam double total) {
        try {
            User gUser = userRepository.findByUsername(gUsername).orElseThrow(() -> new UsernameNotFoundException("User couldnt be found using this gUsername"));
            User rUser = userRepository.findByUsername(rUsername).orElseThrow(() -> new UsernameNotFoundException("User couldnt be found using this rUsername"));
            gUser.setUsdt(gUser.getUsdt() - total);
            rUser.setUsdt(rUser.getUsdt() + total);

            userRepository.save(gUser);
            userRepository.save(rUser);
            return ResponseEntity.status(201).body(new APIReturn("0","SAVED","GAVE: " + total + " TO: " + rUsername + " FROM: " + gUsername));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new APIReturn("556","ERROR","REQUEST FAILED"));
        }
    }
* */