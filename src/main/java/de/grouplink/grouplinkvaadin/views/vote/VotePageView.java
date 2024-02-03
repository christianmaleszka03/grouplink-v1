package de.grouplink.grouplinkvaadin.views.vote;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Flex;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexWrap;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Height;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Position;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import de.grouplink.grouplinkvaadin.data.dto.GroupDTO;
import de.grouplink.grouplinkvaadin.data.enums.Gender;
import de.grouplink.grouplinkvaadin.service.lowlevel.GroupLowLevelService;
import de.grouplink.grouplinkvaadin.views.FakeAppLayout;
import de.grouplink.grouplinkvaadin.views.vote.components.GroupPreferencesView;

import java.util.*;

@PageTitle("Vote")
@Route(value = "vote", layout = FakeAppLayout.class)
@AnonymousAllowed
public class VotePageView extends Div {
    // STATE
    private final int MAX_GROUP_PREFERENCES = 4;
    private final int MIN_GROUP_PREFERENCES = 3;


    private final TextField firstNameTextField = new TextField("Vorname");
    private final TextField lastNameTextField = new TextField("Nachname");
    private final Select<Gender> genderSelect = new Select<>();
    private GroupPreferencesView groupPreferencesView;
    private final TextArea cantGoWithEachOtherTextArea = new TextArea();

    private final Button submitButton = new Button("Absenden", new Icon(VaadinIcon.CHECK));

    private final UnorderedList asideList = new UnorderedList();

    public VotePageView(GroupLowLevelService groupLowLevelService) {
        addClassNames("vote-view");
        addClassNames(Display.FLEX, FlexDirection.COLUMN, Height.FULL);

        Main content = new Main();
        content.addClassNames(Display.GRID, Gap.XLARGE, AlignItems.START, JustifyContent.CENTER, MaxWidth.SCREEN_MEDIUM,
                Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        UUID someEventId = UUID.randomUUID();
        content.add(createCheckoutForm());
        content.add(createAside());
        add(content);
        updateAsideList();
        checkIfSubmitIsPossibleAndChangeButtonState();
    }

    private Component createCheckoutForm() {
        Section checkoutForm = new Section();
        checkoutForm.addClassNames(Display.FLEX, FlexDirection.COLUMN, Flex.GROW);

        H2 header = new H2("Abstimmen");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph note = new Paragraph("Bitte geben Sie mindestens 2 Präferenzen an.");
        note.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        checkoutForm.add(header, note);

        checkoutForm.add(createPersonalDetailsSection());
        checkoutForm.add(createGroupPreferencesSection());
        checkoutForm.add(createCantGoWithEachOtherSection());
        checkoutForm.add(new Hr());
        checkoutForm.add(createFooter());

        return checkoutForm;
    }

    private Section createPersonalDetailsSection() {
        Section personalDetails = new Section();
        personalDetails.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);

        Paragraph stepOne = new Paragraph("Abstimmen 1/3");
        stepOne.addClassNames(Margin.NONE, FontSize.SMALL, TextColor.SECONDARY);

        H3 header = new H3("Persönliche Informationen");
        header.addClassNames(Margin.Bottom.MEDIUM, Margin.Top.SMALL, FontSize.XXLARGE);
        stepOne.addClassNames(Margin.NONE, FontSize.SMALL, TextColor.SECONDARY);

        firstNameTextField.setRequiredIndicatorVisible(true);
        firstNameTextField.addClassNames(Margin.Bottom.SMALL);
        firstNameTextField.addValueChangeListener(e -> {
            updateAsideList();
            checkIfSubmitIsPossibleAndChangeButtonState();
        });

        lastNameTextField.setRequiredIndicatorVisible(true);
        lastNameTextField.addClassNames(Margin.Bottom.SMALL);
        lastNameTextField.addValueChangeListener(e -> {
            updateAsideList();
            checkIfSubmitIsPossibleAndChangeButtonState();
        });

        genderSelect.setLabel("Geschlecht");
        genderSelect.setRequiredIndicatorVisible(true);
        genderSelect.addClassNames(Margin.Bottom.SMALL);
        genderSelect.setItems(Gender.values());
        genderSelect.setItemLabelGenerator(Gender::getDisplayName);
        genderSelect.addValueChangeListener(e -> {
            checkIfSubmitIsPossibleAndChangeButtonState();
            updateAsideList();
        });

        personalDetails.add(stepOne, header, firstNameTextField, lastNameTextField, genderSelect);
        return personalDetails;
    }

    private Component createGroupPreferencesSection() {
        Section paymentInfo = new Section();
        paymentInfo.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);

        Paragraph stepThree = new Paragraph("Abstimmen 2/3");
        stepThree.addClassNames(Margin.NONE, FontSize.SMALL, TextColor.SECONDARY);

        H3 header = new H3("Präferenzen der Gruppen");
        header.addClassNames(Margin.Bottom.MEDIUM, Margin.Top.SMALL, FontSize.XXLARGE);

        UUID someEventId = UUID.randomUUID();

        groupPreferencesView = new GroupPreferencesView(
                List.of(
                new GroupDTO(
                        UUID.randomUUID(),
                        "Gruppe A",
                        "Beschreibung",
                        15,
                        2,
                        10,
                        someEventId
                ),
                new GroupDTO(
                        UUID.randomUUID(),
                        "Gruppe B",
                        "Beschreibung",
                        15,
                        2,
                        10,
                        someEventId
                ),
                new GroupDTO(
                        UUID.randomUUID(),
                        "Gruppe C",
                        "Beschreibung",
                        15,
                        2,
                        10,
                        someEventId
                ),
                new GroupDTO(
                        UUID.randomUUID(),
                        "Gruppe D",
                        "Beschreibung",
                        15,
                        2,
                        10,
                        someEventId
                ),
                new GroupDTO(
                        UUID.randomUUID(),
                        "Gruppe E",
                        "Beschreibung",
                        15,
                        2,
                        10,
                        someEventId
                )),
                MIN_GROUP_PREFERENCES,
                MAX_GROUP_PREFERENCES
        );

        groupPreferencesView.addPreferenceChangeListener(e -> {
            updateAsideList();
            checkIfSubmitIsPossibleAndChangeButtonState();
        });

        paymentInfo.add(stepThree, header, groupPreferencesView);
        return paymentInfo;
    }

    private Section createCantGoWithEachOtherSection() {
        Section personalDetails = new Section();
        personalDetails.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);

        Paragraph stepOne = new Paragraph("Abstimmen 3/3");
        stepOne.addClassNames(Margin.NONE, FontSize.SMALL, TextColor.SECONDARY);

        H3 header = new H3("Mit wem möchten Sie nicht in einer Gruppe sein?");
        header.addClassNames(Margin.Bottom.MEDIUM, Margin.Top.SMALL, FontSize.XXLARGE);

        cantGoWithEachOtherTextArea.setPlaceholder("Geben Sie hier die Namen der Personen ein, mit denen Sie nicht in einer Gruppe sein möchten. (komma-getrennt)");
        cantGoWithEachOtherTextArea.setMaxLength(500);
        cantGoWithEachOtherTextArea.setMinHeight("200px");
        cantGoWithEachOtherTextArea.setWidthFull();


        personalDetails.add(stepOne, header, cantGoWithEachOtherTextArea);
        return personalDetails;
    }

    private Footer createFooter() {
        Footer footer = new Footer();
        footer.addClassNames(Display.FLEX, AlignItems.CENTER, JustifyContent.BETWEEN, Margin.Vertical.MEDIUM);

        submitButton.setWidthFull();
        submitButton.setEnabled(false);
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        footer.add(submitButton);
        return footer;
    }

    private Aside createAside() {
        Aside aside = new Aside();
        aside.addClassNames(Background.CONTRAST_5, BoxSizing.BORDER, Padding.LARGE, BorderRadius.LARGE,
                Position.STICKY);
        Header headerSection = new Header();
        headerSection.addClassNames(Display.FLEX, AlignItems.CENTER, JustifyContent.BETWEEN, Margin.Bottom.MEDIUM);
        H3 header = new H3("Zusammenfassung");
        header.addClassNames(Margin.NONE);
        headerSection.add(header);


        asideList.addClassNames(ListStyleType.NONE, Margin.NONE, Padding.NONE, Display.FLEX, FlexDirection.COLUMN, Gap.MEDIUM);

        updateAsideList();

        aside.add(headerSection, asideList);
        return aside;
    }

    private void updateAsideList() {
        asideList.removeAll();
        asideList.add(createListItem(!firstNameTextField.getValue().isBlank() ? firstNameTextField.getValue() : "Vornamen bitte eingeben", !lastNameTextField.getValue().isBlank() ? lastNameTextField.getValue() : "Nachnamen bitte eingeben", (!firstNameTextField.isEmpty() && !lastNameTextField.isEmpty()) ? new Icon(VaadinIcon.CHECK_CIRCLE) : new Icon(VaadinIcon.QUESTION_CIRCLE), (!firstNameTextField.isEmpty() && !lastNameTextField.isEmpty()) ? "green" : "orange"));

        asideList.add(createListItem(genderSelect.getValue() != null ? genderSelect.getValue().getDisplayName() : "Geschlecht bitte auswählen", "Geschlecht", genderSelect.getValue() != null ? new Icon(VaadinIcon.CHECK_CIRCLE) : new Icon(VaadinIcon.QUESTION_CIRCLE), genderSelect.getValue() != null ? "green" : "orange"));

        Map<GroupDTO, Integer> groupPreferences = groupPreferencesView.getPreferences();
        int preferenceCount = 1;
        for (Map.Entry<GroupDTO, Integer> entry : groupPreferences.entrySet().stream().sorted((a, b) ->{
            if(a.getValue() > b.getValue()) return 1;
            if(a.getValue() < b.getValue()) return -1;
            return 0;
        }).toList()) {
            while(entry.getValue() != preferenceCount) {
                asideList.add(createListItem("---", preferenceCount + ". Wahl", new Icon(VaadinIcon.QUESTION_CIRCLE), "orange"));
                preferenceCount++;
            }
            GroupDTO group = entry.getKey();
            Integer preference = entry.getValue();
            asideList.add(createListItem(group.getName(), preference + ". Wahl", new Icon(VaadinIcon.CHECK_CIRCLE), "green"));
            preferenceCount++;
        }

        if(preferenceCount <= MAX_GROUP_PREFERENCES) {
            for (int i = preferenceCount; i <= MIN_GROUP_PREFERENCES; i++) {
                asideList.add(createListItem("---", i + ". Wahl", new Icon(VaadinIcon.QUESTION_CIRCLE), "orange"));
            }
        }

        //asideList.add(createListItem("---", "3. Wahl", new Icon(VaadinIcon.QUESTION_CIRCLE), "orange"));
    }

    private ListItem createListItem(String primary, String secondary, Icon icon, String iconColor) {
        icon.setColor(iconColor);
        ListItem item = new ListItem();
        item.addClassNames(Display.FLEX, JustifyContent.BETWEEN);

        Div subSection = new Div();
        subSection.addClassNames(Display.FLEX, FlexDirection.COLUMN);

        subSection.add(new Span(primary));
        Span secondarySpan = new Span(secondary);
        secondarySpan.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subSection.add(secondarySpan);

        Span priceSpan = new Span(icon);

        item.add(subSection, priceSpan);
        return item;
    }

    private void checkIfSubmitIsPossibleAndChangeButtonState() {
        boolean hasNeededPreferences = true;
        for(int i = 1; i <= MIN_GROUP_PREFERENCES; i++) {
            if(!groupPreferencesView.getPreferences().containsValue(i)) {
                hasNeededPreferences = false;
                break;
            }
        }
        submitButton.setEnabled(
                        !firstNameTextField.isEmpty() &&
                        !lastNameTextField.isEmpty() &&
                        genderSelect.getValue() != null &&
                        hasNeededPreferences
        );
    }
}