package de.grouplink.grouplinkvaadin.sharedcomponents;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import de.grouplink.grouplinkvaadin.data.dto.GroupDTO;

import java.util.function.Consumer;

public class GroupEditorView extends VerticalLayout {
    // CALLBACKS
    private final Consumer<GroupDTO> onSave;
    private final Consumer<Void> onCancel;

    private final TextField nameTextField = new TextField("Name");
    private final TextArea descriptionTextField = new TextArea("Beschreibung");
    private final NumberField minMembersNumberField = new NumberField("Minimale Teilnehmer");
    private final NumberField maxMembersNumberField = new NumberField("Maximale Teilnehmer");
    private final NumberField idealMembersNumberField = new NumberField("Ideale Teilnehmer");

    private final Button saveButton = new Button("Speichern", VaadinIcon.CHECK.create());
    private final Button cancelButton = new Button("Abbrechen");

    public GroupEditorView(GroupDTO group, Consumer<GroupDTO> onSave, Consumer<Void> onCancel) {
        this.onSave = onSave;
        this.onCancel = onCancel;
        setSizeFull();

        // setup of fields
        nameTextField.setRequired(true);
        nameTextField.setErrorMessage("Name darf nicht leer sein");
        nameTextField.setClearButtonVisible(true);
        nameTextField.setWidth("80%");
        nameTextField.setMinWidth("300px");
        nameTextField.setMaxWidth("500px");
        nameTextField.setPlaceholder("Name der Gruppe");
        nameTextField.addValueChangeListener(e -> {
            checkIfSavingIsPossibleAndSetButtonState();
        });

        descriptionTextField.setClearButtonVisible(true);
        descriptionTextField.setWidth("80%");
        descriptionTextField.setMinWidth("300px");
        descriptionTextField.setMaxWidth("500px");
        descriptionTextField.setPlaceholder("Beschreibung der Gruppe");

        minMembersNumberField.setRequired(true);
        minMembersNumberField.setErrorMessage("Muss angegeben werden");
        minMembersNumberField.setStep(1);
        minMembersNumberField.setMin(0);
        minMembersNumberField.setWidth("80%");
        minMembersNumberField.setMinWidth("300px");
        minMembersNumberField.setMaxWidth("500px");
        minMembersNumberField.addValueChangeListener(e -> {
            checkIfSavingIsPossibleAndSetButtonState();
        });

        maxMembersNumberField.setRequired(true);
        maxMembersNumberField.setErrorMessage("Muss angegeben werden");
        maxMembersNumberField.setStep(1);
        maxMembersNumberField.setMin(1);
        maxMembersNumberField.setWidth("80%");
        maxMembersNumberField.setMinWidth("300px");
        maxMembersNumberField.setMaxWidth("500px");
        maxMembersNumberField.addValueChangeListener(e -> {
            checkIfSavingIsPossibleAndSetButtonState();
        });

        idealMembersNumberField.setStep(1);
        idealMembersNumberField.setMin(0);
        idealMembersNumberField.setWidth("80%");
        idealMembersNumberField.setMinWidth("300px");
        idealMembersNumberField.setMaxWidth("500px");

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        saveButton.addClickListener(e -> onSave.accept(new GroupDTO(
                group != null ? group.getId() : null,
                nameTextField.getValue(),
                descriptionTextField.getValue(),
                minMembersNumberField.getValue().intValue(),
                maxMembersNumberField.getValue().intValue(),
                idealMembersNumberField.getValue().intValue(),
                null
        )));

        cancelButton.addClickListener(e -> onCancel.accept(null));

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, saveButton);

        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        if(group != null) {
            nameTextField.setValue(group.getName());
            descriptionTextField.setValue(group.getDescription());
            minMembersNumberField.setValue((double) group.getMinMembers());
            maxMembersNumberField.setValue((double) group.getMaxMembers());
            idealMembersNumberField.setValue((double) group.getIdealMembers());
        }

        add(nameTextField, descriptionTextField, minMembersNumberField, maxMembersNumberField, idealMembersNumberField, buttonLayout);
    }

    private void checkIfSavingIsPossibleAndSetButtonState() {
       saveButton.setEnabled(!nameTextField.isEmpty() && !minMembersNumberField.isInvalid() && !minMembersNumberField.isEmpty() && !maxMembersNumberField.isInvalid() && !maxMembersNumberField.isEmpty());
    }
}
