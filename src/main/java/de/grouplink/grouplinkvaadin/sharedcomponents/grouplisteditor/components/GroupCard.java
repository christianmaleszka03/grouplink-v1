package de.grouplink.grouplinkvaadin.sharedcomponents.grouplisteditor.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import de.grouplink.grouplinkvaadin.data.dto.EventDTO;
import de.grouplink.grouplinkvaadin.data.dto.GroupDTO;
import de.grouplink.grouplinkvaadin.service.highlevel.OrganizerHighlevelService;
import de.grouplink.grouplinkvaadin.sharedcomponents.GroupEditorView;

public class GroupCard extends ListItem {
    private final OrganizerHighlevelService organizerHighlevelService;

    public GroupCard(GroupDTO group, OrganizerHighlevelService organizerHighlevelService) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("160px");

        //StreamResource imageResource = new StreamResource("arbiana-logistic-logo.png",
        //        () -> getClass().getResourceAsStream("/images/undraw_calendar.png"));
        //
        //Image image = new Image(imageResource, "Illustration");
        //image.setWidth("100%");

        //div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(group.getName());

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        //subtitle.setText("Card subtitle");

        Paragraph description = new Paragraph(
                group.getDescription());
        description.addClassName(Margin.Vertical.MEDIUM);

        Span maxBadge = new Span();
        maxBadge.getElement().setAttribute("theme", "badge");
        maxBadge.setText("Max 5 Personen");
        maxBadge.getStyle().set("margin-bottom" , "5px");

        Span minBadge = new Span();
        minBadge.getElement().setAttribute("theme", "badge");
        minBadge.setText("Min 3 Personen");
        minBadge.getStyle().set("margin-bottom" , "5px");

        Span idealBadge = new Span();
        idealBadge.getElement().setAttribute("theme", "badge");
        idealBadge.setText("Ideal 4 Personen");
        idealBadge.getStyle().set("margin-bottom" , "5px");

        add(header, subtitle, description, maxBadge, minBadge, idealBadge);

        addClickListener(e -> openDialogForGroupEdit(group));
    }

    public void openDialogForGroupEdit(GroupDTO group) {
        Dialog dialog = new Dialog();
        dialog.setWidth("fit-content");
        dialog.setHeight("fit-content");

        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);

        dialog.setHeaderTitle(group.getName() + " bearbeiten");

        dialog.add(new GroupEditorView(
                group,
        (groupDTO) -> organizerHighlevelService.updateGroup(groupDTO)
                (groupDTO) -> dialog.close()
        ));
        dialog.open();
    }
}
