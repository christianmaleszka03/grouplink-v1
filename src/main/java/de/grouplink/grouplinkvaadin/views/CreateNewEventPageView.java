package de.grouplink.grouplinkvaadin.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.grouplink.grouplinkvaadin.service.highlevel.OrganizerHighlevelService;
import de.grouplink.grouplinkvaadin.views.myevents.MyEventsPageView;
import jakarta.annotation.security.PermitAll;

@PageTitle("Neues Event erstellen")
@Route(value = "event-erstellen", layout = AppLayout.class)
@PermitAll
public class CreateNewEventPageView extends VerticalLayout {

    private final OrganizerHighlevelService organizerHighlevelService   ;

    private final TextField nameTextField = new TextField("Name des Events");
    private final TextArea descriptionTextArea = new TextArea("Beschreibung des Events");

    private final Button createButton = new Button("Erstellen");

    private final Button cancelButton = new Button("Abbrechen");

    public CreateNewEventPageView(OrganizerHighlevelService organizerHighlevelService) {
        this.organizerHighlevelService = organizerHighlevelService;
        addClassName("custom-register-view");
        setHeightFull();
        setWidthFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        FlexLayout headerContainer = new FlexLayout();
        headerContainer.addClassName("custom-login-form-header-container");

        VerticalLayout container = new VerticalLayout();

        H3 header = new H3("Neues Event erstellen");
        header.addClassName("custom-register-view-header");

        nameTextField.setRequired(true);
        nameTextField.setRequiredIndicatorVisible(true);
        nameTextField.setErrorMessage("Bitte gib einen Namen ein.");
        nameTextField.setPlaceholder("Mein Event");
        nameTextField.setWidthFull();
        nameTextField.addValueChangeListener(event -> updateEnabledForRegisterButton());

        descriptionTextArea.setPlaceholder("Deine Beschreibung");
        descriptionTextArea.setWidthFull();
        descriptionTextArea.setMinHeight("150px");
        descriptionTextArea.addValueChangeListener(event -> updateEnabledForRegisterButton());

        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelButton.setWidthFull();
        cancelButton.addClickListener(event -> {
            UI.getCurrent().navigate(MyEventsPageView.class);
        });

        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createButton.setEnabled(false);
        createButton.setWidthFull();
        createButton.setDisableOnClick(true);
        createButton.addClickListener(event -> {
            organizerHighlevelService.createEventForCurrentUser(
                    nameTextField.getValue(),
                    descriptionTextArea.getValue()
            );
        });

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(cancelButton, createButton);
        buttonLayout.addClassName("custom-register-view-button-layout");

        container.add(
                headerContainer,
                header,
                nameTextField,
                descriptionTextArea,
                buttonLayout
        );
        container.addClassName("custom-register-view-container");
        add(container);
    }

    private void updateEnabledForRegisterButton() {
        createButton.setEnabled(
                !nameTextField.isEmpty()
        );
    }
}
