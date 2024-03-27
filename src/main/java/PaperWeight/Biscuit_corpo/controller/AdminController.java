package PaperWeight.Biscuit_corpo.controller;

import PaperWeight.Biscuit_corpo.APIReturn;
import PaperWeight.Biscuit_corpo.entity.ERole;
import PaperWeight.Biscuit_corpo.entity.Roles;
import PaperWeight.Biscuit_corpo.entity.User;
import PaperWeight.Biscuit_corpo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import PaperWeight.Biscuit_corpo.repository.UserRepository;

import java.util.*;

@RestController
@RequestMapping("/admin/")
public class AdminController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository urepo;
    @PostMapping(value = "/AddUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIReturn> AddUser (@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        if(username != null) {
            Optional<User> array = urepo.findByUsername(username);
            if(array.stream().count() > 0) {
                return ResponseEntity.unprocessableEntity().body(new APIReturn("55","NOT_SAVED","ALREADY EXIST"));
            } else {
                User add = new User(username, password, email);
                add = urepo.save(add);
                return ResponseEntity.unprocessableEntity().body(new APIReturn("00","SAVED","SAVE WITH EMAIL:" + add.getEmail()));
            }
        }
        return ResponseEntity.unprocessableEntity().body(new APIReturn("56","ERROR","INVAID INPUT"));
    }

    //@RequestParam String name, @RequestParam int active, , @RequestParam int userType
    @PutMapping(value = "/UpdateUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIReturn> upUser (@RequestParam String username, @RequestParam int nrole) {

        Optional<User> array = urepo.findByUsername(username);
        List<User> result = array
                .map(Collections::singletonList) // Create a singleton list if the Optional has a value
                .orElse(Collections.emptyList());
        User crt = null;
        for(User p : result) {
            if(p.getUsername().equals(username)) {
                crt = p;
            }
        }

       // @RequestParam Set<Roles> roles
       // crt.setRoles(roles);
        Set<Roles> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found")));
        switch (nrole) {
            case 2: roles.add(roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found")));
                break;
            case 3: roles.add(roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found")));
                    roles.add(roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found")));
                break;
        }

        crt.setRoles(roles);


        urepo.save(crt);
        return ResponseEntity.unprocessableEntity().body(new APIReturn("00","WORKED","COMPLETED"));
    }
}/*roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
@Id
    private String username;
    private String name;
    @JsonIgnore
    private String password;
    private int active;
    @Transient
    private Set<Roles> roles = new HashSet<>();
    private int userType;
*/