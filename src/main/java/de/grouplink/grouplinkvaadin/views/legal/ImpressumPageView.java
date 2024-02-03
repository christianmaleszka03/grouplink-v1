package de.grouplink.grouplinkvaadin.views.legal;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import de.grouplink.grouplinkvaadin.views.FakeAppLayout;

@AnonymousAllowed
@Route(value = "impressum", layout = FakeAppLayout.class)
public class ImpressumPageView extends VerticalLayout {

        public ImpressumPageView() {
            addClassName("custom-impressum-page-view");
            setHeightFull();
            setWidthFull();
            setAlignItems(Alignment.CENTER);
            setJustifyContentMode(JustifyContentMode.CENTER);
            add("Impressum");
        }
}
