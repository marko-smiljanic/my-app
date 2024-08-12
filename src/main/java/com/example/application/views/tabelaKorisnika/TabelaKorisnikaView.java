package com.example.application.views.tabelaKorisnika;

import com.example.application.DTO.Konverzija;
import com.example.application.DTO.UserDTO;
import com.example.application.entity.Role;
import com.example.application.entity.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.services.UserService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

//@DenyAll
//@PermitAll
//@AnonymousAllowed
//@RolesAllowed({"ADMIN", "USER"})
@RolesAllowed("ADMIN")
@PageTitle("Korisnici2")
@Route(value = "korisnici2", layout = MainLayout.class)
public class TabelaKorisnikaView extends Div {
    @Autowired
    private UserService userService;
    private ListDataProvider<UserDTO> dataProvider;  //ono sto prikazujem
    private Grid<UserDTO> grid;
    private AuthenticatedUser authenticatedUser;
    @Autowired PasswordEncoder passwordEncoder;


    public TabelaKorisnikaView(UserService userService, AuthenticatedUser authenticatedUser, PasswordEncoder passwordEncoder) {
        //refreshGrid();
        this.userService = userService;
        this.authenticatedUser = authenticatedUser;
        this.passwordEncoder = passwordEncoder;

        iscrtaj();
    }


    private void iscrtaj(){
        Button addButton = new Button("Dodaj", event -> addUser());
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidth("100%");
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        buttonLayout.add(addButton);
        add(buttonLayout);


        //true ili false znaci da li zelim automasko kreiranje kolona na osnovu entiteta
        //mapiranje na polja se radi preko geter i seter metoda, znaci json ignore nema svrhu o ovom slucaju
        grid = new Grid<>(UserDTO.class, false);
        grid.addColumn(UserDTO::getUsername).setHeader("Userame");
        grid.addColumn(UserDTO::getIme).setHeader("First name");
        grid.addColumn(UserDTO::getPrezime).setHeader("Last name");
        grid.addColumn(UserDTO::getTelefon).setHeader("Phone");
        grid.addColumn(UserDTO::getRoles).setHeader("Role");

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
        add(grid);  //dodaj u osnovnu komponentu

        //testirano - - RADI!!!!!!
        //provera prava pristupa mi ovde ne treba za delove koda jer je cela ruta (komponenta) zastice za rolu admin anotacijom
//        System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz   " + authenticatedUser.hasRole(Role.USER));
    }

    private void editUser(UserDTO dto) {
        // Kreiraj dijalog
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");

        // Kreiraj polja za unos
        TextField imeField = new TextField("First Name");
        imeField.setValue(dto.getIme() != null ? dto.getIme() : "");
        imeField.setRequired(true);
        imeField.setRequiredIndicatorVisible(true);
        imeField.setErrorMessage("Field is required!!!");

        TextField prezimeField = new TextField("Last Name");
        prezimeField.setValue(dto.getPrezime() != null ? dto.getPrezime() : "");
        prezimeField.setRequired(true);
        prezimeField.setRequiredIndicatorVisible(true);
        prezimeField.setErrorMessage("Field is required!!!");

        TextField  telefonField = new TextField ("Phone");
        telefonField.setValue(dto.getTelefon() != null ? dto.getTelefon() : "");
        telefonField.setPattern("\\+?\\d*");
        telefonField.setErrorMessage("Phone number must contain only numbers and an optional '+' sign, and no space.");

        PasswordField passwordField = new PasswordField("Password");
        //necu da setujem lozinku u polje
        //passswordField.setValue(userDTO.getHashedPassword() == null ? passwordEncoder.encode(userDTO.getHashedPassword()) : "");
        passwordField.setRequired(true);
        passwordField.setRequiredIndicatorVisible(true);
        passwordField.setErrorMessage("Password is required!!!");

        CheckboxGroup<Role> roleCheckboxGroup = new CheckboxGroup<>("Roles");
        roleCheckboxGroup.setLabel("Select Roles");
        roleCheckboxGroup.setItems(EnumSet.allOf(Role.class));
        roleCheckboxGroup.setItemLabelGenerator(Role::name);
        roleCheckboxGroup.setRequired(true);
        roleCheckboxGroup.setErrorMessage("At least one role must be selected.");
        roleCheckboxGroup.setValue(dto.getRoles());   //da bude popunjeno


        // Kreiraj dugme za čuvanje izmena
        Button saveButton = new Button("Save", event -> {
            if (roleCheckboxGroup.getValue().isEmpty() || telefonField.isInvalid() || imeField.isEmpty() || prezimeField.isEmpty() || passwordField.isEmpty()) {
                Notification.show("Please fill all required fields.");
                return;
            }
            dto.setIme(imeField.getValue());
            dto.setPrezime(prezimeField.getValue());
            dto.setTelefon(telefonField.getValue().toString());
            dto.setRoles(roleCheckboxGroup.getSelectedItems());
            String password = passwordField.getValue();
            if (password != null && !password.isEmpty()) {
                dto.setHashedPassword(passwordEncoder.encode(password));
            }

            // Servis uzera za cuvanje u bazu i pretvaranje dto prosledjenih objekata u User objekat
            User n = Konverzija.konvertujUEntitet(dto, User.class);
            n.setId(dto.getId());
            userService.update(n);

            this.osveziPrikaz();
            dialog.close();
        });

        //Otkazivanje izmena
        Button cancelButton = new Button("Cancel", event -> dialog.close());

        dialog.add(new VerticalLayout(imeField, prezimeField, telefonField, passwordField, roleCheckboxGroup, saveButton, cancelButton));
        dialog.open();
    }

    private void addUser() {
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");

        // Kreiraj polja za unos

        TextField usernameField = new TextField("Userame");
        usernameField.setRequired(true);
        usernameField.setRequiredIndicatorVisible(true);
        usernameField.setErrorMessage("Field is required!!!");


        TextField imeField = new TextField("First Name");
        imeField.setRequired(true);
        imeField.setRequiredIndicatorVisible(true);
        imeField.setErrorMessage("Field is required!!!");

        TextField prezimeField = new TextField("Last Name");
        prezimeField.setRequired(true);
        prezimeField.setRequiredIndicatorVisible(true);
        prezimeField.setErrorMessage("Field is required!!!");

        TextField telefonField = new TextField ("Phone");
        telefonField.setPattern("\\+?\\d*");
        telefonField.setErrorMessage("Phone number must contain only numbers and an optional '+' sign, and no space.");

        PasswordField passwordField = new PasswordField("Password");
        passwordField.setRequired(true);
        passwordField.setRequiredIndicatorVisible(true);
        passwordField.setErrorMessage("Password is required!!!");

        CheckboxGroup<Role> roleCheckboxGroup = new CheckboxGroup<>("Roles");
        roleCheckboxGroup.setLabel("Select Roles");
        roleCheckboxGroup.setItems(EnumSet.allOf(Role.class));
        roleCheckboxGroup.setItemLabelGenerator(Role::name);
        roleCheckboxGroup.setRequired(true);
        roleCheckboxGroup.setErrorMessage("At least one role must be selected.");
        //roleCheckboxGroup.setValue(dto.getRoles());

        Button saveButton = new Button("Save", event -> {
            if (roleCheckboxGroup.getValue().isEmpty() || telefonField.isInvalid() || usernameField.isEmpty() || imeField.isEmpty() || prezimeField.isEmpty() || passwordField.isEmpty()) {
                Notification.show("Please fill all required fields.");
                return;
            }
            // Kreiraj novog usera
            User newUser = new User();
            newUser.setUsername(usernameField.getValue());
            newUser.setIme(imeField.getValue());
            newUser.setPrezime(prezimeField.getValue());
            newUser.setTelefon(telefonField.getValue() != null ? telefonField.getValue() : "");
            newUser.setRoles(roleCheckboxGroup.getSelectedItems());
            String password = passwordField.getValue();
            if (password != null && !password.isEmpty()) {
                newUser.setHashedPassword(passwordEncoder.encode(password));
            }

            // Cuvanje u bazu novog usera
            userService.create(newUser);

            // Osvezavanje prikaza grida
            this.osveziPrikaz();
            dialog.close();
        });

        Button cancelButton = new Button("Cancel", event -> dialog.close());
        dialog.add(new VerticalLayout(usernameField, imeField, prezimeField, telefonField, passwordField, roleCheckboxGroup, saveButton, cancelButton));
        dialog.open();
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








}
