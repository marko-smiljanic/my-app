package com.example.application.services;

import com.example.application.DTO.Konverzija;
import com.example.application.DTO.UserDTO;
import com.example.application.entity.Firma;
import com.example.application.entity.User;
import com.example.application.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

//    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public Optional<User> get(Long id) {
        return repository.findById(id);
    }

//    public List<UserDTO> findAll() {
//        //return repository.findAll();
//        ArrayList<UserDTO> listadto = new ArrayList<>();
//        for(User n : repository.findAll()){
//            UserDTO ndto = null;
//            ndto = Konverzija.konvertujUDto(n, UserDTO.class);
//            listadto.add(ndto);
//        }
//        return listadto;
//    }

    public List<UserDTO> findAll2() {
        return repository.findAll().stream()
                .map(user -> Konverzija.konvertujUDto(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User update(User entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<User> list(Pageable pageable, Specification<User> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
