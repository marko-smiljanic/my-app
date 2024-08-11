package com.example.application.views.tabelaKorisnika;

import com.example.application.entity.User;
import com.example.application.services.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@AnonymousAllowed
@PageTitle("Korisnici2")
@Route(value = "korisnici2", layout = MainLayout.class)
public class TabelaKorisnikaView extends Div {
    @Autowired
    private UserService userService;


    public TabelaKorisnikaView(UserService userService) {
        //refreshGrid();
        this.userService = userService;
        iscrtaj();
    }

    private void iscrtaj(){
        Grid<User> grid = new Grid<>(User.class, false);
        grid.addColumn(User::getIme).setHeader("First name");
        grid.addColumn(User::getPrezime).setHeader("Last name");
        grid.addColumn(User::getTelefon).setHeader("Phone");

        grid.addColumn(new ComponentRenderer<>(user -> {
            // Create a horizontal layout to hold both buttons
            HorizontalLayout layout = new HorizontalLayout();

            // Create and configure the "Edit" button
            Button editButton = new Button(new Icon(VaadinIcon.EDIT));
            editButton.addClickListener(e -> editUser(user));
            editButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);

            // Create and configure the "Delete" button
            Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
            deleteButton.addClickListener(e -> deleteUser(user.getId()));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);

            // Add both buttons to the layout
            layout.add(editButton, deleteButton);

            // Return the layout containing both buttons
            return layout;
        })).setHeader("Actions");

        List<User> users = userService.findAll();
        grid.setItems(users);
        add(grid);
    }

    private void editUser(User n){
        //refreshGrid();
    }

    private void deleteUser(Long id){
        //refreshGrid();

    }





}
