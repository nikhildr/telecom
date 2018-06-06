package com.config.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.config.entity.FileData;


@Component
public interface FileRepository extends CrudRepository<FileData, Long> {

}
