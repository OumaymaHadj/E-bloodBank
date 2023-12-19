package com.fsb.eblood.service;

import com.fsb.eblood.dao.repositories.RoleRepository;
import com.fsb.eblood.dao.repositories.UserRepository;
import com.fsb.eblood.dao.entities.Role;
import com.fsb.eblood.dao.enumerations.RoleName;
import com.fsb.eblood.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException ;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Component
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    RoleRepository roleRepo;

    public List<User> findAllByRolesByRolename(RoleName role){
        return userRepo.findAllByRolesName(role);
    }

    public Optional<User> findUserByUsername(String username){
        return userRepo.findByUsername(username);
    }
    public Optional<User> findAppUser(String email){
        return userRepo.findByEmail(email);
    }

    public ResponseEntity<User> updateAppUser(User user) {

        User existingAppUser = userRepo.findByUsername(user.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Could not find User with username = " + user.getUsername())
        );

        if (user.getUsername()!=null) existingAppUser.setUsername(user.getUsername());
        if (user.getEmail()!=null) existingAppUser.setEmail(user.getEmail());
        if (user.getPhone()!=null) existingAppUser.setPhone(user.getPhone());
        if (user.getRegion()!=null) existingAppUser.setRegion(user.getRegion());
        if (user.getImage()!=null) existingAppUser.setImage(user.getImage());
        if (user.getNbDemandeSang()!=0) existingAppUser.setNbDemandeSang(user.getNbDemandeSang());
        if (user.getNbDonSang()!=0) existingAppUser.setNbDonSang(user.getNbDonSang());
        if (user.getNbPoints()!=0) existingAppUser.setNbPoints(user.getNbPoints());
        if (user.getTypeSang()!=null) existingAppUser.setTypeSang(user.getTypeSang());
        if (user.getGps()!=null) existingAppUser.setGps(user.getGps());



        userRepo.save(existingAppUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Set<Role> addRole (User user){

        Role role = new Role(3L,RoleName.DONOR);
        Set<Role> roles = user.getRoles();
        roles.add(role);

        return roles;
    }


    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(long userId) {
        return userRepo.findById(userId);
    }

    public void delete(long id) {
        userRepo.deleteById(id);
    }

    public long getNbUsers() {
        return userRepo.count();
    }

    public long countByTypeSang(String typeSang) {
        return userRepo.countByTypeSang(typeSang);
    }

    public  int enableAppUser(String email){
            return userRepo.enableAppUser(email);
    }

    public ResponseEntity<User> toDonor(User user) {
        User existingAppUser = userRepo.findByUsername(user.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Could not find User with username = " + user.getUsername())
        );
        Role userRole = roleRepo.findByName(RoleName.DONOR);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        existingAppUser.setRoles(roles);
        userRepo.save(existingAppUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}