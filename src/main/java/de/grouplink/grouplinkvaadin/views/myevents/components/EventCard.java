package de.grouplink.grouplinkvaadin.views.myevents.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import de.grouplink.grouplinkvaadin.data.dto.EventDTO;
import de.grouplink.grouplinkvaadin.views.eventdetailview.EventDetailPageView;

public class EventCard extends ListItem {

    public EventCard(EventDTO event) {
        addClickListener(e -> UI.getCurrent().navigate("event-verwalten/" + event.getId()));
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("160px");

        StreamResource imageResource = new StreamResource("arbiana-logistic-logo.png",
                () -> getClass().getResourceAsStream("/images/undraw_calendar.png"));

        Image image = new Image(imageResource, "Illustration");
        image.setWidth("100%");

        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(event.getName());

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        //subtitle.setText("Card subtitle");

        Paragraph description = new Paragraph(
                event.getDescription());
        description.addClassName(Margin.Vertical.MEDIUM);

        //Span badge = new Span();
        //badge.getElement().setAttribute("theme", "badge error");
        //badge.setText("Noch 5 Tage");
        // TODO add badges

        add(div, header, subtitle, description);

    }
}
