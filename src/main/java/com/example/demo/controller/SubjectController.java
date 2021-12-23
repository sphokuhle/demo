package com.example.demo.controller;

import com.example.demo.entity.Stream;
import com.example.demo.entity.Subject;
import com.example.demo.repository.StreamRepository;
import com.example.demo.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subject/")
public class SubjectController {

   @Autowired
   SubjectRepository subjectRepository;

   @Autowired
   StreamRepository streamRepository;

   @GetMapping("all")
   @RolesAllowed({"teacher_authorization"})
   public Iterable<Subject> getAllSubjects() {
       return subjectRepository.findAll(Sort.by("streamId"));
   }

   @GetMapping("findByStream/{streamId}")
   @PreAuthorize("hasAnyAuthority('student_authorization', 'teacher_authorization')")
   public List<Subject> getSubjectsByStream(@PathVariable("streamId") long streamId) {
      Optional<Stream> stream = streamRepository.findById(streamId);
      if(stream != null && stream.get() != null)
         return stream.get().getSubjects();
      return Collections.emptyList();
   }

}
