package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "STREAM", schema="APP")
public class Stream {

    @Id
    @SequenceGenerator(name="STREAM_STREAM_ID_GENERATOR", sequenceName="STREAM_SEQ", schema = "APP", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STREAM_STREAM_ID_GENERATOR")
    @Column(name = "STREAM_ID")
    private long streamId;

    @Column(name = "STREAM_NAME")
    private String name;

    @Column(name = "STREAM_CODE")
    private String code;

    @OneToMany
    @JoinColumn(name = "STREAM_ID", referencedColumnName = "STREAM_ID", updatable = false, insertable = false)
    private List<Subject> subjects;

}
