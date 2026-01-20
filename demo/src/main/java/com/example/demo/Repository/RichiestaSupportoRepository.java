package com.example.demo.Repository;

import com.example.demo.Model.RichiestaSupporto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RichiestaSupportoRepository extends JpaRepository<RichiestaSupporto,Long> {

    List<RichiestaSupporto> findByMentoreId(Long mentoreId);

    List<RichiestaSupporto> findByTeamId(Long teamId);

    List<RichiestaSupporto> findByTeam_Hackathon_id(Long hackathon_id);

}
