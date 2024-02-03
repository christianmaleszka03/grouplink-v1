package de.grouplink.grouplinkvaadin.views;

import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.grouplink.grouplinkvaadin.service.highlevel.AuthenticationHighLevelService;
import de.grouplink.grouplinkvaadin.views.defaults.dashboard.feed.FeedView;
import de.grouplink.grouplinkvaadin.views.defaults.dashboard.imagegallery.ImageGalleryView;
import de.grouplink.grouplinkvaadin.views.myevents.MyEventsPageView;
import de.grouplink.grouplinkvaadin.views.register.RegisterPageView;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class AppLayout extends com.vaadin.flow.component.applayout.AppLayout {
    private final AuthenticationHighLevelService authenticationHighLevelService;

    private H2 viewTitle;

    public AppLayout(AuthenticationHighLevelService authenticationHighLevelService) {
        this.authenticationHighLevelService = authenticationHighLevelService;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        Button logoutButton = new Button("Ausloggen", click ->
                authenticationHighLevelService.manualLogout());

        logoutButton.addClassName("logout-button");
        logoutButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addToNavbar(true, toggle, viewTitle, logoutButton);
    }

    private void addDrawerContent() {
        StreamResource imageResource = new StreamResource("arbiana-logistic-logo.png",
                () -> getClass().getResourceAsStream("/images/grouplink_logo_transparent.png"));
        Image img = new Image(imageResource, "GroupLink Logo");
        img.addClassName("nav-bar-logo");

        Header header = new Header(img);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        //nav.addItem(new SideNavItem("Dashboard", DashboardView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
        //nav.addItem(new SideNavItem("Register", RegisterPageView.class, LineAwesomeIcon.USER.create()));
        //nav.addItem(new SideNavItem("Master-Detail", MasterDetailView.class, LineAwesomeIcon.COLUMNS_SOLID.create()));
        //nav.addItem(new SideNavItem("Image Gallery", ImageGalleryView.class, LineAwesomeIcon.TH_LIST_SOLID.create()));
        //nav.addItem(new SideNavItem("Feed", FeedView.class, LineAwesomeIcon.LIST_SOLID.create()));
        nav.addItem(new SideNavItem("Meine Events", MyEventsPageView.class, LineAwesomeIcon.TICKET_ALT_SOLID.create()));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
