package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.interfaces.SoftDeletable;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class CommonEntity implements SoftDeletable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // activeFrom

    @Column(nullable = false)
    private LocalDateTime lastModifiedAt;

    @Setter
    @Column(nullable = false)
    private Boolean isDeleted;

    public CommonEntity() {
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
        this.isDeleted = false;
    }

    @PrePersist // * Called once before the entity is first saved (INSERT)
    public void prePersist() {
        this.lastModifiedAt = LocalDateTime.now();
    }

    @PreUpdate // * Called every time the entity is updated (UPDATE)
    public void preUpdate() {
        this.lastModifiedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommonEntity that = (CommonEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

// TODO: Even when creating a composite key of the index column + isDeleted, if you delete an entry, then recreate it and you try to delete it also
// TODO: what happens is that you will break the uniqueness, because you already have that entry deleted. This was solved the following way in Santa:
// ! Basically when you create a new entry (with the same value as the previously deleted one),
// ! you override the (previously deleted one) by assigning its id to the new entry.
/*
rule UniqueCommonRecordUndelete
	salience -999
when
	$new: CommonRecord(id == null)
	$old: CommonRecord() from droolsRuleProcedures.checkForUniqueCommonRecord($new, true)
then
	$new.setId($old.getId());
	warnings.add("Undeleting "+$new.getClass().getSimpleName()+" with id: "+$old.getId(), false);
end
*/
