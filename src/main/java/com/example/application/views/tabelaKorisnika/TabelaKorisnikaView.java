package com.example.application.views.tabelaKorisnika;

import com.example.application.DTO.UserDTO;
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
import com.vaadin.flow.data.provider.ListDataProvider;
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
    private ListDataProvider<UserDTO> dataProvider;  //ono sto prikazujem
    private Grid<UserDTO> grid;


    public TabelaKorisnikaView(UserService userService) {
        //refreshGrid();
        this.userService = userService;
        iscrtaj();
    }


    private void iscrtaj(){
        //true ili false znaci da li zelim automasko kreiranje kolona na osnovu entiteta
        //mapiranje na polja se radi preko geter i seter metoda, znaci json ignore nema svrhu o ovom slucaju
        grid = new Grid<>(UserDTO.class, false);
        grid.addColumn(UserDTO::getIme).setHeader("First name");
        grid.addColumn(UserDTO::getPrezime).setHeader("Last name");
        grid.addColumn(UserDTO::getTelefon).setHeader("Phone");

        grid.addColumn(new ComponentRenderer<>(userDTO -> {
            // Create a horizontal layout to hold both buttons
            HorizontalLayout layout = new HorizontalLayout();

            // Create and configure the "Edit" button
            Button editButton = new Button(new Icon(VaadinIcon.EDIT));
            editButton.addClickListener(e -> editUser(userDTO));
            editButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);

            // Create and configure the "Delete" button
            Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
            deleteButton.addClickListener(e -> deleteUser(userDTO.getId()));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);

            // Add both buttons to the layout
            layout.add(editButton, deleteButton);

            // Return the layout containing both buttons
            return layout;
        })).setHeader("Actions");


//        List<UserDTO> users = userService.findAll2();
//        grid.setItems(users);
        dataProvider = new ListDataProvider<>(userService.findAll2());
        grid.setDataProvider(dataProvider);
        add(grid);
    }


    private void editUser(UserDTO n){
        //refreshGrid();2
    }

    private void deleteUser(Long id){
        this.userService.delete(id);
        this.osveziPrikaz();
    }


    //ako ponovo pozovem crtanje prikaza kao na androidu sto sam radio, ovde nece raditi jer se ovde zadrzi stari prikaz a novi i azurirani se iscrta ispod
    //za azuriranje podataka radim preko data providera, jer je to ugradjena vaadin-va komponenta
    private void osveziPrikaz(){
        //azuriranje preko providera
        List<UserDTO> updatedUsers = userService.findAll2();
        dataProvider.getItems().clear();
        dataProvider.getItems().addAll(updatedUsers);
        dataProvider.refreshAll();
    }



//    private void iscrtaj(){
//        Grid<User> grid = new Grid<>(User.class, false);
//        grid.addColumn(User::getIme).setHeader("First name");
//        grid.addColumn(User::getPrezime).setHeader("Last name");
//        grid.addColumn(User::getTelefon).setHeader("Phone");
//
//        grid.addColumn(new ComponentRenderer<>(user -> {
//            // Create a horizontal layout to hold both buttons
//            HorizontalLayout layout = new HorizontalLayout();
//
//            // Create and configure the "Edit" button
//            Button editButton = new Button(new Icon(VaadinIcon.EDIT));
//            editButton.addClickListener(e -> editUser(user));
//            editButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);
//
//            // Create and configure the "Delete" button
//            Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
//            deleteButton.addClickListener(e -> deleteUser(user.getId()));
//            deleteButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);
//
//            // Add both buttons to the layout
//            layout.add(editButton, deleteButton);
//
//            // Return the layout containing both buttons
//            return layout;
//        })).setHeader("Actions");
//
//
//        List<User> users = userService.findAll();
//        grid.setItems(users);
//        add(grid);
//    }






}
