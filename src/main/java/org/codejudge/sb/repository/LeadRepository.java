package org.codejudge.sb.repository;

import java.util.Optional;

import org.codejudge.sb.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LeadRepository extends JpaRepository<Lead, String>{

	Optional<Lead> findByMobile(int mobile);
}
