package edu.mum.sonet.repositories;

import edu.mum.sonet.models.UnhealthyWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Jonathan on 12/12/2019.
 */

@Repository
public interface UnhealthyWordRepository extends JpaRepository<UnhealthyWord, Long> {
}
