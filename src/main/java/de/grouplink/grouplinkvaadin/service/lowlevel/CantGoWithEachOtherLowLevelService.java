package de.grouplink.grouplinkvaadin.service.lowlevel;

import de.grouplink.grouplinkvaadin.data.dto.CantGoWithEachOtherDTO;
import de.grouplink.grouplinkvaadin.data.entity.CantGoWithEachOtherEntity;
import de.grouplink.grouplinkvaadin.data.repository.CantGoWithEachOtherRepository;
import de.grouplink.grouplinkvaadin.data.repository.VoteRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class CantGoWithEachOtherLowLevelService {
    private final CantGoWithEachOtherRepository cantGoWithEachOtherRepository;

    // JUST FOR RETRIEVING ENTITY FROM ID - DO NOT USE FOR ANYTHING ELSE
    private final VoteRepository voteRepository;

    public CantGoWithEachOtherLowLevelService(
            CantGoWithEachOtherRepository cantGoWithEachOtherRepository,
            VoteRepository voteRepository
    ) {
        this.cantGoWithEachOtherRepository = cantGoWithEachOtherRepository;
        this.voteRepository = voteRepository;
    }

    // CREATE
    public CantGoWithEachOtherDTO createNewCantGoWithEachOther(
            @NotNull UUID issuingVoteId,
            @NotBlank String text
    ) {
        CantGoWithEachOtherEntity entityToSave = new CantGoWithEachOtherEntity();

        entityToSave.setIssuingVote(voteRepository.findById(issuingVoteId).orElseThrow());
        entityToSave.setText(text);
        entityToSave.setVotePointedTo(null);

        return new CantGoWithEachOtherDTO(cantGoWithEachOtherRepository.save(entityToSave));
    }

    // READ
    public Optional<CantGoWithEachOtherDTO> getById(@NotNull UUID id) {
        return cantGoWithEachOtherRepository.findById(id).map(CantGoWithEachOtherDTO::new);
    }

    // UPDATE
    public CantGoWithEachOtherDTO updateIssuingVoteForId(@NotNull UUID id, @NotNull UUID issuingVoteId) {
        CantGoWithEachOtherEntity entityToUpdate = cantGoWithEachOtherRepository.findById(id).orElseThrow();
        entityToUpdate.setIssuingVote(voteRepository.findById(issuingVoteId).orElseThrow());
        return new CantGoWithEachOtherDTO(cantGoWithEachOtherRepository.save(entityToUpdate));
    }

    public CantGoWithEachOtherDTO updateTextForId(@NotNull UUID id, @NotBlank String text) {
        CantGoWithEachOtherEntity entityToUpdate = cantGoWithEachOtherRepository.findById(id).orElseThrow();
        entityToUpdate.setText(text);
        return new CantGoWithEachOtherDTO(cantGoWithEachOtherRepository.save(entityToUpdate));
    }

    public CantGoWithEachOtherDTO updateVotePointedToForId(@NotNull UUID id, UUID votePointedToId) {
        CantGoWithEachOtherEntity entityToUpdate = cantGoWithEachOtherRepository.findById(id).orElseThrow();
        entityToUpdate.setVotePointedTo(voteRepository.findById(votePointedToId).orElse(null));
        return new CantGoWithEachOtherDTO(cantGoWithEachOtherRepository.save(entityToUpdate));
    }

    // DELETE
    // NOT INTENDED YET
}
