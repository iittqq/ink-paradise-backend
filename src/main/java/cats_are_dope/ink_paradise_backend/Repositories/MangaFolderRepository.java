package cats_are_dope.ink_paradise_backend.Repositories;

import cats_are_dope.ink_paradise_backend.Models.MangaFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaFolderRepository extends JpaRepository<MangaFolder, Long> {}
