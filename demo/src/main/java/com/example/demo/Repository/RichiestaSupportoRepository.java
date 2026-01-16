package com.example.demo.Repository;

import com.example.demo.Model.RichiestaSupporto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RichiestaSupportoRepository extends JpaRepository<RichiestaSupporto,Long> {

    List<RichiestaSupporto> findByMentoreId(Long mentoreId);

    List<RichiestaSupporto> findByTeamId(Long teamId);

}
