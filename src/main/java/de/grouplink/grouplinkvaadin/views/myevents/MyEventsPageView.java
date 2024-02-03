package de.grouplink.grouplinkvaadin.views.myevents;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.grouplink.grouplinkvaadin.data.dto.EventDTO;
import de.grouplink.grouplinkvaadin.service.highlevel.OrganizerHighlevelService;
import de.grouplink.grouplinkvaadin.views.AppLayout;
import de.grouplink.grouplinkvaadin.views.CreateNewEventPageView;
import de.grouplink.grouplinkvaadin.views.defaults.dashboard.imagegallery.ImageGalleryViewCard;
import de.grouplink.grouplinkvaadin.views.myevents.components.EventCard;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Route(value = "meine-events", layout = AppLayout.class)
@PageTitle("Meine Events")
@PermitAll
@RouteAlias(value = "", layout = AppLayout.class)
public class MyEventsPageView extends Main implements HasComponents, HasStyle {
    private final OrganizerHighlevelService organizerHighlevelService;

    private OrderedList imageContainer;

    public MyEventsPageView(OrganizerHighlevelService organizerHighlevelService) {
        this.organizerHighlevelService = organizerHighlevelService;
        constructUI();

        List<EventDTO> allEventsOfUser = organizerHighlevelService.getAllEventsForCurrentUser();
        for (EventDTO event : allEventsOfUser) {
            imageContainer.add(new EventCard(event));
        }
    }

    private void constructUI() {
        addClassNames("image-gallery-view");
        addClassNames(LumoUtility.MaxWidth.SCREEN_LARGE, LumoUtility.Margin.Horizontal.AUTO, LumoUtility.Padding.Bottom.LARGE, LumoUtility.Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Meine Events");
        header.addClassNames(LumoUtility.Margin.Bottom.NONE, LumoUtility.Margin.Top.XLARGE, LumoUtility.FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Wähle ein bestehendes Event oder erstelle ein neues.");
        description.addClassNames(LumoUtility.Margin.Bottom.XLARGE, LumoUtility.Margin.Top.NONE, LumoUtility.TextColor.SECONDARY);
        headerContainer.add(header, description);

        Button createEventButton = new Button("Neues Event erstellen");
        createEventButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createEventButton.addClickListener(event -> UI.getCurrent().navigate(CreateNewEventPageView.class));

        imageContainer = new OrderedList();
        imageContainer.addClassNames(LumoUtility.Gap.MEDIUM, LumoUtility.Display.GRID, LumoUtility.ListStyleType.NONE, LumoUtility.Margin.NONE, LumoUtility.Padding.NONE);

        container.add(headerContainer, createEventButton);
        add(container, imageContainer);

    }
}
