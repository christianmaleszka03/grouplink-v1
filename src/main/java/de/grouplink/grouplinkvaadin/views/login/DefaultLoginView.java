package de.grouplink.grouplinkvaadin.views.login;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import de.grouplink.grouplinkvaadin.security.AuthenticatedUserHelper;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class DefaultLoginView extends VerticalLayout implements BeforeEnterObserver {

    private final AuthenticatedUserHelper authenticatedUserHelper;

    private LoginForm login = new LoginForm();
    private Anchor registerLink = new Anchor("register", "Noch kein Account? Jetzt registrieren.");

    public DefaultLoginView(AuthenticatedUserHelper authenticatedUserHelper) {
        this.authenticatedUserHelper = authenticatedUserHelper;
        addClassName("custom-login-view");
        login.addClassName("custom-login-form");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        login.setAction("login");
        login.setForgotPasswordButtonVisible(false);

        LoginI18n loginI18n = LoginI18n.createDefault();
        loginI18n.getForm().setUsername("E-Mail");
        loginI18n.getForm().setTitle("Login");
        loginI18n.getForm().setSubmit("Einloggen");
        loginI18n.getForm().setPassword("Passwort");
        loginI18n.getErrorMessage().setTitle("Fehler beim Einloggen.");
        loginI18n.getErrorMessage().setMessage("E-Mail oder Passwort falsch. Bitte versuchen Sie es erneut.");
        login.setI18n(loginI18n);

        StreamResource imageResource = new StreamResource("arbiana-logistic-logo.png",
                () -> getClass().getResourceAsStream("/images/grouplink_logo_transparent.png"));
        Image img = new Image(imageResource, "GroupLink Logo");
        img.addClassName("custom-login-form-img");

        FlexLayout headerContainer = new FlexLayout();
        headerContainer.add(img);
        headerContainer.addClassName("custom-login-form-header-container");

        registerLink.setHref("register");
        registerLink.addClassName("custom-login-form-register-anchor");
        add(headerContainer, login, registerLink);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(authenticatedUserHelper.get().isPresent()) {
            beforeEnterEvent.rerouteTo("");
        } else
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}