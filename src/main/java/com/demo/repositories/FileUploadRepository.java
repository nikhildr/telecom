package com.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.demo.entity.FileData;


@Component
public interface FileUploadRepository extends CrudRepository<FileData, Long> {

}
