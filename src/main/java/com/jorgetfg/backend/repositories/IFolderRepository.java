package com.jorgetfg.backend.repositories;

import com.jorgetfg.backend.entities.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFolderRepository extends JpaRepository<Folder, Long> {
}
