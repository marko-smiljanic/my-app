package com.example.application.views.prikazKorisnika;

import com.example.application.entitet.User;
import com.example.application.repozitorijum.UserRepository;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@AnonymousAllowed
@PageTitle("Korisnici")
@Route(value = "korisnici", layout = MainLayout.class)
public class PrikazKorisnikaView extends VerticalLayout {
    private final Grid<User> grid = new Grid<>(User.class);
    @Autowired
    private UserRepository userService;


    public PrikazKorisnikaView(UserRepository userService) {
        this.userService = userService;
        addClassName("user-list-view");
        setSizeFull();
        configureGrid();
        add(grid);
        updateGrid();
    }

    private void configureGrid() {
        grid.addClassName("user-grid");
        grid.setSizeFull();
        grid.setColumns("username", "ime", "prezime", "telefon");

        // Add any additional column configuration here if needed
    }

    private void updateGrid() {
        grid.setItems(userService.findAll());
    }
}
