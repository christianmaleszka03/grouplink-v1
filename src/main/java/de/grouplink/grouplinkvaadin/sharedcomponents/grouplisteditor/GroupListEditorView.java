package de.grouplink.grouplinkvaadin.sharedcomponents.grouplisteditor;

import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.grouplink.grouplinkvaadin.data.dto.GroupDTO;
import de.grouplink.grouplinkvaadin.sharedcomponents.grouplisteditor.components.GroupCard;
import de.grouplink.grouplinkvaadin.views.myevents.components.EventCard;

import java.util.List;
import java.util.UUID;

public class GroupListEditorView extends HorizontalLayout {
    private List<GroupDTO> groups;
    public GroupListEditorView(List<GroupDTO> groups) {
        this.groups = groups;
        setWidthFull();

        OrderedList groupContainerOL = new OrderedList();
        groupContainerOL.getStyle().set("width", "100%");
        groupContainerOL.addClassNames(LumoUtility.Gap.MEDIUM, LumoUtility.Display.GRID, LumoUtility.ListStyleType.NONE, LumoUtility.Margin.NONE, LumoUtility.Padding.NONE);

        for(int i = 0; i < 13; i++) {
            groupContainerOL.add(new GroupCard(new GroupDTO(
                    UUID.randomUUID(),
                    "Gruppenname",
                    "Beschreibung",
                    15,
                    5,
                    10,
                    null
            )));
        }

        add(groupContainerOL);
    }
}
