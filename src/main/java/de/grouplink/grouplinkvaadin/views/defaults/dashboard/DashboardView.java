//package de.grouplink.grouplinkvaadin.views.defaults.dashboard;
//
//import com.vaadin.flow.component.Composite;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.component.dependency.Uses;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.html.H1;
//import com.vaadin.flow.component.html.H2;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.orderedlayout.FlexComponent;
//import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
//import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.progressbar.ProgressBar;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
//import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
//import de.grouplink.grouplinkvaadin.views.AppLayout;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//
//@PageTitle("Dashboard")
//@Route(value = "dashboard", layout = AppLayout.class)
//@Uses(Icon.class)
//public class DashboardView extends Composite<VerticalLayout> {
//
//    public DashboardView() {
//        VerticalLayout layoutColumn2 = new VerticalLayout();
//        H1 h1 = new H1();
//        HorizontalLayout layoutRow = new HorizontalLayout();
//        VerticalLayout layoutColumn3 = new VerticalLayout();
//        H2 h2 = new H2();
//        ProgressBar progressBar = new ProgressBar();
//        VerticalLayout layoutColumn4 = new VerticalLayout();
//        H1 h12 = new H1();
//        H2 h22 = new H2();
//        VerticalLayout layoutColumn5 = new VerticalLayout();
//        H2 h23 = new H2();
//        Button buttonPrimary = new Button();
//        Button buttonSecondary = new Button();
//        Button buttonSecondary2 = new Button();
//        VerticalLayout layoutColumn6 = new VerticalLayout();
//        H2 h24 = new H2();
//        Grid basicGrid = new Grid(SamplePerson.class);
//        getContent().setWidth("100%");
//        getContent().getStyle().set("flex-grow", "1");
//        layoutColumn2.setWidthFull();
//        getContent().setFlexGrow(1.0, layoutColumn2);
//        layoutColumn2.setWidth("100%");
//        layoutColumn2.getStyle().set("flex-grow", "1");
//        h1.setText("Eddys Gruppenwahl 2024");
//        h1.setWidth("max-content");
//        layoutRow.setWidthFull();
//        layoutColumn2.setFlexGrow(1.0, layoutRow);
//        layoutRow.addClassName(Gap.XLARGE);
//        layoutRow.setWidth("100%");
//        layoutRow.getStyle().set("flex-grow", "1");
//        layoutRow.setAlignItems(Alignment.CENTER);
//        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);
//        layoutColumn3.setHeightFull();
//        layoutRow.setFlexGrow(1.0, layoutColumn3);
//        layoutColumn3.setWidth("401px");
//        layoutColumn3.getStyle().set("flex-grow", "1");
//        layoutColumn3.setJustifyContentMode(JustifyContentMode.CENTER);
//        layoutColumn3.setAlignItems(Alignment.START);
//        h2.setText("Noch 15 Tage");
//        h2.setWidth("max-content");
//        progressBar.setValue(0.5);
//        layoutColumn4.setHeightFull();
//        layoutRow.setFlexGrow(1.0, layoutColumn4);
//        layoutColumn4.setWidth("358px");
//        layoutColumn4.getStyle().set("flex-grow", "1");
//        layoutColumn4.setJustifyContentMode(JustifyContentMode.CENTER);
//        layoutColumn4.setAlignItems(Alignment.START);
//        h12.setText("32");
//        h12.setWidth("max-content");
//        h22.setText("abgegebene Stimmen");
//        h22.setWidth("max-content");
//        layoutColumn5.setHeightFull();
//        layoutRow.setFlexGrow(1.0, layoutColumn5);
//        layoutColumn5.setWidth("357px");
//        layoutColumn5.setHeight("100%");
//        layoutColumn5.setJustifyContentMode(JustifyContentMode.CENTER);
//        layoutColumn5.setAlignItems(Alignment.START);
//        h23.setText("Abstimmung teilen");
//        h23.setWidth("max-content");
//        buttonPrimary.setText("Link versenden");
//        layoutColumn5.setAlignSelf(FlexComponent.Alignment.START, buttonPrimary);
//        buttonPrimary.setWidth("min-content");
//        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        buttonSecondary.setText("Formular ausdrucken");
//        layoutColumn5.setAlignSelf(FlexComponent.Alignment.START, buttonSecondary);
//        buttonSecondary.setWidth("min-content");
//        buttonSecondary2.setText("Formular einlesen");
//        layoutColumn5.setAlignSelf(FlexComponent.Alignment.START, buttonSecondary2);
//        buttonSecondary2.setWidth("min-content");
//        layoutColumn6.setWidthFull();
//        getContent().setFlexGrow(1.0, layoutColumn6);
//        layoutColumn6.setWidth("100%");
//        layoutColumn6.getStyle().set("flex-grow", "1");
//        h24.setText("Abgegebene Stimmen");
//        h24.setWidth("max-content");
//        basicGrid.setWidth("100%");
//        basicGrid.getStyle().set("flex-grow", "0");
//        setGridSampleData(basicGrid);
//        getContent().add(layoutColumn2);
//        layoutColumn2.add(h1);
//        layoutColumn2.add(layoutRow);
//        layoutRow.add(layoutColumn3);
//        layoutColumn3.add(h2);
//        layoutColumn3.add(progressBar);
//        layoutRow.add(layoutColumn4);
//        layoutColumn4.add(h12);
//        layoutColumn4.add(h22);
//        layoutRow.add(layoutColumn5);
//        layoutColumn5.add(h23);
//        layoutColumn5.add(buttonPrimary);
//        layoutColumn5.add(buttonSecondary);
//        layoutColumn5.add(buttonSecondary2);
//        getContent().add(layoutColumn6);
//        layoutColumn6.add(h24);
//        layoutColumn6.add(basicGrid);
//    }
//
//    private void setGridSampleData(Grid grid) {
//        grid.setItems(query -> samplePersonService.list(
//                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
//                .stream());
//    }
//
//    @Autowired()
//    private SamplePersonService samplePersonService;
//}
