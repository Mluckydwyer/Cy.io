package com.cyio.backend.repository;

import com.cyio.backend.model.ThumbnailFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbnailRepository extends JpaRepository<ThumbnailFile, String> {

}
