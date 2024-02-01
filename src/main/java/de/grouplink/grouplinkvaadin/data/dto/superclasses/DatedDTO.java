package de.grouplink.grouplinkvaadin.data.dto.superclasses;

import java.time.ZonedDateTime;

public interface DatedDTO extends AbstractDTO {
    ZonedDateTime getCreatedAt();
}
