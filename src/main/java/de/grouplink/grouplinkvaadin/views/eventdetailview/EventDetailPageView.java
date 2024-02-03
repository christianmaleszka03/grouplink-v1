package de.grouplink.grouplinkvaadin.views.eventdetailview;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.grouplink.grouplinkvaadin.data.dto.EventDTO;
import de.grouplink.grouplinkvaadin.data.dto.VoteDTO;
import de.grouplink.grouplinkvaadin.service.highlevel.OrganizerHighlevelService;
import de.grouplink.grouplinkvaadin.views.AppLayout;
import de.grouplink.grouplinkvaadin.views.CreateNewEventPageView;
import de.grouplink.grouplinkvaadin.views.myevents.components.EventCard;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.PageRequest;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@PageTitle("Event verwalten")
@Route(value = "event-verwalten", layout = AppLayout.class)
@PermitAll
public class EventDetailPageView extends Composite<VerticalLayout> implements HasUrlParameter<String> {
    private final OrganizerHighlevelService organizerHighlevelService;

    private EventDTO event;

    public EventDetailPageView(OrganizerHighlevelService organizerHighlevelService) {
        this.organizerHighlevelService = organizerHighlevelService;
        getContent().addClassNames("event-detail-page-view");
        getContent().removeClassName("max-w-screen-lg");
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
        try {
            Optional<EventDTO> eventOptional = organizerHighlevelService.getEventByIdForCurrentUser(UUID.fromString(parameter));
            if (eventOptional.isEmpty()) {
                constructNotAvailableView();
                return;
            }
            event = eventOptional.get();
            constructAvailableView();
        } catch(RuntimeException e) {
            e.printStackTrace();
            constructNotAvailableView();
        }
    }

    private void constructNotAvailableView() {
        H1 h1 = new H1();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setFlexGrow(1.0, h1);
        getContent().setAlignItems(FlexComponent.Alignment.CENTER);
        getContent().setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        h1.setText("Event nicht verfügbar");
        getContent().add(h1);
    }

    private void constructAvailableView() {
        constructHeaderSection();
        constructHighlightsSection();
        createDivider();
        constructGroupSection();
        createDivider();
        constructGridSection();
        getContent().removeClassName("max-w-screen-lg");
    }

    private void constructHeaderSection() {
        H1 nameHeading = new H1(event.getName());
        nameHeading.addClassNames("event-details-header-heading");
        HorizontalLayout headerSectionHL = new HorizontalLayout();
        headerSectionHL.add(nameHeading);
        headerSectionHL.setAlignItems(FlexComponent.Alignment.START);
        headerSectionHL.setJustifyContentMode(FlexComponent.JustifyContentMode.START);

        headerSectionHL.setWidthFull();
        getContent().setFlexGrow(1.0, headerSectionHL);
        headerSectionHL.setWidth("100%");
        headerSectionHL.getStyle().set("flex-grow", "1");
        nameHeading.setWidth("max-content");
        getContent().add(headerSectionHL);
    }

    private void constructHighlightsSection() {
        HorizontalLayout highlightsSectionHL = new HorizontalLayout();

        // DAYS REMAINING
        VerticalLayout daysRemainingVL = new VerticalLayout();
        H2 daysRemainingH2 = new H2(event.getEndTimestamp() != null ? "Noch 15 Tage" : "Kein Enddatum gesetzt");
        daysRemainingH2.setWidth("max-content");
        if(event.getEndTimestamp() != null) {
            long daysRemaining = Duration.between(ZonedDateTime.now(), event.getEndTimestamp()).toDays();
            long daysPassed = Duration.between(event.getStartTimestamp(), ZonedDateTime.now()).toDays();
            ProgressBar progressBar = new ProgressBar();
            progressBar.setValue((double) daysPassed / (daysPassed + daysRemaining));
            daysRemainingVL.add(daysRemainingH2, progressBar);
        } else {
            Button setEndDateButton = new Button("Jetzt festlegen");
            daysRemainingVL.add(daysRemainingH2, setEndDateButton);
        }

        // VOTES
        VerticalLayout votesVL = new VerticalLayout();
        H1 votesH1 = new H1("32");
        votesH1.setWidth("max-content");
        H2 votesH2 = new H2("abgegebene Stimmen");
        votesH2.setWidth("max-content");
        votesVL.add(votesH1, votesH2);

        // SHARE
        VerticalLayout shareVL = new VerticalLayout();
        H2 shareH2 = new H2("Abstimmung teilen");
        shareH2.setWidth("max-content");
        Button sendLinkButton = new Button("Link versenden");
        sendLinkButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button printFormButton = new Button("Formular ausdrucken");

        Button readFormButton = new Button("Formular einlesen");

        shareVL.add(shareH2, sendLinkButton, printFormButton, readFormButton);

        highlightsSectionHL.add(daysRemainingVL, votesVL, shareVL);
        highlightsSectionHL.setWidthFull();
        highlightsSectionHL.addClassName(LumoUtility.Gap.XLARGE);
        highlightsSectionHL.setWidth("100%");
        highlightsSectionHL.getStyle().set("flex-grow", "1");
        highlightsSectionHL.setAlignItems(FlexComponent.Alignment.CENTER);
        highlightsSectionHL.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        highlightsSectionHL.setFlexGrow(1.0, daysRemainingVL);
        highlightsSectionHL.setFlexGrow(1.0, votesVL);
        highlightsSectionHL.setFlexGrow(1.0, shareVL);
        getContent().add(highlightsSectionHL);
    }

    private void constructGroupSection() {
        VerticalLayout groupContainerVL = new VerticalLayout();
        OrderedList groupContainerOL = new OrderedList();
        addClassNames("image-gallery-view");
        addClassNames(LumoUtility.MaxWidth.SCREEN_LARGE, LumoUtility.Margin.Horizontal.AUTO, LumoUtility.Padding.Bottom.LARGE, LumoUtility.Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.START);
        container.setWidthFull();

        H2 heading = new H2("Verfügbare Gruppen");

        Button createEventButton = new Button("Neue Gruppe erstellen");
        createEventButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createEventButton.addClickListener(event -> UI.getCurrent().navigate(CreateNewEventPageView.class));

        groupContainerOL = new OrderedList();
        groupContainerOL.getStyle().set("width", "60%");
        groupContainerOL.addClassNames(LumoUtility.Gap.MEDIUM, LumoUtility.Display.GRID, LumoUtility.ListStyleType.NONE, LumoUtility.Margin.NONE, LumoUtility.Padding.NONE);

        container.add(heading, createEventButton);
        groupContainerVL.add(container, groupContainerOL);
        groupContainerVL.setWidthFull();

        groupContainerOL.add(new EventCard(event));
        groupContainerOL.add(new EventCard(event));
        groupContainerOL.add(new EventCard(event));
        groupContainerOL.add(new EventCard(event));
        groupContainerOL.add(new EventCard(event));
        groupContainerOL.add(new EventCard(event));
        groupContainerOL.add(new EventCard(event));
        groupContainerOL.add(new EventCard(event));
        groupContainerOL.add(new EventCard(event));
        groupContainerOL.add(new EventCard(event));
        groupContainerOL.add(new EventCard(event));


        getContent().add(groupContainerVL);
    }

    private void constructGridSection() {
        VerticalLayout gridSectionVL = new VerticalLayout();

        H2 votesH2 = new H2("Abgegebene Stimmen");
        votesH2.setWidth("max-content");
        votesH2.addClassName("event-details-grid-heading");

        Grid<VoteDTO> voteGrid = new Grid<>();
        voteGrid.addColumn(vote -> vote.getFirstName() + " " + vote.getLastName()).setHeader("Name");
        voteGrid.addColumn(vote -> vote.getCreatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))).setHeader("Abgegeben am");
        voteGrid.setItems(event.getVotes());

        gridSectionVL.add(votesH2, voteGrid);
        gridSectionVL.setWidthFull();
        gridSectionVL.getStyle().set("flex-grow", "1");
        gridSectionVL.setFlexGrow(1.0, voteGrid);
        getContent().add(gridSectionVL);
    }

    private void createDivider() {
        getContent().add(new Hr());
    }
}
