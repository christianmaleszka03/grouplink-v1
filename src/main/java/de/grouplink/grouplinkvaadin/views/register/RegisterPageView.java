package de.grouplink.grouplinkvaadin.views.register;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import de.grouplink.grouplinkvaadin.service.highlevel.UnauthenticatedHighLevelService;

@PageTitle("Register")
@Route(value = "register")
@AnonymousAllowed
@Uses(Icon.class)
public class RegisterPageView extends VerticalLayout {

    private final UnauthenticatedHighLevelService unauthenticatedHighLevelService;

    private final TextField firstNameTextField = new TextField("Vorname");
    private final TextField lastNameTextField = new TextField("Nachname");
    private final EmailField emailField = new EmailField("E-Mail");
    private final PasswordField passwordField = new PasswordField("Passwort");
    private final PasswordField passwordRepeatField = new PasswordField("Passwort wiederholen");
    private final Checkbox isCompanyCheckbox = new Checkbox("Ich bin ein Unternehmen");
    private final Checkbox termsAndConditionsCheckbox = new Checkbox("Ich akzeptiere die AGBs und habe die Datenschutzbestimmungen zur Kenntnis genommen.");

    private final Button registerButton = new Button("Registrieren");

    private Anchor loginLink = new Anchor("login", "Bereits registriert? Hier einloggen");

    public RegisterPageView(UnauthenticatedHighLevelService unauthenticatedHighLevelService) {
        this.unauthenticatedHighLevelService = unauthenticatedHighLevelService;
        addClassName("custom-register-view");
        setHeightFull();
        setWidthFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        StreamResource imageResource = new StreamResource("arbiana-logistic-logo.png",
                () -> getClass().getResourceAsStream("/images/grouplink_logo.png"));
        Image img = new Image(imageResource, "GroupLink Logo");
        img.addClassName("custom-login-form-img");

        FlexLayout headerContainer = new FlexLayout();
        headerContainer.add(img);
        headerContainer.addClassName("custom-login-form-header-container");

        VerticalLayout container = new VerticalLayout();

        H3 header = new H3("Registrieren");
        header.addClassName("custom-register-view-header");

        firstNameTextField.setRequired(true);
        firstNameTextField.setRequiredIndicatorVisible(true);
        firstNameTextField.setErrorMessage("Bitte gib deinen Vornamen ein.");
        firstNameTextField.setPlaceholder("Max");
        firstNameTextField.setWidthFull();
        firstNameTextField.addValueChangeListener(event -> updateEnabledForRegisterButton());

        lastNameTextField.setRequired(true);
        lastNameTextField.setRequiredIndicatorVisible(true);
        lastNameTextField.setErrorMessage("Bitte gib deinen Nachnamen ein.");
        lastNameTextField.setPlaceholder("Mustermann");
        lastNameTextField.setWidthFull();
        lastNameTextField.addValueChangeListener(event -> updateEnabledForRegisterButton());

        emailField.setRequired(true);
        emailField.setRequiredIndicatorVisible(true);
        emailField.setErrorMessage("Bitte gib deine E-Mail-Adresse ein.");
        emailField.setPlaceholder("deine@mail.de");
        emailField.setWidthFull();
        emailField.addValueChangeListener(event -> updateEnabledForRegisterButton());

        passwordField.setRequired(true);
        passwordField.setRequiredIndicatorVisible(true);
        passwordField.setErrorMessage("Wähle ein sicheres Passwort.");
        passwordField.setWidthFull();
        passwordField.addValueChangeListener(event -> updateEnabledForRegisterButton());

        passwordRepeatField.setRequired(true);
        passwordRepeatField.setRequiredIndicatorVisible(true);
        passwordRepeatField.setErrorMessage("Bitte wiederhole dein Passwort.");
        passwordRepeatField.setWidthFull();
        passwordRepeatField.addValueChangeListener(event -> {
            updateEnabledForRegisterButton();
        });

        termsAndConditionsCheckbox.setRequiredIndicatorVisible(true);
        termsAndConditionsCheckbox.addValueChangeListener(event -> updateEnabledForRegisterButton());

        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.setEnabled(false);
        registerButton.setWidthFull();
        registerButton.addClickListener(event -> {
            registerButton.setEnabled(false);
            try {
                unauthenticatedHighLevelService.registerNewUser(
                        firstNameTextField.getValue(),
                        lastNameTextField.getValue(),
                        emailField.getValue(),
                        passwordField.getValue(),
                        isCompanyCheckbox.getValue()
                );
                Notification.show("Registrierung erfolgreich. Du kannst dich jetzt einloggen.", 5000, Notification.Position.BOTTOM_CENTER);
                UI.getCurrent().navigate("login");
            } catch (Exception e) {
                registerButton.setEnabled(true);
                e.printStackTrace();
            }

        });

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(registerButton);
        buttonLayout.addClassName("custom-register-view-button-layout");

        loginLink.addClassName("custom-register-view-login-link");
        loginLink.setWidthFull();

        container.add(
                headerContainer,
                header,
                firstNameTextField,
                lastNameTextField,
                emailField,
                passwordField,
                passwordRepeatField,
                isCompanyCheckbox,
                termsAndConditionsCheckbox,
                buttonLayout,
                loginLink
        );
        container.addClassName("custom-register-view-container");
        add(container);
    }

    private void updateEnabledForRegisterButton() {
        registerButton.setEnabled(
                !firstNameTextField.isEmpty() &&
                !lastNameTextField.isEmpty() &&
                !emailField.isEmpty() &&
                !passwordField.isEmpty() &&
                !passwordRepeatField.isEmpty() &&
                passwordField.getValue().equals(passwordRepeatField.getValue()) &&
                termsAndConditionsCheckbox.getValue()
        );
    }
}
