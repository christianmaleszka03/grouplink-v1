package de.grouplink.grouplinkvaadin.views.vote.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.grouplink.grouplinkvaadin.data.dto.GroupDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GroupPreferencesView extends VerticalLayout {
    private final List<GroupDTO> availableGroups;
    private final int minPreferences;
    private final int maxPreferences;

    private List<Consumer<Map<GroupDTO, Integer>>> preferenceChangeListeners = new ArrayList<>();

    // STATE
    private boolean currentlyUpdatingComboboxes = false;

    private List<ComboBox<GroupDTO>> preferenceComboBoxes = new ArrayList<>();

    private VerticalLayout preferencesLayout = new VerticalLayout();

    public GroupPreferencesView(List<GroupDTO> availableGroups, int minPreferences, int maxPreferences) {
        this.availableGroups = availableGroups;
        this.minPreferences = minPreferences;
        this.maxPreferences = maxPreferences;

        setWidthFull();
        addClassNames("group-preferences-view");

        for(int i = 0; i < minPreferences; i++) {
            addCombobox(true);
        }

        preferencesLayout.setWidthFull();
        preferencesLayout.addClassNames("group-preferences-layout");
        add(preferencesLayout);
    }

    private void updateComboboxes() {
        currentlyUpdatingComboboxes = true;
        preferencesLayout.removeAll();
        boolean hasEmptyCombobox = false;
        for(ComboBox<GroupDTO> comboBox : preferenceComboBoxes) {
            if(comboBox.getValue() == null) {
                hasEmptyCombobox = true;
                break;
            }
        }

        if(!hasEmptyCombobox && preferenceComboBoxes.size() < maxPreferences) {
            addCombobox(false);
        }

        List<GroupDTO> selectedGroups = new ArrayList<>();
        for(ComboBox<GroupDTO> comboBox : preferenceComboBoxes) {
            GroupDTO selectedGroup = comboBox.getValue();
            if(selectedGroup != null) {
                selectedGroups.add(selectedGroup);
            }
        }

        for(ComboBox<GroupDTO> comboBox : preferenceComboBoxes) {
            GroupDTO valueBefore = comboBox.getValue();
            List<GroupDTO> newItemList = availableGroups.stream()
                    .filter(group -> !selectedGroups.contains(group) || group.equals(comboBox.getValue()))
                    .toList();
            comboBox.setItems(newItemList);
            if(valueBefore != null) {
                comboBox.setValue(valueBefore);
            }
        }

        preferenceComboBoxes.forEach(preferencesLayout::add);
        currentlyUpdatingComboboxes = false;
        firePreferenceChangeListeners();
    }

    private void addCombobox(boolean required) {
        System.out.println("addCombobox");
        ComboBox<GroupDTO> newComboBox = new ComboBox<>((preferenceComboBoxes.size() + 1) + ". Präferenz (optional)");
        newComboBox.addClassNames(LumoUtility.Margin.Bottom.SMALL);
        newComboBox.setItems(availableGroups);
        newComboBox.setClearButtonVisible(true);
        newComboBox.setItemLabelGenerator(GroupDTO::getName);
        newComboBox.setAllowCustomValue(false);
        newComboBox.setPlaceholder("Gruppe auswählen");
        newComboBox.addClassNames("group-preference-combobox");
        if(required) {
            newComboBox.setLabel((preferenceComboBoxes.size() + 1) + ". Präferenz*");
        }
        newComboBox.addValueChangeListener(event -> {
            if(currentlyUpdatingComboboxes) {
                return;
            }
            updateComboboxes();
        });
        preferenceComboBoxes.add(newComboBox);
        updateComboboxes();
    }

    public Map<GroupDTO, Integer> getPreferences() {
        Map<GroupDTO, Integer> resultMap = new HashMap<>();
        for(int i = 0; i < preferenceComboBoxes.size(); i++) {
            ComboBox<GroupDTO> comboBox = preferenceComboBoxes.get(i);
            GroupDTO selectedGroup = comboBox.getValue();
            if(selectedGroup != null) {
                resultMap.put(selectedGroup, i + 1);
            }
        }
        return resultMap;
    }

    public void addPreferenceChangeListener(Consumer<Map<GroupDTO, Integer>> listener) {
        preferenceChangeListeners.add(listener);
    }

    private void firePreferenceChangeListeners() {
        Map<GroupDTO, Integer> preferences = getPreferences();
        for(Consumer<Map<GroupDTO, Integer>> listener : preferenceChangeListeners) {
            listener.accept(preferences);
        }
    }
}
