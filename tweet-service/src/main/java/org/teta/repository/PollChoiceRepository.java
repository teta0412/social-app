package org.teta.repository;

import org.teta.model.PollChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollChoiceRepository extends JpaRepository<PollChoice, Long> {
}
